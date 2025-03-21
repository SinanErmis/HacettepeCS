#ifndef FILE_UTILS_H
#define FILE_UTILS_H

#include <string>
#include <vector>
#include <fstream>
#include <map>

// Function to read lines from a file and store them in a vector
std::vector<std::string> readLines(const std::string& fileName);

// Function to append a string as a new line to a file
void appendLine(const std::string& fileName, const std::string& content);

// Function to convert a space-separated string of integers into a vector of integers
std::vector<int> stringToIntegerVector(const std::string& input);

// Function to split a string with a delimeter.
std::vector<std::string> splitString(const std::string& mainStr, const std::string& delimiter = " ")

#endif // FILE_UTILS_H
