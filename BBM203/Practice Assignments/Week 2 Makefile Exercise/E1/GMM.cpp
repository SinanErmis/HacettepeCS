#include <iostream>
#include <fstream>
#include <sstream>
#include <vector>
#include <string>
#include <cmath>
#include "GMM.h"

/**
 * Reads the content of a file and returns each line.
 * 
 * @param fileName The name of the file to be read.
 * @return A vector containing each line from the file.
 */
std::vector<std::string> read_file(const std::string &fileName)
{
    std::vector<std::string> lines;    // Vector to store each line from the file.
    std::ifstream inputFile(fileName); // File stream for reading the file.

    std::string line;
    // Read each line from the file until EOF (end-of-file) is reached.
    while (std::getline(inputFile, line))
    {                          
        lines.push_back(line); // Add the line to the vector.
    }

    inputFile.close(); // Close the file after reading.
    return lines; // Return the vector of lines.
}

/**
 * Parses input data to extract calorie information.
 *
 * This function processes each line of product information
 * and extracts nutritional data (i.e. protein, carbohydrate, and fat). 
 * It then calculates the corresponding calorie value for each product and stores it in a dynamically allocated array.
 *
 * @param input A vector of strings, where each string represents a line of product data.
 * @return A pointer to a dynamically allocated array containing calorie values.
 * @throws std::runtime_error If more entries are processed than allowed by TOTAL_CAPACITY.
 */
double *parse_calory_input(const std::vector<std::string> &input)
{
    double *calories = new double[TOTAL_CAPACITY]; // Array to store calorie values.
    int index = 0;

    for (const auto &line : input)
    {
        std::vector<std::string> productData; // Vector to store split data from the line.
        std::stringstream inputStream(line); // Create a stringstream to split the line.
        std::string item;

        // Split the line by tabs and store each part in `productData`.
        while (std::getline(inputStream, item, '\t'))
        {
            productData.push_back(item);
        }

        std::vector<std::string> nutritionValues; // Vector to store nutrition values (protein, carbs, fat).
        std::stringstream productStream(productData[2]); // Extract the nutritional data
        std::string i;

        // Split the nutritional data by spaces and store in `nutritionValues`.
        while (std::getline(productStream, i, ' '))
        {
            nutritionValues.push_back(i);
        }

        // Calculate calories using the parsed nutrition values.
        double calorie = calculate_calorie(nutritionValues);

        // Store the calorie value in the array.
        calories[index] = calorie; 
        index++;

        // Input file should not have more lines than the total number of slots in the GMM.
        if (index >= TOTAL_CAPACITY)
        {
            std::cerr << "ERROR: Corrupt Data" << std::endl;
            std::exit(-1); // Exit the program with an error code.
        }
    }

    return calories;
}

/**
 * Calculates the calorie content based on nutrition values.
 *
 * @param nutritionValues A vector of strings representing the nutrition values (protein, carbohydrate, fat).
 * @return The calculated calorie content as a double.
 * @throws std::invalid_argument If there are fewer than 3 nutrition values in the input.
 */
double calculate_calorie(const std::vector<std::string> &nutritionValues)
{
    // Check if there are enough nutrition values (protein, carbs, fat).
    if (nutritionValues.size() < 3)
    {
        throw std::invalid_argument("ERROR: Insufficient nutrition values");
    }

    // Convert the protein, carbohydrate, and fat values (in that order) from strings to doubles. 
    double protein = std::stod(nutritionValues[1]);
    double carbohydrate = std::stod(nutritionValues[2]);
    double fat = std::stod(nutritionValues[3]);

    // Calculate calories using the Atwater system:
    // 4 calories per gram of protein and carbohydrate, 9 calories per gram of fat.
    int calorie = protein * 4 + carbohydrate * 4 + fat * 9;

    return calorie;
}

/**
 * @brief Calculates the standard deviation of an array of values.
 *
 *
 * @param values A pointer to the array of values.
 * @param size The size of the array (number of values).
 * @return The standard deviation of the array values.
 */
double calculate_standard_deviation(double *values, int size)
{
    double sum; // Variable to store the sum of the array elements.
    double mean, size, variance = 0.0; // Variables for mean and variance calculations.

    // Calculate the sum of the array elements (calories).
    for (int i = 0; i < size; ++i)
    {
        sum += values[i];
    }

    // Calculate the mean (average) of the values.
    mean = sum / 24;

    // Calculate the variance.
    for (int i = 0; i < size; ++i)
    {
        variance += (values[i] - mean) * (values[i] - mean); // (value - mean) squared.
    }

    variance /= size; // For population standard deviation (divide by total size).

    // Return the square root of the variance to get the standard deviation.
    return std::sqrt(variance);
}