import java.io.File
import java.lang.Thread.yield

data class Ingredient(
    val name: String,
    val capacity: Int,
    val durability: Int,
    val flavor: Int,
    val texture: Int,
    val calories: Int
)

fun recipeFactory(ingredients: List<Ingredient>, amount: Int): Sequence<List<Pair<Ingredient, Int>>> {

    fun sumArray(array: IntArray, lastIndex: Int): Int {
        var sum = 0
        for (i in 0..lastIndex) {
            sum += array[i]
        }
        return sum
    }

    return sequence {
        if (ingredients.size == 1) {
            yield(listOf(ingredients[0] to amount))
        }
        val n = ingredients.size
        val quantities = IntArray(n)
        var counterIndex = n - 2
        while (counterIndex >= 0) {
            quantities[n - 1] = amount - sumArray(quantities, n - 2)
            val recipe = ingredients.mapIndexed { index, ingredient -> Pair(ingredient, quantities[index]) }
            yield(recipe)
            counterIndex = n - 2
            quantities[counterIndex]++
            while (counterIndex >= 0 && quantities[counterIndex] > amount) {
                quantities[counterIndex] = 0
                counterIndex--
                if (counterIndex >= 0) {
                    quantities[counterIndex]++
                }
            }
        }
    }
}

fun main() {

    fun getIngredients(input: List<String>): List<Ingredient> {
        val parser =
            Regex("""(\w+): capacity (-?\d+), durability (-?\d+), flavor (-?\d+), texture (-?\d+), calories (-?\d+)""")
        val ingredients = input.map { line ->
            (parser.matchEntire(line) ?: error("Invalid input: $line")).destructured.let {
                val (name, capacity, durability, flavor, texture, calories) = it
                Ingredient(
                    name,
                    capacity.toInt(),
                    durability.toInt(),
                    flavor.toInt(),
                    texture.toInt(),
                    calories.toInt()
                )
            }
        }
        return ingredients
    }

    fun calculatePoints(recipe: List<Pair<Ingredient, Int>>): Int {
        var capacityPoints = 0
        var durabilityPoints = 0
        var flavorPoints = 0
        var texturePoints = 0
        recipe.forEach {
            val (ingredient, spoons) = it
            capacityPoints += spoons * ingredient.capacity
            durabilityPoints += spoons * ingredient.durability
            flavorPoints += spoons * ingredient.flavor
            texturePoints += spoons * ingredient.texture
        }
        if (capacityPoints < 0 || durabilityPoints < 0 || flavorPoints < 0 || texturePoints < 0) {
            return 0
        }
        return capacityPoints * durabilityPoints * flavorPoints * texturePoints
    }

    fun calculateCalories(recipe: List<Pair<Ingredient, Int>>): Int {
        var caloriesPoints = 0
        recipe.forEach {
            val (ingredient, spoons) = it
            caloriesPoints += spoons * ingredient.calories
        }
        if (caloriesPoints < 0) {
            return 0
        }
        return caloriesPoints
    }

    fun part1(input: List<String>) = recipeFactory(getIngredients(input), 100)
        .map { calculatePoints(it) }
        .maxOrNull()!!

    fun part2(input: List<String>) = recipeFactory(getIngredients(input), 100)
        .filter { calculateCalories(it) == 500 }
        .map { calculatePoints(it) }
        .maxOrNull()!!

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day15_test")
    check(part1(testInput) == 62842880)
    check(part2(testInput) == 57600000)

    val input = readInput("Day15_data")
    println("Part 1 answer: ${part1(input)}")
    println("Part 2 answer: ${part2(input)}")
}

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = File("src", "$name.txt")
    .readLines()

