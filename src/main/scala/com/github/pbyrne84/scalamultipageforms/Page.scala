package com.github.pbyrne84.scalamultipageforms

import io.circe.Decoder.Result
import io.circe.{Decoder, DecodingFailure, Encoder, FailedCursor, HCursor, Json, JsonObject}

sealed trait Page
case object StartPage extends Page
case object SecondPageA extends Page
case object SecondPageB extends Page
case object ThirdPageA extends Page
case object ThirdPageB extends Page
case object FourthPageA extends Page
case object FourthPageB extends Page

case object Finished extends Page

sealed trait PageValues {
  val page: Page
}

sealed trait IntValue extends PageValues {
  val value: Int
}

sealed trait StringValue extends PageValues {
  val value: String
}

sealed trait MultiStringStringValue extends PageValues {
  val values: List[String]
}

object StartPageValues {
  private val validOptions = List(1, 2, 3)
  private val discriminator = "startPage"

  def validated(value: Int): Either[String, StartPageValues] = {
    if (validOptions.contains(value)) {
      Right(StartPageValues(value))
    } else {
      Left(s"Invalid StartPageValues value $value, valid values ${validOptions.mkString(", ")}")
    }
  }

  implicit val encodeStartPageValues: Encoder[StartPageValues] = new Encoder[StartPageValues] {
    override def apply(startPageValues: StartPageValues): Json = JsonObject(
      "type" -> Json.fromString(discriminator),
      "value" -> Json.fromInt(startPageValues.value)
    ).toJson
  }

  implicit val decodeStartPageValues: Decoder[StartPageValues] = new Decoder[StartPageValues] {
    override def apply(c: HCursor): Result[StartPageValues] = {
      c.get[String]("type") match {
        case Left(value) => Left(value)
        case Right(value) =>
          if (value == discriminator) {
            c.get[Int]("value").map(value => StartPageValues(value))
          } else {
            Left(DecodingFailure("", List.empty))
          }
      }
    }
  }
}

case class StartPageValues(value: Int) extends IntValue {
  override val page: Page = StartPage
}

case class SecondPageAValues(value: String) extends StringValue {
  override val page: Page = SecondPageA
}

case class SecondPageBValues(value: String) extends StringValue {
  override val page: Page = SecondPageB
}

case class ThirdPageAValues(values: List[String]) extends MultiStringStringValue {
  override val page: Page = ThirdPageA
}
