//// <DO NOT CHANGE>
#ifndef GMM
#define GMM

#include <vector>
#include <string>
#include <iostream>

#define ROW_CAPACITY 6
#define COLUMN_CAPACITY 3
#define TOTAL_CAPACITY ROW_CAPACITY * COLUMN_CAPACITY
#define MIN_DIVERSTY 200
//// </DO NOT CHANGE>

std::vector<std::string> read_file(const std::string& fileName);
double calculate_calorie(const std::vector<std::string>& nutritionValues);
double * parse_calorie_input(const std::vector<std::string>& input); 
double calculate_standard_deviation(double * values, int size);

// HINT: The intern speaks Greek

#endif