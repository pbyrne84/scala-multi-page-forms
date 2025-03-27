package com.github.pbyrne84.scalamultipageforms

import cats.data.NonEmptyList
import org.scalatest.freespec.AnyFreeSpecLike

class PageNavigationSpec extends AnyFreeSpecLike {
  import org.scalatest.matchers.should.Matchers._

  private val pageNavigation = new PageNavigation

  "calculateNextPage" - {

    def createAnsweredPages(head: Page, other: Page*) = {
      NonEmptyList(head = head, tail = other.toList)
    }

    "should navigate from the start page to" - {
      val startPageOnlyAnswered = createAnsweredPages(StartPage)
      "SecondPageA when the first option was selected" in {
        pageNavigation.calculateNextPage(startPageOnlyAnswered, StartPageValues(1)) shouldBe Right(SecondPageA)
      }

      "SecondPageB when the second option was selected" in {
        pageNavigation.calculateNextPage(startPageOnlyAnswered, StartPageValues(2)) shouldBe Right(SecondPageB)
      }

      "ThirdPageA, skipping the SecondPages when the third option was selected" in {
        pageNavigation.calculateNextPage(startPageOnlyAnswered, StartPageValues(3)) shouldBe Right(ThirdPageA)
      }
    }

    "should navigate from StartPage->SecondPageA to ThirdPageA" in {
      pageNavigation.calculateNextPage(
        answeredPages = createAnsweredPages(StartPage, SecondPageA),
        lastAnswer = SecondPageAValues("banana")
      ) shouldBe Right(
        ThirdPageA
      )
    }

    "should navigate from StartPage->SecondPageB to ThirdPageB" in {
      pageNavigation.calculateNextPage(
        answeredPages = createAnsweredPages(StartPage, SecondPageB),
        lastAnswer = SecondPageBValues("banana")
      ) shouldBe Right(
        ThirdPageB
      )
    }

    "ThirdPageA" - {
      "should navigate to FourthPageA if ThirdPageA came from the start page" in {
        pageNavigation.calculateNextPage(
          answeredPages = createAnsweredPages(StartPage, ThirdPageA),
          lastAnswer = ThirdPageAValues(List("banana", "cat", "donkey"))
        ) shouldBe Right(
          FourthPageA
        )
      }

      "should navigate to FourthPageB if ThirdPageA came from StartPage->SecondPageA" in {
        pageNavigation.calculateNextPage(
          answeredPages = createAnsweredPages(StartPage, SecondPageA, ThirdPageA),
          lastAnswer = ThirdPageAValues(List("banana", "cat", "donkey"))
        ) shouldBe Right(
          FourthPageB
        )
      }

    }
  }

}
