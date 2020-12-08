import java.io.File

/*
--- Day 7: Handy Haversacks ---
You land at the regional airport in time for your next flight. In fact, it looks like you'll even have time to grab some food: all flights are currently delayed due to issues in luggage processing.

Due to recent aviation regulations, many rules (your puzzle input) are being enforced about bags and their contents; bags must be color-coded and must contain specific quantities of other color-coded bags. Apparently, nobody responsible for these regulations considered how long they would take to enforce!

For example, consider the following rules:

light red bags contain 1 bright white bag, 2 muted yellow bags.
dark orange bags contain 3 bright white bags, 4 muted yellow bags.
bright white bags contain 1 shiny gold bag.
muted yellow bags contain 2 shiny gold bags, 9 faded blue bags.
shiny gold bags contain 1 dark olive bag, 2 vibrant plum bags.
dark olive bags contain 3 faded blue bags, 4 dotted black bags.
vibrant plum bags contain 5 faded blue bags, 6 dotted black bags.
faded blue bags contain no other bags.
dotted black bags contain no other bags.
These rules specify the required contents for 9 bag types. In this example, every faded blue bag is empty, every vibrant plum bag contains 11 bags (5 faded blue and 6 dotted black), and so on.

You have a shiny gold bag. If you wanted to carry it in at least one other bag, how many different bag colors would be valid for the outermost bag? (In other words: how many colors can, eventually, contain at least one shiny gold bag?)

In the above rules, the following options would be available to you:

A bright white bag, which can hold your shiny gold bag directly.
A muted yellow bag, which can hold your shiny gold bag directly, plus some other bags.
A dark orange bag, which can hold bright white and muted yellow bags, either of which could then hold your shiny gold bag.
A light red bag, which can hold bright white and muted yellow bags, either of which could then hold your shiny gold bag.
So, in this example, the number of bag colors that can eventually contain at least one shiny gold bag is 4.

How many bag colors can eventually contain at least one shiny gold bag? (The list of rules is quite long; make sure you get all of it.)

--- Part Two ---
It's getting pretty expensive to fly these days - not because of ticket prices, but because of the ridiculous number of bags you need to buy!

Consider again your shiny gold bag and the rules from the above example:

faded blue bags contain 0 other bags.
dotted black bags contain 0 other bags.
vibrant plum bags contain 11 other bags: 5 faded blue bags and 6 dotted black bags.
dark olive bags contain 7 other bags: 3 faded blue bags and 4 dotted black bags.
So, a single shiny gold bag must contain 1 dark olive bag (and the 7 bags within it) plus 2 vibrant plum bags (and the 11 bags within each of those): 1 + 1*7 + 2 + 2*11 = 32 bags!

Of course, the actual rules have a small chance of going several levels deeper than this example; be sure to count all of the bags, even if the nesting becomes topologically impractical!

Here's another example:

shiny gold bags contain 2 dark red bags.
dark red bags contain 2 dark orange bags.
dark orange bags contain 2 dark yellow bags.
dark yellow bags contain 2 dark green bags.
dark green bags contain 2 dark blue bags.
dark blue bags contain 2 dark violet bags.
dark violet bags contain no other bags.
In this example, a single shiny gold bag must contain 126 other bags.

How many individual bags are required inside your single shiny gold bag?

 */

class BagNode(val color: String) {
    val bagMap = mutableMapOf<BagNode, Int>()

    fun addBag(bag: BagNode, amount: Int) {
        this.bagMap[bag] = amount
    }

    fun countBags(sourceBag: BagNode): Int {
        // Base Case
        if(sourceBag == null)
            return 0

        if(bagMap.isEmpty())
            return 1

        var numOfBags = 1
        for((bag, count) in sourceBag.bagMap.entries) {
            var countSubBags = countBags(bag)
            println("${bag.color}: $count * $countSubBags")
            numOfBags += count * countSubBags
        }
        return numOfBags
    }

    fun containsBag(bag: BagNode): Boolean {
        return containsBag(bag, this)
    }

    fun containsBag(targetBag: BagNode, sourceBag: BagNode): Boolean {
        // Base Case 1
        // We reach null
        if(sourceBag == null)
            return false

        // Base Case 2
        // Check if this source bag is the target
        if(targetBag == sourceBag)
            return true

        // Recursive Case
        for(bag in bagMap.keys) {
            var found = bag.containsBag(targetBag, bag)
            if(found)
                return true
        }

        return false
    }

    override fun equals(other: Any?): Boolean {
        if(!(other is BagNode))
            return false
        var otherBad = other as BagNode
        return otherBad.color == this.color
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }
}

fun main(args: Array<String>) {
    val filename = "C:\\Users\\lccue\\IdeaProjects\\AdventOfCode2020\\input\\7_input.txt"
    var listOfBags = readInput7(filename)

    val targetBag = BagNode("shiny gold")

    var counter = 0
    for(bag in listOfBags) {
        var flag = bag.containsBag(targetBag)
        //println("${bag.color} contains: ${targetBag.color} = $flag")
        println(bag.color)
        for(subBag in bag.bagMap.keys)
            println("\t${subBag.color}: ${bag.bagMap[subBag]}")
        if(flag && bag != targetBag)
            counter++
    }
    println("Total available bags: $counter")

    var goldBag = findBag("shiny gold", listOfBags)
    var totalBags = goldBag.countBags(goldBag)

    println("Total bags needed for a gold bag: ${totalBags - 1}")

}

fun findBag(color: String, listOfBags: List<BagNode>): BagNode {
    for(bag in listOfBags) {
        if(bag.color == color) {
            return bag
        }
    }
    return BagNode(color)
}

fun containsBag(color: String, listOfBags: List<BagNode>): Boolean {
    for(bag in listOfBags) {
        if(bag.color == color) {
            return true
        }
    }
    return false
}

fun readInput7(filename: String): List<BagNode> {
    val file = File(filename)
    var listOfBags = mutableListOf<BagNode>()

    file.forEachLine {
        // Split by 'contain' gives us two strings:
        // [0]: Super Bag
        // [1]: All child Bags
        var tokens = it.split("contain")
        var colorTokens = tokens[0].trim().split(" ")
        var color = colorTokens[0] + " " + colorTokens[1]

        val superBag = findBag(color, listOfBags)

        if(tokens[1].contains("no")) {
            // Do nothing
        }
        else {

            // Split [1] by ','
            // Every element is a bag
            var bags = tokens[1].trim().split(",")
            for (bagString in bags) {
                var bagTokens = bagString.replace(".", "").trim().split(" ")
                var count = bagTokens[0].toInt()
                var color = bagTokens[1] + " " + bagTokens[2]

                var childBag = findBag(color, listOfBags)
                superBag.addBag(childBag, count)
                if(!containsBag(color, listOfBags))
                    listOfBags.add(childBag)
            }
        }
        if(!containsBag(color, listOfBags))
            listOfBags.add(superBag)
    }

    return listOfBags
}