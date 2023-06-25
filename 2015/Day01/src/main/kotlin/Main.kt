import java.io.File

fun main() {

    fun part1(input: List<String>): Int {
        val directions = input[0].groupingBy { it }.eachCount()
        val ups = directions['('] ?: 0
        val downs = directions[')'] ?: 0
        return ups - downs
    }

    fun part2(input: List<String>): Int {
        var floor = 0
        for ((index, instruction) in input[0].withIndex()) {
            when (instruction) {
                '(' -> floor++
                ')' -> floor--
                else -> throw Exception("Wrong data in input!")
            }
            if (floor == -1)
                return index + 1
        }
        throw Exception("Never goes to the basement")
    }


// test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == -1)
    check(part2(testInput) == 5)

    val input = readInput("Day01_data")
    println(part1(input))
    println(part2(input))
}

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = File("src", "$name.txt")
    .readLines()

