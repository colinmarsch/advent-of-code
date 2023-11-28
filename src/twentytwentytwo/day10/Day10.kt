package twentytwentytwo.day10

import readInput
import kotlin.math.abs

fun main() {
    fun part1(input: List<String>): Int {
        var cycleCount = 1
        var x = 1
        var total = 0
        val specialCycles = mutableSetOf(20, 60, 100, 140, 180, 220)
        input.forEach { line ->
            var delta = 0
            if (line == "noop") {
                cycleCount++
            } else {
                delta = line.split(" ")[1].toInt()
                cycleCount += 2
                x += delta
            }
            val toRemove = mutableSetOf<Int>()
            specialCycles.forEach {
                if (cycleCount - it == 0) {
                    total += x * it
                    toRemove.add(it)
                } else if (cycleCount - it == 1) {
                    total += (x - delta) * it
                    toRemove.add(it)
                }
            }
            specialCycles.removeAll(toRemove)
        }
        return total
    }

    fun part2(input: List<String>) {
        var cycleCount = 1
        var x = 1
        drawPixel(cycleCount, x)
        input.forEach { line ->
            if (line == "noop") {
                cycleCount++
                drawPixel(cycleCount, x)
            } else {
                val delta = line.split(" ")[1].toInt()
                cycleCount += 2
                drawPixel(cycleCount - 1, x)
                x += delta
                drawPixel(cycleCount, x)
            }
        }
    }

    val input = readInput("day10", "Day10_input")
    println(part1(input))
    part2(input)
}

private fun drawPixel(cycleCount: Int, x: Int) {
    if (cycleCount > 240) return

    if (abs(x - ((cycleCount % 40) - 1)) <= 1) {
        print("#")
    } else {
        print(".")
    }
    if (cycleCount % 40 == 0) println()
}
