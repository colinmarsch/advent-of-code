package day3

import readInput
import kotlin.text.CharCategory.UPPERCASE_LETTER

fun main() {
    fun part1(input: List<String>): Int = input.sumOf { sack ->
        val firstSack = sack.substring(0, sack.length / 2)
        val secondSack = sack.substring(sack.length / 2)
        (firstSack.toSet().intersect(secondSack.toSet())).sumOf { score(it) }
    }

    fun part2(input: List<String>): Int {
        var counter = 0
        var groupId = 0
        val map = input.groupBy { line ->
            counter++
            val ret = groupId
            if (counter % 3 == 0) {
                groupId++
                counter = 0
            }
            return@groupBy ret
        }
        return map.values.sumOf { group ->
            (group[0].toSet() intersect group[1].toSet() intersect group[2].toSet()).sumOf {
                score(it)
            }
        }
    }

    val input = readInput("day3", "Day03_input")
    println(part1(input))
    println(part2(input))
}

private fun score(char: Char): Int = if (char.category == UPPERCASE_LETTER) {
    char - 'A' + 27
} else {
    char - 'a' + 1
}
