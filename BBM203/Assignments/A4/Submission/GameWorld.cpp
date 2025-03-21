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

bool GameWorld::hasAccess(RealmShaper *realmShaper, Isle *isle)
{
    if(realmShaper == nullptr){
        std::cout << "[Access Control] " << "RealmShaper not found!" << std::endl;
        return false;
    }
    int playerDepth = shaperTree.getDepth(realmShaper);

    if(playerDepth == -1){
        std::cout << "[Access Control] " << "RealmShaper not found!" << std::endl;
        return false;
    }
    // Calculate the minimum depth in the World Tree the player can access
    int minMapDepthAccess = mapTree.calculateMinMapDepthAccess(playerDepth, shaperTree.getDepth(), mapTree.getDepth());

    // Check if the Isle's depth is accessible
    int mapDepth = mapTree.getDepth(mapTree.findNode(*isle));
    //std::cout << "[minMapDepthAccess: " <<minMapDepthAccess<<"] [playerDepth: "<< playerDepth << "] [shaperTreeDepth: " << shaperTree.getDepth() << "] [mapDepth: " << mapTree.getDepth() << "] [isleDepth: " <<mapDepth<<"]"<<std::endl;
    //shaperTree.displayTree();
    return mapDepth >= minMapDepthAccess;

}

void GameWorld::exploreArea(RealmShaper *realmShaper, Isle *isle)
{
    if(hasAccess(realmShaper, isle)){
        realmShaper->collectItem(isle->getItem());
        std::cout << "[Explore Area] " << realmShaper->getName() << " visited " << isle->getName() << std::endl;
        std::cout << "[Energy] " << realmShaper->getName() << "'s new energy level is " << realmShaper->getEnergyLevel() << std::endl;
        auto currentIsle = mapTree.findIsle(realmShaper->currentIsleName);
        if (currentIsle != nullptr){
            currentIsle->decreaseShaperCount();
        }
        realmShaper->currentIsleName = isle->getName();
        if (isle->increaseShaperCount()){
            std::cout << "[Owercrowding] " << isle->getName() << "self-destructed, it will be removed from the map" << std::endl;
            mapTree.remove(isle);
        }
    }
    else {
        std::cout << "[Explore Area] " << realmShaper->getName() << " does not have access to explore area " << *isle <<"."<< std::endl;
    }




    // You will need to implement a mechanism to keep track of how many realm shapers are at an Isle at the same time
    // There are more than one ways to do this, so it has been left completely to you
    // Use shaperCount, but that alone will not be enough,
    // you will likely need to add attributes that are not currently defined
    // to RealmShaper or Isle or other classes depending on your implementation
}

void GameWorld::craft(RealmShaper *shaper, const std::string &isleName){

    if(shaper->hasEnoughEnergy()){
        std::cout << "[Energy] " << shaper->getName() << " has enough energy points: " << shaper->getEnergyLevel() << std::endl;
        shaper->loseEnergy();

        mapTree.insert(new Isle(isleName));

        std::cout << "[Craft] " << shaper->getName() << " crafted new Isle " << isleName << std::endl;
    }
    else{
        std::cout << "[Energy] " << shaper->getName() << " does not have enough energy points: " << shaper->getEnergyLevel() << std::endl;
    }
}

void GameWorld::displayGameState()
{
    //mapTree.displayMap();
    //shaperTree.displayTree();
}

void GameWorld::processGameEvents(const std::string &accessLogs, const std::string &duelLogs)
{
    std::ifstream accessFile(accessLogs);
    std::ifstream duelFile(duelLogs);

    if (!accessFile.is_open() || !duelFile.is_open())
    {
        std::cerr << "Error opening log files." << std::endl;
        return;
    }

    std::queue<std::string> accessQueue;
    std::queue<std::string> duelQueue;

    // Read accessLogs, ignoring lines that start with '#'
    std::string line;
    while (std::getline(accessFile, line))
    {
        if (!line.empty() && line[0] != '#')
        {
            accessQueue.push(line);
        }
    }

    // Read duelLogs, ignoring lines that start with '#'
    while (std::getline(duelFile, line))
    {
        if (!line.empty() && line[0] != '#')
        {
            duelQueue.push(line);
        }
    }

    accessFile.close();
    duelFile.close();

    int accessCount = 0;

    // Process logs based on the 5-1 rule
    while (!accessQueue.empty() || !duelQueue.empty())
    {
        if (!accessQueue.empty())
        {
            // Process an access log
            std::string accessLog = accessQueue.front();
            accessQueue.pop();
            accessCount++;

            std::istringstream iss(accessLog);
            std::string playerName, isleName;
            iss >> playerName >> isleName;
            RealmShaper *realmShaper = shaperTree.findPlayer(playerName);

            if(realmShaper != nullptr) {
                if (mapTree.findNode(isleName) != nullptr) {
                    exploreArea(realmShaper, mapTree.findNode(isleName)->isle);
                } else {
                    craft(realmShaper, isleName);
                }
            }


            // After 5 access logs, process 1 duel log
            if (accessCount % 5 == 0 && !duelQueue.empty())
            {
                std::string duelLog = duelQueue.front();
                duelQueue.pop();

                std::string duelistName, result;
                std::istringstream issduel (duelLog);
                issduel >> duelistName >> result;

                RealmShaper* duelShaper = shaperTree.findPlayer(duelistName);
                if(duelShaper != nullptr) {
                    shaperTree.duel(duelShaper, result == "1");
                }
            }
        }
        else if (!duelQueue.empty())
        {
            // Process remaining duel logs
            std::string duelLog = duelQueue.front();
            duelQueue.pop();

            std::string duelistName, result;
            std::istringstream issduel (duelLog);
            issduel >> duelistName >> result;
            RealmShaper* duelShaper = shaperTree.findPlayer(duelistName);
            if(duelShaper != nullptr) {
                shaperTree.duel(duelShaper, result == "1");
            }
        }

        // Display the current game state
        displayGameState();
    }
}

void GameWorld::saveGameState(const std::string &currentIsles, const std::string &currentWorld, const std::string &currentShapers, const std::string &currentPlayerTree)
{
    mapTree.writeIslesToFile(currentIsles);
    mapTree.writeToFile(currentWorld);
    shaperTree.writeToFile(currentPlayerTree);
    shaperTree.writeShapersToFile(currentShapers);
}