#include <string>
#include <vector>
#include <fstream>
#include <map>

std::vector<std::string> letters = {"A1", "A2", "A3", "B1", "B2", "B3", "C1", "C2", "C3", "D", "F1", "F2", "F3"};


/**
 * Reads the contents of a file and stores each line as a string in a vector.
 *
 * @param fileName The name of the file to read.
 * @return A vector of strings containing the lines from the file.
 * @note If the file cannot be opened, the program exits with a status code of -1.
 */
std::vector<std::string> readLines(const std::string& fileName) {

    //Fill this method.

}


/**
 * Appends a string to a file as a new line.
 *
 * @param fileName The name of the file to append to.
 * @param content The string to be appended as a new line.
 */
void appendLine(const std::string& fileName, const std::string& content) {

    //Fill this method.

}

/**
 * Converts a space-separated string of integers into a vector of integers.
 *
 * @param input The input string containing space-separated integers.
 * @return A vector of integers.
 */
std::vector<int> stringToIntegerVector(const std::string& input) {

    //Fill this method

}

/**
 * This function calculates the letter grade based on the given grade point.
 *
 * @param point: Grade point (float)
 * @return: Letter grade (std::string)
 */
std::string calculateGrade(float point) {

    //Fill this method

}

/**
 * Returns the frequencies of letters grades in a vector<string> as a map.
 * Takes parameter as a vector such as {"A1", "A2", "F1", "B3"}
 *
 * @param elements: The array containing elements (std::vector<std::string>)
 * @return: A map containing the counts of elements (std::map<std::string, int>)
 */
std::map<std::string, int> countLetters(const std::vector<std::string>& elements) {

    //Fill this method.

}

/**
 * Split a string into a vector of substrings based on a delimiter.
 *
 * @param mainStr The main string to be split.
 * @param delimiter The delimiter string used for splitting (default is " ").
 * @return A vector of strings containing the substrings.
 */
std::vector<std::string> splitString(const std::string& mainStr, const std::string& delimiter = " ") {

	//Fill this method.

}
