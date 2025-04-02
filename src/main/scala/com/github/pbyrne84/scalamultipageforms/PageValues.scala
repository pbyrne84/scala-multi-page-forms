package com.github.pbyrne84.scalamultipageforms

import io.circe.{Decoder, Encoder, HCursor, Json}

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

object StartPageValues extends JsonPageValues {
  private val validOptions = List(1, 2, 3)
  protected val discriminator = "startPage"

  def validated(value: Int): Either[String, StartPageValues] = {
    if (validOptions.contains(value)) {
      Right(StartPageValues(value))
    } else {
      Left(s"Invalid StartPageValues value $value, valid values ${validOptions.mkString(", ")}")
    }
  }

  implicit val encodeStartPageValues: Encoder[StartPageValues] =
    createSingleValueEncoder((startPageValues: StartPageValues) => Json.fromInt(startPageValues.value))

  implicit val decodeStartPageValues: Decoder[StartPageValues] =
    createSingleValueDecoder { (hCursor: HCursor) =>
      hCursor.get[Int]("value").map(value => StartPageValues(value))
    }
}

case class StartPageValues(value: Int) extends IntValue {
  override val page: Page = StartPage
}

case object SecondPageAValues extends JsonPageValues {
  override protected val discriminator: String = "secondPageA"

  implicit val encodeStartPageValues: Encoder[SecondPageAValues] =
    createSingleValueEncoder((secondPageAValues: SecondPageAValues) => Json.fromString(secondPageAValues.value))

  implicit val decodeStartPageValues: Decoder[SecondPageAValues] =
    createSingleValueDecoder { (hCursor: HCursor) =>
      hCursor.get[String]("value").map(value => SecondPageAValues(value))
    }

}

case class SecondPageAValues(value: String) extends StringValue {
  override val page: Page = SecondPageA
}

case class SecondPageBValues(value: String) extends StringValue {
  override val page: Page = SecondPageB
}

case object ThirdPageAValues extends JsonPageValues {
  override protected val discriminator: String = "thirdPageA"

  implicit val encodeStartPageValues: Encoder[ThirdPageAValues] =
    createSingleValueEncoder((thirdPageAValues: ThirdPageAValues) =>
      Json.fromValues(thirdPageAValues.values.map(Json.fromString))
    )

  implicit val decodeStartPageValues: Decoder[ThirdPageAValues] =
    createSingleValueDecoder { (hCursor: HCursor) =>
      hCursor.get[List[String]]("value").map(value => ThirdPageAValues(value))
    }
}

case class ThirdPageAValues(values: List[String]) extends MultiStringStringValue {
  override val page: Page = ThirdPageA
}
