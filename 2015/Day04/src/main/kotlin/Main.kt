import java.io.File
import java.math.BigInteger
import java.security.MessageDigest

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')

fun main() {

    fun part1(input: List<String>): Int {
        val key = input[0]
        var number = 1
        while ("${key}${number}".md5().startsWith("00000") == false) {
            number++
        }
        return number
    }


    fun part2(input: List<String>): Int {
        val key = input[0]
        var number = 1
        while ("${key}${number}".md5().startsWith("000000") == false) {
            number++
        }
        return number
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    check(part1(testInput) == 609043)
    check(part2(testInput) == 6742839)

    val input = readInput("Day04_data")
    println("Part 1 answer: ${part1(input)}")
    println("Part 2 answer: ${part2(input)}")
}

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = File("src", "$name.txt")
    .readLines()

