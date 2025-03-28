package com.github.pbyrne84.scalamultipageforms

sealed trait Page {}
case object StartPage extends Page
case object SecondPageA extends Page
case object SecondPageB extends Page
case object ThirdPageA extends Page
case object ThirdPageB extends Page
case object ThirdPageC extends Page
case object ThirdPageD extends Page

case object Finished extends Page

sealed trait PageValues {
  val page: Page
}

sealed trait IntValue extends PageValues {
  val value: Int
}

case class StartPageValues(value: Int) extends IntValue {
  override val page: Page = StartPage
}
