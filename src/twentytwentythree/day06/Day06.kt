package twentytwentythree.day06

import readInput

fun main() {
  fun part1(input: List<String>): Int {
    val times = input[0].split(" ").mapNotNull { it.toIntOrNull() }
    val distances = input[1].split(" ").mapNotNull { it.toIntOrNull() }

    var sum = 1
    for (i in times.indices) {
      var ways = 0
      val time = times[i]
      val distance = distances[i]
      for (j in 1 until time) {
        val currentDistance = (time - j) * j
        if (currentDistance > distance) {
          ways++
        }
      }
      sum *= ways
    }
    return sum
  }

  fun part2(input: List<String>): Int {
    val times = input[0].split(" ").mapNotNull { it.toIntOrNull() }
    val distances = input[1].split(" ").mapNotNull { it.toIntOrNull() }

    val time = times.joinToString("").toLong()
    val distance = distances.joinToString("").toLong()
    var ways = 0

    for (j in 1 until time) {
      val currentDistance = (time - j) * j
      if (currentDistance > distance) {
        ways++
      }
    }

    return ways
  }

  val input = readInput("twentytwentythree/day06", "Day06_input")
  println(part1(input))
  println(part2(input))
}
