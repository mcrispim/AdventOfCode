import java.io.File

class Graph(input: List<String>) {

    data class Edge(val from: String, val to: String, val distance: Int)

    var nodes = mutableSetOf<String>()
    var shortestEdge: Edge? = null
    val edges = mutableMapOf<String, List<Edge>>()

    init {
        input.forEach {
            val (city1, _, city2, _, distanceStr) = it.split(" ")
            val distance = distanceStr.toInt()
            nodes += city1
            nodes += city2
            val edge1 = Edge(city1, city2, distance)
            val edge2 = Edge(city2, city1, distance)
            edges[city1] = edges.getOrDefault(city1, emptyList()) + edge1
            edges[city2] = edges.getOrDefault(city2, emptyList()) + edge2
            if (shortestEdge == null || edge1.distance < shortestEdge!!.distance) {
                shortestEdge = edge1
            }
        }
    }

    fun traverseLongeststPath(): Int {
        var longestDistance = Int.MIN_VALUE
        for(startCity in nodes) {
            var distance = 0
            var thisNode = startCity
            val citiesVisited = mutableSetOf<String>(thisNode)
            while (citiesVisited.size < nodes.size) {
                val nextEdge = edges[thisNode]!!.filter { !citiesVisited.contains(it.to) }.maxByOrNull { it.distance }!!
                distance += nextEdge.distance
                citiesVisited += nextEdge.to
                thisNode = nextEdge.to
            }
            if (distance > longestDistance) {
                longestDistance = distance
            }
        }
        return longestDistance
    }

    fun traverseShortestPath(): Int {
        var shortestDistance = Int.MAX_VALUE
        for(startCity in nodes) {
            var distance = 0
            var thisNode = startCity
            val citiesVisited = mutableSetOf<String>(thisNode)
            while (citiesVisited.size < nodes.size) {
                val nextEdge = edges[thisNode]!!.filter { !citiesVisited.contains(it.to) }.minByOrNull { it.distance }!!
                distance += nextEdge.distance
                citiesVisited += nextEdge.to
                thisNode = nextEdge.to
            }
            if (distance < shortestDistance) {
                shortestDistance = distance
            }
        }
        return shortestDistance
    }
}

fun main() {

    fun part1(input: List<String>): Int {
        val graph = Graph(input)
        return graph.traverseShortestPath()
    }

    fun part2(input: List<String>): Int {
        val graph = Graph(input)
        return graph.traverseLongeststPath()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day09_test")
    check(part1(testInput) == 605)
    check(part2(testInput) == 982)

    val input = readInput("Day09_data")
    println("Part 1 answer: ${part1(input)}")
    println("Part 2 answer: ${part2(input)}")
}

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = File("src", "$name.txt")
    .readLines()

