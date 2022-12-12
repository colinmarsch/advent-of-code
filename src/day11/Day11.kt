package day11

import readInput

fun main() {
    data class Monkey(
        val items: MutableList<Long>,
        val operation: (Long, Int) -> Long,
        val test: Long,
        val trueId: Int,
        val falseId: Int,
        var inspected: Long = 0,
    )

    fun part1(input: List<String>): Long {
        val monkeys = mutableListOf<Monkey>()
        val parts = mutableListOf<List<String>>()
        input.chunked(7) { section ->
            parts.add(section[2].dropWhile { it != '=' }.split(" "))
            monkeys.add(
                Monkey(
                    items = section[1].split(",").map { it.filter { !it.isLetter() && it != ':' && !it.isWhitespace() }.toLong() }
                        .toMutableList(),
                    operation = { old, index ->
                        val op = parts[index][2]
                        val delta = if (parts[index][3] == "old") old else parts[index][3].toLong()

                        if (op == "*") {
                            old * delta
                        } else {
                            old + delta
                        }
                    },
                    test = section[3].split(" ").last().toLong(),
                    trueId = section[4].split(" ").last().toInt(),
                    falseId = section[5].split(" ").last().toInt(),
                )
            )
        }

        repeat(20) {
            monkeys.forEachIndexed { index, monkey ->
                monkey.items.forEach { startingWorry ->
                    val itemWorry = monkey.operation(startingWorry, index) / 3.toLong()
                    if (itemWorry % monkey.test == 0L) {
                        monkeys[monkey.trueId].items.add(itemWorry)
                    } else {
                        monkeys[monkey.falseId].items.add(itemWorry)
                    }
                    monkey.inspected++
                }
                monkey.items.clear()
            }
        }

        val topTwo = monkeys.sortedByDescending { it.inspected }.take(2).map { it.inspected }
        return (topTwo[0] * topTwo[1])
    }

    fun part2(input: List<String>): Long {
        val monkeys = mutableListOf<Monkey>()
        val parts = mutableListOf<List<String>>()
        input.chunked(7) { section ->
            parts.add(section[2].dropWhile { it != '=' }.split(" "))
            monkeys.add(
                Monkey(
                    items = section[1].split(",").map { it.filter { !it.isLetter() && it != ':' && !it.isWhitespace() }.toLong() }
                        .toMutableList(),
                    operation = { old, index ->
                        val op = parts[index][2]
                        val delta = if (parts[index][3] == "old") old else parts[index][3].toLong()

                        if (op == "*") {
                            old * delta
                        } else {
                            old + delta
                        }
                    },
                    test = section[3].split(" ").last().toLong(),
                    trueId = section[4].split(" ").last().toInt(),
                    falseId = section[5].split(" ").last().toInt(),
                )
            )
        }

        val lcm = monkeys.map { it.test }.reduce(Long::times)

        repeat(10_000) {
            monkeys.forEachIndexed { index, monkey ->
                monkey.items.forEach { startingWorry ->
                    val itemWorry = monkey.operation(startingWorry, index) % lcm
                    if (itemWorry % monkey.test == 0L) {
                        monkeys[monkey.trueId].items.add(itemWorry)
                    } else {
                        monkeys[monkey.falseId].items.add(itemWorry)
                    }
                    monkey.inspected++
                }
                monkey.items.clear()
            }
        }

        val topTwo = monkeys.sortedByDescending { it.inspected }.take(2).map { it.inspected }
        return (topTwo[0] * topTwo[1])
    }

    val input = readInput("day11", "Day11_input")
    println(part1(input))
    println(part2(input))
}
