import java.io.File

typealias Position = Pair<Int, Int>

fun main() {

    fun part1(input: List<String>): Int {
        var noelPosition = Position(0, 0)
        val houses = mutableSetOf(noelPosition)
        for (direction in input[0]) {
            when (direction) {
                '^' -> noelPosition = Position(noelPosition.first, noelPosition.second + 1)
                'v' -> noelPosition = Position(noelPosition.first, noelPosition.second - 1)
                '<' -> noelPosition = Position(noelPosition.first - 1, noelPosition.second)
                '>' -> noelPosition = Position(noelPosition.first + 1, noelPosition.second)
                else -> throw Exception("Unexpected direction: $direction")
            }
            houses.add(noelPosition)
        }
        return houses.size
    }

    fun part2(input: List<String>): Int {

        fun changeDriver(driver: String) = if (driver == "noel") "robot" else "noel"

        var driver = "noel"
        var noelPosition = Position(0, 0)
        var robotPosition = Position(0, 0)
        val houses = mutableSetOf(noelPosition)
        for (direction in input[0]) {
            var position = if (driver == "noel") noelPosition else robotPosition
            when (direction) {
                '^' -> position = Position(position.first, position.second + 1)
                'v' -> position = Position(position.first, position.second - 1)
                '<' -> position = Position(position.first - 1, position.second)
                '>' -> position = Position(position.first + 1, position.second)
                else -> throw Exception("Unexpected direction: $direction")
            }
            houses.add(position)
            if (driver == "noel") {
                noelPosition = position
            } else {
                robotPosition = position
            }
            driver = changeDriver(driver)
        }
        return houses.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 4)
    check(part2(testInput) == 3)

    val input = readInput("Day03_data")
    println("Part 1 answer: ${part1(input)}")
    println("Part 2 answer: ${part2(input)}")
}

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = File("src", "$name.txt")
    .readLines()

