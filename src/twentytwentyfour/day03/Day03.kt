package twentytwentyfour.day03

import readInput
import kotlin.math.min

fun main() {
    fun part1(input: List<String>): Long {
        return input.sumOf { line ->
            var sum = 0L
            for (i in line.indices) {
                if (i < line.length - 8) {
                    if (line.substring(i, i + 4) == "mul(") {
                        val firstNumString = line.substring(i + 4, i + 7).split(",")[0]
                        val firstNum = firstNumString.toLongOrNull() ?: continue
                        val secondNumString = line.substring(
                            i + 5 + firstNumString.length,
                            min(i + 8 + firstNumString.length, line.length - 1)
                        ).split(")")[0]
                        val secondNum = secondNumString.toLongOrNull() ?: continue

                        if (line[i + 5 + firstNumString.length + secondNumString.length] != ')') continue

                        sum += firstNum * secondNum
                    }
                }
            }
            return@sumOf sum
        }
    }

    fun part2(input: List<String>): Long {
        var process = true
        return input.sumOf { line ->
            var sum = 0L
            for (i in line.indices) {
                if (i < line.length - 8) {
                    if (line.substring(i, i + 4) == "mul(") {
                        val firstNumString = line.substring(i + 4, i + 7).split(",")[0]
                        val firstNum = firstNumString.toLongOrNull() ?: continue
                        val secondNumString = line.substring(
                            i + 5 + firstNumString.length,
                            min(i + 8 + firstNumString.length, line.length - 1)
                        ).split(")")[0]
                        val secondNum = secondNumString.toLongOrNull() ?: continue

                        if (line[i + 5 + firstNumString.length + secondNumString.length] != ')') continue

                        if (process) {
                            sum += firstNum * secondNum
                        }
                    }
                }
                if (i < line.length - 7) {
                    if (line.substring(i, i + 7) == "don't()") {
                        process = false
                    }
                }
                if (i < line.length - 4) {
                    if (line.substring(i, i + 4) == "do()") {
                        process = true
                    }
                }
            }
            return@sumOf sum
        }
    }

    val input = readInput("twentytwentyfour/day03", "Day03_input")
    println(part1(input))
    println(part2(input))
}
