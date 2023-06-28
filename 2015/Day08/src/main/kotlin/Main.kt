import java.io.File

fun main() {

    fun stringInMemoryLength(raw: String): Int {
        val string = raw.drop(1).dropLast(1)
        var length = 0
        var index = 0
        val lastIndex = string.length - 1
        while (index <= lastIndex) {
            if (string[index] == '\\' && index < lastIndex) {
                when (string[index + 1]) {
                    '\\' -> index += 1
                    '"' -> index += 1
                    'x' -> index += 3
                    else -> {}
                }
            }
            index += 1
            length += 1
        }
        return length
    }

    fun part1(input: List<String>): Int {
        var rawCharsLength = 0
        var inMemoryCharsLength = 0
        input.forEach {
            rawCharsLength += it.length
            inMemoryCharsLength += stringInMemoryLength(it)
        }
        return rawCharsLength - inMemoryCharsLength
    }

    fun escapedStringLength(oldString: String): Int {
        var newString = ""
        oldString.forEach {
            when (it) {
                '\\' -> newString += "\\\\"
                '"' -> newString += "\\\""
                else -> newString += it
            }
        }
        return newString.length + 2
    }

    fun part2(input: List<String>): Int {
        var originalStringLength = 0
        var newStringLength = 0
        input.forEach {
            originalStringLength += it.length
            newStringLength += escapedStringLength(it)
            //           println("(${it.length}) $it ==>  ${stringInMemoryLength(it)}")
        }
        return newStringLength - originalStringLength
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day08_test")
    check(part1(testInput) == 12)
    check(part2(testInput) == 19)

    val input = readInput("Day08_data")
    println("Part 1 answer: ${part1(input)}")
    println("Part 2 answer: ${part2(input)}")
}

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = File("src", "$name.txt")
    .readLines()

