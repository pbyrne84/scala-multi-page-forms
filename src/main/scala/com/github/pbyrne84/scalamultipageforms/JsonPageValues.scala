package com.github.pbyrne84.scalamultipageforms

import io.circe.Decoder.Result
import io.circe.{Decoder, DecodingFailure, Encoder, HCursor, Json, JsonObject}

trait JsonPageValues {
  protected val discriminator: String

  protected def createSingleValueEncoder[A <: PageValues](encodeValueCall: (A) => Json): Encoder[A] =
    new Encoder[A] {
      override def apply(startPageValues: A): Json = JsonObject(
        "type" -> Json.fromString(discriminator),
        "value" -> encodeValueCall(startPageValues)
      ).toJson
    }

  protected def createSingleValueDecoder[A <: PageValues](decodeValueCall: (HCursor) => Decoder.Result[A]): Decoder[A] =
    new Decoder[A] {
      override def apply(c: HCursor): Result[A] = {
        c.get[String]("type") match {
          case Left(decodingFailure) => Left(decodingFailure)
          case Right(foundDiscriminator) =>
            if (foundDiscriminator == discriminator) {
              decodeValueCall(c)
            } else {
              Left(
                DecodingFailure(
                  s"Unexpected discriminator, expected $discriminator, got $foundDiscriminator",
                  List.empty
                )
              )
            }
        }
      }
    }
}
