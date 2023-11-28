package twentytwentytwo.day4

import readInput

fun main() {
    fun part1(input: List<String>): Int = input.count { pair ->
        val ranges = pair.split(",")
        val first = ranges[0].split("-").map { it.toInt() }
        val second = ranges[1].split("-").map { it.toInt() }
        ((first[0] <= second[0] && second[1] <= first[1]) || (second[0] <= first[0] && first[1] <= second[1]))
    }

    fun part2(input: List<String>): Int = input.count { pair ->
        val ranges = pair.split(",")
        val first = ranges[0].split("-").map { it.toInt() }
        val second = ranges[1].split("-").map { it.toInt() }
        if (first[0] < second[0]) {
            second[0] <= first[1]
        } else {
            first[0] <= second[1]
        }
    }

    val input = readInput("day4", "Day04_input")
    println(part1(input))
    println(part2(input))
}

