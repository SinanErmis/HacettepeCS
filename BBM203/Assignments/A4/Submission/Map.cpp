#include "Map.h"

Map::Map()
{
    this->root = nullptr;
}

void clear(MapNode *node)
{
    if (!node)
        return;

    clear(node->left);
    clear(node->right);
    if(node->isle){
        delete node->isle;
        node->isle = nullptr;
    }
    delete node;
    node = nullptr;
}

Map::~Map()
{
    if(root){
        clear(root);
        root = nullptr;
    }
}

void Map::initializeMap(std::vector<Isle *> isles)
{
    for (auto *isle : isles)
    {
        insert(isle);
    }

    rotationNeeded = false;
    dropEnabled = true;
    rotationCount = 0;
    populateWithItems();
}

MapNode *Map::rotateRight(MapNode *current)
{
    rotationNeeded = true;

    MapNode *newRoot = current->left;
    MapNode *cache = newRoot->right;

    newRoot->right = current;
    current->left = cache;

    current->height = 1 + std::max(height(current->left), height(current->right));
    newRoot->height = 1 + std::max(height(newRoot->left), height(newRoot->right));

    return newRoot;
}

MapNode *Map::rotateLeft(MapNode *current)
{
    rotationNeeded = true;

    MapNode *newRoot = current->right;
    MapNode *cache = newRoot->left;

    newRoot->left = current;
    current->right = cache;

    current->height = 1 + std::max(height(current->left), height(current->right));
    newRoot->height = 1 + std::max(height(newRoot->left), height(newRoot->right));

    return newRoot;
}

int Map::calculateMinMapDepthAccess(int playerDepth, int totalShaperTreeHeight, int totalMapDepth)
{
    return (int)totalMapDepth * ((double)playerDepth / totalShaperTreeHeight);
}

int Map::height(MapNode *node)
{
    if(node){
        return node->height;
    }
    return -1; //0; Why we assign leaf node 0 height for god's sake? This cost me HOURS to fix. Apparently HUBBM is the only place that counts leaves height as 0
}

// a good reference https://www.geeksforgeeks.org/insertion-in-an-avl-tree/
MapNode *Map::insert(MapNode *node, Isle *isle)
{
    if (!node)
        return new MapNode(isle);

    if (*isle < *(node->isle))
        node->left = insert(node->left, isle);
    else if (*isle > *(node->isle))
        node->right = insert(node->right, isle);
    else // Isles canot be equal
        return node;

    node->height = 1 + std::max(height(node->left), height(node->right));

    int balance = height(node->left) - height(node->right);

    // Left left
    if (balance > 1 && *isle < *(node->left->isle)) {
        return rotateRight(node);
    }
    // Right right
    if (balance < -1 && *isle > *(node->right->isle)) {
        return rotateLeft(node);
    }
    // Left right
    if (balance > 1 && *isle > *(node->left->isle))
    {
        node->left = rotateLeft(node->left);
        return rotateRight(node);
    }

    // Right left
    if (balance < -1 && *isle < *(node->right->isle))
    {
        node->right = rotateRight(node->right);
        return rotateLeft(node);
    }

    return node;
}

void Map::insert(Isle *isle)
{
    root = insert((root), isle);

    if(rotationNeeded){
        rotationCount++;

        if(rotationCount % 3 == 0){
            populateWithItems();
        }

        rotationNeeded = false;
    }

    // you might need to insert some checks / functions here depending on your implementation
}

MapNode *Map::remove(MapNode *node, Isle *isle)
{
    if (!node) {
        return nullptr;
    }

    // Standard BST delete logic
    if (*isle < *(node->isle)) {
        node->left = remove(node->left, isle);
    } else if (*isle > *(node->isle)) {
        node->right = remove(node->right, isle);
    } else {
        // This is the node to be deleted

        // Case 1 & 2: No child or one child
        if (node->left == nullptr || node->right == nullptr) {
            MapNode *temp = (node->left) ? node->left : node->right;

            // If no children
            if (temp == nullptr) {
                // node is a leaf
                delete node->isle;
                delete node;
                return nullptr;
            } else {
                // One child: Replace node with that child
                // Instead of copying node data, just return the child after deleting node.
                delete node->isle;
                delete node;
                return temp;
            }
        }

        // Case 3: Two children
        // Get biggest in the left subtree (this cost me hours)
        MapNode *temp = node->left;
        while (temp->right != nullptr)
            temp = temp->right;

        delete node->isle;
        node->isle = new Isle(temp->isle->getName());
        for (int i = 0; i < temp->isle->getShaperCount(); ++i) {
            node->isle->increaseShaperCount();
        }
        // Delete the successor
        node->left = remove(node->left, temp->isle);
    }

    // If the tree had only one node and it was deleted
    if (!node)
        return node;

    // Update height
    node->height = 1 + std::max(height(node->left), height(node->right));

    // Get the balance factor
    int balance = height(node->left) - height(node->right);

    // Check for rotations
    if (balance > 1) {
        int leftBalance = 0;
        if (node->left) {
            leftBalance = height(node->left->left) - height(node->left->right);
        }

        // Left-Left Case
        if (leftBalance >= 0) {
            return rotateRight(node);
        }

        // Left-Right Case
        if (leftBalance < 0) {
            node->left = rotateLeft(node->left);
            return rotateRight(node);
        }
    }

    if (balance < -1) {
        int rightBalance = 0;
        if (node->right) {
            rightBalance = height(node->right->left) - height(node->right->right);
        }

        // Right-Right Case
        if (rightBalance <= 0) {
            return rotateLeft(node);
        }

        // Right-Left Case
        if (rightBalance > 0) {
            node->right = rotateRight(node->right);
            return rotateLeft(node);
        }
    }

    return node;
}

void Map::remove(Isle *isle)
{
    root = remove((root), isle);
    if(rotationNeeded){
        rotationCount++;

        if(rotationCount % 3 == 0){
            populateWithItems();
        }

        rotationNeeded = false;
    }
    // you might need to insert some checks / functions here depending on your implementation
}

void Map::preOrderItemDrop(MapNode *current, int &count)
{
    if(!current){
        return;
    }

    count++;

    if(count %  5 == 0){
        current->isle->setItem(EINSTEINIUM);
        std::cout << "[Item Drop] " << "EINSTEINIUM dropped on Isle: " << current->isle->getName() << std::endl;
    }

    preOrderItemDrop(current->left, count);
    preOrderItemDrop(current->right, count);
}

// to Display the values by Post Order Method .. left - right - node
void Map::postOrderItemDrop(MapNode *current, int &count)
{
    if(!current){
        return;
    }
    postOrderItemDrop(current->left, count);
    postOrderItemDrop(current->right, count);

    count++;

    if(count %  3 == 0){
        current->isle->setItem(GOLDIUM);
        std::cout << "[Item Drop] " << "GOLDIUM dropped on Isle: " << current->isle->getName() << std::endl;
    }
}

MapNode *Map::findFirstEmptyIsle(MapNode *node)
{
    // Standard level traversal stuff
    if (!node) return nullptr;

    std::queue<MapNode *> queue;
    queue.push(node);

    while (!queue.empty())
    {
        MapNode *current = queue.front();
        queue.pop();

        if (current->isle->getItem() == EMPTY)
        {
            return current;
        }

        if (current->left)
        {
            queue.push(current->left);
        }

        if (current->right)
        {
            queue.push(current->right);
        }
    }

    return nullptr;
}

void Map::dropItemBFS()
{
    auto emptyNode = findFirstEmptyIsle(root);

    if(emptyNode) {
        emptyNode->isle->setItem(AMAZONITE);
        std::cout << "[BFS Drop] " << "AMAZONITE dropped on Isle: " << emptyNode->isle->getName() << std::endl;
    }
    else {
        std::cout << "[BFS Drop] " << "No eligible Isle found for AMAZONITE drop." << std::endl;
    }
}

void Map::displayMap()
{
    std::cout << "[World Map]" << std::endl;
    display(root, 0, 0);
}

int Map::getDepth(MapNode *node)
{
    if (!node)
        return -1;

    MapNode *current = root;
    int depth = 0;

    while (current)
    {
        if (current == node)
            return depth;

        depth++;
        if (*(node->isle) < *(current->isle))
            current = current->left;
        else
            current = current->right;
    }

    return -1;
}


// Function to calculate the depth of a specific node in the AVL tree
int Map::getIsleDepth(Isle *isle)
{
    MapNode *node = findNode(*isle);
    return getDepth(node);
}

int calculateDepthRecursive(MapNode *current) {
    if (!current)
        return 0;

    int leftDepth = calculateDepthRecursive(current->left);
    int rightDepth = calculateDepthRecursive(current->right);

    return (current->left || current->right) ? 1 + std::max(leftDepth, rightDepth) : 0;
}

int Map::getDepth()
{
    return calculateDepthRecursive(root);
}

void Map::populateWithItems()
{
    if(!dropEnabled) return;
    int count = 0;
    postOrderItemDrop(root, count);
    count = 0;
    preOrderItemDrop(root, count);

    if(rotationNeeded){
        dropItemBFS();
    }
}

Isle *Map::findIsle(Isle isle)
{
    MapNode *node = findNode(isle);
    return node ? node->isle : nullptr;
}

Isle *Map::findIsle(std::string name)
{
    MapNode *node = findNode(name);
    return node ? node->isle : nullptr;
}

MapNode *Map::findNode(Isle isle)
{
    MapNode *current = root;

    while (current)
    {
        if (*(current->isle) == isle)
        {
            return current;
        }
        else if (isle < *(current->isle))
        {
            current = current->left;
        }
        else
        {
            current = current->right;
        }
    }

    return nullptr;
}

MapNode *Map::findNode(std::string name)
{
    MapNode *current = root;

    while (current)
    {
        if (current->isle->getName() == name)
        {
            return current;
        }
        else if (name < current->isle->getName())
        {
            current = current->left;
        }
        else
        {
            current = current->right;
        }
    }

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

    std::cout << "[" << *current->isle << "] - (" << current->height << ")\n"
              << std::endl;

    if (current->right)
        display(current->right, depth + 1, 2);
}

void Map::writeToFile(const std::string &filename)
{
    if (!root)
    {
        std::cerr << "[Error] Tree is empty!" << std::endl;
        return;
    }

    std::ofstream outFile(filename);
    if (!outFile.is_open())
    {
        std::cerr << "[Error] Could not open file: " << filename << std::endl;
        return;
    }

    std::queue<MapNode *> q;
    q.push(root);

    int count = std::pow(2.0, getDepth() + 1) - 1;
    int index = 0;
    while (index < count)
    {
        MapNode *current = q.front();
        q.pop();


        outFile << (current ? current->isle->getName() : "NULL") << " ";

        if(current != nullptr){
            q.push(current->left);
            q.push(current->right);

        }
        int depth = index == 0 ? 0 : static_cast<int>(std::ceil(std::log2(index + 1))) - 1;
        int lastInDepth = std::pow(2, depth + 1) - 2;
        if(index == lastInDepth){
            outFile << std::endl;
        }

        index++;
    }

    outFile.close();
}

// Helper function for in-order traversal
void inOrderTraversal(MapNode *current, std::ofstream &outFile)
{
    if (!current)
        return;

    inOrderTraversal(current->left, outFile);
    outFile << current->isle->getName() << std::endl;
    inOrderTraversal(current->right, outFile);
}

void Map::writeIslesToFile(const std::string &filename)
{
    if (!root)
    {
        //std::cerr << "[Error] Tree is empty." << std::endl;
        return;
    }

    std::ofstream outFile(filename);
    if (!outFile.is_open())
    {
        std::cerr << "[Error] Could not open file: " << filename << std::endl;
        return;
    }

    inOrderTraversal(root, outFile);

    //std::cout << "[Output] " << "Isles have been written to " << filename << " in in alphabetical order." << std::endl;
}