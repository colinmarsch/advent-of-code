package twentytwentythree.day05

import readInput

fun main() {
  fun findMatchKey(map: Map<Pair<Long, Long>, Pair<Long, Long>>, seed: Long): Long {
    val entryMatch = map.entries.firstOrNull { it.key.first <= seed && it.key.second >= seed }
    return if (entryMatch != null) {
      val diff = seed - entryMatch.key.first
      return entryMatch.value.first + diff
    } else {
      seed
    }
  }

  fun part1(input: List<String>): Long {
    val seedToSoil = mutableMapOf<Pair<Long, Long>, Pair<Long, Long>>()
    val soilToFertilizer = mutableMapOf<Pair<Long, Long>, Pair<Long, Long>>()
    val fertilizerToWater = mutableMapOf<Pair<Long, Long>, Pair<Long, Long>>()
    val waterToLight = mutableMapOf<Pair<Long, Long>, Pair<Long, Long>>()
    val lightToTemperature = mutableMapOf<Pair<Long, Long>, Pair<Long, Long>>()
    val temperatureToHumidity = mutableMapOf<Pair<Long, Long>, Pair<Long, Long>>()
    val humidityToLocation = mutableMapOf<Pair<Long, Long>, Pair<Long, Long>>()
    val seeds = mutableSetOf<Long>()

    var currentMap = seedToSoil
    input.forEach { line ->
      val title = line.split(":")[0]
      if (title == "seeds") {
        seeds.addAll(line.split(":")[1].split(" ").mapNotNull { it.toLongOrNull() })
      } else if (title.contains("map")) {
        val mapType = title.split(" ")[0]
        currentMap = when (mapType) {
          "seed-to-soil" -> seedToSoil
          "soil-to-fertilizer" -> soilToFertilizer
          "fertilizer-to-water" -> fertilizerToWater
          "water-to-light" -> waterToLight
          "light-to-temperature" -> lightToTemperature
          "temperature-to-humidity" -> temperatureToHumidity
          "humidity-to-location" -> humidityToLocation
          else -> throw Exception("Unknown map type")
        }
      } else if (line.isNotBlank()) {
        val nums = line.split(" ").mapNotNull { it.toLongOrNull() }
        val destStart = nums[0]
        val sourceStart = nums[1]
        val range = nums[2]
        currentMap[Pair(sourceStart, sourceStart + range)] = Pair(destStart, destStart + range)
      }
    }

    return seeds.minOf { seed ->
      // println("Next seed:")
      // println(seed)
      val soil = findMatchKey(seedToSoil, seed)
      // println(soil)
      val fertilizer = findMatchKey(soilToFertilizer, soil)
      // println(fertilizer)
      val water = findMatchKey(fertilizerToWater, fertilizer)
      // println(water)
      val light = findMatchKey(waterToLight, water)
      // println(water)
      val temperature = findMatchKey(lightToTemperature, light)
      // println(temperature)
      val humidity = findMatchKey(temperatureToHumidity, temperature)
      // println(humidity)
      val location = findMatchKey(humidityToLocation, humidity)
      // println(location)
      location
    }
  }

  fun part2(input: List<String>): Long {
    val seedToSoil = mutableMapOf<Pair<Long, Long>, Pair<Long, Long>>()
    val soilToFertilizer = mutableMapOf<Pair<Long, Long>, Pair<Long, Long>>()
    val fertilizerToWater = mutableMapOf<Pair<Long, Long>, Pair<Long, Long>>()
    val waterToLight = mutableMapOf<Pair<Long, Long>, Pair<Long, Long>>()
    val lightToTemperature = mutableMapOf<Pair<Long, Long>, Pair<Long, Long>>()
    val temperatureToHumidity = mutableMapOf<Pair<Long, Long>, Pair<Long, Long>>()
    val humidityToLocation = mutableMapOf<Pair<Long, Long>, Pair<Long, Long>>()
    val seedRanges = mutableSetOf<Pair<Long, Long>>()

    var currentMap = seedToSoil
    input.forEach { line ->
      val title = line.split(":")[0]
      if (title == "seeds") {
        seedRanges.addAll(
          line.split(":")[1].split(" ").mapNotNull { it.toLongOrNull() }.chunked(2).map {
            val start = it[0]
            val range = it[1]
            Pair(start, start + range)
          })
      } else if (title.contains("map")) {
        val mapType = title.split(" ")[0]
        currentMap = when (mapType) {
          "seed-to-soil" -> seedToSoil
          "soil-to-fertilizer" -> soilToFertilizer
          "fertilizer-to-water" -> fertilizerToWater
          "water-to-light" -> waterToLight
          "light-to-temperature" -> lightToTemperature
          "temperature-to-humidity" -> temperatureToHumidity
          "humidity-to-location" -> humidityToLocation
          else -> throw Exception("Unknown map type")
        }
      } else if (line.isNotBlank()) {
        val nums = line.split(" ").mapNotNull { it.toLongOrNull() }
        val destStart = nums[0]
        val sourceStart = nums[1]
        val range = nums[2]
        currentMap[Pair(sourceStart, sourceStart + range)] = Pair(destStart, destStart + range)
      }
    }

    return seedRanges.minOf { seedRange ->
      val seeds = seedRange.first..seedRange.second
      seeds.minOf { seed ->
        val soil = findMatchKey(seedToSoil, seed)
        val fertilizer = findMatchKey(soilToFertilizer, soil)
        val water = findMatchKey(fertilizerToWater, fertilizer)
        val light = findMatchKey(waterToLight, water)
        val temperature = findMatchKey(lightToTemperature, light)
        val humidity = findMatchKey(temperatureToHumidity, temperature)
        val location = findMatchKey(humidityToLocation, humidity)
        location
      }
    }
  }

  val input = readInput("twentytwentythree/day05", "Day05_input")
  println(part1(input))
  println(part2(input))
}
