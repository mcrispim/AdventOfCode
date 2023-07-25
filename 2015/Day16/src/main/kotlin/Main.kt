import java.io.File

typealias Auntie = Map<String, Int>
typealias SearchAuntie = Map<String, Pair<String, Int>>

val aunties = mutableListOf<Auntie>()

fun readAunties(input: List<String>): List<Auntie> {
    input.forEach { line ->
        val auntie = mutableMapOf<String, Int>()
        val properties = line.substringAfter(":").split(",")
        properties.forEach { property ->
            val (key, value) = property.split(":").map { it.trim() }
            auntie[key] = value.toInt()
        }
        aunties.add(auntie)
    }
    return aunties
}

fun main() {

    fun compareAunties(
        auntie: Auntie,
        searchAuntie: Auntie,
        greaterProperties: Set<String>,
        lesserProperties: Set<String>
    ): Boolean {
        for ((key, value) in auntie) {
            if (!searchAuntie.containsKey(key)) return false
            if (key in greaterProperties && value <= searchAuntie[key]!!) return false
            if (key in lesserProperties && value >= searchAuntie[key]!!) return false
            if (key !in  greaterProperties && key !in lesserProperties && value != searchAuntie[key]!!) return false
        }
        return true
    }

    fun searchAuntie(
        searchAuntie: Auntie,
        greaterProperties: Set<String> = setOf(),
        lesserProperties: Set<String> = setOf()
    ): Int {
        aunties.forEachIndexed { index, auntie ->
            val found = compareAunties(auntie, searchAuntie, greaterProperties, lesserProperties)
            if (found) {
                return index + 1
            }
        }
        return 0
    }

    fun part1(input: List<String>): Int {
        aunties.clear()
        readAunties(input)
        val mysteriousAuntie = mapOf(
            "children" to 3,
            "cats" to 7,
            "samoyeds" to 2,
            "pomeranians" to 3,
            "akitas" to 0,
            "vizslas" to 0,
            "goldfish" to 5,
            "trees" to 3,
            "cars" to 2,
            "perfumes" to 1
        )
        return searchAuntie(mysteriousAuntie)
    }

    fun part2(input: List<String>): Int {
        aunties.clear()
        readAunties(input)
        val mysteriousAuntie = mapOf(
            "children" to 3,
            "cats" to 7,
            "samoyeds" to 2,
            "pomeranians" to 3,
            "akitas" to 0,
            "vizslas" to 0,
            "goldfish" to 5,
            "trees" to 3,
            "cars" to 2,
            "perfumes" to 1
        )
        val greaterProperties = setOf("cats", "trees")
        val lesserProperties = setOf("pomeranians", "goldfish")
        return searchAuntie(mysteriousAuntie, greaterProperties, lesserProperties)
    }

    val input = readInput("Day16_data")
    println("Part 1 answer: ${part1(input)}")
    println("Part 2 answer: ${part2(input)}")
}

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = File("src", "$name.txt")
    .readLines()
