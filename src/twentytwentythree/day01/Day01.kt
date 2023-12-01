package twentytwentythree.day01

import readInput

fun main() {
  fun part1(input: List<String>): Int {
    return input.sumOf { line ->
      val filtered = line.filter { it.isDigit() }
      filtered.first().digitToInt() * 10 + filtered.last().digitToInt()
    }
  }

  fun part2(input: List<String>): Int {
    val digitWords = listOf(
      "one",
      "two",
      "three",
      "four",
      "five",
      "six",
      "seven",
      "eight",
      "nine",
    )

    return input.sumOf { line ->
      val firstDigitIndex = line.indexOfFirst { it.isDigit() }
      val firstDigitWord = line.findAnyOf(digitWords)
      val firstDigitWordIndex = firstDigitWord?.first ?: Int.MAX_VALUE
      val firstDigit = if (firstDigitIndex != -1 && firstDigitIndex < firstDigitWordIndex) {
        line[firstDigitIndex].digitToInt()
      } else {
        digitWords.indexOf(firstDigitWord!!.second) + 1
      }

      val lastDigitIndex = line.indexOfLast { it.isDigit() }
      val lastDigitWord = line.findLastAnyOf(digitWords)
      val lastDigitWordIndex = lastDigitWord?.first ?: Int.MIN_VALUE
      val lastDigit = if (lastDigitIndex != -1 && lastDigitIndex > lastDigitWordIndex) {
        line[lastDigitIndex].digitToInt()
      } else {
        digitWords.indexOf(lastDigitWord!!.second) + 1
      }

      firstDigit * 10 + lastDigit
    }
  }

  val input = readInput("twentytwentythree/day01", "Day01_input")
  println(part1(input))
  println(part2(input))
}
