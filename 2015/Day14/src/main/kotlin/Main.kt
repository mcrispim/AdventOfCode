import java.io.File

data class Reindeer(val name: String, val speed: Int, val flyTime: Int, val restTime: Int)

fun parseReindeers(input: List<String>): List<Reindeer> {
    //
    // Comet can fly 14 km/s for 10 seconds, but then must rest for 127 seconds.
    //
    val reinders = mutableListOf<Reindeer>()
    val parser = Regex("""(\w+) can fly (\d+) km/s for (\d+) seconds, but then must rest for (\d+) seconds.""")
    input.forEach { line ->
        (parser.matchEntire(line) ?: error("Invalid input: $line")).destructured.let {
            val (name, speed, flyTime, restTime) = it
            reinders.add(Reindeer(name, speed.toInt(), flyTime.toInt(), restTime.toInt()))
        }
    }
    return reinders
}

data class Competitor(val reindeer: Reindeer, var distance: Int = 0, var points: Int = 0, var action: String, var until: Int)

fun main() {

    fun part1(input: List<String>, raceTime: Int = 0): Int {
        val reindeers = parseReindeers(input)
        var maxDistance = 0
        reindeers.forEach { reindeer ->
            var distance = 0
            var time = 0
            var type = "fly"
            while (time <= raceTime) {
                if (type == "fly") {
                    distance += reindeer.speed * reindeer.flyTime
                    time += reindeer.flyTime
                    type = "rest"
                } else {
                    time += reindeer.restTime
                    type = "fly"
                }
            }
            if (type == "rest") {
                distance -= reindeer.speed * (time - raceTime)
            }
            if (distance > maxDistance) {
                maxDistance = distance
            }
        }
        return maxDistance
    }

    fun part2(input: List<String>, raceTime: Int = 0): Int {
        val reindeers = parseReindeers(input)
        val competitors = reindeers.map { reindeer -> Competitor(reindeer, 0, 0, "fly", reindeer.flyTime) }
        var time = 0
        while (time <= raceTime) {
            time++
            competitors.forEach { competitor ->
                competitor.distance += if (competitor.action == "fly") competitor.reindeer.speed else 0
                competitor.until--
                if (competitor.until == 0) {
                    if (competitor.action == "fly") {
                        competitor.action = "rest"
                        competitor.until = competitor.reindeer.restTime
                    } else {
                        competitor.action = "fly"
                        competitor.until = competitor.reindeer.flyTime
                    }
                }
            }
            val maxDistance = competitors.maxBy { it.distance }.distance
            competitors.filter { it.distance == maxDistance }.forEach { it.points++ }
        }
        return competitors.maxBy { it.points }.points
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day14_test")
    check(part1(testInput, 1000) == 1120)
    check(part2(testInput, 1000) == 689)

    val input = readInput("Day14_data")
    println("Part 1 answer: ${part1(input, 2503)}")
    println("Part 2 answer: ${part2(input, 2503)}")
}

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = File("src", "$name.txt")
    .readLines()

