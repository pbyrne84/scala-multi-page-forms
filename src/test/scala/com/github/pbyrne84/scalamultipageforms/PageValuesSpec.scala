package com.github.pbyrne84.scalamultipageforms

import io.circe.Decoder
import org.scalactic.source.Position
import org.scalatest.EitherValues
import org.scalatest.freespec.AnyFreeSpecLike

class PageValuesSpec extends AnyFreeSpecLike with EitherValues {

  import io.circe.syntax._
  import org.scalatest.matchers.should.Matchers._

  private val nonDecisionalTestData = new NonDecisionalValuesTestData

  "StartPageValues" - {
    "should encode and decode" in {
      testDecodingAndEncodingOfSingle(nonDecisionalTestData.startPage)
    }
  }

  private def testDecodingAndEncodingOfSingle[A <: PageValues](
      pageValuesJsonPairing: PageValuesJsonPairing[A]
  )(implicit position: Position, decoder: Decoder[A]) = {
    pageValuesJsonPairing.values.toJson shouldBe pageValuesJsonPairing.json
    pageValuesJsonPairing.json.as[A] shouldBe Right(pageValuesJsonPairing.values)
  }

  "SecondPageAValues" - {
    "should encode and decode" in {
      testDecodingAndEncodingOfSingle(nonDecisionalTestData.secondPageA)
    }
  }

  "SecondPageBValues" - {
    "should encode and decode" in {
      testDecodingAndEncodingOfSingle(nonDecisionalTestData.secondPageB)
    }
  }

  "ThirdPageAValues" - {
    "should encode and decode" in {
      testDecodingAndEncodingOfSingle(nonDecisionalTestData.thirdPageA)
    }
  }

  "all values as a list" - {

    "should encode to json" in {
      nonDecisionalTestData.allValues.asJson shouldBe nonDecisionalTestData.allJsonValues
    }

    "should decode from json" in {
      nonDecisionalTestData.allJsonValues.as[List[PageValues]] shouldBe Right(nonDecisionalTestData.allValues.toList)
    }
  }

}
