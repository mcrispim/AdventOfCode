import java.io.File

fun main() {

    fun part1(input: List<String>): Int {

        // Rule 1
        // It contains at least three vowels (aeiou only), like aei, xazegov, or aeiouaeiouaeiou.
        fun String.rule1Nice(): Boolean = this.split(Regex("(a|e|i|o|u)")).size > 3

        // Rule 2
        // It contains at least one letter that appears twice in a row, like xx, abcdde (dd), or aabbccdd (aa, bb, cc, or dd).
        fun String.rule2Nice(): Boolean = Regex(".*(.)\\1{1,}.*").matches(this)

        // Rule 3
        // It does not contain the strings ab, cd, pq, or xy, even if they are part of one of the other requirements.
        fun String.rule3Nice(): Boolean = this.split(Regex("(ab|cd|pq|xy)")).size <= 1

        var nices = 0
        for (string in input) {
            if (string.rule1Nice() && string.rule2Nice() && string.rule3Nice()) {
                nices++
//                println("=> $string - nice")
            } else {
//                println("=> $string - naughty")
            }
        }
        return nices
    }

    fun part2(input: List<String>): Int {

        // Rule 1
        // It contains a pair of any two letters that appears at least twice in the string without overlapping,
        // like xyxy (xy) or aabcdefgaa (aa), but not like aaa (aa, but it overlaps).
        fun String.rule1Nice(): Boolean = Regex(".*(.)(.).*\\1\\2.*").matches(this)

        // Rule 2
        // It contains at least one letter which repeats with exactly one letter between them, like xyx,
        // abcdefeghi (efe), or even aaa.
        fun String.rule2Nice(): Boolean = Regex(".*(.).\\1.*").matches(this)

        var nices = 0
        for (string in input) {
            if (string.rule1Nice() && string.rule2Nice()) {
                nices++
            }
        }
        return nices
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_test")
    check(part1(testInput) == 2)
    check(part2(testInput) == 0)

    val input = readInput("Day05_data")
    println("Part 1 answer: ${part1(input)}")
    println("Part 2 answer: ${part2(input)}")
}

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = File("src", "$name.txt")
    .readLines()

