package twentytwentythree.day04

import readInput
import kotlin.math.pow

fun main() {
    fun part1(input: List<String>): Int {
        return input.sumOf { line ->
            val numbers = line.split(":")[1].split("|")
            val winningNumbers = numbers[0].split(" ").mapNotNull { it.toIntOrNull() }.toSet()
            val myNumbers = numbers[1].split(" ").mapNotNull { it.toIntOrNull() }.toSet()
            2.toDouble().pow(winningNumbers.intersect(myNumbers).size - 1).toInt()
        }
    }

    fun part2(input: List<String>): Int {
        val stackCounts = mutableListOf<Int>()
        for (i in input.indices) {
            stackCounts.add(1)
        }

        return input.sumOf { line ->
            val currentCount = stackCounts.removeFirst()
            val numbers = line.split(":")[1].split("|")
            val winningNumbers = numbers[0].split(" ").mapNotNull { it.toIntOrNull() }.toSet()
            val myNumbers = numbers[1].split(" ").mapNotNull { it.toIntOrNull() }.toSet()
            val newCards = winningNumbers.intersect(myNumbers).size
            for (i in 0 until newCards) {
                stackCounts[i] += currentCount
            }
            currentCount
        }
    }

    val input = readInput("twentytwentythree/day04", "Day04_input")
    println(part1(input))
    println(part2(input))
}
