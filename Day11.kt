import java.io.File
import kotlin.math.floor

/*
--- Day 11: Seating System ---
Your plane lands with plenty of time to spare. The final leg of your journey is a ferry that goes directly to the tropical island where you can finally start your vacation. As you reach the waiting area to board the ferry, you realize you're so early, nobody else has even arrived yet!

By modeling the process people use to choose (or abandon) their seat in the waiting area, you're pretty sure you can predict the best place to sit. You make a quick map of the seat layout (your puzzle input).

The seat layout fits neatly on a grid. Each position is either floor (.), an empty seat (L), or an occupied seat (#). For example, the initial seat layout might look like this:

L.LL.LL.LL
LLLLLLL.LL
L.L.L..L..
LLLL.LL.LL
L.LL.LL.LL
L.LLLLL.LL
..L.L.....
LLLLLLLLLL
L.LLLLLL.L
L.LLLLL.LL
Now, you just need to model the people who will be arriving shortly. Fortunately, people are entirely predictable and always follow a simple set of rules. All decisions are based on the number of occupied seats adjacent to a given seat (one of the eight positions immediately up, down, left, right, or diagonal from the seat). The following rules are applied to every seat simultaneously:

If a seat is empty (L) and there are no occupied seats adjacent to it, the seat becomes occupied.
If a seat is occupied (#) and four or more seats adjacent to it are also occupied, the seat becomes empty.
Otherwise, the seat's state does not change.
Floor (.) never changes; seats don't move, and nobody sits on the floor.

--- Part Two ---
As soon as people start to arrive, you realize your mistake. People don't just care about adjacent seats - they care about the first seat they can see in each of those eight directions!

Now, instead of considering just the eight immediately adjacent seats, consider the first seat in each of those eight directions. For example, the empty seat below would see eight occupied seats:

.......#.
...#.....
.#.......
.........
..#L....#
....#....
.........
#........
...#.....
The leftmost empty seat below would only see one empty seat, but cannot see any of the occupied ones:

.............
.L.L.#.#.#.#.
.............
The empty seat below would see no occupied seats:

.##.##.
#.#.#.#
##...##
...L...
##...##
#.#.#.#
.##.##.
Also, people seem to be more tolerant than you expected: it now takes five or more visible occupied seats for an occupied seat to become empty (rather than four or more from the previous rules). The other rules still apply: empty seats that see no occupied seats become occupied, seats matching no rule don't change, and floor never changes.

Given the same starting layout as above, these new rules cause the seating area to shift around as follows:

 */

fun main(args: Array<String>) {
    val testFile = "C:\\Users\\lccue\\IdeaProjects\\AdventOfCode2020\\input\\11_test_input.txt"
    val file = "C:\\Users\\lccue\\IdeaProjects\\AdventOfCode2020\\input\\11_input.txt"
    var floorPlan = readInput11(file)

    var iterations = 1
    while(true) {
        var newFloorPlan = simulateSeating2(floorPlan) ?: break
        floorPlan = newFloorPlan
        println("Iteration: $iterations")
        for (list in newFloorPlan) {
            println(list)
        }
        iterations++
    }

    var totalSeatsOccupied = 0
    floorPlan.forEach {
        it.forEach {
            if(it == '#')
                totalSeatsOccupied++
        }
    }
    println("Total seats occupied: $totalSeatsOccupied")
}

fun simulateSeating(floorPlan: List<List<Char>>): List<List<Char>>? {
    val newFloorPlan = mutableListOf<List<Char>>()
    var changeCount = 0

    for(row in floorPlan.indices) {
        val rowOfSeats = mutableListOf<Char>()
        for(col in floorPlan[row].indices) {
            val p = floorPlan[row][col]
            if(p == 'L') {
                val neighbors = getNeighbors(floorPlan, col, row)
                var hasNoNeighbors = true
                neighbors.forEach {
                    if(it == '#')
                        hasNoNeighbors = false
                }
                if(hasNoNeighbors) {
                    rowOfSeats.add('#')
                    changeCount++
                }
                else
                    rowOfSeats.add('L')
            }
            else if(p == '#') {
                val neighbors = getNeighbors(floorPlan, col, row)
                var occupiedCount = 0
                neighbors.forEach {
                    if(it == '#')
                        occupiedCount += 1
                }
                if(occupiedCount >= 4) {
                    rowOfSeats.add('L')
                    changeCount++
                }
                else
                    rowOfSeats.add('#')
            }
            else
                rowOfSeats.add('.')
        }
        newFloorPlan.add(rowOfSeats)
    }
    if(changeCount == 0)
        return null
    return newFloorPlan
}

fun simulateSeating2(floorPlan: List<List<Char>>): List<List<Char>>? {
    val newFloorPlan = mutableListOf<List<Char>>()
    var changeCount = 0

    for(row in floorPlan.indices) {
        val rowOfSeats = mutableListOf<Char>()
        for(col in floorPlan[row].indices) {
            val p = floorPlan[row][col]
            if(p == 'L') {
                val neighbors = getNeighbors2(floorPlan, col, row)
                var hasNoNeighbors = true
                neighbors.forEach {
                    if(it == '#')
                        hasNoNeighbors = false
                }
                if(hasNoNeighbors) {
                    rowOfSeats.add('#')
                    changeCount++
                }
                else
                    rowOfSeats.add('L')
            }
            else if(p == '#') {
                val neighbors = getNeighbors2(floorPlan, col, row)
                var occupiedCount = 0
                neighbors.forEach {
                    if(it == '#')
                        occupiedCount += 1
                }
                if(occupiedCount >= 5) {
                    rowOfSeats.add('L')
                    changeCount++
                }
                else
                    rowOfSeats.add('#')
            }
            else
                rowOfSeats.add('.')
        }
        newFloorPlan.add(rowOfSeats)
    }
    if(changeCount == 0)
        return null
    return newFloorPlan
}

fun getNeighbors2(floorPlan: List<List<Char>>, x: Int, y: Int): List<Char> {
    val neighbors = mutableListOf<Char>()
    for(dy in -1..1) {
        for(dx in -1..1) {
            try {
                if(dy == 0 && dx == 0)
                    continue
                for(d in 1..floorPlan.size) {
                    val neighbor = floorPlan[y + (dy * d)][x + (dx * d)]
                    if(neighbor != '.') {
                        neighbors.add(neighbor)
                        break
                    }
                }
            }
            catch(e: Exception) {
                // Do nothing
            }
        }
    }
    return neighbors
}

fun getNeighbors(floorPlan: List<List<Char>>, x: Int, y: Int): List<Char> {
    val neighbors = mutableListOf<Char>()
    for(dy in -1..1) {
        for(dx in -1..1) {
            try {
                val neighbor = floorPlan[y + dy][x + dx]
                if(dy == 0 && dx == 0)
                    continue
                if(neighbor != '.')
                    neighbors.add(neighbor)
            }
            catch(e: Exception) {
                // Do nothing
            }
        }
    }
    return neighbors
}


fun readInput11(fileName: String): List<List<Char>>{
    val seatArray = mutableListOf<List<Char>>()
    val file = File(fileName)
    file.forEachLine {
        val seats = mutableListOf<Char>()
        it.forEach { seats.add(it) }
        seatArray.add(seats)
    }
    return seatArray
}