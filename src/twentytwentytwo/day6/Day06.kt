package twentytwentytwo.day6

import readInput

fun main() {
    fun part1(input: List<String>): Int {
        val line = input[0]
        for (i in 0 until line.length - 4) {
            val curr = line.subSequence(i, i + 4)
            if (curr.toSet().size == 4) {
                return i + 4
            }
        }
        return 0
    }

    fun part2(input: List<String>): Int {
        val line = input[0]
        for (i in 0 until line.length - 14) {
            val curr = line.subSequence(i, i + 14)
            if (curr.toSet().size == 14) {
                return i + 14
            }
        }
        return 0
    }

    val input = readInput("day6", "Day06_input")
    println(part1(input))
    println(part2(input))
}

