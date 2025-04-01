package com.github.pbyrne84.scalamultipageforms

import cats.data.NonEmptyList

class PageNavigation {

  val startPage: Page = StartPage

  def calculateNextPage(answeredPages: NonEmptyList[Page], lastAnswer: PageValues): Either[String, Page] = {
    val answerPagesList = answeredPages.toList
    answerPagesList match {
      case StartPage :: Nil =>
        lastAnswer match {
          case value: StartPageValues => calculateNextPageFromStartPage(value.value)
          case _ =>
            Left(s"Unsupported answer type ${lastAnswer.getClass.getSimpleName}, expected StartPageValues")
        }

      case StartPage :: SecondPageA :: Nil =>
        lastAnswer match {
          case _: SecondPageAValues => Right(ThirdPageA)
          case _ =>
            Left(s"Unsupported answer type ${lastAnswer.getClass.getSimpleName}, expected SecondPageAValues")
        }

      case StartPage :: SecondPageA :: ThirdPageA :: Nil =>
        lastAnswer match {
          case _: ThirdPageAValues => Right(FourthPageB)
          case _ =>
            Left(s"Unsupported answer type ${lastAnswer.getClass.getSimpleName}, expected ThirdPageAValues")
        }

      case StartPage :: SecondPageB :: Nil =>
        lastAnswer match {
          case _: SecondPageBValues => Right(ThirdPageB)
          case _ =>
            Left(s"Unsupported answer type ${lastAnswer.getClass.getSimpleName}, expected SecondPageBValues")
        }

      case StartPage :: ThirdPageA :: Nil =>
        lastAnswer match {
          case _: ThirdPageAValues => Right(FourthPageA)
          case _ =>
            Left(s"Unsupported answer type ${lastAnswer.getClass.getSimpleName}, expected ThirdPageAValues")
        }

      case _ =>
        Left(s"Unsupported journey ${answerPagesList.mkString("->")}")
    }

  }

  private def calculateNextPageFromStartPage(int: Int): Either[String, Page] = {
    val valueToNextPageMap: Map[Int, Page] = Map(
      1 -> SecondPageA,
      2 -> SecondPageB,
      3 -> ThirdPageA
    )

    valueToNextPageMap
      .get(int)
      .map(Right.apply[String, Page])
      .getOrElse(Left(s"Invalid option $int, ${valueToNextPageMap.keys.mkString(",")}"))
  }

}
