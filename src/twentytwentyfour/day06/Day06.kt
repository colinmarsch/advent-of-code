package twentytwentyfour.day06

import readInput

fun main() {
  fun part1(input: List<String>): Int {
    val map = input.map { it.toMutableList() }

    // find the starting pos of the guard
    var startingX: Int? = null
    var startingY: Int? = null
    val guardSet = setOf('^', 'v', '<', '>')
    for (y in map.indices) {
      for (x in map[y].indices) {
        if (map[y][x] in guardSet) {
          startingX = x
          startingY = y
          break
        }
      }
    }

    // iterate through their whole path, counting steps
    var x = startingX!!
    var y = startingY!!
    while (true) {
      val current = map[y][x]
      when (current) {
        '^' -> {
          y--
          if (y !in map.indices || x !in map[y].indices) {
            break
          }
          if (map[y][x] == '#') {
            y++
            map[y][x] = '>'
          } else {
            map[y][x] = '^'
          }
        }
        'v' -> {
          y++
          if (y !in map.indices || x !in map[y].indices) {
            break
          }
          if (map[y][x] == '#') {
            y--
            map[y][x] = '<'
          } else {
            map[y][x] = 'v'
          }
        }
        '<' -> {
          x--
          if (y !in map.indices || x !in map[y].indices) {
            break
          }
          if (map[y][x] == '#') {
            x++
            map[y][x] = '^'
          } else {
            map[y][x] = '<'
          }
        }
        '>' -> {
          x++
          if (y !in map.indices || x !in map[y].indices) {
            break
          }
          if (map[y][x] == '#') {
            x--
            map[y][x] = 'v'
          } else {
            map[y][x] = '>'
          }
        }
      }
    }
    return map.sumOf {
        it.count { c -> c == '^' || c == 'v' || c == '<' || c == '>' }
    }
  }

  fun part2(input: List<String>): Int {
    val map = input.map { it.toMutableList() }
    val pathCoordMap = input.map { it.toMutableList() }

    // find the starting pos of the guard
    var startingX: Int? = null
    var startingY: Int? = null
    val guardSet = setOf('^', 'v', '<', '>')
    for (y in map.indices) {
      for (x in map[y].indices) {
        if (map[y][x] in guardSet) {
          startingX = x
          startingY = y
          break
        }
      }
    }

    val pathCoordSet = buildPathSet(List(map.size) { idx -> pathCoordMap[idx] }, startingX!!, startingY!!)
    pathCoordSet.remove(Pair(startingX, startingY))

    var loops = 0
    pathCoordSet.forEach { coord ->
      val newMap = input.map { it.toMutableList() }
      if (findLoop(newMap, startingX!!, startingY!!, coord)) {
        loops++
      }
    }

    return loops
  }

  val input = readInput("twentytwentyfour/day06", "Day06_input")
  println(part1(input))
  println(part2(input))
}

private fun findLoop(map: List<MutableList<Char>>, startingX: Int, startingY: Int, coord: Pair<Int, Int>): Boolean {
  var x = startingX
  var y = startingY
  val prevValue = map[coord.second][coord.first]
  map[coord.second][coord.first] = '#'
  val seenMap = mutableMapOf<Pair<Int, Int>, MutableSet<Char>>()
  var loop = false
  while (!loop) {
    val current = map[y][x]
    val seen = seenMap.getOrDefault(Pair(x, y), mutableSetOf())
    seen.add(current)
    seenMap[Pair(x, y)] = seen
    when (current) {
      '^' -> {
        y--
        if (y !in map.indices || x !in map[y].indices) {
          break
        }
        if (map[y][x] == '#') {
          y++
          map[y][x] = '>'
        } else {
          map[y][x] = '^'
        }
      }
      'v' -> {
        y++
        if (y !in map.indices || x !in map[y].indices) {
          break
        }
        if (map[y][x] == '#') {
          y--
          map[y][x] = '<'
        } else {
          map[y][x] = 'v'
        }
      }
      '<' -> {
        x--
        if (y !in map.indices || x !in map[y].indices) {
          break
        }
        if (map[y][x] == '#') {
          x++
          map[y][x] = '^'
        } else {
          map[y][x] = '<'
        }
      }
      '>' -> {
        x++
        if (y !in map.indices || x !in map[y].indices) {
          break
        }
        if (map[y][x] == '#') {
          x--
          map[y][x] = 'v'
        } else {
          map[y][x] = '>'
        }
      }
    }
    if (seenMap[Pair(x, y)]?.contains(map[y][x]) == true) {
      loop = true
      break
    }
  }
  map[coord.second][coord.first] = prevValue
  return loop
}

private fun buildPathSet(map: List<List<Char>>, x: Int, y: Int) : MutableSet<Pair<Int, Int>> {
  var x = x
  var y = y
  val map = map.map { it.toMutableList() }
  while (true) {
    val current = map[y][x]
    when (current) {
      '^' -> {
        y--
        if (y !in map.indices || x !in map[y].indices) {
          break
        }
        if (map[y][x] == '#') {
          y++
          map[y][x] = '>'
        } else {
          map[y][x] = '^'
        }
      }
      'v' -> {
        y++
        if (y !in map.indices || x !in map[y].indices) {
          break
        }
        if (map[y][x] == '#') {
          y--
          map[y][x] = '<'
        } else {
          map[y][x] = 'v'
        }
      }
      '<' -> {
        x--
        if (y !in map.indices || x !in map[y].indices) {
          break
        }
        if (map[y][x] == '#') {
          x++
          map[y][x] = '^'
        } else {
          map[y][x] = '<'
        }
      }
      '>' -> {
        x++
        if (y !in map.indices || x !in map[y].indices) {
          break
        }
        if (map[y][x] == '#') {
          x--
          map[y][x] = 'v'
        } else {
          map[y][x] = '>'
        }
      }
    }
  }
  return map.flatMapIndexed { y, list ->
    list.mapIndexedNotNull { x, c ->
      if (c == '^' || c == 'v' || c == '<' || c == '>') {
        Pair(x, y)
      } else {
        null
      }
    }
  }.toMutableSet()
}
