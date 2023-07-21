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

fun addHostToPrefsAndPeople() {
    for (p in people) {
        prefs[Pair(p, "Host")] = 0
        prefs[Pair("Host", p)] = 0
    }
    people.add("Host")
}

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
    people = everybody.toMutableList()
}

fun happyness(people: List<String>): Int {
    var happyness = 0
    happyness += prefs[Pair(people.last(), people.first())]!!
    happyness += prefs[Pair(people.first(), people.last())]!!

    for ((personA, personB) in people.zipWithNext()) {
        happyness += prefs[Pair(personA, personB)]!!
        happyness += prefs[Pair(personB, personA)]!!
    }
    return happyness
}

fun calculateHappyness(table: Sequence<List<String>>): Int {
    var happyness = 0
    table.forEachIndexed { index, people ->
        val normalizedPeople = normalizePeople(people)
        if (combinations.containsKey(normalizedPeople)) {
            return@forEachIndexed
        }
        val thisHappyness = happyness(normalizedPeople)
        combinations[normalizedPeople] = thisHappyness
        if (thisHappyness > happyness) {
            happyness = thisHappyness
        }
    }
    return happyness
}

fun normalizePeople(peopleAtTable: List<String>): List<String> {
    val firstPerson = people.first()
    val firstIndex = peopleAtTable.indexOf(firstPerson)
    val result = peopleAtTable.subList(firstIndex, peopleAtTable.size) +
            peopleAtTable.subList(0, firstIndex)
    return result
}

val prefs = mutableMapOf<Pair<String, String>, Int>()
var people = mutableListOf<String>()
val combinations = mutableMapOf<List<String>, Int>()

fun main() {

    fun findMostHappynnes(): Int {
        val table: Sequence<List<String>> = generateSequence(people)
        return calculateHappyness(table)
    }

    val testInput = readInput("Day13_test")
    mountPrefsAndPeople(testInput)
//    printPrefsAndPeople()
    var part1Result = findMostHappynnes()
    check(part1Result == 330)

    val input = readInput("Day13_data")
    mountPrefsAndPeople(input)
    part1Result = findMostHappynnes()
    println("Part 1 answer: $part1Result")
    addHostToPrefsAndPeople()
    val part2Result2 = findMostHappynnes()
    println("Part 2 answer: $part2Result2")
}

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = File("src", "$name.txt")
    .readLines()
