#include "RealmShapers.h"
#include <cmath>
#include <algorithm>
#include <queue>

ShaperTree::ShaperTree()
{
}

ShaperTree::~ShaperTree()
{
    // Free all dynamically allocated memory for realmShapers
    for (auto *shaper : realmShapers) {

            delete shaper;

    }
    realmShapers.clear();
}

void ShaperTree::initializeTree(std::vector<RealmShaper *> shapers)
{
    for (auto &shaper : shapers) {
        insert(shaper);
    }
}

int ShaperTree::getSize()
{
    return realmShapers.size();
}

std::vector<RealmShaper *> ShaperTree::getTree()
{
    return realmShapers;
}

bool ShaperTree::isValidIndex(int index)
{
    if(index < 0 || index >= realmShapers.size()) return false;
    return true;
}

void ShaperTree::insert(RealmShaper *shaper)
{
    realmShapers.push_back(shaper);
}

int ShaperTree::remove(RealmShaper *shaper)
{
    // Find index
    int index = findIndex(shaper);
    // If index is valid
    if (isValidIndex(index)) {
        delete realmShapers[index];
        realmShapers.erase(realmShapers.begin() + index);
    }
    return index;
}

int ShaperTree::findIndex(RealmShaper *shaper)
{
    for (int i = 0; i < realmShapers.size(); ++i) {
        if (*realmShapers[i] == *shaper) {
            return i;
        }
    }
    return -1;
}

int ShaperTree::getDepth(RealmShaper *shaper)
{
    int index = findIndex(shaper);
    if(!isValidIndex(index))
    {
        return -1;
    }

    if(index == 0)
    {
        return 0;
    }
    return static_cast<int>(std::ceil(std::log2(index + 2))) - 1; // According to schematic posted in piazza
}

int ShaperTree::getDepth()
{
    int size = realmShapers.size();
    if(size == 0){
        return 0;
    }
    return static_cast<int>(std::ceil(std::log2(size + 1))) - 1; // According to schematic posted in piazza
}

RealmShaper ShaperTree::duel(RealmShaper *challenger, bool result)
{
    int index = findIndex(challenger);
    if (index == -1) return *challenger; // Challenger not found

    RealmShaper *parent = getParent(challenger);
    if (!parent) return *challenger; // No parent to duel

    RealmShaper *victor = result ? challenger : parent;
    RealmShaper *loser = result ? parent : challenger;

    victor->gainHonour();
    loser->loseHonour();

    // To exactly match example output file...
    if(result) {
        replace(challenger, parent);
        std::cout << "[Duel] " << victor->getName() << " won the duel" << std::endl;
        std::cout << "[Honour] " << "New honour points: " << challenger->getName() << "-" << challenger->getHonour() << " "
                  << parent->getName() << "-" << parent->getHonour() << std::endl;
    }
    else{
        std::cout << "[Duel] " << loser->getName() << " lost the duel" << std::endl;
        std::cout << "[Honour] " << "New honour points: " << challenger->getName() << " - " << challenger->getHonour() << " "
                  << parent->getName() << " - " << parent->getHonour() << std::endl;
    }




    if (loser->getHonour() <= 0) {
        std::cout << "[Duel] " << loser->getName() << " lost all honour, delete" << std::endl;
        remove(loser);
    }

    return *victor;
}

RealmShaper *ShaperTree::getParent(RealmShaper *shaper)
{
    int index = findIndex(shaper);
    if (index <= 0) return nullptr; // Root has no parent

    int parentIndex = (index - 1) / 2;
    if (parentIndex >= 0 && parentIndex < realmShapers.size()) {
        return realmShapers[parentIndex];
    }
    return nullptr;
}

void ShaperTree::replace(RealmShaper *player_low, RealmShaper *player_high)
{
    int lowIndex = findIndex(player_low);
    int highIndex = findIndex(player_high);

    if (lowIndex == -1 || highIndex == -1) return; // Players not found

    std::swap(realmShapers[lowIndex], realmShapers[highIndex]);
}

RealmShaper *ShaperTree::findPlayer(RealmShaper shaper)
{
    for (auto *player : realmShapers) {
        if (*player == shaper) {
            return player;
        }
    }
    return nullptr;
}

// Find shaper by name
RealmShaper *ShaperTree::findPlayer(std::string name)
{
    for (auto *player : realmShapers) {
        if (player->getName() == name) {
            return player;
        }
    }
    return nullptr;
}

void inOrderHelper(const std::vector<RealmShaper*>& realmShapers, int index, std::vector<std::string> &result) {
    if (index >= realmShapers.size()) return;

    int leftIndex = 2 * index + 1;
    int rightIndex = 2 * index + 2;

    // Traverse left
    inOrderHelper(realmShapers, leftIndex, result);

    // Visit node
    result.push_back(realmShapers[index]->getName());

    // Traverse right
    inOrderHelper(realmShapers, rightIndex, result);
}

std::vector<std::string> ShaperTree::inOrderTraversal(int index)
{
    std::vector<std::string> result = {};
    inOrderHelper(realmShapers, index, result);
    return result;
}

void preOrderHelper(const std::vector<RealmShaper*>& realmShapers, int index, std::vector<std::string> &result) {
    if (index >= realmShapers.size()) return;

    // Visit node
    result.push_back(realmShapers[index]->getName());

    int leftIndex = 2 * index + 1;
    int rightIndex = 2 * index + 2;

    // Traverse left
    preOrderHelper(realmShapers, leftIndex, result);

    // Traverse right
    preOrderHelper(realmShapers, rightIndex, result);
}

std::vector<std::string> ShaperTree::preOrderTraversal(int index)
{
    std::vector<std::string> result = {};
    preOrderHelper(realmShapers, index, result);
    return result;
}

void postOrderHelper(const std::vector<RealmShaper*>& realmShapers, int index, std::vector<std::string> &result) {
    if (index >= realmShapers.size()) return;

    int leftIndex = 2 * index + 1;
    int rightIndex = 2 * index + 2;

    // Traverse left
    postOrderHelper(realmShapers, leftIndex, result);

    // Traverse right
    postOrderHelper(realmShapers, rightIndex, result);

    // Visit node
    result.push_back(realmShapers[index]->getName());
}

std::vector<std::string> ShaperTree::postOrderTraversal(int index)
{
    std::vector<std::string> result = {};
    postOrderHelper(realmShapers, index, result);
    return result;
}

void ShaperTree::preOrderTraversal(int index, std::ofstream &outFile)
{
    if (index >= realmShapers.size()) return;

    // Write current node
    outFile << realmShapers[index]->getName() << std::endl;

    int leftIndex = 2 * index + 1;
    int rightIndex = 2 * index + 2;

    // Traverse left
    preOrderTraversal(leftIndex, outFile);

    // Traverse right
    preOrderTraversal(rightIndex, outFile);
}

void ShaperTree::breadthFirstTraversal(std::ofstream &outFile)
{
    if (realmShapers.empty()) return;

    std::queue<int> q;
    q.push(0);

    while (!q.empty()) {
        int currentIndex = q.front();
        q.pop();

        // Write current node
        outFile << realmShapers[currentIndex]->getName() << std::endl;

        int leftIndex = 2 * currentIndex + 1;
        int rightIndex = 2 * currentIndex + 2;

        if (leftIndex < realmShapers.size()) q.push(leftIndex);
        if (rightIndex < realmShapers.size()) q.push(rightIndex);
    }
}

void ShaperTree::displayTree()
{
    std::cout << "[Shaper Tree]" << std::endl;
    printTree(0, 0, "");
}

// Helper function to print tree with indentation
void ShaperTree::printTree(int index, int level, const std::string &prefix)
{
    if (!isValidIndex(index))
        return;

    std::cout << prefix << (level > 0 ? "   └---- " : "") << *realmShapers[index] << std::endl;
    int left = 2 * index + 1;
    int right = 2 * index + 2;

    if (isValidIndex(left) || isValidIndex(right))
    {
        printTree(left, level + 1, prefix + (level > 0 ? "   │   " : "")); // ╎
        printTree(right, level + 1, prefix + (level > 0 ? "   │   " : ""));
    }
}

void ShaperTree::writeShapersToFile(const std::string &filename)
{
    std::ofstream outFile(filename);
    if (!outFile.is_open()) {
        std::cerr << "[Error] Could not open file: " << filename << std::endl;
        return;
    }

    breadthFirstTraversal(outFile);
    //std::cout << "[Output] " << "Shapers have been written to " << filename << " according to rankings." << std::endl;
}

void ShaperTree::writeToFile(const std::string &filename)
{
    std::ofstream outFile(filename);
    if (!outFile.is_open()) {
        std::cerr << "[Error] Could not open file: " << filename << std::endl;
        return;
    }

    // Write shapers in pre-order
    preOrderTraversal(0, outFile);

    outFile.close();
    //std::cout << "[Output] " << "Tree have been written to " << filename << " in pre-order." << std::endl;
}
