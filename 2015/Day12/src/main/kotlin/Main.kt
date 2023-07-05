import java.io.File

fun main() {

    fun part1(input: List<String>): Int {
        val jason = input[0]
        var sum = 0
        var index = 0
        while (index < jason.length) {
            val c = jason[index]
            when (c) {
                in setOf('-', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9') -> {
                    var value = "$c"
                    index++
                    while (index < jason.length && jason[index] in '0'..'9') {
                        value += jason[index]
                        index++
                    }
                    sum += value.toInt()
                }

                '\"' -> {
                    while (index < jason.length && jason[index] != '\"') {
                        index++
                    }
                    index++
                }

                else -> index++
            }
        }
        return sum
    }

    fun part2(input: List<String>): Int {
        var jason = input[0]
        val openBrackets = mutableListOf<Pair<Int, Int>>()
        var sum = 0
        var index = 0
        while (index < jason.length) {
            val c = jason[index]
            when (c) {
                in setOf('-', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9') -> {
                    var value = "$c"
                    index++
                    while (index < jason.length && jason[index] in '0'..'9') {
                        value += jason[index]
                        index++
                    }
                    sum += value.toInt()
                }

                '\"' -> {
                    while (index < jason.length && jason[index] != '\"') {
                        index++
                    }
                    index++
                }

                '{' -> {
                    openBrackets.add(Pair(index, sum))
                    sum = 0
                    index++
                }

                '}' -> {
                    val (startIndex, previousSum) = openBrackets.removeLast()
                    val objStr = jason.substring(startIndex, index + 1)
                    if (objStr.contains(":\"red\"")) {
                        sum = previousSum
                    } else {
                        sum += previousSum
                    }
                    jason = jason.substring(0, startIndex) + jason.substring(index + 1)
                    index = startIndex
                }

                else -> index++
            }
        }
        return sum
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day12_test")
    check(part1(testInput) == 12)
    check(part2(testInput) == 9)

    val input = readInput("Day12_data")
    println("Part 1 answer: ${part1(input)}")
    println("Part 2 answer: ${part2(input)}")
}

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = File("src", "$name.txt").readLines()

