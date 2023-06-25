import java.io.File

fun main() {

    fun surface(l: Int, w: Int, h: Int) =  2 * l * w + 2 * w * h + 2 * h * l

    fun extraPaper(l: Int, w: Int, h: Int): Int {
        val (dim1, dim2) = listOf(l, w, h).sorted().take(2)
        return dim1 * dim2
    }

    fun ribbon(l: Int, w: Int, h: Int): Int {
        val (dim1, dim2) = listOf(l, w, h).sorted().take(2)
        return 2 * dim1  + 2 * dim2
    }

    fun bow(l: Int, w: Int, h: Int) = l * w * h

    fun part1(input: List<String>): Int {
        var paper = 0
        for (box in input) {
            val (l, w, h) = box.split("x").map { it.toInt() }
            paper += surface(l, w, h) + extraPaper(l, w, h)
        }
        return paper
    }

    fun part2(input: List<String>): Int {
        var totalRibbon = 0
        for (box in input) {
            val (l, w, h) = box.split("x").map { it.toInt() }
            totalRibbon += ribbon(l, w, h) + bow(l, w, h)
        }
        return totalRibbon
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 101)
    check(part2(testInput) == 48)

    val input = readInput("Day02_data")
    println(part1(input))
    println(part2(input))
}

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = File("src", "$name.txt")
    .readLines()

