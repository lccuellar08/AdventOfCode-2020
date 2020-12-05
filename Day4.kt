import java.io.File

/*
--- Day 4: Passport Processing ---
You arrive at the airport only to realize that you grabbed your North Pole Credentials instead of your passport. While these documents are extremely similar, North Pole Credentials aren't issued by a country and therefore aren't actually valid documentation for travel in most of the world.
It seems like you're not the only one having problems, though; a very long line has formed for the automatic passport scanners, and the delay could upset your travel itinerary.
Due to some questionable network security, you realize you might be able to solve both of these problems at the same time.
The automatic passport scanners are slow because they're having trouble detecting which passports have all required fields. The expected fields are as follows:

byr (Birth Year)
iyr (Issue Year)
eyr (Expiration Year)
hgt (Height)
hcl (Hair Color)
ecl (Eye Color)
pid (Passport ID)
cid (Country ID)
Passport data is validated in batch files (your puzzle input). Each passport is represented as a sequence of key:value pairs separated by spaces or newlines. Passports are separated by blank lines.

 */

data class Passport(val fields: Map<String, String>) {

    private fun isValidCount(): Boolean {
        // Check if passport has 8 fields. We assume all keys are valid
        if(fields.size == 8)
            return true

        // Check if we have at least 7 fields, and cid is not one of them
        if(fields.size == 7) {
            return !fields.containsKey("cid")
        }

        // We either have less than 7 or more than 8 fields, return false
        return false
    }

    fun isValid(): Boolean {
        if(!isValidCount()) {
            println("Not valid count")
            return false
        }
        // Now assuming at least 7, at most 8 fields.
        // Assume that all field names are valid
        try {
            // Birth Year
            // byr (Birth Year) - four digits; at least 1920 and at most 2002.
            val birthYear = fields["byr"]?.toInt()
            if(birthYear == null || birthYear < 1920 || birthYear > 2002) {
                println("Invalid birthyear: $birthYear")
                return false
            }

            // Issue Year
            // iyr (Issue Year) - four digits; at least 2010 and at most 2020.
            val issueYear = fields["iyr"]?.toInt()
            if(issueYear == null || issueYear < 2010 || issueYear > 2020) {
                println("Invalid issueYear: $issueYear")
                return false
            }

            // Expiration Year
            // eyr (Expiration Year) - four digits; at least 2020 and at most 2030.
            val expirationYear = fields["eyr"]?.toInt()
            if(expirationYear == null || expirationYear < 2020 || expirationYear > 2030) {
                println("Invalid expirationYear: $expirationYear")
                return false
            }

            // Height
            // hgt (Height) - a number followed by either cm or in:
            // If cm, the number must be at least 150 and at most 193.
            // If in, the number must be at least 59 and at most 76.

            // Let's check for a substring of cm or in
            val cmIndex = fields["hgt"]?.indexOf("cm")
            val inIndex = fields["hgt"]?.indexOf("in")
            if(cmIndex != -1 && cmIndex != null) {
                val height = fields["hgt"]?.subSequence(0, cmIndex).toString().toInt()
                if(height < 150 || height > 193) {
                    println("Invalid Height: ${fields["hgt"]}")
                    return false
                }
            }
            else if(inIndex != -1 && inIndex != null) {
                val height = fields["hgt"]?.subSequence(0, inIndex).toString().toInt()
                if(height < 59 || height > 76) {
                    println("Invalid Height: ${fields["hgt"]}")
                    return false
                }
            }
            else {
                println("Invalid Height: ${fields["hgt"]}")
                println("\t No unit")
                return false
            }

            // Hair Color
            // hcl (Hair Color) - a # followed by exactly six characters 0-9 or a-f.
            if(fields["hcl"]?.length != 7) {
                println("Invalid hair color: ${fields["hcl"]}")
                return false
            }

            if(fields["hcl"]?.get(0) != '#') {
                println("Invalid hair color: ${fields["hcl"]}")
                println("\tFirst character not equal to '#'")
                return false
            }

            // ^[0-9a-f]{6}$
            if(fields["hcl"]?.contains(Regex("^#[0-9a-f]{6}\$")) == false) {
                println("Invalid hair color: ${fields["hcl"]}")
                return false
            }

            // Eye Color
            // ecl (Eye Color) - exactly one of: amb blu brn gry grn hzl oth.
            val eyeColors = listOf<String>("amb", "blu", "brn", "gry", "grn", "hzl", "oth")
            if(!eyeColors.contains(fields["ecl"])) {
                println("Invalid eye color: ${fields["ecl"]}")
                return false
            }

            // Passport ID
            // pid (Passport ID) - a nine-digit number, including leading zeroes.
            val passportID = fields["pid"]?.toLong()
            if(fields["pid"]?.length != 9)
                return false
            if(passportID == null || passportID < 0 || passportID > 999999999) {
                println("Invalid passport ID: $passportID")
                return false
            }

        // cid (Country ID) - ignored, missing or not.
        } catch (e: Exception) {
            println(e.message)
            return false
        }

    return true
    }

    override fun toString(): String {
        return fields.toString()
    }
}

fun main(args: Array<String>) {

    val filename = "C:\\Users\\lccue\\IdeaProjects\\AdventOfCode2020\\input\\4_input.txt"
    val passports = readInput4(filename)

    var validPassportCount = 0
    for(passport in passports) {
        if(passport.isValid()) {
            validPassportCount++
        }
//        println(passport.fields["pid"])
    }

    println("Total Passports: ${passports.size}")
    println("Total valid: $validPassportCount")
}

fun copyMap(map: Map<String, String>): Map<String, String> {
    val newMap = mutableMapOf<String,String>()
    for (entry in map) {
        newMap[entry.key] = entry.value
    }
    return newMap
}

fun readInput4(filename: String): List<Passport> {
    var file = File(filename)
    var passports = mutableListOf<Passport>()
    var fields = mutableMapOf<String, String>()

    file.forEachLine {
        if(it.isBlank()) {
            val fieldsCopy = copyMap(fields)
            val passport = Passport(fieldsCopy)
            passports.add(passport)
            fields.clear()
        }
        else {
            var tokens = it.split(" ")
            for(token in tokens) {
                var keyValues = token.split(":")
                fields[keyValues[0]] = keyValues[1]
            }
        }
    }
    if(fields.isNotEmpty()) {
        val fieldsCopy = copyMap(fields)
        val passport = Passport(fieldsCopy)
        passports.add(passport)
    }

    return passports
}