package day13

import readInput

fun main() {
    fun part1(input: List<String>): Int {
        var total = 0
        input.chunked(3).forEachIndexed { index, pair ->
            val left = parse(pair[0])
            val right = parse(pair[1])
            if (inOrder(left, right)) {
                total += index + 1
            }
        }
        return total
    }

    fun part2(input: List<String>): Int {
        var total = 0
        val items = input.filter { it.isNotEmpty() }.map { parse(it) }.toMutableList()
        val first = ListItem(mutableListOf(ListItem(mutableListOf(IntItem(2)))))
        val second = ListItem(mutableListOf(ListItem(mutableListOf(IntItem(6)))))
        items.add(first)
        items.add(second)
        items.sort()
        return (items.indexOf(first) + 1) * (items.indexOf(second) + 1)
    }

    val input = readInput("day13", "Day13_input")
    println(part1(input))
    println(part2(input))
}

interface Item : Comparable<Item> {
    override fun compareTo(other: Item): Int
}

class IntItem(val value: Int) : Item {
    override fun compareTo(other: Item): Int {
        return when (other) {
            is IntItem -> this.value.compareTo(other.value)
            is ListItem -> {
                return ListItem(mutableListOf(this)).compareTo(other)
            }

            else -> throw IllegalStateException()
        }
    }
}

class ListItem(val list: MutableList<Item> = mutableListOf()) : Item {
    override fun compareTo(other: Item): Int {
        when (other) {
            is IntItem -> {
                return this.compareTo(ListItem(mutableListOf(other)))
            }

            is ListItem -> {
                list.forEachIndexed { index, item ->
                    if (index >= other.list.size) return 1
                    val compare = item.compareTo(other.list[index])
                    if (compare != 0)
                        return compare
                }
                return if (list.size == other.list.size) 0 else -1
            }

            else -> throw IllegalStateException()
        }
    }
}

private fun inOrder(left: ListItem, right: ListItem): Boolean {
    return left <= right
}

private fun parse(line: String): ListItem {

    val stack = ArrayDeque<MutableList<Any>>()
    var current = mutableListOf<Any>()
    var number = -1

    fun flushNumber() {
        if (number != -1) {
            current.add(number)
            number = -1
        }
    }

    line.forEach { char ->
        when (char) {
            '[' -> {
                stack.addLast(current)
                current = mutableListOf()
                stack.last().add(current)
            }

            ']' -> {
                flushNumber()
                current = stack.removeLast()
            }

            ',' -> flushNumber()

            else -> number = number.coerceAtLeast(0) * 10 + (char - '0')
        }
    }
    return ListItem((current[0] as List<*>).toItem().toMutableList())
}

private fun List<*>.toItem(): List<Item> = map {
    if (it is List<*>) {
        ListItem(it.toItem().toMutableList())
    } else {
        IntItem(it as Int)
    }
}
