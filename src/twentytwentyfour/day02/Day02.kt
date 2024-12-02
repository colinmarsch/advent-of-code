package twentytwentyfour.day02

import readInput
import kotlin.math.abs

fun main() {
    fun part1(reports: List<String>): Long {
        return reports.sumOf { report: String ->
            val levels = report.split(" ").map { it.toInt() }
            if (levels == levels.sorted() || levels == levels.sortedDescending()) {
                for (i in levels.indices) {
                    if (i == levels.size - 1) {
                        break
                    }
                    if (abs(levels[i] - levels[i + 1]) !in 1..3) {
                        return@sumOf 0L
                    }
                }
                return@sumOf 1L
            } else {
                return@sumOf 0L
            }
        }
    }

    fun part2(reports: List<String>): Long {
        return reports.sumOf { report: String ->
            val levels = report.split(" ").map { it.toInt() }
            for (i in levels.indices) {
                if (checkLevels(levels.subList(0, i) + levels.subList(i + 1, levels.size))) {
                    return@sumOf 1L
                }
            }
            return@sumOf 0L
        }
    }

    val input = readInput("twentytwentyfour/day02", "Day02_input")
    println(part1(input))
    println(part2(input))
}

private fun checkLevels(levels: List<Int>): Boolean {
    if (levels == levels.sorted() || levels == levels.sortedDescending()) {
        for (i in levels.indices) {
            if (i == levels.size - 1) {
                break
            }
            if (abs(levels[i] - levels[i + 1]) !in 1..3) {
                return false
            }
        }
        return true
    } else {
        return false
    }
}
