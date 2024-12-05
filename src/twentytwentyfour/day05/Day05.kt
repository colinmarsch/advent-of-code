package twentytwentyfour.day05

import readInput

fun main() {
  fun part1(input: List<String>): Long {
    val orderingRules = input.filter { it.contains("|") }
    val afterMap = mutableMapOf<Int, MutableSet<Int>>()

    for (rule in orderingRules) {
      val parts = rule.split("|")
      val before = parts[0].toInt()
      val after = parts[1].toInt()
      afterMap[before] = afterMap.getOrDefault(before, mutableSetOf()).apply { add(after) }
    }

    val pageUpdates = input.filter { it.contains(",") }

    var sum = 0L
    for (update in pageUpdates) {
      val parts = update.split(",")
      val seenPages = mutableSetOf<Int>()
      var validUpdate = true
      for (part in parts) {
        val pageNumber = part.toInt()
        val invalidBeforePages = afterMap.getOrDefault(pageNumber, mutableSetOf())
        if (seenPages.any { invalidBeforePages.contains(it) }) {
          validUpdate = false
          break
        }
        seenPages.add(pageNumber)
      }
      if (validUpdate) {
        sum += parts[parts.size / 2].toInt()
      }
    }
    return sum
  }

  fun part2(input: List<String>): Long {
    val orderingRules = input.filter { it.contains("|") }
    val afterMap = mutableMapOf<Int, MutableSet<Int>>()

    for (rule in orderingRules) {
      val parts = rule.split("|")
      val before = parts[0].toInt()
      val after = parts[1].toInt()
      afterMap[before] = afterMap.getOrDefault(before, mutableSetOf()).apply { add(after) }
    }

    val pageUpdates = input.filter { it.contains(",") }

    var sum = 0L
    for (update in pageUpdates) {
      val parts = update.split(",")
      val seenPages = mutableSetOf<Int>()
      var validUpdate = true
      for (part in parts) {
        val pageNumber = part.toInt()
        val invalidBeforePages = afterMap.getOrDefault(pageNumber, mutableSetOf())
        if (seenPages.any { invalidBeforePages.contains(it) }) {
          validUpdate = false
          break
        }
        seenPages.add(pageNumber)
      }

      if (!validUpdate) {
        val pageUpdateSet = parts.map { it.toInt() }.toMutableSet()
        val validPageOrder = mutableListOf<Int>()
        while (pageUpdateSet.isNotEmpty()) {
          val nextItem = pageUpdateSet.find { afterMap[it]?.none { it in pageUpdateSet } == true }
          validPageOrder.add(nextItem!!)
          pageUpdateSet.remove(nextItem)
        }
        sum += validPageOrder[validPageOrder.size / 2]
      }
    }
    return sum
  }

  val input = readInput("twentytwentyfour/day05", "Day05_input")
  println(part1(input))
  println(part2(input))
}
