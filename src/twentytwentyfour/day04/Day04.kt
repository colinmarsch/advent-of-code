package twentytwentyfour.day04

import readInput

fun main() {
    fun part1(input: List<String>): Int {
        val grid = buildGrid(input)
        var sum = 0
        for (i in grid.indices) {
            for (j in grid[i].indices) {
                if (grid[i][j] == 'X') {
                    sum += searchXMAS(grid, i, j)
                }
            }
        }
        return sum
    }

    fun part2(input: List<String>): Long {
        val grid = buildGrid(input)
        val diagonalMASMap = findDiagonalMAS(grid)
        return diagonalMASMap.entries.sumOf {
            if (it.value == 2) {
                1L
            } else {
                0L
            }
        }
    }

    val input = readInput("twentytwentyfour/day04", "Day04_input")
    println(part1(input))
    println(part2(input))
}

private fun buildGrid(input: List<String>): List<List<Char>> {
    val grid = mutableListOf<List<Char>>()
    input.forEach { line ->
        grid.add(line.toList())
    }
    return grid
}

private fun searchXMAS(grid: List<List<Char>>, i: Int, j: Int): Int {
    var count = 0

    // right
    if (j <= grid[i].size - 4) {
        if (grid[i][j] == 'X' && grid[i][j + 1] == 'M' && grid[i][j + 2] == 'A' && grid[i][j + 3] == 'S') {
            count++
        }
    }

    // left
    if (j >= 3) {
        if (grid[i][j] == 'X' && grid[i][j - 1] == 'M' && grid[i][j - 2] == 'A' && grid[i][j - 3] == 'S') {
            count++
        }
    }

    // up
    if (i >= 3) {
        if (grid[i][j] == 'X' && grid[i - 1][j] == 'M' && grid[i - 2][j] == 'A' && grid[i - 3][j] == 'S') {
            count++
        }
    }

    // down
    if (i <= grid.size - 4) {
        if (grid[i][j] == 'X' && grid[i + 1][j] == 'M' && grid[i + 2][j] == 'A' && grid[i + 3][j] == 'S') {
            count++
        }
    }

    // down right
    if (i <= grid.size - 4 && j <= grid[i].size - 4) {
        if (grid[i][j] == 'X' && grid[i + 1][j + 1] == 'M' && grid[i + 2][j + 2] == 'A' && grid[i + 3][j + 3] == 'S') {
            count++
        }
    }

    // down left
    if (i >= 3 && j <= grid[i].size - 4) {
        if (grid[i][j] == 'X' && grid[i - 1][j + 1] == 'M' && grid[i - 2][j + 2] == 'A' && grid[i - 3][j + 3] == 'S') {
            count++
        }
    }

    // up right
    if (i <= grid.size - 4 && j >= 3) {
        if (grid[i][j] == 'X' && grid[i + 1][j - 1] == 'M' && grid[i + 2][j - 2] == 'A' && grid[i + 3][j - 3] == 'S') {
            count++
        }
    }

    // up left
    if (i >= 3 && j >= 3) {
        if (grid[i][j] == 'X' && grid[i - 1][j - 1] == 'M' && grid[i - 2][j - 2] == 'A' && grid[i - 3][j - 3] == 'S') {
            count++
        }
    }

    return count
}

private fun findDiagonalMAS(grid: List<List<Char>>): Map<Pair<Int, Int>, Int> {
    val diagonalMAS = mutableMapOf<Pair<Int, Int>, Int>()

    for (i in grid.indices) {
        for (j in grid[i].indices) {
            if (grid[i][j] == 'M') {
                if (i <= grid.size - 3 && j <= grid[i].size - 3) {
                    if (grid[i + 1][j + 1] == 'A' && grid[i + 2][j + 2] == 'S') {
                        diagonalMAS[Pair(i+1, j+1)] = diagonalMAS.getOrDefault(Pair(i+1, j+1), 0) + 1
                    }
                }
                if (i >= 2 && j <= grid[i].size - 3) {
                    if (grid[i - 1][j + 1] == 'A' && grid[i - 2][j + 2] == 'S') {
                        diagonalMAS[Pair(i-1, j+1)] = diagonalMAS.getOrDefault(Pair(i-1, j+1), 0) + 1
                    }
                }
                if (i <= grid.size - 3 && j >= 2) {
                    if (grid[i + 1][j - 1] == 'A' && grid[i + 2][j - 2] == 'S') {
                        diagonalMAS[Pair(i+1, j-1)] = diagonalMAS.getOrDefault(Pair(i+1, j-1), 0) + 1
                    }
                }
                if (i >= 2 && j >= 2) {
                    if (grid[i - 1][j - 1] == 'A' && grid[i - 2][j - 2] == 'S') {
                        diagonalMAS[Pair(i-1, j-1)] = diagonalMAS.getOrDefault(Pair(i-1, j-1), 0) + 1
                    }
                }
            }
        }
    }

    return diagonalMAS
}