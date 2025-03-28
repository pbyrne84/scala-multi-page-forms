package com.github.pbyrne84.scalamultipageforms

import cats.data.NonEmptyList

class PageNavigation {

  val startPage: Page = StartPage

  def calculateNextPage(answeredPages: NonEmptyList[Page], lastAnswer: PageValues): Either[String, Page] = {
    answeredPages.toList match {
      case StartPage :: Nil =>
        lastAnswer match {
          case value: IntValue => calculateNextPageFromStartPage(value.value)

          case _ =>
            Left(s"Unsupported answer type ${lastAnswer.getClass.getSimpleName}, expected IntValue")
        }
    }

  }

  private def calculateNextPageFromStartPage(int: Int): Either[String, Page] = {
    val valueToNextPageMap: Map[Int, Page] = Map(
      1 -> SecondPageA,
      2 -> SecondPageB,
      3 -> ThirdPageA
    )

    val maybeA = valueToNextPageMap
      .get(int)

    val option: Option[Either[String, Page]] = maybeA
      .map(Right.apply)

    option
      .getOrElse(Left(s"Invalid option $int, ${valueToNextPageMap.keys.mkString(",")}"))
  }
}
