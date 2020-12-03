import java.io.File
import kotlin.system.measureTimeMillis

/*
--- Day 1: Report Repair ---
After saving Christmas five years in a row, you've decided to take a vacation at a nice resort on a tropical island.
Surely, Christmas will go on without you.

The tropical island has its own currency and is entirely cash-only.
The gold coins used there have a little picture of a starfish; the locals just call them stars.
None of the currency exchanges seem to have heard of them,
but somehow, you'll need to find fifty of these coins by the time you arrive so you can pay the deposit on your room.

To save your vacation, you need to get all fifty stars by December 25th.

Collect stars by solving puzzles.
Two puzzles will be made available on each day in the Advent calendar; the second puzzle is unlocked when you complete the first.
Each puzzle grants one star. Good luck!

Before you leave, the Elves in accounting just need you to fix your expense report (your puzzle input); apparently, something isn't quite adding up.

Specifically, they need you to find the two entries that sum to 2020 and then multiply those two numbers together.
 */

fun main(args: Array<String>) {
    val fileName = "C:\\Users\\lccue\\IdeaProjects\\AdventOfCode2020\\input\\1_input.txt"
    val numbers = readInput(fileName)
    val numIterations = 1000
    //val numbers = listOf<Int>(1721, 979, 366, 675, 1456, 299)

    println("Finding Pair")
    var elapsedTime = 0.0
    for (x in 0 until numIterations)
        elapsedTime += measureTimeMillis { findSumInPair2(2020, numbers) }
    elapsedTime /= numIterations

    val pair = findSumInPair(2020, numbers)
    println("Pair is: $pair")

    var finalAnswer = pair.first * pair.second
    println("Final Answer: $finalAnswer")
    println("Average Elapsed Time: ${elapsedTime}ms")

    println("--------------------------------------------------------------")

    println("Finding Triple")
    elapsedTime = 0.0
    for (x in 0 until numIterations)
        elapsedTime += measureTimeMillis { findSumInTriple2(2020, numbers) }
    elapsedTime /= numIterations
    val triple = findSumInTriple(2020, numbers)
    println("Triple is: $triple")

    finalAnswer = triple.first * triple.second * triple.third
    println("Final Answer: $finalAnswer")
    println("Average Elapsed Time: ${elapsedTime}ms")
}

fun findSumInPair(sum: Int, numbers: IntArray): Pair<Int, Int> {
    var n1 = 0
    var n2 = 0

    var counter = 0

    for(index1 in 0 until numbers.size) {
        for(index2 in index1 until numbers.size) {
            if (numbers[index1] + numbers[index2] == sum) {
                n1 = numbers[index1]
                n2 = numbers[index2]
            }
            counter++
        }
    }

    println("Iterations: $counter")
    return Pair(n1,n2)
}

fun findSumInTriple(sum: Int, numbers: IntArray): Triple<Int, Int, Int> {
    var n1 = 0
    var n2 = 0
    var n3 = 0

    for(index1 in 0 until numbers.size) {
        for(index2 in index1 until numbers.size) {
            for(index3 in index2 until numbers.size) {
                if (numbers[index1] + numbers[index2]  + numbers[index3] == sum) {
                    n1 = numbers[index1]
                    n2 = numbers[index2]
                    n3 = numbers[index3]
                }
            }
        }
    }

    return Triple(n1,n2,n3)
}

/*
1. Sort List
2. Only consider numbers when at least 1 is greater than half of sum
 */
fun findSumInPair2(sum: Int, numbers: IntArray): Pair<Int, Int> {
    var n1 = 0
    var n2 = 0
    val halfOfSum = sum / 2.0

    numbers.sort()

    var counter = 0

    for(index1 in 0 until numbers.size) {
        if(numbers[index1] < halfOfSum)
            continue
        for(index2 in index1 until numbers.size) {
            counter++
            if (numbers[index1] < halfOfSum && numbers[index2] < halfOfSum)
                continue
            if (numbers[index1] + numbers[index2] == sum) {
                n1 = numbers[index1]
                n2 = numbers[index2]
            }
        }
    }
    println("Iterations: $counter")

    return Pair(n1, n2)
}

fun findSumInTriple2(sum: Int, numbers: IntArray): Triple<Int, Int, Int> {
    var n1 = 0
    var n2 = 0
    var n3 = 0
    val thirdOfSum = sum / 3.0


    numbers.sort()
    var counter = 0

    for(index1 in 0 until numbers.size) {
        if(numbers[index1] < thirdOfSum)
            continue
        for(index2 in index1 until numbers.size) {
            if(numbers[index2] < thirdOfSum)
                continue
            for(index3 in index2 until numbers.size) {
                counter++
                if (numbers[index1] + numbers[index2]  + numbers[index3] == sum) {
                    n1 = numbers[index1]
                    n2 = numbers[index2]
                    n3 = numbers[index3]
                }
            }
        }
    }

    //println("Iterations: $counter")

    return Triple(n1,n2,n3)
}

fun readInput(fileName: String): IntArray {
    val numbers = mutableListOf<Int>()
    File(fileName).forEachLine {
        numbers.add(it.toInt())
    }
    return numbers.toIntArray()
}