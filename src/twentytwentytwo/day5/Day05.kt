package twentytwentytwo.day5

import readInput

fun main() {
    fun part1(input: List<String>): String {
        val stacks = listOf(
            ArrayDeque(listOf('W', 'B', 'G', 'Z', 'R', 'D', 'C', 'V')),
            ArrayDeque(listOf('V', 'T', 'S', 'B', 'C', 'F', 'W', 'G')),
            ArrayDeque(listOf('W', 'N', 'S', 'B', 'C')),
            ArrayDeque(listOf('P', 'C', 'V', 'J', 'N', 'M', 'G', 'Q')),
            ArrayDeque(listOf('B', 'H', 'D', 'F', 'L', 'S', 'T')),
            ArrayDeque(listOf('N', 'M', 'W', 'T', 'V', 'J')),
            ArrayDeque(listOf('G', 'T', 'S', 'C', 'L', 'F', 'P')),
            ArrayDeque(listOf('Z', 'D', 'B')),
            ArrayDeque(listOf('W', 'Z', 'N', 'M')),
        )

        val instructions = input.drop(10)
        instructions.map { line ->
            val values = line.split(" ").filter { part -> part.toIntOrNull() != null }
            val numMoved = values[0].toInt()
            val src = values[1].toInt() - 1
            val dest = values[2].toInt() - 1

            for (i in 0 until numMoved) {
                stacks[dest].addFirst(stacks[src].removeFirst())
            }
        }

        var result = ""
        stacks.forEach {
            result += it.removeFirst()
        }

        return result
    }

    fun part2(input: List<String>): String {
        val stacks = listOf(
            ArrayDeque(listOf('W', 'B', 'G', 'Z', 'R', 'D', 'C', 'V')),
            ArrayDeque(listOf('V', 'T', 'S', 'B', 'C', 'F', 'W', 'G')),
            ArrayDeque(listOf('W', 'N', 'S', 'B', 'C')),
            ArrayDeque(listOf('P', 'C', 'V', 'J', 'N', 'M', 'G', 'Q')),
            ArrayDeque(listOf('B', 'H', 'D', 'F', 'L', 'S', 'T')),
            ArrayDeque(listOf('N', 'M', 'W', 'T', 'V', 'J')),
            ArrayDeque(listOf('G', 'T', 'S', 'C', 'L', 'F', 'P')),
            ArrayDeque(listOf('Z', 'D', 'B')),
            ArrayDeque(listOf('W', 'Z', 'N', 'M')),
        )

        val instructions = input.drop(10)
        instructions.map { line ->
            val values = line.split(" ").filter { part -> part.toIntOrNull() != null }
            val numMoved = values[0].toInt()
            val src = values[1].toInt() - 1
            val dest = values[2].toInt() - 1

            val toMove = mutableListOf<Char>()
            for (i in 0 until numMoved) {
                toMove += stacks[src].removeFirst()
            }
            toMove.reversed().forEach { stacks[dest].addFirst(it) }
        }

        var result = ""
        stacks.forEach {
            result += it.removeFirst()
        }

        return result
    }

    val input = readInput("day5", "Day05_input")
    println(part1(input))
    println(part2(input))
}

