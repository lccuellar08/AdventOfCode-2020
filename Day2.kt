import java.io.File
import kotlin.math.min

/*
--- Day 2: Password Philosophy ---
Your flight departs in a few days from the coastal airport; the easiest way down to the coast from here is via toboggan.

The shopkeeper at the North Pole Toboggan Rental Shop is having a bad day. "Something's wrong with our computers; we can't log in!" You ask if you can take a look.

Their password database seems to be a little corrupted: some of the passwords wouldn't have been allowed by the Official Toboggan Corporate Policy that was in effect when they were chosen.

To try to debug the problem, they have created a list (your puzzle input) of passwords (according to the corrupted database) and the corporate policy when that password was set.

For example, suppose you have the following list:

1-3 a: abcde
1-3 b: cdefg
2-9 c: ccccccccc
Each line gives the password policy and then the password. The password policy indicates the lowest and highest number of times a given letter must appear for the password to be valid. For example, 1-3 a means that the password must contain a at least 1 time and at most 3 times.

In the above example, 2 passwords are valid. The middle password, cdefg, is not; it contains no instances of b, but needs at least 1. The first and third passwords are valid: they contain one a or nine c, both within the limits of their respective policies.
 */

fun main(args: Array<String>) {
    val listOfPasswords = readInput_2("C:\\Users\\lccue\\IdeaProjects\\AdventOfCode2020\\input\\2_input.txt")
    val totalValidPasswords = countValidPasswords(listOfPasswords)

    println("Total Passwords: ${listOfPasswords.size}")
    println("Total Valid Passwords: ${totalValidPasswords}")

}

fun countValidPasswords(listOfPasswords: List<String>): Int {
    var counter = 0


    for(line in listOfPasswords) {
        val tokens = line.split(" ")

        val minMaxCriteria = tokens[0]
        val minMaxTokens = minMaxCriteria.split("-")
        val minimumCount = minMaxTokens[0].toInt()
        val maximumCount = minMaxTokens[1].toInt()

        val charCriteria = tokens[1].get(0)

        val password = tokens[2]

        val isValid = isPasswordValid2(password, minimumCount, maximumCount, charCriteria)
        if(isValid)
            counter++

    }

    return counter
}

fun isPasswordValid2(password: String, minimumCount: Int, maximumCount: Int, charCriteria: Char): Boolean {
    //3-6 s: ssdsssss
    var counter = 0
    val c1 = password.get(minimumCount - 1)
    val c2 = password.get(maximumCount - 1)

    if(c1 == charCriteria)
        counter++
    if(c2 == charCriteria)
        counter++

    return (counter == 1)
}

fun isPasswordValid1(password: String, minimumCount: Int, maximumCount: Int, charCriteria: Char): Boolean {
    //3-6 s: ssdsssss
    var counter = 0

    for(c in password) {
        if(c == charCriteria)
            counter++
    }

    return (counter >= minimumCount && counter <= maximumCount)
}

fun readInput_2(fileName: String): List<String> {
    val listOfPasswords = mutableListOf<String>()

    File(fileName).forEachLine {
        listOfPasswords.add(it)
    }
    return listOfPasswords
}