package twentytwentythree.day08

import readInput

fun main() {
  fun Set<String>.allZ(): Boolean {
    return this.all { it.last() == 'Z' }
  }

  fun part1(input: List<String>): Int {
    val directions = input[0].toList()
    val nodeMap = mutableMapOf<String, Pair<String, String>>()
    input.subList(2, input.size).forEach { line ->
      val splits = line.split(" = ")
      val nodeName = splits[0]
      val nodeLeft = splits[1].split(",")[0].substring(1)
      val nodeRight =
        splits[1].split(",")[1].substring(0, splits[1].split(",")[1].length - 1).trim()
      nodeMap[nodeName] = Pair(nodeLeft, nodeRight)
    }

    var currentNode = "AAA"
    var stepCount = 0
    while (currentNode != "ZZZ") {
      val currentValue = nodeMap[currentNode]!!
      val currentDirection = directions[stepCount++ % directions.size]
      currentNode = if (currentDirection == 'L') {
        currentValue.first
      } else {
        currentValue.second
      }
    }
    return stepCount
  }

  fun part2(input: List<String>): Long {
    val directions = input[0].toList()
    val nodeMap = mutableMapOf<String, Pair<String, String>>()
    val startingNodes = mutableSetOf<String>()
    input.subList(2, input.size).forEach { line ->
      val splits = line.split(" = ")
      val nodeName = splits[0]
      val nodeLeft = splits[1].split(",")[0].substring(1)
      val nodeRight =
        splits[1].split(",")[1].substring(0, splits[1].split(",")[1].length - 1).trim()
      nodeMap[nodeName] = Pair(nodeLeft, nodeRight)
      if (nodeName.last() == 'A') {
        startingNodes.add(nodeName)
      }
    }

    var currentNodes = startingNodes
    var stepCount = 0L
    while (!currentNodes.allZ()) {
      val newNodes = mutableSetOf<String>()
      currentNodes.forEach { currentNode ->
        val currentValue = nodeMap[currentNode]!!
        val currentDirection = directions[(stepCount % directions.size).toInt()]
        newNodes.add(
          if (currentDirection == 'L') {
            currentValue.first
          } else {
            currentValue.second
          }
        )
      }
      currentNodes = newNodes
      stepCount++
    }
    return stepCount
  }

  val input = readInput("twentytwentythree/day08", "Day08_input")
  println(part1(input))
  println(part2(input))
}
