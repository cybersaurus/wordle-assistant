import scala.io.Source

type Word = String

sealed trait WordChecker {
  def check(word: Word): Boolean
}

sealed trait Square extends WordChecker

final case class FullMatchSquare(position: Int, letter: Char) extends Square {
  override def check(word: Word): Boolean = letter == word.charAt(position-1)
}
final case class PartialMatchSquare(position: Int, letter: Char) extends Square {
  override def check(word: Word): Boolean = word.contains(letter) && letter != word.charAt(position-1)
}
final case class NoMatchSquare(position: Int, letter: Char, guess: String, scores: String) extends Square {
  import Utils.StringOps

  override def check(word: Word): Boolean = word.containsNot(letter) || canIgnoreUnmatchedLetter(word, letter)

  private def canIgnoreUnmatchedLetter(word: Word, letter: Char): Boolean =
      guess.count(_ == letter) > 1 &&
      word
        .zip(scores)
        .zipWithIndex
        .find { case ((l, s), i) => (l == letter) && Set('G', 'Y').contains(s) && (i + 1 != position) }
        .nonEmpty
}

final case class Row(squares: List[Square]) {
  def check(word: Word): Boolean = squares.forall(_.check(word))
}
object Row {
  def apply(squares: Square*): Row = Row(squares.toList)
}

final case class Guess(row: Row)
object Guess {
  def apply(line: String): Guess = {
    import Utils.Tuple2Ops

    val (letters, scores) = line.span(_ != ' ').map(_.trim)

    val squares: Seq[Square] = letters.zip(scores).zipWithIndex.map { case ((letter, score), index) => score match {
      case 'F' | 'G'       => FullMatchSquare(index+1, letter)
      case 'P' | 'Y'       => PartialMatchSquare(index+1, letter)
      case 'N' | 'W' | '_' => NoMatchSquare(index+1, letter, letters, scores)
    }}

    new Guess(Row(squares.toList))
  }
}

final case class Guesses(guesses: List[Guess] = List.empty) {
  def check(word: Word): Boolean = guesses.forall(_.row.check(word))
}

final case class Grid(guesses: List[Guess] = List.empty) {
  def guessed(row: Row): Grid = guessed(Guess(row))
  def guessed(guess: Guess): Grid = Grid(guesses :+ guess)

  def check(word: Word): Boolean = guesses.forall(_.row.check(word))
}

object Dictionary {
  val words: LazyList[String] = Source
    .fromFile("/usr/share/dict/words")
    .getLines()
    .to(LazyList)
    .filter(_.length == 5)
    .map(_.toUpperCase)
}

object Utils {
  implicit class StringOps(str: String) {
    def containsNot(ch: Char): Boolean = !str.contains(ch)
  }

  implicit class Tuple2Ops[A](tuple2: Tuple2[A, A]) {
    def map[B](f: A => B): Tuple2[B, B] = ((f(tuple2._1), f(tuple2._2)))
  }
}
