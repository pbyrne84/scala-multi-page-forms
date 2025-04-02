package com.github.pbyrne84.scalamultipageforms

import io.circe.Json
import io.circe.parser.parse
import org.scalatest.EitherValues

trait PageValuesJsonPairing[A <: PageValues] {
  val values: A
  val json: Json
}

class NonDecisionalValuesTestData extends EitherValues {

  private val all = List(startPage, secondPageA, secondPageB, thirdPageA)

  val allJsonValues: Json = Json.fromValues(
    all.map(_.json)
  )

  val allValues: Seq[PageValues] = all.map(_.values)

  object startPage extends PageValuesJsonPairing[StartPageValues] {
    val values: StartPageValues = StartPageValues(1)
    val json: Json = parse("""
                          |{
                          |  "type" : "startPage",
                          |  "value" : 1
                          |}
                          |""".stripMargin).value
  }

  object secondPageA extends PageValuesJsonPairing[SecondPageAValues] {
    val values = SecondPageAValues("cat")
    val json: Json = parse("""
                             |{
                             |  "type" : "secondPageA",
                             |  "value" : "cat"
                             |}
                             |""".stripMargin).value
  }

  object secondPageB extends PageValuesJsonPairing[SecondPageBValues] {
    val values = SecondPageBValues("dog")
    val json: Json = parse("""
                            |{
                            |  "type" : "secondPageB",
                            |  "value" : "dog"
                            |}
                            |""".stripMargin).value

  }

  object thirdPageA extends PageValuesJsonPairing[ThirdPageAValues] {
    val values = ThirdPageAValues(List("cat", "dog"))
    val json: Json = parse("""
                            |{
                            |  "type" : "thirdPageA",
                            |  "value" : ["cat","dog"]
                            |}
                            |""".stripMargin).value

  }

}
