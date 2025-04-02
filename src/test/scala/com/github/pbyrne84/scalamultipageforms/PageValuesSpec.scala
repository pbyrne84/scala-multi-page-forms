package com.github.pbyrne84.scalamultipageforms

import io.circe.parser.parse
import org.scalatest.EitherValues
import org.scalatest.freespec.AnyFreeSpecLike

class PageValuesSpec extends AnyFreeSpecLike with EitherValues {

  import org.scalatest.matchers.should.Matchers._

  "StartPageValues" - {

    val startPageJson = parse("""
        |{
        |  "type" : "startPage",
        |  "value" : 1
        |}
        |""".stripMargin).value

    import io.circe.syntax._
    val startPageValues = StartPageValues(1)
    "should encode to json" in {
      startPageValues.asJson shouldBe startPageJson
    }

    "should decode from json" in {
      startPageJson.as[StartPageValues] shouldBe Right(startPageValues)
    }
  }

  "ThirdPageAValues" - {
    val thirdPageAJson = parse("""
        |{
        |  "type" : "thirdPageA",
        |  "value" : ["cat","dog"]
        |}
        |""".stripMargin).value

    import io.circe.syntax._
    val thirdPageAValues = ThirdPageAValues(List("cat", "dog"))
    "should encode to json" in {
      thirdPageAValues.asJson shouldBe thirdPageAJson
    }

    "should decode from json" in {
      thirdPageAJson.as[ThirdPageAValues] shouldBe Right(thirdPageAValues)
    }
  }

}
