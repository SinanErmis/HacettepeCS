#include "GameWorld.h"

GameWorld::GameWorld() : mapTree(), shaperTree() {}

void GameWorld::initializeGame(std::vector<Isle *> places, std::vector<RealmShaper *> players)
{
    shaperTree.initializeTree(players);
    mapTree.initializeMap(places);
}

Map& GameWorld::getMapTree()
{
    return mapTree;
}
ShaperTree& GameWorld::getShaperTree()
{
    return shaperTree;
}

bool GameWorld::hasAccess(RealmShaper *realmShaper, MapNode *isle)
{
    bool hasAccess = false;

    // TODO: Check if the realmShaper has access to collect item from the isle
    // Get necessary depth values
    // Upper half means: x <=TotalDepth/2
    // Use // std::cout << "[Access Control] " << "RealmShaper not found!" << std::endl;

    return hasAccess;
}

void GameWorld::exploreArea(RealmShaper *realmShaper, MapNode *isle)
{
    // TODO:
    // Check if realmShaper has access
    // Use // std::cout << "[Explore Area] " << realmShaper->getName() << " does not have access to collect item from area " << node->isle->getName() << "." << std::endl;
    // If realmShaper has access
    // Visit isle, 
    // collect item

    // Use // std::cout << "[Explore Area] " << realmShaper->getName() << " visited " << node->isle->getName() << std::endl;
    // Use // std::cout << "[Energy] " << realmShaper->getName() << "'s energy level is " << realmShaper->getEnergyLevel() << std::endl;
    
}

void GameWorld::craft(RealmShaper *shaper, const std::string &isleName){
    // TODO: Check energy and craft new isle if possible
    // Use std::cout << "[Energy] " << shaperName << " has enough energy points: " << shaperEnergyLevel << std::endl;
    // Use std::cout << "[Craft] " << shaperName << " crafted new Isle " << isleName << std::endl;
    // Use std::cout << "[Energy] " << shaperName << " does not have enough energy points: " << shaperEnergyLevel << std::endl;
}

void GameWorld::displayGameState()
{
    // TODO: Comment these out before uploading to TurBo and Submit
    mapTree.displayMap();
    shaperTree.displayTree();
}

// TODO: Implement functions to read and parse Access logs

void GameWorld::processGameEvents(const std::string &accessLogs)
{
    // TODO:
    // Read Access logs
    // Process them

    // This function should call exploreArea and craft functions

}

void GameWorld::saveGameState(const std::string &currentIsles, const std::string &currentWorld)
{
    mapTree.writeIslesToFile(currentIsles);
    mapTree.writeToFile(currentWorld);
}