import java.io.File

data class Element(val name: String, val sequence: String, val nextSequence: String) {
    val size
        get() = sequence.length
}

fun readTable(name: String): Map<String, Element> {
    val input = File("src", name).readLines()
    // first line is header
    return buildMap<String, Element> {
        for (line in input.drop(1)) {
            val (_, element, sequence, nextSequence, _) = line.split("\t")
            set(element, Element(element, sequence, nextSequence))
        }
    }
}

val elements = readTable("elements.txt")

fun findElement(sequence: String): Element {
    for ((name, element) in elements) {
        if (element.sequence == sequence) {
            return element
        }
    }
    throw IllegalArgumentException("Unknown sequence $sequence")
}

fun onePass(seed: String): String {
    var seedElements = seed.split(".")
    val result = buildString {
        for (element in seedElements) {
            append(elements[element]?.nextSequence ?: throw IllegalArgumentException("Unknown element $element"))
            append(".")
        }
    }
    return result.dropLast(1)
}

fun nPasses(seed: String, times: Int): String {
    var result = seed
    repeat(times) {
        result = onePass(result)
    }
    return result
}

fun lengthOfSequence(sequence: String): Int {
    val sequenceElements = sequence.split(".")
    var result = 0
    for (element in sequenceElements) {
        result += elements[element]?.size ?: throw IllegalArgumentException("Unknown element $element")
    }
    return result
}

fun main() {
    val mySeed = "1321131112"
    val seedElement = findElement(mySeed)
    val result = nPasses(seedElement.name, 40)
    println("Part 1 result: ${lengthOfSequence(result)}")
    val result2 = nPasses(result, 10)
    println("Part 2 result: ${lengthOfSequence(result2)}")
}
