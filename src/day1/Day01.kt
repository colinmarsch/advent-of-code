import java.lang.Integer.max

fun main() {
    fun part1(input: List<String>): Int {
        var maxCalorie = 0
        var currentCalorieCount = 0
        var currentIndex = 0

        input.forEach { line ->
            val currentInt = line.toIntOrNull()
            if (currentInt != null) {
                currentCalorieCount += currentInt
            } else {
                if (currentCalorieCount > maxCalorie) {
                    maxCalorie = currentCalorieCount
                }
                currentCalorieCount = 0
                currentIndex++
            }
        }
        return maxCalorie
    }

    fun part2(input: List<String>): Int {
        var maxCalorie = 0
        var currentCalorieCount = 0
        var currentIndex = 0

        val calorieList = mutableListOf<Int>()
        input.forEach { line ->
            val currentInt = line.toIntOrNull()
            if (currentInt != null) {
                currentCalorieCount += currentInt
            } else {
                if (currentCalorieCount > maxCalorie) {
                    maxCalorie = currentCalorieCount
                }
                calorieList.add(currentCalorieCount)
                currentCalorieCount = 0
                currentIndex++
            }
        }

        calorieList.sortDescending()

        return calorieList[0] + calorieList[1] + calorieList[2]
    }

    val input = readInput("day1", "Day01_input")
    println(part1(input))
    println(part2(input))
}
