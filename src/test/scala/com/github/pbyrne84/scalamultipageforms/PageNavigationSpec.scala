package com.github.pbyrne84.scalamultipageforms

import cats.data.NonEmptyList
import org.scalatest.freespec.AnyFreeSpecLike

class PageNavigationSpec extends AnyFreeSpecLike {
  import org.scalatest.matchers.should.Matchers._

  private val pageNavigation = new PageNavigation

  "calculateNextPage" - {

    "should navigate from the start page to" - {
      val startPageOnlyAnswered = NonEmptyList.one(StartPage)
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
  }
}
