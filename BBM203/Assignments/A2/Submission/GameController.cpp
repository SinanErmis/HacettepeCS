#include "GameController.h"

// Simply instantiates the game
GameController::GameController(
        const string &space_grid_file_name,
        const string &celestial_objects_file_name,
        const string &leaderboard_file_name,
        const string &player_file_name,
        const string &player_name

) {
    game = new AsteroidDash(space_grid_file_name, celestial_objects_file_name, leaderboard_file_name, player_file_name,
                            player_name);
    // TODO: Your code here, if you want to perform extra initializations
}

// Reads commands from the given input file, executes each command in a game tick
void GameController::play(const string &commands_file) {
    ifstream file(commands_file);
    if (!file.is_open()) {
        throw std::runtime_error("Failed to open file: " + commands_file);
    }

    vector<vector<int>> bullets;
    string line;
    // game->update_space_grid();
    // game->game_time++
    // game->current_score = 0;

    while (std::getline(file,line) && !game->game_over) {
        game->update_space_grid();

        if(line== "PRINT_GRID") {
            game->print_space_grid();
        }
        else if(line == "MOVE_UP") {
            game->player->move_up();
        }
        else if(line == "MOVE_DOWN") {
            game->player->move_down(game->space_grid.size());
        }
        else if(line=="MOVE_RIGHT") {
            game->player->move_right(game->space_grid[0].size());
        }
        else if(line=="MOVE_LEFT") {
            game->player->move_left();
        }
        else if(line=="SHOOT") {
            game->shoot();
        }
        else if(line=="NOP") {
        }
        else {
            cout<<"Unknown command: " <<line<<endl;
        }

        game->game_time++;
    }

    game->leaderboard.insert(new LeaderboardEntry(game->current_score, time(nullptr), game->player->player_name));
    if(game->game_over) {
        cout << "GAME OVER!"<<endl;
    }
    else {
        cout << "GAME FINISHED! No more commands!"<<endl;
    }
    game->print_space_grid();
    game->leaderboard.print_leaderboard();

    file.close();
}

// Destructor to delete dynamically allocated member variables here
GameController::~GameController() {
    delete game;
}
