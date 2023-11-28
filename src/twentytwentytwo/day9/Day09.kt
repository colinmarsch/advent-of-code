package twentytwentytwo.day9

import readInput
import kotlin.math.abs
import kotlin.math.sign

fun main() {
    val tailList = mutableListOf(
        Pair(0, 0),
        Pair(0, 0),
        Pair(0, 0),
        Pair(0, 0),
        Pair(0, 0),
        Pair(0, 0),
        Pair(0, 0),
        Pair(0, 0),
        Pair(0, 0),
    )
    val spaces = mutableSetOf(Pair(0, 0))

    fun part1(input: List<String>): Int {
        var tailX = 0
        var tailY = 0
        var headX = 0
        var headY = 0

        input.forEach {
            val parts = it.split(" ")
            val direction = parts[0]
            val amount = parts[1].toInt()
            for (i in 0 until amount) {
                when (direction) {
                    "D" -> {
                        headY--
                    }

                    "U" -> {
                        headY++
                    }

                    "L" -> {
                        headX--
                    }

                    "R" -> {
                        headX++
                    }
                }
                moveTail(tailX, tailY, headX, headY, direction) { x, y ->
                    tailX = x
                    tailY = y
                    spaces.add(Pair(tailX, tailY))
                }
            }
        }
        return spaces.size
    }

    fun moveTail(tailIndex: Int, headX: Int, headY: Int) {
        if (tailIndex > 8) return
        if (abs(tailList[tailIndex].first - headX) <= 1 && abs(tailList[tailIndex].second - headY) <= 1) return

        // Lines
        if (tailList[tailIndex].first == headX - 2 && tailList[tailIndex].second == headY) {
            tailList[tailIndex] = Pair(tailList[tailIndex].first + 1, tailList[tailIndex].second)
        }
        if (tailList[tailIndex].first == headX + 2 && tailList[tailIndex].second == headY) {
            tailList[tailIndex] = Pair(tailList[tailIndex].first - 1, tailList[tailIndex].second)
        }
        if (tailList[tailIndex].second == headY - 2 && tailList[tailIndex].first == headX) {
            tailList[tailIndex] = Pair(tailList[tailIndex].first, tailList[tailIndex].second + 1)
        }
        if (tailList[tailIndex].second == headY + 2 && tailList[tailIndex].first == headX) {
            tailList[tailIndex] = Pair(tailList[tailIndex].first, tailList[tailIndex].second - 1)
        }

        // Diagonals
        if (tailList[tailIndex].second == headY + 2 && tailList[tailIndex].first < headX) {
            tailList[tailIndex] =
                Pair(tailList[tailIndex].first + 1, tailList[tailIndex].second - 1)
        }
        if (tailList[tailIndex].second == headY + 2 && tailList[tailIndex].first > headX) {
            tailList[tailIndex] =
                Pair(tailList[tailIndex].first - 1, tailList[tailIndex].second - 1)
        }
        if (tailList[tailIndex].second == headY - 2 && tailList[tailIndex].first < headX) {
            tailList[tailIndex] =
                Pair(tailList[tailIndex].first + 1, tailList[tailIndex].second + 1)
        }
        if (tailList[tailIndex].second == headY - 2 && tailList[tailIndex].first > headX) {
            tailList[tailIndex] =
                Pair(tailList[tailIndex].first - 1, tailList[tailIndex].second + 1)
        }


        if (tailList[tailIndex].first == headX - 2) {
            tailList[tailIndex] = Pair(
                tailList[tailIndex].first + 1,
                tailList[tailIndex].second + (headY - tailList[tailIndex].second).sign
            )
        }
        if (tailList[tailIndex].first == headX + 2) {
            tailList[tailIndex] = Pair(
                tailList[tailIndex].first - 1,
                tailList[tailIndex].second + (headY - tailList[tailIndex].second).sign
            )
        }

        moveTail(tailIndex + 1, tailList[tailIndex].first, tailList[tailIndex].second)
    }

    fun part2(input: List<String>): Int {
        var headX = 0
        var headY = 0

        input.forEach {
            val parts = it.split(" ")
            val direction = parts[0]
            val amount = parts[1].toInt()
            for (i in 0 until amount) {
                when (direction) {
                    "D" -> {
                        headY--
                    }

                    "U" -> {
                        headY++
                    }

                    "L" -> {
                        headX--
                    }

                    "R" -> {
                        headX++
                    }
                }
                moveTail(tailIndex = 0, headX = headX, headY = headY)
                spaces.add(tailList[8])
            }
        }
        return spaces.size
    }

    val input = readInput("day9", "Day09_input")
    println(part1(input))
    spaces.clear()
    println(part2(input))
}

private fun moveTail(
    tailX: Int,
    tailY: Int,
    headX: Int,
    headY: Int,
    direction: String,
    body: (x: Int, y: Int) -> Unit
) {
    if (abs(tailX - headX) <= 1 && abs(tailY - headY) <= 1) return

    when (direction) {
        "D" -> {
            body(headX, headY + 1)
        }

        "U" -> {
            body(headX, headY - 1)
        }

        "L" -> {
            body(headX + 1, headY)
        }

        "R" -> {
            body(headX - 1, headY)
        }
    }
}
