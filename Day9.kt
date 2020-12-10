import java.io.File

/*
--- Day 9: Encoding Error ---
With your neighbor happily enjoying their video game, you turn your attention to an open data port on the little screen in the seat in front of you.

Though the port is non-standard, you manage to connect it to your computer through the clever use of several paperclips. Upon connection, the port outputs a series of numbers (your puzzle input).

The data appears to be encrypted with the eXchange-Masking Addition System (XMAS) which, conveniently for you, is an old cypher with an important weakness.

XMAS starts by transmitting a preamble of 25 numbers. After that, each number you receive should be the sum of any two of the 25 immediately previous numbers. The two numbers will have different values, and there might be more than one such pair.

For example, suppose your preamble consists of the numbers 1 through 25 in a random order. To be valid, the next number must be the sum of two of those numbers:

26 would be a valid next number, as it could be 1 plus 25 (or many other pairs, like 2 and 24).
49 would be a valid next number, as it is the sum of 24 and 25.
100 would not be valid; no two of the previous 25 numbers sum to 100.
50 would also not be valid; although 25 appears in the previous 25 numbers, the two numbers in the pair must be different.
Suppose the 26th number is 45, and the first number (no longer an option, as it is more than 25 numbers ago) was 20. Now, for the next number to be valid, there needs to be some pair of numbers among 1-19, 21-25, or 45 that add up to it:

26 would still be a valid next number, as 1 and 25 are still within the previous 25 numbers.
65 would not be valid, as no two of the available numbers sum to it.
64 and 66 would both be valid, as they are the result of 19+45 and 21+45 respectively.
Here is a larger example which only considers the previous 5 numbers (and has a preamble of length 5):

35
20
15
25
47
40
62
55
65
95
102
117
150
182
127
219
299
277
309
576
In this example, after the 5-number preamble, almost every number is the sum of two of the previous 5 numbers; the only number that does not follow this rule is 127.

The first step of attacking the weakness in the XMAS data is to find the first number in the list (after the preamble) which is not the sum of two of the 25 numbers before it. What is the first number that does not have this property?

--- Part Two ---
The final step in breaking the XMAS encryption relies on the invalid number you just found: you must find a contiguous set of at least two numbers in your list which sum to the invalid number from step 1.

Again consider the above example:

35
20
15
25
47
40
62
55
65
95
102
117
150
182
127
219
299
277
309
576
In this list, adding up all of the numbers from 15 through 40 produces the invalid number from step 1, 127. (Of course, the contiguous set of numbers in your actual list might be much longer.)

To find the encryption weakness, add together the smallest and largest number in this contiguous range; in this example, these are 15 and 47, producing 62.

What is the encryption weakness in your XMAS-encrypted list of numbers?

*/

fun main(args: Array<String>) {
    val testFile1 = "C:\\Users\\lccue\\IdeaProjects\\AdventOfCode2020\\input\\9_input.txt"
    var input = readInput9(testFile1)
    val res = findInvalidNum(input, 25)
    println("Result is: $res")
    val subArray = subArraySum(input, res)
    println("Contiguous array is:")
    subArray.forEach { println(it) }
    val minV = subArray.minOrNull() ?: 0
    val maxV = subArray.maxOrNull() ?: 1
    val finalSolution = minV + maxV
    println("Final solution is: $finalSolution")
}

fun subArraySum(input: LongArray, sum: Long): LongArray {
    var rollingSum = 0L
    var subArraySize = 2
    var iterations = 0
    while(true) {
        println("Iteration: $iterations")
        for(i in 0 until input.size - subArraySize) {
            rollingSum = 0L
            var subArray = input.sliceArray(i until (i + subArraySize)) // [i, subArraySize)
            for(j in subArray.indices) {
                //print("${subArray[j]}, ")
                rollingSum += subArray[j]
            }
            println()
            if(rollingSum == sum)
                return subArray
            else
                continue
        }
        subArraySize++
        if(subArraySize >= input.size)
            break
        iterations++
    }
    return LongArray(1)
}

fun findInvalidNum(input: LongArray, preambleLength: Int): Long {
    // Using 2 data structures
    // Map<Int, MutableList<Int>>: Map every input to its calculated sums
    // Set<Int>: Store the sums of the preamble

    val preamble = mutableListOf<Long>()
    val inMap = mutableMapOf<Long, MutableList<Long>>()
    val sumMap = mutableMapOf<Long,Int>()

    for(i in input.indices) {
        if(i < preambleLength) {
            preamble.add(input[i])
            for(j in 0 until i) { // i+1 <= j < preamble.size
                val s = input[i] + input[j]
                if(inMap[input[i]] == null)
                    inMap[input[i]] = mutableListOf(s)
                else
                    inMap[input[i]]?.add(s)

                if(inMap[input[j]] == null)
                    inMap[input[j]] = mutableListOf(s)
                else
                    inMap[input[j]]?.add(s)

                if(sumMap[s] == null)
                    sumMap[s] = 1
                else
                    sumMap[s] = (sumMap[s] ?: 0) + 1
            }
        }
        else {
//            println(sumMap)
//            println(preamble)
//            println("\t ${input[i]}")
            // Check if our number is valid
            if(sumMap[input[i]] == null || sumMap[input[i]] !!< 1)
                return input[i]
            else {
                // We are valid
                // Remove the sums corresponding to the first element
                var removeSums = inMap[preamble[0]]
                //println("removing these: $removeSums")
                if (removeSums != null) {
                    for(sum in removeSums) {
                        sumMap[sum] = (sumMap[sum] ?: 0) - 1
                        for(num in preamble) {
                            if(num != preamble[0])
                                inMap[num]?.remove(sum)
                        }
                    }
                }

                // Generate new sums, with input[i]
                for(j in 0 until preambleLength) { // 0 <= j < i
                    val s = input[i] + preamble[j]
                    if(inMap[input[i]] == null)
                        inMap[input[i]] = mutableListOf(s)
                    else
                        inMap[input[i]]?.add(s)

                    if(inMap[preamble[j]] == null)
                        inMap[preamble[j]] = mutableListOf(s)
                    else
                        inMap[preamble[j]]?.add(s)

                    if(sumMap[s] == null)
                        sumMap[s] = 1
                    else
                        sumMap[s] = (sumMap[s] ?: 0) + 1
                }

                // Update Preamble
                preamble.removeAt(0)
                preamble.add(input[i])
            }
        }
    }
    return -1
}

fun readInput9(fileName: String): LongArray {
    val numList = mutableListOf<Long>()
    val file = File(fileName)
    file.forEachLine {
        numList.add(it.toLong())
    }
    return numList.toLongArray()
}