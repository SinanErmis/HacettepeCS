#include "Map.h"

Map::Map()
{
    this->root = nullptr;
}
Map::~Map()
{
    // TODO: Free any dynamically allocated memory if necessary
}

MapNode* Map::getRoot(){
    return root;
}

bool Map::isRed(MapNode *node)
{
    return node != nullptr && node->isRed;
}


void Map::initializeMap(std::vector<Isle *> isles)
{
    // TODO: Insert innitial isles to the tree
    // Then populate with Goldium and Einstainium items
}

MapNode *Map::rotateRight(MapNode *current)
{
    // TODO: Perform right rotation according to LLRBT
    // return necessary new root
    // Use std::cerr << "[Right Rotation] " << "Called on invalid node!" << std::endl;
    return nullptr;
}

MapNode *Map::rotateLeft(MapNode *current)
{
    // TODO: Perform left rotation according to LLRBT
    // return necessary new root
    // Use std::cerr << "[Left Rotation] " << "Called on invalid node!" << std::endl;
    return nullptr;
}

MapNode *Map::insert(MapNode *node, Isle *isle)
{
    MapNode *newNode = nullptr;

    // TODO: Recursively insert isle to the tree
    // returns inserted node

    return newNode;
}

void Map::insert(Isle *isle)
{
    root = insert((root), isle);
}

void Map::preOrderItemDrop(MapNode *current, int &count)
{
    // TODO: Drop EINSTEINIUM according to rules
    // Use std::cout << "[Item Drop] " << "EINSTEINIUM dropped on Isle: " << current->isle->getName() << std::endl;
}

// to Display the values by Post Order Method .. left - right - node
void Map::postOrderItemDrop(MapNode *current, int &count)
{
    // TODO: Drop GOLDIUM according to rules
    // Use  std::cout << "[Item Drop] " << "GOLDIUM dropped on Isle: " << current->isle->getName() << std::endl;
}

MapNode *Map::findFirstEmptyIsle(MapNode *node)
{
    // TODO: Find first Isle with no item
    return nullptr;
}

void Map::dropItemBFS()
{
    // TODO: Drop AMAZONITE according to rules
    // Use std::cout << "[BFS Drop] " << "AMAZONITE dropped on Isle: " << targetNode->isle->getName() << std::endl;
    // Use std::cout << "[BFS Drop] " << "No eligible Isle found for AMAZONITE drop." << std::endl;
}

void Map::displayMap()
{
    std::cout << "[World Map]" << std::endl;
    display(root, 0, 0);
}

int Map::getDepth(MapNode *node)
{
    // TODO: Return node depth if found, else
    return -1;
}

void Map::populateWithItems()
{
    // TODO: Distribute fist GOLDIUM than EINSTEINIUM than AMAZONITE
}

Isle *Map::findIsle(Isle isle)
{
    // TODO: Find isle by value
    return nullptr;
}

Isle *Map::findIsle(std::string name)
{
    // TODO: Find isle by name
    return nullptr;
}

MapNode *Map::findNode(Isle isle)
{
    // TODO: Find node by value
    return nullptr;
}

MapNode *Map::findNode(std::string name)
{
    // TODO: Find node by name
    return nullptr;
}

void Map::display(MapNode *current, int depth, int state)
{
    // SOURCE:

    if (current->left)
        display(current->left, depth + 1, 1);

    for (int i = 0; i < depth; i++)
        printf("     ");

    if (state == 1) // left
        printf("   ┌───");
    else if (state == 2) // right
        printf("   └───");

    std::cout << "[" << *current->isle << "] -  - (" << (current->isRed ? "\033[31mRed\033[0m" : "Black") << ")\n"
              << std::endl;

    if (current->right)
        display(current->right, depth + 1, 2);
}

void Map::writeToFile(const std::string &filename)
{
    // TODO: Write the tree to filename output level by level
}

void Map::writeIslesToFile(const std::string &filename)
{
    // TODO: Write Isles to output file in alphabetical order
    // Use std::cout << "[Output] " << "Isles have been written to " << filename << " in in alphabetical order." << std::endl;
}