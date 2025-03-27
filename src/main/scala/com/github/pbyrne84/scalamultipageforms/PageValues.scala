package com.github.pbyrne84.scalamultipageforms

import cats.syntax.functor._
import io.circe.{Decoder, Encoder, HCursor, Json}

object PageValues {
  implicit val pageValuesEncoder: Encoder[PageValues] = new Encoder[PageValues] {
    override def apply(a: PageValues): Json = a.toJson
  }

  implicit val pageValuesDecoder: Decoder[PageValues] =
    List[Decoder[PageValues]](
      Decoder[StartPageValues].widen,
      Decoder[SecondPageAValues].widen,
      Decoder[SecondPageBValues].widen,
      Decoder[ThirdPageAValues].widen
    ).reduceLeft(_ or _)

}

sealed trait PageValues {
  val page: Page

  def toJson: Json
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

object StartPageValues
    extends JsonPageValues[StartPageValues](
      discriminator = "startPage",
      encodeValueCall = (startPageValues: StartPageValues) => Json.fromInt(startPageValues.value),
      decodeValueCall = (hCursor: HCursor) => hCursor.get[Int]("value").map(value => new StartPageValues(value))
    ) {
  private val validOptions = List(1, 2, 3)

  def validated(value: Int): Either[String, StartPageValues] = {
    if (validOptions.contains(value)) {
      Right(StartPageValues(value))
    } else {
      Left(s"Invalid StartPageValues value $value, valid values ${validOptions.mkString(", ")}")
    }
  }

}

case class StartPageValues(value: Int) extends IntValue {
  override val page: Page = StartPage
  override def toJson: Json = StartPageValues.encodeStartPageValues(this)
}

case object SecondPageAValues
    extends JsonPageValues[SecondPageAValues](
      discriminator = "secondPageA",
      encodeValueCall = (secondPageAValues: SecondPageAValues) => Json.fromString(secondPageAValues.value),
      decodeValueCall = (hCursor: HCursor) => hCursor.get[String]("value").map(value => new SecondPageAValues(value))
    ) {}

case class SecondPageAValues(value: String) extends StringValue {
  override val page: Page = SecondPageA

  override def toJson: Json = SecondPageAValues.encodeStartPageValues(this)

}

case object SecondPageBValues
    extends JsonPageValues[SecondPageBValues](
      discriminator = "secondPageB",
      encodeValueCall = (secondPageAValues: SecondPageBValues) => Json.fromString(secondPageAValues.value),
      decodeValueCall = (hCursor: HCursor) => hCursor.get[String]("value").map(value => new SecondPageBValues(value))
    ) {}

case class SecondPageBValues(value: String) extends StringValue {
  override val page: Page = SecondPageB
  override def toJson: Json = SecondPageBValues.encodeStartPageValues(this)
}

case object ThirdPageAValues
    extends JsonPageValues[ThirdPageAValues](
      discriminator = "thirdPageA",
      encodeValueCall = (thirdPageAValues: ThirdPageAValues) =>
        Json.fromValues(thirdPageAValues.values.map(Json.fromString)),
      decodeValueCall = (hCursor: HCursor) =>
        hCursor.get[List[String]]("value").map(value => new ThirdPageAValues(value))
    ) {}

case class ThirdPageAValues(values: List[String]) extends MultiStringStringValue {
  override val page: Page = ThirdPageA

  override def toJson: Json = ThirdPageAValues.encodeStartPageValues(this)
}
