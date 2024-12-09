package twentytwentyfour.day07

import readInput

fun main() {
    fun part1(input: List<String>): Long {
        val operators = setOf(" + ", " * ")
        return input.sumOf { line ->
            val parts = line.split(" ")
            val testValue = parts[0].substringBefore(":").toLong()
            val numbers = parts.subList(1, parts.size).map { it.toLong() }
            return@sumOf testExpressions(numbers, testValue, operators)
        }
    }

    fun part2(input: List<String>): Long {
        val operators = setOf(" + ", " * ", " || ")
        return input.sumOf { line ->
            val parts = line.split(" ")
            val testValue = parts[0].substringBefore(":").toLong()
            val numbers = parts.subList(1, parts.size).map { it.toLong() }
            return@sumOf testExpressions(numbers, testValue, operators)
        }
    }

    val input = readInput("twentytwentyfour/day07", "Day07_input")
    println(part1(input))
    println(part2(input))
}

private fun expressionParser(expression: String): Long {
    val parts = expression.split(" ").filter { it.isNotEmpty() }
    var runningTotal = 0L
    var lastOperator = "+"
    for (part in parts) {
        when (part) {
            "+" -> lastOperator = "+"
            "*" -> lastOperator = "*"
            "||" -> lastOperator = "||"
            else -> {
                val number = part.toLong()
                runningTotal = when (lastOperator) {
                    "+" -> runningTotal + number
                    "*" -> runningTotal * number
                    "||" -> "$runningTotal$number".toLong()
                    else -> error("Invalid operator")
                }
            }
        }
    }
    return runningTotal
}

private fun testExpressions(numbers: List<Long>, testValue: Long, operators: Set<String>, runningExpression: String = ""): Long {
    if (numbers.isEmpty()) {
        return if (testValue == expressionParser(runningExpression)) testValue else 0
    }

    val number = numbers.first()
    val remainingNumbers = numbers.subList(1, numbers.size)
    return operators.maxOf { operator ->
        testExpressions(remainingNumbers, testValue, operators, if (runningExpression.isNotBlank()) "$runningExpression$operator$number" else "$number$operator")
    }
}
