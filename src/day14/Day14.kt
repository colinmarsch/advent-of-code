package day14

import readInput

// All x points need to subtract 450
fun main() {
    val grid = mutableListOf<MutableList<Char>>()

    // 150 by 200 grid
    // x is 450 to 600, y is 0 to 200
    fun setupGrid(input: List<String>, addFloor: Boolean = false) {
        grid.clear()

        for (y in 0..175) {
            grid.add(mutableListOf())
            for (x in 0..1200) {
                grid[y].add('.')
            }
        }

        input.forEach {
            val points: List<List<Int>> =
                it.split("->").map { it.trim() }.map { it.split(",").map { it.toInt() } }
            var prevX = points[0][0] + 150
            var prevY = points[0][1]
            for (i in 1 until points.size) {
                val currX = points[i][0] + 150
                val currY = points[i][1]

                for (x in if (prevX <= currX) prevX..currX else currX..prevX) {
                    grid[prevY][x] = '#'
                    grid[currY][x] = '#'
                }

                for (y in if (prevY <= currY) prevY..currY else currY..prevY) {
                    grid[y][currX] = '#'
                    grid[y][prevX] = '#'
                }

                prevX = currX
                prevY = currY
            }
        }

        if (addFloor) {
            grid[grid.indexOfLast { it.contains('#') } + 2].replaceAll { '#' }
        }
    }

    fun drawGrid() {
        for (r in 0 until grid.size) {
            for (c in 0 until grid[0].size) {
                print(grid[r][c])
            }
            println()
        }
    }

    fun dropSand(): Boolean {
        var sandX = 650
        var sandY = 0

        val held: Boolean
        while (true) {
            if (sandY + 1 >= grid.size) {
                held = false
                break
            }

            val next = grid[sandY + 1][sandX]

            if (next == '.') {
                sandY++
            } else if (next == '#' || next == 'o') {
                if (grid[sandY + 1][sandX - 1] == '.') {
                    sandY++
                    sandX--
                } else if (grid[sandY + 1][sandX + 1] == '.') {
                    sandY++
                    sandX++
                } else {
                    grid[sandY][sandX] = 'o'
                    held = !(sandY == 0 && sandX == 650)
                    break
                }
            }
        }

        return held
    }

    fun part1(input: List<String>): Int {
        setupGrid(input)

        // add the sand here starting from point (50, 0), count the ones that stop until one exits
        var count = 0
        while (true) {
            if (!dropSand()) break
            count++
        }

        drawGrid()

        return count
    }

    fun part2(input: List<String>): Int {
        setupGrid(input, addFloor = true)

        var count = 0
        while (true) {
            if (!dropSand()) break
            count++
        }

        drawGrid()

        return count + 1 // to count the last piece of sand
    }

    val input = readInput("day14", "test")
    println(part1(input))
    println(part2(input))
}
