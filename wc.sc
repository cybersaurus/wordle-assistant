  val words = scala.io.Source
    .fromFile("/usr/share/dict/words")
    .getLines()
    .to(LazyList)
    .filter(_.length == 5)
    .map(_.toUpperCase)
    .distinct
  println(s"Total words: ${words.size}")
