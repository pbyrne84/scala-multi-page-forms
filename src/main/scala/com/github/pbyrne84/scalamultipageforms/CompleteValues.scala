package com.github.pbyrne84.scalamultipageforms

import cats.data.NonEmptyList

object CompleteValues {
  import io.circe._
  import io.circe.generic._

  implicit val completeValuesEncoder: Encoder.AsObject[CompleteValues] = semiauto.deriveEncoder[CompleteValues]
  implicit val completeValuesDecoder: Decoder[CompleteValues] = semiauto.deriveDecoder[CompleteValues]
}

case class CompleteValues(private val answers: Seq[PageValues]) {

  val maybeAnsweredPageValues: Option[NonEmptyList[PageValues]] =
    answers.headOption.map(head => NonEmptyList(head, answers.tail.toList))

  val completedPages: Seq[Page] = answers.map(_.page)
  val startPageValues: Option[StartPageValues] = answers.collectFirst { case value: StartPageValues => value }
  val secondPageAValues: Option[SecondPageAValues] = answers.collectFirst { case value: SecondPageAValues => value }
  val secondPageBValues: Option[SecondPageBValues] = answers.collectFirst { case value: SecondPageBValues => value }
  val thirdPageAValues: Option[ThirdPageAValues] = answers.collectFirst { case value: ThirdPageAValues => value }
}
