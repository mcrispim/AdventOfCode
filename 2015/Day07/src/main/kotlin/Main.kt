import java.io.File

enum class InstructionType {
    AND, OR, LSHIFT, RSHIFT, NOT, ASSIGN
}

data class Instruction(val type: InstructionType, val leftArgs: List<String>, val rightArg: String)

class Circuit(inputs: Map<String, UShort>, instructions: List<Instruction>) {
    val wires = inputs.toMutableMap()
    val instructionBuffer = instructions.toMutableList()

    init {
        while (instructionBuffer.isNotEmpty()) {
            val instruction = instructionBuffer.removeAt(0)
            execute(instruction)
        }
    }

    private fun execute(instruction: Instruction) {
        try {
            when (instruction.type) {
                InstructionType.ASSIGN -> {         // 123 -> x
                    val value = getArg(instruction.leftArgs[0])
                    wires[instruction.rightArg] = value
                }

                InstructionType.NOT -> {            // NOT x -> h
                    val value = getArg(instruction.leftArgs[0])
                    val result = value.inv()
                    wires[instruction.rightArg] = result
                }

                InstructionType.AND -> {            // x AND y -> h
                    val value1 = getArg(instruction.leftArgs[0])
                    val value2 = getArg(instruction.leftArgs[1])
                    val result = value1 and value2
                    wires[instruction.rightArg] = result
                }

                InstructionType.OR -> {             // x OR y -> h
                    val value1 = getArg(instruction.leftArgs[0])
                    val value2 = getArg(instruction.leftArgs[1])
                    val result = value1 or value2
                    wires[instruction.rightArg] = result
                }

                InstructionType.LSHIFT -> {         // x LSHIFT y -> h
                    val value = getArg(instruction.leftArgs[0]).toInt()
                    val positions = getArg(instruction.leftArgs[1]).toInt()
                    val result = value shl positions
                    wires[instruction.rightArg] = result.toUShort()
                }

                InstructionType.RSHIFT -> {
                    val value = getArg(instruction.leftArgs[0]).toInt()
                    val positions = getArg(instruction.leftArgs[1]).toInt()
                    val result = value ushr positions
                    wires[instruction.rightArg] = result.toUShort()
                }
            }
        } catch (e: Exception) {
            instructionBuffer.add(instruction)
        }
    }

    private fun getArg(arg: String): UShort {
        return try {
            arg.toUShort()
        } catch (e: Exception) {
            wires[arg] ?: throw Exception("Unknown wire $arg")
        }
    }

}

fun main() {

    fun parseInstruction(instructionLine: String): Instruction {
        val instructionParts = instructionLine.split(" -> ")
        val args = instructionParts[0].split(" ")
        when (args.size) {
            1 -> return Instruction(InstructionType.ASSIGN, listOf(args[0]), instructionParts[1])
            2 -> return Instruction(InstructionType.NOT, listOf(args[1]), instructionParts[1])
            3 -> when (args[1]) {
                "AND" -> return Instruction(InstructionType.AND, listOf(args[0], args[2]), instructionParts[1])
                "OR" -> return Instruction(InstructionType.OR, listOf(args[0], args[2]), instructionParts[1])
                "LSHIFT" -> return Instruction(
                    InstructionType.LSHIFT,
                    listOf(args[0], args[2]),
                    instructionParts[1]
                )

                "RSHIFT" -> return Instruction(
                    InstructionType.RSHIFT,
                    listOf(args[0], args[2]),
                    instructionParts[1]
                )

                else -> throw Exception("Unknown instruction type")
            }

            else -> throw Exception("Unknown instruction type")
        }
    }

    fun parseStructure(input: List<String>): Pair<MutableMap<String, UShort>, MutableList<Instruction>> {
        val wires = mutableMapOf<String, UShort>()
        val instructions = mutableListOf<Instruction>()

        input.forEach {
            val instruction = parseInstruction(it)
            if (instruction.type == InstructionType.ASSIGN) {
                val arg = instruction.leftArgs[0].toIntOrNull()
                if (arg != null) {
                    wires[instruction.rightArg] = arg.toUShort()
                    return@forEach
                }
            }
            instructions.add(instruction)
        }
        return Pair(wires, instructions)
    }

    fun part1(input: List<String>): MutableMap<String, UShort> {
        val (wires, instructions) = parseStructure(input)
        val circuit = Circuit(wires, instructions)
        return circuit.wires
    }

    fun part2(input: List<String>, oldWires: MutableMap<String, UShort> = mutableMapOf()): Int {
        val (wires, instructions) = parseStructure(input)
        val wireA = oldWires["a"] ?: 0.toUShort()
        wires.forEach { (k, _) -> wires[k] = 0.toUShort() }
        wires["b"] = wireA!!
        val circuit = Circuit(wires, instructions)
        return circuit.wires["a"]?.toInt() ?: 0
    }

// test if implementation meets criteria from the description, like:
    val testInput = readInput("Day07_test")
    check((part1(testInput)["a"]?.toInt() ?: 0) == 0)
    check(part2(testInput) == 0)

    val input = readInput("Day07_data")
    val wires = part1(input)
    println("Part 1 answer: ${wires["a"]?.toInt() ?: 0}")
    println("Part 2 answer: ${part2(input, wires)}")
}

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = File("src", "$name.txt")
    .readLines()

