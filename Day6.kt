import java.io.File

/*
As your flight approaches the regional airport where you'll switch to a much larger plane, customs declaration forms are distributed to the passengers.

The form asks a series of 26 yes-or-no questions marked a through z. All you need to do is identify the questions for which anyone in your group answers "yes". Since your group is just you, this doesn't take very long.

However, the person sitting next to you seems to be experiencing a language barrier and asks if you can help. For each of the people in their group, you write down the questions for which they answer "yes", one per line. For example:

abcx
abcy
abcz
In this group, there are 6 questions to which anyone answered "yes": a, b, c, x, y, and z. (Duplicate answers to the same question don't count extra; each question counts at most once.)

Another group asks for your help, then another, and eventually you've collected answers from every group on the plane (your puzzle input). Each group's answers are separated by a blank line, and within each group, each person's answers are on a single line. For example:

abc

a
b
c

ab
ac

a
a
a
a

b
This list represents answers from five groups:

The first group contains one person who answered "yes" to 3 questions: a, b, and c.
The second group contains three people; combined, they answered "yes" to 3 questions: a, b, and c.
The third group contains two people; combined, they answered "yes" to 3 questions: a, b, and c.
The fourth group contains four people; combined, they answered "yes" to only 1 question, a.
The last group contains one person who answered "yes" to only 1 question, b.
In this example, the sum of these counts is 3 + 3 + 3 + 1 + 1 = 11.

For each group, count the number of questions to which anyone answered "yes". What is the sum of those counts?
 */

fun main(args: Array<String>) {
    val filename = "C:\\Users\\lccue\\IdeaProjects\\AdventOfCode2020\\input\\6_input.txt"
    val finalList = readInput6(filename)
    println("Total groups: ${finalList.size}")
    var count = 0
    for(group in finalList) {
        val uniqueCount = countUniqueQuestions2(group)
        count += uniqueCount

        println("Total People: ${group.size}")
        println("\tTotal Questions: ${uniqueCount}")
    }
    println("###########################")
    println("Total count: $count")
}

fun countUniqueQuestions2(group: List<String>): Int {
    // Create a map, that will hold every question
    var questionMap = mutableMapOf<Char, Int>()
    for(person in group) {
        for (question in person) {
            val currentCount = questionMap[question] ?: 0
            questionMap[question] = currentCount + 1
        }
    }
    
    var uniqueCount = 0
    for(question in questionMap.keys) {
        if(questionMap[question] == group.size)
            uniqueCount += 1
    }
    
    return uniqueCount
}

fun countUniqueQuestions1(group: List<String>): Int {
    // Create a map, that will hold every question
    var questionMap = mutableMapOf<Char, Int>()
    for(person in group) {
        for(question in person) {
            val currentCount = questionMap[question] ?: 0
            questionMap[question] = currentCount + 1
        }
    }
    return questionMap.keys.size
}

fun copyList(l: List<String>): MutableList<String> {
    val copy = mutableListOf<String>()
    for(e in l)
        copy.add(e)
    return copy
}

fun readInput6(fileName: String):List<List<String>> {
    var finalList = mutableListOf<MutableList<String>>()
    var group = mutableListOf<String>()

    var file = File(fileName)

    file.forEachLine {
        if(it.isBlank()) {
            val groupCopy = copyList(group)
            finalList.add(groupCopy)
            group.clear()
        }
        else {
            group.add(it)
        }
    }
    if(group.isNotEmpty()) {
        val groupCopy = copyList(group)
        finalList.add(groupCopy)
    }

    return finalList
}