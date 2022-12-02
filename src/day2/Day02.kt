fun main() {
    fun part1(input: List<String>): Int = input.sumOf { round ->
        val choices = round.split(" ")
        val them = choices[0]
        val me = choices[1]
        win(them, me) + extraScore(me)
    }

    fun part2(input: List<String>): Int = input.sumOf { round ->
        val choices = round.split(" ")
        val them = choices[0]
        val result = choices[1]
        val me = myChoice(them, result)
        extraScore(me) + win(them, me)
    }

    val input = readInput("day2", "Day02_input")
    println(part1(input))
    println(part2(input))
}

private fun win(them: String, me: String): Int = when (them) {
    "A" -> {
        when (me) {
            "X", "A" -> 3
            "Y", "B" -> 6
            "Z", "C" -> 0
            else -> 0
        }
    }
    "B" -> when (me) {
        "X", "A" -> 0
        "Y", "B" -> 3
        "Z", "C" -> 6
        else -> 0
    }
    "C" -> when (me) {
        "X", "A" -> 6
        "Y", "B" -> 0
        "Z", "C" -> 3
        else -> 0
    }
    else -> 0
}

private fun extraScore(me: String): Int = when (me) {
    "X", "A" -> 1
    "Y", "B" -> 2
    "Z", "C" -> 3
    else -> 0
}

private fun myChoice(them: String, result: String): String = when (them) {
    "A" -> {
        when (result) {
            "X" -> "C"
            "Y" -> "A"
            "Z" -> "B"
            else -> ""
        }
    }
    "B" -> when (result) {
        "X" -> "A"
        "Y" -> "B"
        "Z" -> "C"
        else -> ""
    }
    "C" -> when (result) {
        "X" -> "B"
        "Y" -> "C"
        "Z" -> "A"
        else -> ""
    }
    else -> ""
}
