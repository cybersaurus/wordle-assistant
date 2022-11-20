#!/usr/bin/env -S scala-cli

//> using file "scala/main/model.scala"

import scala.io.StdIn

val matches: List[String] = LazyList
  .from(1)
  .map(i => StdIn.readLine(s"Enter guess [$i]: "))
  .takeWhile(_.nonEmpty)
  .map(Guess.apply)
  .foldLeft((Grid(), List.empty[String])) { case ((grid, _), guess) =>
    val updatedGrid = grid.guessed(guess)
    val matches = Dictionary.words
      .filter(updatedGrid.check)
      .toList
    println(s"Matches: [${matches.size}]")
    (updatedGrid, matches)
  }
  ._2

println(s"${matches.size} matches:")
println(matches.take(40).mkString("\n"))
