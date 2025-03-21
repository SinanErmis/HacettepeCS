#ifndef GAMEWORLD_H
#define GAMEWORLD_H

#include "Map.h"
#include "RealmShapers.h"

class GameWorld
{
private:
    Map mapTree;
    ShaperTree shaperTree;

public:
    // Constructor decleration
    GameWorld();

    // Getters
    Map& getMapTree();
    ShaperTree& getShaperTree();

    // Initilizes game by initilizing the trees
    void initializeGame(std::vector<Isle *> isles, std::vector<RealmShaper *> realmShapers);

    // Checks access for a realmShaper for a isle
    bool hasAccess(RealmShaper *realmShaper, MapNode *isle);

    // Player explores existing area
    void exploreArea(RealmShaper *realmShaper, MapNode *isle);

    // Player crafts none-existing Isle
    void craft(RealmShaper *shaper, const std::string &isleName);

    // Displays game state in terminal
    void displayGameState();

    // 
    void processGameEvents(const std::string &accessLogs);

    // Saves (writes) current game state to output files
    void saveGameState(const std::string &currentIsles, const std::string &currentWorld);

    // TODO: Declare and implement functions to read and parse Access logs
};

#endif
