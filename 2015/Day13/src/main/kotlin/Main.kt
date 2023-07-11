import java.io.File
import java.util.*

fun generateSequence(A: List<String>): Sequence<List<String>> {
    return sequence {
        val n = A.size
        val c = IntArray(n)
        val list = A.toMutableList()

        yield(list.toList())

        var i = 0;
        while (i < n) {
            if (c[i] < i) {
                if (i % 2 == 0) {
                    Collections.swap(list, 0, i)
                } else {
                    Collections.swap(list, c[i], i)
                }
                yield(list)
                c[i] += 1
                i = 0
            } else {
                c[i] = 0
                i += 1
            }
        }
    }
}

val prefs = mutableMapOf<Pair<String, String>, Int>()
var people = listOf<String>()

fun main() {

    // Alice would gain 54 happiness units by sitting next to Bob.
    fun mountPrefsAndPeople(input: List<String>) {
        val everybody = mutableSetOf<String>()
        prefs.clear()
        input.forEach {
            val parts = it.split(" ")
            val name1 = parts[0]
            val name2 = parts[10].dropLast(1)
            val value = if (parts[2] == "gain")
                parts[3].toInt()
            else
                -(parts[3].toInt())
            prefs[Pair(name1, name2)] = value
            everybody.add(name1)
            everybody.add(name2)
        }
        people = everybody.toList()
    }

    fun CalculateHappyness(people: List<String>): Int {
        var happyness = 0
        happyness += prefs[Pair(people.last(), people.first())]!!
        happyness += prefs[Pair(people.first(), people.last())]!!

        for ((personA, personB) in people.zipWithNext()) {
            happyness += prefs[Pair(personA, personB)]!!
            happyness += prefs[Pair(personB, personA)]!!
        }
        return happyness
    }

    fun part1(input: List<String>): Int {
        val table = generateSequence(people)

        var happyness = 0
        var group = listOf<String>()
        table.forEach {
            val thisHappyness = CalculateHappyness(it)
            println("$it $thisHappyness")
            if (thisHappyness > happyness) {
                happyness = thisHappyness
                group = it
            }
        }
        return happyness
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day13_test")
    mountPrefsAndPeople(testInput)


    val part1Result = part1(testInput)
    check(part1Result == 330)
    check(part2(testInput) == 12)

    val input = readInput("Day13_data")
    println("Part 1 answer: ${part1(input)}")
    println("Part 2 answer: ${part2(input)}")
}

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = File("src", "$name.txt")
    .readLines()
