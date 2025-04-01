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
    import io.circe.parser._
    "should encode to json" in {
      StartPageValues(1).asJson shouldBe startPageJson
    }

    "should decode from json" in {
      startPageJson.as[StartPageValues] shouldBe Right(StartPageValues(1))
    }
  }
}
