package day12

import readInput

fun main() {
    fun part1(input: List<String>): Int {
        val matrix = mutableListOf<List<Char>>()
        input.forEach { matrix.add(it.toList()) }

        val graph = Graph<Square>()
        lateinit var start: Square
        lateinit var end: Square

        for (i in 0 until matrix.size) {
            for (j in 0 until matrix[0].size) {
                var isStart = false
                var isEnd = false
                val char = if (matrix[i][j] == 'S') {
                    isStart = true
                    'a'
                } else if (matrix[i][j] == 'E') {
                    isEnd = true
                    'z'
                } else {
                    matrix[i][j]
                }

                val square = Square(char, i, j)
                if (isStart) start = square
                if (isEnd) end = square

                graph.vertices.add(square)
                val neighbours = mutableSetOf<Square>()
                if (i - 1 >= 0) {
                    neighbours.add(Square(matrix[i - 1][j], i - 1, j))
                }
                if (i + 1 < matrix.size) {
                    neighbours.add(Square(matrix[i + 1][j], i + 1, j))
                }
                if (j - 1 >= 0) {
                    neighbours.add(Square(matrix[i][j - 1], i, j - 1))
                }
                if (j + 1 < matrix[0].size) {
                    neighbours.add(Square(matrix[i][j + 1], i, j + 1))
                }

                val updatedNeighbours = neighbours.map {
                    when (it.height) {
                        'S' -> {
                            it.copy(height = 'a')
                        }

                        'E' -> {
                            it.copy(height = 'z')
                        }

                        else -> {
                            it
                        }
                    }
                }.toSet()
                graph.edges[square] = updatedNeighbours
                val weights = updatedNeighbours.map {
                    Pair(square, it)
                }.associateWith {
                    if (it.second.height - it.first.height > 1) 500000 else 1
                }
                graph.weights.putAll(weights)
            }
        }

        val shortestPathTree = dijkstra(graph, start)

        return shortestPath(shortestPathTree, start, end).size - 1
    }

    fun part2(input: List<String>): Int {
        val matrix = mutableListOf<List<Char>>()
        input.forEach { matrix.add(it.toList()) }

        val graph = Graph<Square>()
        val possibleStarts = mutableListOf<Square>()
        lateinit var end: Square

        for (i in 0 until matrix.size) {
            for (j in 0 until matrix[0].size) {
                var isStart = false
                var isEnd = false
                val char = if (matrix[i][j] == 'S' || matrix[i][j] == 'a') {
                    isStart = true
                    'a'
                } else if (matrix[i][j] == 'E') {
                    isEnd = true
                    'z'
                } else {
                    matrix[i][j]
                }

                val square = Square(char, i, j)
                if (isStart) possibleStarts.add(square)
                if (isEnd) end = square

                graph.vertices.add(square)
                val neighbours = mutableSetOf<Square>()
                if (i - 1 >= 0) {
                    neighbours.add(Square(matrix[i - 1][j], i - 1, j))
                }
                if (i + 1 < matrix.size) {
                    neighbours.add(Square(matrix[i + 1][j], i + 1, j))
                }
                if (j - 1 >= 0) {
                    neighbours.add(Square(matrix[i][j - 1], i, j - 1))
                }
                if (j + 1 < matrix[0].size) {
                    neighbours.add(Square(matrix[i][j + 1], i, j + 1))
                }

                val updatedNeighbours = neighbours.map {
                    when (it.height) {
                        'S' -> {
                            it.copy(height = 'a')
                        }

                        'E' -> {
                            it.copy(height = 'z')
                        }

                        else -> {
                            it
                        }
                    }
                }.toSet()
                graph.edges[square] = updatedNeighbours
                val weights = updatedNeighbours.map {
                    Pair(square, it)
                }.associateWith {
                    if (it.first.height - it.second.height > 1) 500000 else 1
                }
                graph.weights.putAll(weights)
            }
        }


        val shortestPathTrees = dijkstra(graph, end)
        val pathes =
            possibleStarts.map { shortestPath(shortestPathTrees, end, it) }.filter { it.size != 1 }
        return pathes.minOf { it.size - 1 }
    }

    val input = readInput("day12", "Day12_input")
    println(part1(input))
    println(part2(input))
}

data class Square(
    val height: Char,
    val row: Int,
    val col: Int,
)

data class Graph<T>(
    val vertices: MutableSet<T> = mutableSetOf(),
    val edges: MutableMap<T, Set<T>> = mutableMapOf(),
    val weights: MutableMap<Pair<T, T>, Int> = mutableMapOf(),
)

private fun <T> dijkstra(graph: Graph<T>, start: T): Map<T, T?> {
    val vertexSet: MutableSet<T> =
        mutableSetOf() // a subset of vertices, for which we know the true distance

    val delta = graph.vertices.associateWith { 100000 }.toMutableMap()
    delta[start] = 0

    val previous: MutableMap<T, T?> = graph.vertices.associateWith { null }.toMutableMap()

    while (vertexSet != graph.vertices) {
        val v: T = delta
            .filter { !vertexSet.contains(it.key) }
            .minBy { it.value }
            .key

        graph.edges.getValue(v).minus(vertexSet).forEach { neighbor ->
            val newPath = delta.getValue(v) + graph.weights.getValue(Pair(v, neighbor))

            if (newPath < delta.getValue(neighbor)) {
                delta[neighbor] = newPath
                previous[neighbor] = v
            }
        }

        vertexSet.add(v)
    }

    return previous.toMap()
}

private fun <T> shortestPath(shortestPathTree: Map<T, T?>, start: T, end: T): List<T> {
    fun pathTo(start: T, end: T): List<T> {
        if (shortestPathTree[end] == null) return listOf(end)
        return listOf(pathTo(start, shortestPathTree[end]!!), listOf(end)).flatten()
    }

    return pathTo(start, end)
}
