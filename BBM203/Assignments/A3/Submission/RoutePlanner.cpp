#include "RoutePlanner.h"
#include <iostream>
#include <fstream>

// Array to help you out with name of the cities in order
const std::string cities[81] = { 
    "Adana", "Adiyaman", "Afyon", "Agri", "Amasya", "Ankara", "Antalya", "Artvin", "Aydin", "Balikesir", "Bilecik", 
    "Bingol", "Bitlis", "Bolu", "Burdur", "Bursa", "Canakkale", "Cankiri", "Corum", "Denizli", "Diyarbakir", "Edirne", 
    "Elazig", "Erzincan", "Erzurum", "Eskisehir", "Gaziantep", "Giresun", "Gumushane", "Hakkari", "Hatay", "Isparta", 
    "Mersin", "Istanbul", "Izmir", "Kars", "Kastamonu", "Kayseri", "Kirklareli", "Kirsehir", "Kocaeli", "Konya", "Kutahya", 
    "Malatya", "Manisa", "Kaharamanmaras", "Mardin", "Mugla", "Mus", "Nevsehir", "Nigde", "Ordu", "Rize", "Sakarya", 
    "Samsun", "Siirt", "Sinop", "Sivas", "Tekirdag", "Tokat", "Trabzon", "Tunceli", "Urfa", "Usak", "Van", "Yozgat", 
    "Zonguldak", "Aksaray", "Bayburt", "Karaman", "Kirikkale", "Batman", "Sirnak", "Bartin", "Ardahan", "Igdir", 
    "Yalova", "Karabuk", "Kilis", "Osmaniye", "Duzce" 
};

// Constructor to initialize and load constraints
RoutePlanner::RoutePlanner(const std::string& distance_data, const std::string& priority_data, const std::string& restricted_data, int maxDistance)
    : maxDistance(maxDistance), totalDistanceCovered(0), numPriorityProvinces(0), numWeatherRestrictedProvinces(0) {

    map.loadDistanceData(distance_data);
    loadPriorityProvinces(priority_data);
    loadWeatherRestrictedProvinces(restricted_data);
}

// Load priority provinces from txt file to an array of indices
void RoutePlanner::loadPriorityProvinces(const std::string& filename) {
    std::ifstream inputFile(filename);
    if (!inputFile.is_open()) {
        std::cerr << "Error: Could not open file " << filename << std::endl;
        return;
    }

    std::string line;

    while (std::getline(inputFile, line) && numPriorityProvinces < MAX_PRIORITY_PROVINCES) {
        // Find the number inside parentheses
        size_t start = line.find('(');
        size_t end = line.find(')');

        // If both start and end is found and end comes after start
        if (start != std::string::npos && end != std::string::npos && end > start) {
            std::string numberStr = line.substr(start + 1, end - start - 1);

            priorityProvinces[numPriorityProvinces] = std::stoi(numberStr); // Convert to integer and store in the array
            numPriorityProvinces++;
        }
    }

    inputFile.close();
}

// Load weather-restricted provinces from txt file to an array of indices
void RoutePlanner::loadWeatherRestrictedProvinces(const std::string& filename) {
    std::ifstream inputFile(filename);
    if (!inputFile.is_open()) {
        std::cerr << "Error: Could not open file " << filename << std::endl;
        return;
    }

    std::string line;

    while (std::getline(inputFile, line) && numWeatherRestrictedProvinces < MAX_PRIORITY_PROVINCES) {
        // Find the number inside parentheses
        size_t start = line.find('(');
        size_t end = line.find(')');

        // If both start and end is found and end comes after start
        if (start != std::string::npos && end != std::string::npos && end > start) {
            std::string numberStr = line.substr(start + 1, end - start - 1);

            weatherRestrictedProvinces[numWeatherRestrictedProvinces] = std::stoi(numberStr); // Convert to integer and store in the array
            numWeatherRestrictedProvinces++;
        }
    }

    inputFile.close();
}

// Checks if a province is a priority province
bool RoutePlanner::isPriorityProvince(int province) const {
    for (int i = 0; i < numPriorityProvinces; i++){
      if(priorityProvinces[i] == province) return true;
    }
    return false;
}

// Checks if a province is weather-restricted
bool RoutePlanner::isWeatherRestricted(int province) const {
    for (int i = 0; i < numWeatherRestrictedProvinces; i++){
        if(weatherRestrictedProvinces[i] == province) return true;
    }
    return false;

}

// Begins the route exploration from the starting point
void RoutePlanner::exploreRoute(int startingCity) {
    // Start exploration from the initial city
    map.markAsVisited(startingCity);
    stack.push(startingCity);
    route.push_back(startingCity);

    // Continue exploring until the exploration is complete
    while (!isExplorationComplete()) {
        if (!stack.isEmpty()) {
            int currentProvince = stack.peek(); // Peek at the top of the stack
            exploreFromProvince(currentProvince);
        }
    }

    displayResults();
}

// Helper function to explore from a specific province
void RoutePlanner::exploreFromProvince(int province) {
    enqueueNeighbors(province);

    while (!queue.isEmpty()) {
        int neighbor = queue.dequeue();

        // Skip visited or invalid neighbors
        if (map.isVisited(neighbor)) continue;
        if (!map.isWithinRange(province, neighbor, maxDistance)) continue;
        if (isWeatherRestricted(neighbor)) {
            std::cout << "Province " << cities[neighbor] << " is weather-restricted. Skipping.\n";
            continue;
        }

        // Update state for the valid neighbor
        totalDistanceCovered += map.getDistance(province, neighbor);
        map.markAsVisited(neighbor);
        stack.push(neighbor);
        route.push_back(neighbor);

        // Explore from this new province
        exploreFromProvince(neighbor);
    }

    // If the queue is empty, try backtracking
    if (queue.isEmpty()) {
        backtrack();
    }
}

void RoutePlanner::enqueueNeighbors(int province) {
    // Enqueue priority & non-priority neighbors to the queue according to given constraints
    // Loop through all possible neighboring provinces
    for (int i = 0; i < MAX_SIZE; ++i) {
        if (i == province) continue; // Skip self
        if (map.isVisited(i) || !map.isWithinRange(province, i, maxDistance)) continue;

        if (isPriorityProvince(i)) {
            queue.enqueuePriority(i);
        } else {
            queue.enqueue(i);
        }
    }
}

void RoutePlanner::backtrack() {
    if (!stack.isEmpty()) {
        stack.pop(); // Pop the last visited province
        if (!stack.isEmpty()) {
            int previousProvince = stack.peek(); // Peek the previous province
            exploreFromProvince(previousProvince); // Continue exploration from there
        }
    }
}

bool RoutePlanner::isExplorationComplete() const {
    // Check if both stack and queue are empty
    if (stack.isEmpty() && queue.isEmpty()) return true;

    // Check if there are any unvisited provinces within range
    for (int i = 0; i < MAX_SIZE; ++i) {
        if (!map.isVisited(i) && map.isWithinRange(route.back(), i, maxDistance)) {
            return false; // Unvisited provinces found
        }
    }

    return true; // No provinces left to explore
}

void RoutePlanner::displayResults() const {
    std::cout << "----------------------------" << std::endl << "Journey Completed!" << std::endl<<"----------------------------"<<std::endl;
    std::cout << "Total Number of Provinces Visited: " << route.size() << std::endl;
    std::cout << "Total Distance Covered: " << totalDistanceCovered << "km" << std::endl;
    std::cout << "Route Taken:" << std::endl;
    for (int i = 0; i< route.size() - 1; i++) {
        std::cout << cities[route[i]] << " -> ";
    }
    std::cout << cities[route.size() - 1] << std::endl;

    std::cout << "Priority Provinces Status:"<< std::endl;

    int visitedPriorityProvinces = 0;
    for (int i = 0; i< numPriorityProvinces; i++) {
        bool isVisited = map.isVisited(priorityProvinces[i]);
        if(isVisited) visitedPriorityProvinces++;
        std::cout << "- " << cities[priorityProvinces[i]] << " (" << ( isVisited? "Visited" : "Not Visited") << ")" << std::endl;
    }
    std::cout << "Total Priority Provinces Visited: " << visitedPriorityProvinces << " out of " << numPriorityProvinces<< std::endl;

    if (visitedPriorityProvinces == numPriorityProvinces) {
        std::cout << "Success: All priority provinces were visited.";
    }
    else {
        std::cout << "Warning: Not all priority provinces were visited.";
    }
}


