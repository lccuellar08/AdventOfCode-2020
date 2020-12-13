import java.io.File


/*
--- Day 12: Rain Risk ---
Your ferry made decent progress toward the island, but the storm came in faster than anyone expected. The ferry needs to take evasive actions!

Unfortunately, the ship's navigation computer seems to be malfunctioning; rather than giving a route directly to safety, it produced extremely circuitous instructions. When the captain uses the PA system to ask if anyone can help, you quickly volunteer.

The navigation instructions (your puzzle input) consists of a sequence of single-character actions paired with integer input values. After staring at them for a few minutes, you work out what they probably mean:

Action N means to move north by the given value.
Action S means to move south by the given value.
Action E means to move east by the given value.
Action W means to move west by the given value.
Action L means to turn left the given number of degrees.
Action R means to turn right the given number of degrees.
Action F means to move forward by the given value in the direction the ship is currently facing.
The ship starts by facing east. Only the L and R actions change the direction the ship is facing. (That is, if the ship is facing east and the next instruction is N10, the ship would move north 10 units, but would still move east if the following action were F.)

--- Part Two ---
Before you can give the destination to the captain, you realize that the actual action meanings were printed on the back of the instructions the whole time.

Almost all of the actions indicate how to move a waypoint which is relative to the ship's position:

Action N means to move the waypoint north by the given value.
Action S means to move the waypoint south by the given value.
Action E means to move the waypoint east by the given value.
Action W means to move the waypoint west by the given value.
Action L means to rotate the waypoint around the ship left (counter-clockwise) the given number of degrees.
Action R means to rotate the waypoint around the ship right (clockwise) the given number of degrees.
Action F means to move forward to the waypoint a number of times equal to the given value.
The waypoint starts 10 units east and 1 unit north relative to the ship. The waypoint is relative to the ship; that is, if the ship moves, the waypoint moves with it.

For example, using the same instructions as above:

F10 moves the ship to the waypoint 10 times (a total of 100 units east and 10 units north), leaving the ship at east 100, north 10. The waypoint stays 10 units east and 1 unit north of the ship.
N3 moves the waypoint 3 units north to 10 units east and 4 units north of the ship. The ship remains at east 100, north 10.
F7 moves the ship to the waypoint 7 times (a total of 70 units east and 28 units north), leaving the ship at east 170, north 38. The waypoint stays 10 units east and 4 units north of the ship.
R90 rotates the waypoint around the ship clockwise 90 degrees, moving it to 4 units east and 10 units south of the ship. The ship remains at east 170, north 38.
F11 moves the ship to the waypoint 11 times (a total of 44 units east and 110 units south), leaving the ship at east 214, south 72. The waypoint stays 4 units east and 10 units south of the ship.
After these operations, the ship's Manhattan distance from its starting position is 214 + 72 = 286.

Figure out where the navigation instructions actually lead. What is the Manhattan distance between that location and the ship's starting position?

 */

class Ferry(var x: Int = 0 , var y: Int = 0, var dir: Int = 0) {
    var wX = 10
    var wY = 1

    fun getManhattanDistance(): Int {
        return kotlin.math.abs(x) + kotlin.math.abs(y)
    }

    fun processCommand(command: Char, v: Int) {
        when(command) {
            'N' -> this.wY += v
            'S' -> this.wY -= v
            'E' -> this.wX += v
            'W' -> this.wX -= v
            'L' -> {
                val numRotations = v / 90
                for(i in 0 until numRotations) {
                    val newX = -1 * this.wY
                    val newY = this.wX
                    this.wX = newX
                    this.wY = newY
                }
            }
            'R' -> {
                val numRotations = v / 90
                for(i in 0 until numRotations) {
                    val newX = this.wY
                    val newY = -1 * this.wX
                    this.wX = newX
                    this.wY = newY
                }

            }
            'F' -> goForward(v)
        }
    }

    fun goForward(v: Int) {
//        when(this.dir) {
//            0 -> x += v
//            90 -> y += v
//            180 -> x -= v
//            270 -> y -= v
//            360 -> {x += v; println("Weird, this shouldn't happen")}
//        }
        this.x += v * this.wX
        this.y += v * this.wY
    }

    fun printFerry() {
        println("X: ${this.x}, Y: ${this.y}")
        println("wX: ${this.wX}, wY: ${this.wY}")
    }
}

fun main(args: Array<String>) {
    val testFile = "C:\\Users\\lccue\\IdeaProjects\\AdventOfCode2020\\input\\12_test_input.txt"
    val file = "C:\\Users\\lccue\\IdeaProjects\\AdventOfCode2020\\input\\12_input.txt"
    val commands = readInput12(file)
    val ferry = Ferry()
    for(command in commands) {
        println(command)
        ferry.processCommand(command.first, command.second)
        ferry.printFerry()
    }
    // 120469
    // 34123
    println("The manhattan distance: ${ferry.getManhattanDistance()}")
}

fun readInput12(fileName: String): List<Pair<Char,Int>> {
    val commands = mutableListOf<Pair<Char,Int>>()
    val file = File(fileName)

    file.forEachLine {
        val command = it[0]
        val value = it.substring(1 until it.length).toInt()
        commands.add(Pair(command, value))
    }

    return commands
}