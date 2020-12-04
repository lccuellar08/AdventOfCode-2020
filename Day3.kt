import java.io.File

/*
--- Day 3: Toboggan Trajectory ---
With the toboggan login problems resolved, you set off toward the airport. While travel by toboggan might be easy, it's certainly not safe: there's very minimal steering and the area is covered in trees. You'll need to see which angles will take you near the fewest trees.

Due to the local geology, trees in this area only grow on exact integer coordinates in a grid. You make a map (your puzzle input) of the open squares (.) and trees (#) you can see. For example:

..##.......
#...#...#..
.#....#..#.
..#.#...#.#
.#...##..#.
..#.##.....
.#.#.#....#
.#........#
#.##...#...
#...##....#
.#..#...#.#
These aren't the only trees, though; due to something you read about once involving arboreal genetics and biome stability, the same pattern repeats to the right many times:

..##.........##.........##.........##.........##.........##.......  --->
#...#...#..#...#...#..#...#...#..#...#...#..#...#...#..#...#...#..
.#....#..#..#....#..#..#....#..#..#....#..#..#....#..#..#....#..#.
..#.#...#.#..#.#...#.#..#.#...#.#..#.#...#.#..#.#...#.#..#.#...#.#
.#...##..#..#...##..#..#...##..#..#...##..#..#...##..#..#...##..#.
..#.##.......#.##.......#.##.......#.##.......#.##.......#.##.....  --->
.#.#.#....#.#.#.#....#.#.#.#....#.#.#.#....#.#.#.#....#.#.#.#....#
.#........#.#........#.#........#.#........#.#........#.#........#
#.##...#...#.##...#...#.##...#...#.##...#...#.##...#...#.##...#...
#...##....##...##....##...##....##...##....##...##....##...##....#
.#..#...#.#.#..#...#.#.#..#...#.#.#..#...#.#.#..#...#.#.#..#...#.#  --->
You start on the open square (.) in the top-left corner and need to reach the bottom (below the bottom-most row on your map).

The toboggan can only follow a few specific slopes (you opted for a cheaper model that prefers rational numbers); start by counting all the trees you would encounter for the slope right 3, down 1:

From your starting position at the top-left, check the position that is right 3 and down 1. Then, check the position that is right 3 and down 1 from there, and so on until you go past the bottom of the map.
 */

fun main(args: Array<String>) {
    val filename = "C:\\Users\\lccue\\IdeaProjects\\AdventOfCode2020\\input\\3_input.txt"
    //val filename = "C:\\Users\\lccue\\IdeaProjects\\AdventOfCode2020\\input\\3_test_input.txt"
    val inputArray = readInput3(filename)

    /*
    Right 1, down 1.
    Right 3, down 1. (This is the slope you already checked.)
    Right 5, down 1.
    Right 7, down 1.
    Right 1, down 2.
     */

//    val num1 = transverseMap(0,0,1,1, inputArray).toLong()
    val num2 = transverseMap(0,0,3,1, inputArray).toLong()
//    val num3 = transverseMap(0,0,5,1, inputArray).toLong()
//    val num4 = transverseMap(0,0,7,1, inputArray).toLong()
//    val num5 = transverseMap(0,0,1,2, inputArray).toLong()
    //764809840
//    println("1: $num1\n" +
//            "2: $num2\n" +
//            "3: $num3\n" +
//            "4: $num4\n" +
//            "5: $num5\n")
//
//    println("Final Answer: ${num1 * num2 * num3 * num4 * num5}")
}

fun transverseMap(startX: Int, startY: Int, dX: Int, dY: Int, mapArray: Array<CharArray>): Int {
    // Base Case
    // Check if we're past the last line
    if(startY > (mapArray.size - 1)) {
        return 0
    }

    val modX = startX % mapArray[startY].size

    Thread.sleep(300)
    visualizeLine(modX, mapArray[startY])

    // Recursive Case: startX is valid
    if(mapArray[startY][modX] == '#')
    // Recursively call the same method, but move the starting position by dX and dY
        return 1 + transverseMap(modX + dX, startY + dY, dX, dY, mapArray)
    else
        return 0 + transverseMap(modX + dX, startY + dY, dX, dY, mapArray)

}

fun visualizeLine(posX: Int, charArray: CharArray) {
    for(i in charArray.indices) {
        if(i == posX)
            print("O")
        else
            print(charArray[i])
    }
    println()
}

fun readInput3(filename: String): Array<CharArray> {
    val file = File(filename)
    val charArrayList = mutableListOf<CharArray>()

    file.forEachLine {
        val arrayOfCharacters = it.toCharArray()
        charArrayList.add(arrayOfCharacters)
    }

    return (charArrayList.toTypedArray())
}