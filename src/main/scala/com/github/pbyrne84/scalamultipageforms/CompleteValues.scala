package com.github.pbyrne84.scalamultipageforms

object CompleteValues {}

case class CompleteValues(answers: List[PageValues]) {

  val maybeJourneyWithLastValue: Option[(List[Page], PageValues)] = {
    answers.lastOption match {
      case Some(value) => Some(answers.map(_.page), value)
      case None => None
    }
  }

  val completedPages: Seq[Page] = answers.map(_.page)
  val startPageValues: Seq[StartPageValues] = answers.collect { case value: StartPageValues => value }
  val secondPageAValues: Seq[SecondPageAValues] = answers.collect { case value: SecondPageAValues => value }
  val secondPageBValues: Seq[SecondPageBValues] = answers.collect { case value: SecondPageBValues => value }
  val thirdPageAValues: Seq[ThirdPageAValues] = answers.collect { case value: ThirdPageAValues => value }

}
