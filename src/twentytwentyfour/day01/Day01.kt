package twentytwentyfour.day01

import readInput
import kotlin.math.abs

fun main() {
  fun part1(input: List<String>): Long {
    val leftList: MutableList<Long> = mutableListOf()
    val rightList: MutableList<Long> = mutableListOf()

    for (line in input) {
      val numbers = line.split(" ").filter { it.isNotBlank() }
      leftList.add(numbers[0].toLong())
      rightList.add(numbers[1].toLong())
    }

    leftList.sort()
    rightList.sort()

    var sum = 0L
    for (i in leftList.indices) {
      sum += abs(leftList[i] - rightList[i])
    }
    return sum
  }

  fun part2(input: List<String>): Long {
    val leftList: MutableList<Long> = mutableListOf()
    val rightList: MutableList<Long> = mutableListOf()

    for (line in input) {
      val numbers = line.split(" ").filter { it.isNotBlank() }
      leftList.add(numbers[0].toLong())
      rightList.add(numbers[1].toLong())
    }

    var sum = 0L
    for (i in leftList.indices) {
      val count = rightList.count { it == leftList[i] }
      sum += count * leftList[i]
    }
    return sum
  }

  val input = readInput("twentytwentyfour/day01", "Day01_input")
  println(part1(input))
  println(part2(input))
}
