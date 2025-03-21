#ifndef MAP_H
#define MAP_H

#include <iostream>
#include <memory>
#include <vector>
#include <fstream>
#include <queue>
#include <cmath>
#include "Isle.h"

struct MapNode
{
    Isle *isle;
    MapNode *left, *right;
    bool isRed; // Color of the node: red = true, black = false

    MapNode(Isle *isle) : isle(isle), left(nullptr), right(nullptr), isRed(true){}
    ~MapNode()
    {
        // TODO: Free any dynamically allocated memory if necessary
    }
};

class Map
{
private:
    MapNode *root; // Root node of the tree

    // LLRBT helper methods
    bool isRed(MapNode *node);

    // LLRBT Rotations
    MapNode *rotateRight(MapNode *current);
    MapNode *rotateLeft(MapNode *current);

    // Helper functions for recursive LLRBT insertion and deletion
    // USE IT
    MapNode *insert(MapNode *node, Isle *isle);

    // Item distribution functions
    void preOrderItemDrop(MapNode *current, int &count);
    void postOrderItemDrop(MapNode *current, int &count);

    // Display helper
    void display(MapNode *current, int depth, int state);

public:
    // Constructor declaration
    Map();
    // Destructor
    ~Map();

    MapNode* getRoot(); //Root Getter

    // Tree operations
    void insert(Isle *isle);

    /// Search
    Isle *findIsle(Isle isle);
    Isle *findIsle(std::string name);
    MapNode *findNode(Isle isle);
    MapNode *findNode(std::string name);

    // Initilize tree from a vector
    void initializeMap(std::vector<Isle *> isles);

    MapNode *findFirstEmptyIsle(MapNode *node);

    int getDepth(MapNode *node); // Determines the depth of a node within the tree.
    
    // Display tree in terminal
    void displayMap();

    // Item drop api
    void populateWithItems();
    void dropItemBFS();


    // Write the AVL tree to a file with levels
    void writeToFile(const std::string &filename);
    
    // Write current Isles to file in alphabetical order
    void writeIslesToFile(const std::string &filename);
};

#endif