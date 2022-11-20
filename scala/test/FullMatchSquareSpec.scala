//> using lib "org.scalatest::scalatest::3.2.9"

import org.scalatest._
import org.scalatest.matchers.must.Matchers
import org.scalatest.wordspec.AnyWordSpec

class FullMatchSquareSpec extends AnyWordSpec with Matchers {
  implicit class StringOps(str: String) {
    def zipWithStartingIndex(offset: Int): Seq[(Char, Int)] =
      str.zipWithIndex.map { case (c,i) => (c, i+offset) }
  }

  "FullMatchSquareSpec" should {
    "return true when supplied word contains the same letter at this position" in {
      "STARE".zipWithStartingIndex(1).foreach { case (l, i) =>
        FullMatchSquare (i, l).check("STARE") mustBe true
      }
    }
  }
}