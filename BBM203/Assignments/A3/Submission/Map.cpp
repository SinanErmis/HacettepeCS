#include "Map.h"
#include <fstream>
#include <iostream>
#include <sstream>

Map::Map() {
    // Initialize the 2D distance matrix
    distanceMatrix = new int*[MAX_SIZE];
    for (int i = 0; i < MAX_SIZE; ++i) {
        distanceMatrix[i] = new int[MAX_SIZE];
        for (int j = 0; j < MAX_SIZE; ++j) {
            distanceMatrix[i][j] = 0; // Initialize distances to 0
        }
    }

    // Initialize all provinces as unvisited
    for (int i = 0; i < MAX_SIZE; ++i) {
        visited[i] = false;
    }
}

Map::~Map() {
    for (int i = 0; i < MAX_SIZE; ++i) {
        delete[] distanceMatrix[i];
    }
    delete[] distanceMatrix;
}

// Loads distance data from a file and fills the distanceMatrix
void Map::loadDistanceData(const std::string& filename) {
    std::ifstream file(filename);
    if (!file.is_open()) {
        throw std::runtime_error("Could not open file: " + filename);
    }

    std::string line;
    int row = 0;

    while (std::getline(file, line) && row < MAX_SIZE) {
        std::stringstream ss(line);
        std::string cell;
        int col = 0;

        while (std::getline(ss, cell, ',') && col < MAX_SIZE) {
            distanceMatrix[row][col] = std::stoi(cell);
            ++col;
        }
        ++row;
    }
    file.close();
}

// Checks if the distance between two provinces is within the allowed maxDistance
bool Map::isWithinRange(int provinceA, int provinceB, int maxDistance) const {
    return distanceMatrix[provinceA][provinceB] <= maxDistance;
}

// Marks a province as visited
void Map::markAsVisited(int province) {
    visited[province] = true;
}

// Checks if a province has already been visited
bool Map::isVisited(int province) const {
    return visited[province];
}

// Resets all provinces to unvisited
void Map::resetVisited() {
    for (int i = 0; i < MAX_SIZE; ++i) {
        visited[i] = false;
    }
}

// Function to count the number of visited provinces
int Map::countVisitedProvinces() const {
    int count = 0;
    for (int i = 0; i < MAX_SIZE; ++i) {
        if (visited[i]) {
            ++count;
        }
    }
    return count;
}

// Function to get the distance between two provinces
int Map::getDistance(int provinceA, int provinceB) const {
    return distanceMatrix[provinceA][provinceB];
}