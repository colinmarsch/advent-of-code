package twentytwentytwo.day16

import twentytwentytwo.day12.Graph
import readInput

fun main() {
    data class ValveNode(
        val id: String,
        val flowRate: Int,
        val neighbours: MutableSet<Pair<String, Int>>,
    )

    val graph = mutableMapOf<String, ValveNode>()
    var availableMinutes = 30
    val startValve by lazy { graph["AA"]!! }

    fun createGraph(input: List<String>): ValveNode {
        input
            .sortedBy { it.split(" ")[1] }
            .forEach {
                val id = it.split(" ")[1]
                val flowRate = it.split(" ")[4].dropLast(1).split("=")[1].toInt()
                val neighbours =
                    it.split(" to ")[1].split(" ").drop(1).map { it.replace(",", "") }
                        .map { Pair(it, 1) }.toMutableSet()
                val valveNode = ValveNode(id, flowRate, neighbours)
                graph[id] = valveNode
            }

        val weights = mutableMapOf<Pair<String, String>, Int>()
        graph.values.forEach { node ->
            node.neighbours.forEach { neighbour ->
                weights[Pair(node.id, neighbour.first)] = 1
            }
        }

        val dijkstraGraph = Graph(
            vertices = graph.values.map { it.id }.toMutableSet(),
            edges = graph.values.map { it.id }
                .associateWith { graph[it]!!.neighbours.map { it.first }.toSet() }.toMutableMap(),
            weights = weights,
        )

        graph.keys.forEach { id ->
            val currNode = graph[id]!!
            val otherNodes = graph.values.toSet() - currNode.neighbours.map { graph[it.first]!! }
                .toSet() - currNode
            val shortestPathTree = dijkstra(dijkstraGraph, currNode.id)
            otherNodes.forEach { other ->
                currNode.neighbours.removeIf { it.first == other.id }
                if (other.flowRate > 0) {
                    val path = shortestPath(shortestPathTree, currNode.id, other.id)
                    currNode.neighbours.add(Pair(other.id, path.size - 1))
                }
            }
        }

        return graph["AA"]!!
    }

    var maxPressureReleased = 0

    fun findMaxPath(
        releasedPressure: Int,
        position: ValveNode,
        visited: Set<ValveNode>,
        minute: Int,
        elephant: Boolean
    ) {
        maxPressureReleased = maxOf(releasedPressure, maxPressureReleased)

        position.neighbours.filter { graph[it.first]!!.flowRate > 0 }.forEach { (id, distance) ->
            val newMinute = minute + distance + 1
            val candidate = graph[id]!!
            if (newMinute < availableMinutes && candidate !in visited) {
                findMaxPath(
                    releasedPressure = releasedPressure + (availableMinutes - newMinute) * candidate.flowRate,
                    position = candidate,
                    visited = visited + candidate,
                    minute = newMinute,
                    elephant = elephant,
                )
            }
        }
        if (elephant) {
            findMaxPath(
                releasedPressure = releasedPressure,
                position = startValve,
                visited = visited,
                minute = 0,
                elephant = false,
            )
        }
    }

    fun part1(input: List<String>): Int {
        val root = createGraph(input)
        maxPressureReleased = 0
        availableMinutes = 30
        findMaxPath(0, root, emptySet(), 0, false)
        return maxPressureReleased
    }

    fun part2(input: List<String>): Int {
        val root = createGraph(input)
        maxPressureReleased = 0
        availableMinutes = 26
        findMaxPath(0, root, emptySet(), 0, true)
        return maxPressureReleased
    }

    val input = readInput("day16", "Day16_input")
    println(part1(input))
    println(part2(input))
}

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

