package twentytwentythree.day07

import readInput
import kotlin.math.max

enum class HandType(val value: Int) {
  FIVE_OF_A_KIND(7),
  FOUR_OF_A_KIND(6),
  FULL_HOUSE(5),
  THREE_OF_A_KIND(4),
  TWO_PAIR(3),
  ONE_PAIR(2),
  HIGH_CARD(1),
}

class Card(private val value: Char) : Comparable<Card> {
  override fun compareTo(other: Card): Int {
    return ORDER.indexOf(this.value) - ORDER.indexOf(other.value)
  }

  companion object {
    // Define the order of cards from highest to lowest
    private val ORDER = listOf('A', 'K', 'Q', 'J', 'T', '9', '8', '7', '6', '5', '4', '3', '2')
  }
}

fun main() {
  class Hand(val value: String) : Comparable<Hand> {
    val cardsMap = value.toList().groupBy { it }
    override fun compareTo(other: Hand): Int {
      val thisType = determineHandType(this)
      val otherType = determineHandType(other)

      val typeComparison = thisType.compareTo(otherType)
      if (typeComparison != 0) {
        return typeComparison
      }

      // If both hands have the same type, compare them card by card
      for (i in value.indices) {
        val thisCard = Card(value[i])
        val otherCard = Card(other.value[i])

        val cardComparison = thisCard.compareTo(otherCard)
        if (cardComparison != 0) {
          return cardComparison
        }
      }

      return 0 // Both hands are equal
    }

    private fun determineHandType(hand: Hand): HandType {
      val maxCount = hand.cardsMap.values.maxOfOrNull {
        it.size
      } ?: 0
      val maxCard = hand.cardsMap.entries.maxByOrNull { it.value.size }!!.key

      val tempHand = hand.cardsMap.toMutableMap()
      tempHand.remove(maxCard)

      val secondMaxCount =
        tempHand.values.maxOfOrNull { if (it.size <= maxCount) it.size else 0 } ?: 0

      return when {
        maxCount == 5 -> HandType.FIVE_OF_A_KIND
        maxCount == 4 -> HandType.FOUR_OF_A_KIND
        maxCount == 3 && secondMaxCount == 2 -> HandType.FULL_HOUSE
        maxCount == 3 -> HandType.THREE_OF_A_KIND
        maxCount == 2 && secondMaxCount == 2 -> HandType.TWO_PAIR
        maxCount == 2 -> HandType.ONE_PAIR
        else -> HandType.HIGH_CARD
      }
    }
  }

  fun part1(input: List<String>): Long {
    var index = 1
    val sorted = input.sortedByDescending { line ->
      Hand(line.split(" ")[0])
    }
    return sorted.sumOf { line ->
      line.split(" ")[1].toLong() * index++
    }
  }

  fun part2(input: List<String>): Int {
    return 0
  }

  val input = readInput("twentytwentythree/day07", "Day07_input")
  println(part1(input))
  println(part2(input))
}
