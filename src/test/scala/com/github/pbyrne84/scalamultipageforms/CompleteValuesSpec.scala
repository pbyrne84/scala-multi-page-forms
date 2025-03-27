package com.github.pbyrne84.scalamultipageforms

import org.scalatest.EitherValues
import org.scalatest.freespec.AnyFreeSpecLike

class CompleteValuesSpec extends AnyFreeSpecLike with EitherValues {

  import io.circe.parser._
  import io.circe.syntax._
  import org.scalatest.matchers.should.Matchers._

  private val nonDecisionalValuesTestData = new NonDecisionalValuesTestData

  "CompleteValues" - {
    val completeValuesJson = parse(
      """
        |{
        |  "answers" : [
        |    {
        |      "type" : "startPage",
        |      "value" : 1
        |    },
        |    {
        |      "type" : "secondPageA",
        |      "value" : "cat"
        |    },
        |    {
        |      "type" : "secondPageB",
        |      "value" : "dog"
        |    },
        |    {
        |      "type" : "thirdPageA",
        |      "value" : [
        |        "cat",
        |        "dog"
        |      ]
        |    }
        |  ]
        |}""".stripMargin
    ).value

    val completeValuesWithAllEntries = CompleteValues(nonDecisionalValuesTestData.allValues.toList)

    "should encode" in {
      completeValuesWithAllEntries.asJson shouldBe completeValuesJson
    }

    "should decode" in {
      completeValuesJson.as[CompleteValues] shouldBe Right(completeValuesWithAllEntries)
    }

    "maybeAnsweredPageValues" - {
      "should return None when there are no values" in {
        CompleteValues(List.empty).maybeAnsweredPageValues shouldBe None
      }

      "should return NonEmptyList of the values when they exist" in {
        completeValuesWithAllEntries.maybeAnsweredPageValues shouldBe Some(nonDecisionalValuesTestData.allValues)
      }

    }
  }

}
