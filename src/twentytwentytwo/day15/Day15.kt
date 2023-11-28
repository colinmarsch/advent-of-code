package twentytwentytwo.day15

import readInput
import kotlin.math.abs

fun main() {
    // row index -> set of spaces that are covered in that row
    val coveredSpots = mutableMapOf<Int, Set<Int>>()
    val borderPoints = mutableSetOf<Pair<Int, Int>>()
    val beacons = mutableSetOf<Pair<Int, Int>>()
    val sensors = mutableSetOf<Triple<Int, Int, Int>>()

    fun coverAllSpots(
        minDist: Int,
        start: Pair<Int, Int>,
    ) {
        if (2000000 in start.second - minDist..start.second + minDist) {
            val diff = abs(abs(start.second - 2000000) - minDist)
            val cols = (start.first - diff..start.first + diff).filter {
                !beacons.contains(Pair(it, 2000000))
            }
            coveredSpots[2000000] = coveredSpots.getOrDefault(2000000, emptySet()) + cols
        }
    }

    fun uncoverSpots(
        minDist: Int,
        start: Pair<Int, Int>,
    ) {
        val maxValue = 4000000
        for (r in start.second - minDist - 1..start.second + minDist + 1) {
            if (r !in 0..maxValue) continue
            val diff = abs(abs(start.second - r) - (minDist + 1))
            val cols = listOf(start.first - diff, start.first + diff)
            for (c in cols) {
                if (c !in 0..maxValue) continue
                if (!beacons.contains(Pair(c, r))) {
                    borderPoints.add(Pair(c, r))
                }
            }
        }
    }

    fun setupSensorsAndKnownBeacons(input: List<String>, uncover: Boolean = false) {
        coveredSpots.clear()

        input.forEach { line ->
            val parts = line.split(" ")
            val sx = parts[2].dropLast(1).split("=").last().toInt()
            val sy = parts[3].dropLast(1).split("=").last().toInt()

            val bx = parts[8].dropLast(1).split("=").last().toInt()
            val by = parts[9].split("=").last().toInt()

            val minDist = abs(sx - bx) + abs(sy - by)
            beacons.add(Pair(bx, by))
            sensors.add(Triple(sx, sy, minDist))
            if (!uncover) {
                coverAllSpots(minDist, Pair(sx, sy))
            } else {
                uncoverSpots(minDist, Pair(sx, sy))
            }
        }
    }

    fun part1(input: List<String>): Int {
        setupSensorsAndKnownBeacons(input)

        return coveredSpots[2000000]!!.size
    }

    fun part2(input: List<String>): Long {
        setupSensorsAndKnownBeacons(input, true)

        for (point in borderPoints) {
            if (sensors.all { abs(it.first - point.first) + abs(it.second - point.second) > it.third }) {
                return point.first.toLong() * 4000000L + point.second.toLong()
            }
        }

        return 0L
    }

    val input = readInput("day15", "Day15_input")
    println(part1(input))
    println(part2(input))
}
