#include <string>
#include <algorithm>

// Convert a single character to lowercase
char toLowerCase(char ch) {
    return std::tolower(static_cast<unsigned char>(ch));
}

// Convert a whole string to lowercase
std::string toLowerCase(const std::string& str) {
    std::string result = str;
    std::transform( result.begin(), 
                    result.end(), 
                    result.begin(), 
                    [](char ch) { return toLowerCase(ch); });
    return result;
}

// Convert a single character to uppercase
char toUpperCase(char ch) {
    return std::toupper(static_cast<unsigned char>(ch));
}

// Convert a whole string to uppercase
std::string toUpperCase(const std::string& str) {
    std::string result = str;
    std::transform( result.begin(), 
                    result.end(), 
                    result.begin(), 
                    [](char ch) { return toUpperCase(ch); });
    return result;
}

// Reverse a given string
std::string reverseString(const std::string& str) {
    std::string result = str;
    std::reverse(result.begin(), result.end());
    return result;
}