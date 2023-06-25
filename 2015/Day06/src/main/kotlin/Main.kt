import java.io.File

enum class Command {
    ON, OFF, TOGGLE
}

data class Rectangle(val x1: Int, val y1: Int, val x2: Int, val y2: Int)

fun main() {

    fun readCommand(line: String): Pair<Command, Rectangle> {
        /* Command examples
        turn on 0,0 through 999,999
        toggle 0,0 through 999,0
        turn off 499,499 through 500,500
         */
        val parts = line.split(" ")
        val command = if (parts[1] == "on") Command.ON else if (parts[1] == "off") Command.OFF else Command.TOGGLE
        var numbersIndex = if (command == Command.TOGGLE) 1 else 2
        val (x1, y1) = parts[numbersIndex].split(",").map { it.toInt() }
        val (x2, y2) = parts[numbersIndex + 2].split(",").map { it.toInt() }
        return Pair(command, Rectangle(x1, y1, x2, y2))
    }

    fun part1(input: List<String>): Int {

        class Grid {
            val cells = Array(1000) { IntArray(1000) { 0 } }
            var lightsOn = 0

            fun processComando(command: Command, rectangle: Rectangle) {
                for (x in rectangle.x1..rectangle.x2) {
                    for (y in rectangle.y1..rectangle.y2) {
                        when (command) {
                            Command.ON -> if (cells[x][y] == 0) {
                                cells[x][y] = 1
                                lightsOn++
                            }

                            Command.OFF -> if (cells[x][y] == 1) {
                                cells[x][y] = 0
                                lightsOn--
                            }

                            Command.TOGGLE -> {
                                if (cells[x][y] == 1) {
                                    cells[x][y] = 0
                                    lightsOn--
                                } else {
                                    cells[x][y] = 1
                                    lightsOn++
                                }
                            }
                        }
                    }
                }
            }
        }

        val grid = Grid()
        input.forEach {
            val (command, rectangle) = readCommand(it)
//            println("Lights on: ${grid.lightsOn}")
//            println("$command, $rectangle")
            grid.processComando(command, rectangle)
        }
//        println("Lights on: ${grid.lightsOn}")
        return grid.lightsOn
    }

    fun part2(input: List<String>): Int {

        class Grid {
            val cells = Array(1000) { IntArray(1000) { 0 } }
            var totalBrightness = 0

            fun processComando(command: Command, rectangle: Rectangle) {
                for (x in rectangle.x1..rectangle.x2) {
                    for (y in rectangle.y1..rectangle.y2) {
                        when (command) {
                            Command.ON -> {
                                cells[x][y] = cells[x][y] + 1
                                totalBrightness++
                            }

                            Command.OFF -> if (cells[x][y] > 0) {
                                cells[x][y] = cells[x][y] - 1
                                totalBrightness--
                            }

                            Command.TOGGLE -> {
                                cells[x][y] = cells[x][y] + 2
                                totalBrightness += 2
                            }
                        }
                    }
                }
            }
        }

        val grid = Grid()
        input.forEach {
            val (command, rectangle) = readCommand(it)
//            println("Lights on: ${grid.lightsOn}")
//            println("$command, $rectangle")
            grid.processComando(command, rectangle)
        }
//        println("Lights on: ${grid.lightsOn}")
        return grid.totalBrightness

    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day06_test")
    check(part1(testInput) == 998_996)
    check(part2(testInput) == 1_000_000 + 2_000 - 4)

    val input = readInput("Day06_data")
    println("Part 1 answer: ${part1(input)}")
    println("Part 2 answer: ${part2(input)}")
}

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = File("src", "$name.txt")
    .readLines()
