import java.io.File

fun main() {

    fun nextPassword(password: String): String {
        val chars = password.toCharArray()
        var i = chars.size - 1
        while (i >= 0) {
            if (chars[i] == 'z') {
                chars[i] = 'a'
                i--
                continue
            }
            chars[i]++
            break
        }
        return String(chars)
    }

    fun countDoubles(password: String): Int {
        var i = 0
        var count = 0
        while (i < password.length - 1) {
            if (password[i] == password[i + 1]) {
                count++
                i++
            }
            i++
        }
        return count
    }

    fun hasTripleIncreasing(password: String): Boolean {
        var i = 0
        while (i < password.length - 2) {
            if (password[i] + 1 == password[i + 1] && password[i + 1] + 1 == password[i + 2]) {
                return true
            }
            i++
        }
        return false
    }

    fun hasInvalidChar(password: String): Boolean {
        val invalidChars = setOf('i', 'o', 'l')
        var i = 0
        while (i < password.length) {
            if (password[i] in invalidChars) {
                return true
            }
            i++
        }
        return false
    }

    fun isValid(password: String): Boolean {
        if (!hasTripleIncreasing(password)) {
            return false
        }
        if (hasInvalidChar(password)) {
            return false
        }
        return countDoubles(password) >= 2
    }

    fun part1(input: String): String {
        var password = nextPassword(input)
        while (!isValid(password)) {
            password = nextPassword(password)
        }
        return password
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    check(part1("abcdefgh") == "abcdffaa")
    check(part1("ghijklmn") == "ghjaabcc")

    val part1result = part1("cqjxjnds")
    println("Part 1 answer: ${part1("cqjxjnds")}")
    println("Part 2 answer: ${part1(part1result)}")
}
