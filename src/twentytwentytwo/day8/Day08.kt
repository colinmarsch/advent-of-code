package twentytwentytwo.day8

import readInput

fun main() {
    fun part1(input: List<String>): Int {
        val trees = mutableListOf<List<Int>>()
        input.forEach { trees.add(it.chunked(1).map { it.toInt() }) }

        var visibleTrees = trees.size * 4 - 4
        for (i in 1 until trees.size - 1) {
            for (j in 1 until trees[0].size - 1) {
                if (isVisible(i, j, trees)) visibleTrees++
            }
        }
        return visibleTrees
    }

    fun part2(input: List<String>): Int {
        val trees = mutableListOf<List<Int>>()
        input.forEach { trees.add(it.chunked(1).map { it.toInt() }) }

        var maxScenicScore = 0
        for (i in 1 until trees.size - 1) {
            for (j in 1 until trees[0].size - 1) {
                val currScore = scenicScore(i, j, trees)
                if (currScore > maxScenicScore) {
                    maxScenicScore = currScore
                }
            }
        }
        return maxScenicScore
    }

    val input = readInput("day8", "Day08_input")
    println(part1(input))
    println(part2(input))
}

private fun isVisible(i: Int, j: Int, trees: List<List<Int>>): Boolean {
    val height = trees[i][j]

    var visible = true
    for (q in 0 until i) {
        if (trees[q][j] >= height) {
            visible = false
            break
        }
    }
    if (visible) return true

    visible = true
    for (w in i + 1 until trees.size) {
        if (trees[w][j] >= height) {
            visible = false
            break
        }
    }
    if (visible) return true

    visible = true
    for (e in 0 until j) {
        if (trees[i][e] >= height) {
            visible = false
            break
        }
    }
    if (visible) return true

    visible = true
    for (r in j + 1 until trees.size) {
        if (trees[i][r] >= height) {
            visible = false
            break
        }
    }
    return visible
}

private fun scenicScore(i: Int, j: Int, trees: List<List<Int>>): Int {
    val height = trees[i][j]
    var totalScore = 1

    var distance = 0
    for (q in i - 1 downTo 0) {
        distance++
        if (trees[q][j] >= height) {
            break
        }
    }
    totalScore *= distance

    distance = 0
    for (w in i + 1 until trees.size) {
        distance++
        if (trees[w][j] >= height) {
            break
        }
    }
    totalScore *= distance

    distance = 0
    for (e in j - 1 downTo 0) {
        distance++
        if (trees[i][e] >= height) {
            break
        }
    }
    totalScore *= distance

    distance = 0
    for (r in j + 1 until trees.size) {
        distance++
        if (trees[i][r] >= height) {
            break
        }
    }
    totalScore *= distance

    return totalScore
}

