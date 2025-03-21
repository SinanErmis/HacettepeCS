#include <vector>
#include <string>
#include <fstream>
#include <iostream>
#include <sstream>
#include <map>

/**
 * Reads the contents of a file and stores each line as a string in a vector.
 *
 * @param fileName The name of the file to read.
 * @return A vector of strings containing the lines from the file.
 * @note If the file cannot be opened, the program exits with a status code of -1.
 */
std::vector<std::string> readLinesFromFile(const std::string& fileName) {
    std::vector<std::string> lines; // Create a vector to store the lines
    std::ifstream file(fileName); // Open the file

    if (!file.is_open()) {
        std::cerr << "Error: Unable to open file: " << fileName << std::endl;
        std::exit(-1); // Exit the program with a status code of -1
    }

    std::string line;
    while (std::getline(file, line)) { // Read each line from the file
        lines.push_back(line); // Add the line to the vector
    }

    file.close(); // Close the file
    return lines;
}

/**
 * Converts a space-separated string of integers into a vector of integers.
 *
 * @param input The input string containing space-separated integers.
 * @return A vector of integers.
 */
std::vector<int> stringToIntegerVector(const std::string& input) {
    std::vector<int> result;
    std::istringstream iss(input);
    int number;

    while (iss >> number) {
        result.push_back(number);
    }

    return result;
}

/**
 * This function calculates the letter grade based on the given grade point.
 *
 * @param point: Grade point (float)
 * @return: Letter grade (std::string)
 */
std::string calculateGrade(float point) {
    if (point >= 95) {
        return "A1";
    } else if (point >= 90) {
        return "A2";
    } else if (point >= 85) {
        return "A3";
    } else if (point >= 80) {
        return "B1";
    } else if (point >= 75) {
        return "B2";
    } else if (point >= 70) {
        return "B3";
    } else if (point >= 65) {
        return "C1";
    } else if (point >= 60) {
        return "C2";
    } else if (point >= 55) {
        return "C3";
    } else if (point >= 50) {
        return "D";
    } else {
        return "F3";
    }
}

/**
 * This function counts the occurrences of each element in the given array and returns a map.
 *
 * @param elements: The array containing elements (std::vector<std::string>)
 * @return: A map containing the counts of elements (std::map<std::string, int>)
 */
std::map<std::string, int> countLetters(const std::vector<std::string>& elements) {
    std::map<std::string, int> elementCount;

    // Initialize each letter grade with a count of 0
    std::vector<std::string> allGrades = {"A1", "A2", "A3", "B1", "B2", "B3", "C1", "C2", "C3", "D", "F1", "F2", "F3"};
    for (const std::string& grade : allGrades) {
        elementCount[grade] = 0;
    }

    // Count occurrences of each element in the array
    for (const std::string& element : elements) {
        elementCount[element]++;
    }

    return elementCount;
}


