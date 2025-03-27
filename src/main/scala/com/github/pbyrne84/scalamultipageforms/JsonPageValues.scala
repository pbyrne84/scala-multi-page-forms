package com.github.pbyrne84.scalamultipageforms

import com.github.pbyrne84.scalamultipageforms.StartPageValues.{createSingleValueDecoder, createSingleValueEncoder}
import io.circe.Decoder.Result
import io.circe.{Decoder, DecodingFailure, Encoder, HCursor, Json, JsonObject}

abstract class JsonPageValues[A <: PageValues](
    discriminator: String,
    encodeValueCall: (A) => Json,
    decodeValueCall: (HCursor) => Decoder.Result[A]
) {

  protected def createSingleValueEncoder: Encoder[A] =
    new Encoder[A] {
      override def apply(startPageValues: A): Json = JsonObject(
        "type" -> Json.fromString(discriminator),
        "value" -> encodeValueCall(startPageValues)
      ).toJson
    }

  protected def createSingleValueDecoder: Decoder[A] =
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

  implicit val encodeStartPageValues: Encoder[A] =
    createSingleValueEncoder

  implicit val decodeStartPageValues: Decoder[A] =
    createSingleValueDecoder
}
