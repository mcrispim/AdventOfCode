import java.io.File


fun combinations(arr: Array<Int>, r: Int): List<List<Int>> {
    val data = Array(r) { 0 }
    val results = mutableListOf<List<Int>>()
    for (n in 1..r) {
        combinationUtil(arr, n, 0, data, 0, results)
    }
    return results
}

fun combinationUtil(arr: Array<Int>, r: Int, index: Int, data: Array<Int>, i: Int, results: MutableList<List<Int>>) {

    if (index == r) {
        results.add(data.slice(0..<r).toList())
        return
    }

    if (i >= arr.size)
        return

    data[index] = arr[i]
    combinationUtil(arr, r, index + 1, data, i + 1, results)

    combinationUtil(arr, r, index, data, i + 1, results)
}

fun main() {

    fun part1(input: List<String>, liters: Int): Int {
        val volumes = input.map { it.toInt() }.sortedDescending()
        return combinations(volumes.toTypedArray(), volumes.size).filter { it.sum() == liters }.size
    }

    fun part2(input: List<String>, liters: Int): Int {
        val allVolumes = input.map { it.toInt() }.sortedDescending()
        val volumesWithLiters =
            combinations(allVolumes.toTypedArray(), allVolumes.size).filter { it.sum() == liters }
        val minNumberOfVolumes = volumesWithLiters.minOfOrNull { it.size }
        return volumesWithLiters.filter { it.size == minNumberOfVolumes }.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day17_test")
    check(part1(testInput, liters = 25) == 4)
    check(part2(testInput, liters = 25) == 3)

    val input = readInput("Day17_data")
    println("Part 1 answer: ${part1(input, 150)}")
    println("Part 2 answer: ${part2(input, 150)}")
}

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = File("src", "$name.txt")
    .readLines()

