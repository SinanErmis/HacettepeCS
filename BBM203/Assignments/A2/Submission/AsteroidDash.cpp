#include "AsteroidDash.h"


// Constructor to initialize AsteroidDash with the given parameters
AsteroidDash::AsteroidDash(const string &space_grid_file_name,
                           const string &celestial_objects_file_name,
                           const string &leaderboard_file_name,
                           const string &player_file_name,
                           const string &player_name)

        : leaderboard_file_name(leaderboard_file_name), leaderboard(Leaderboard()) {

    read_player(player_file_name, player_name);  // Initialize player using the player.dat file
    read_space_grid(space_grid_file_name);  // Initialize the grid after the player is loaded
    read_celestial_objects(celestial_objects_file_name);  // Load celestial objects
    leaderboard.read_from_file(leaderboard_file_name);
}

#pragma region Input Reading

// Function to read the space grid from a file
void AsteroidDash::read_space_grid(const string &input_file) {
    ifstream file(input_file);
    if(!file.is_open()) {
        throw std::runtime_error("Failed to open file: " + input_file);
    }

    string line;
    while (std::getline(file, line)) {
        std::vector<int> row;

        for (size_t i = 0; i < line.length(); i += 2) {
            row.push_back(line[i] - '0');  // Convert char to integer
        }

        space_grid.push_back(row);
    }

    file.close();
}

// Function to read the player from a file
void AsteroidDash::read_player(const string &player_file_name, const string &player_name) {
    ifstream file(player_file_name);
    if(!file.is_open()) {
        throw std::runtime_error("Failed to open file: " + player_file_name);
    }

    string line;

    //Read player's position in first line
    std::getline(file, line);
    int row, col;
    std::sscanf(line.c_str(), "%d %d", &row, &col); // Using sscanf to parse the integers

    //Read player's shape into matrix
    vector<vector<bool>> shape;
    while (std::getline(file, line)) {
        vector<bool> shape_line;
        for (int i = 0; i<line.length(); ++i) {
            if(line[i] == '0') {
                shape_line.push_back(false);
            }
            else if(line[i] == '1') {
                shape_line.push_back(true);
            }
        }

        shape.push_back(shape_line);
    }

    player = new Player(shape, row, col, player_name);

    file.close();
}

// Function to read celestial objects from a file
void AsteroidDash::read_celestial_objects(const string &input_file) {
    ifstream file(input_file);
    if (!file.is_open()) {
        throw std::runtime_error("Failed to open file: " + input_file);
    }

    string line;
    int start = -1;
    int time = -1;
    ObjectType type = ObjectType::ASTEROID;
    vector<vector<bool>> shape;
    bool objectInProgress = false;

    while (std::getline(file, line)) {
        // Skip empty lines unless we have an object in progress
        if (line.empty()) {
            if (objectInProgress) {
                // Create a new CelestialObject when we finish an object definition
                CelestialObject* celestial_object = new CelestialObject(shape, type, start, time);
                if (celestial_objects_list_head == nullptr) {
                    celestial_objects_list_head = celestial_object;
                } else {
                    CelestialObject* temp = celestial_objects_list_head;
                    while (temp->next_celestial_object != nullptr) {
                        temp = temp->next_celestial_object;
                    }
                    temp->next_celestial_object = celestial_object;
                }

                // Reset for the next object
                start = -1;
                time = -1;
                type = ObjectType::ASTEROID;
                shape.clear();
                objectInProgress = false;
            }
            continue;
        }

        // Indicate that we're currently defining an object
        objectInProgress = true;

        // Parse shape data if it begins with '{' or '['
        if (line[0] == '{' || line[0] == '[') {
            shape.clear();  // Reset shape before reading a new one
            do {
                bool should_break = false;
                vector<bool> shape_line;
                for (char ch : line) {
                    if (ch == '1') shape_line.push_back(true);
                    else if (ch == '0') shape_line.push_back(false);
                    else if (ch == ']' || ch == '}') {
                        should_break = true;
                        break;
                    }
                }
                shape.push_back(shape_line);
                if(should_break) break;
            } while (std::getline(file, line));
            continue;
        }

        // Parse other fields
        if (line[0] == 's') start = std::stoi(line.substr(2));
        else if (line[0] == 't') time = std::stoi(line.substr(2));
        else if (line[0] == 'e') {
            string type_str = line.substr(2);
            if (type_str == "ammo") type = AMMO;
            else if (type_str == "life") type = LIFE_UP;
        }
    }

    // Check if there’s any remaining object data to be added at the end of the file
    if (objectInProgress) {
        CelestialObject* celestial_object = new CelestialObject(shape, type, start, time);

        if (celestial_objects_list_head == nullptr) {
            celestial_objects_list_head = celestial_object;
        } else {
            CelestialObject* temp = celestial_objects_list_head;
            while (temp->next_celestial_object != nullptr) {
                temp = temp->next_celestial_object;
            }
            temp->next_celestial_object = celestial_object;
        }
    }

    // Output celestial objects with ASCII representation for quick testing
    // for (CelestialObject* temp = celestial_objects_list_head; temp != nullptr; temp = temp->next_celestial_object) {
    //     cout << "Type: " << temp->object_type << ", Start: " << temp->starting_row << endl;
    //     cout << "Shape:" << endl;
    //     for (const auto& row : temp->shape) {
    //         for (bool cell : row) cout << (cell ? '#' : ' ');
    //         cout << endl;
    //     }
    //     cout << "-----------------" << endl;
    // }

    //Setup rotations for them. It's at the end because copy constructor also copies next celestial object of original
    CelestialObject * current = celestial_objects_list_head;
    while (current != nullptr) {
        CelestialObject::setup_rotations(current);
        current = current->next_celestial_object;
    }

    file.close();
}

#pragma endregion

// Print the entire space grid
void AsteroidDash::print_space_grid() const {
    cout << "Tick: " << game_time <<endl;
    cout << "Lives: " << player->lives <<endl;
    cout << "Ammo: " << player->current_ammo <<endl;
    cout << "Score: " << current_score <<endl;
    int high_score = 0;
    if(leaderboard.head_leaderboard_entry) {
        high_score = leaderboard.head_leaderboard_entry->score;
    }
    cout << "High Score: " << high_score << endl;

    for (const auto & row : space_grid) {
        for (const auto cell : row) {
            if(cell == 0) {
                cout << unoccupiedCellChar;
            }
            else {
                cout << occupiedCellChar;
            }
        }
        cout << endl;
    }
    cout << endl;
}


#define PROJECTILE_HIT_ASTEROID_POINT 10

// Function to update the space grid with player, celestial objects, and any other changes
// It is called in every game tick before moving on to the next tick.

//I'm using a convention which makes it easier
#define PLAYER_ID 1
#define PROJECTILE_ID 2
#define ASTEROID_ID 3
#define LIFE_UP_ID 4
#define AMMO_ID 5
void AsteroidDash::update_space_grid() {

    bool player_collided_asteroid = false;
    // Reset the grid
    int grid_height = space_grid.size();
    int grid_width = space_grid[0].size();

    for (int i = 0; i< grid_height; i++) {
        for (int j = 0; j < grid_width; j++) {
            space_grid[i][j] = 0;
        }
    }

    // Put player
    for (int row = 0; row<player->spacecraft_shape.size(); row++) {
        for (int column = 0; column<player->spacecraft_shape[row].size(); column++) {
            if(player->spacecraft_shape[row][column]) {
                space_grid[player->position_row + row][player->position_col+column] = PLAYER_ID;
            }
        }
    }

    // Put projectiles
    for (int i = 0; i < projectile_positions.size(); i++) {
        if(space_grid[projectile_positions[i][0]][projectile_positions[i][1]] != PLAYER_ID) {
            space_grid[projectile_positions[i][0]][projectile_positions[i][1]] = PROJECTILE_ID;
        }
    }

    // Move and execute first pass of collision detection
    CelestialObject* current = celestial_objects_list_head;
    while (current != nullptr) {
        if(!current->is_active) {
            current = current->next_celestial_object;
            continue;
        }
        int moved_frame = static_cast<int>(game_time) - current->time_of_appearance + 1;
        if(moved_frame > 0 && moved_frame < grid_width + current->shape[0].size()) {
            int start_column = std::max(moved_frame - grid_width, 0);
            int max_column = std::min(static_cast<int>(current->shape[0].size()), moved_frame);

            for (int row = 0; row < current->shape.size(); row++) {
                bool collided = false;
                for (int column = start_column; column < max_column; column++) {
                    if(current->shape[row][column]) {
                        int grid_row = current->starting_row + row;
                        int grid_column = grid_width - moved_frame + column;
                        int grid_value = space_grid[grid_row][grid_column];

                        if(grid_value == PLAYER_ID) {
                            current->is_active = false;

                            if(current->object_type == ASTEROID) {
                                player->lives--;
                                player_collided_asteroid = true;
                                if(player->lives == 0) {
                                    game_over = true;
                                    //todo game over
                                }
                            }
                            else if(current->object_type == LIFE_UP) {
                                player->lives++;
                            }
                            else if(current->object_type == AMMO) {
                                    player->current_ammo = player->max_ammo;
                            }

                            collided = true;
                            break;
                        }
                        else if(grid_value == PROJECTILE_ID && current->object_type == ASTEROID) {
                            current_score += PROJECTILE_HIT_ASTEROID_POINT;
                            current->shape[row][column] = false;
                            space_grid[grid_row][grid_column] = 0;
                            for (int i = 0; i < projectile_positions.size(); i++) {
                                if(projectile_positions[i][0] == grid_row && projectile_positions[i][1] == grid_column) {
                                    projectile_positions.erase(projectile_positions.begin() + i);
                                    break;
                                }
                            }



                            bool all_cleared = true;
                            for (int i = 0; i<current->shape.size(); i++) {
                                for (int j = 0; j<current->shape[i].size(); j++) {
                                    if (current->shape[i][j]) {
                                        all_cleared = false;
                                        break;
                                    }
                                }
                                if(!all_cleared) break;
                            }

                            if(all_cleared) {
                                current->is_active = false;
                                current_score+=current->total_cell_count * 100;
                            }else {
                                CelestialObject::delete_rotations(current);

                                if(row % 2 == 1 && (current->shape.size() - 1) / 2 == row) {
                                    //no rotation
                                }
                                else if(row < current->shape.size() / 2) {
                                    CelestialObject::rotate_clockwise(current);
                                }
                                else {
                                    CelestialObject::rotate_counter_clockwise(current);
                                }

                                CelestialObject::setup_rotations(current);
                            }

                            collided = true;
                            break;
                            //todo check all shape destroyed
                        }

                        // space_grid[grid_row][grid_column] = 1;
                    }
                }
                if(collided) break;
            }
        }

        current = current->next_celestial_object;
    }

    // Move and draw projectiles
    for (int i = 0; i < projectile_positions.size(); i++) {
        // Delete old placement if it doesn't interfere with anything
        if(space_grid[projectile_positions[i][0]][projectile_positions[i][1]] == PROJECTILE_ID) {
            space_grid[projectile_positions[i][0]][projectile_positions[i][1]] = 0;
        }

        // Move projectile 1 step to right
        projectile_positions[i][1]++;

        // If projectile hit the end of the grid
        if(projectile_positions[i][1] >= grid_width) {
            // Remove projectile from the vector
            projectile_positions.erase(projectile_positions.begin() + i);
            i--; // Decrease index by 1 because vector got resized
        }
        // If new position has an asteroid
        // else if(space_grid[projectile_positions[i][0]][projectile_positions[i][1]] == ASTEROID_ID) {
        //     // Same as above, destroy the cell and rotate that asteroid
        //     // todo
        //
        //     // Remove projectile from the vector
        //     projectile_positions.erase(projectile_positions.begin() + i);
        //     i--; // Decrease index by 1 because vector got resized
        // }
        // If new position has nothing else (life up or ammo)
        else if (space_grid[projectile_positions[i][0]][projectile_positions[i][1]] == 0) {
            // Draw
            space_grid[projectile_positions[i][0]][projectile_positions[i][1]] = PROJECTILE_ID;
        }
    }

    // Run collision detection between asteroids and projectiles once again
    current = celestial_objects_list_head;
    while (current != nullptr) {
        if(!current->is_active) {
            current = current->next_celestial_object;
            continue;
        }
        int moved_frame = static_cast<int>(game_time) - current->time_of_appearance + 1;
        if(moved_frame > 0 && moved_frame < grid_width + current->shape[0].size()) {

            for (int row = 0; row < current->shape.size(); row++) {
                bool collided = false;
                int start_column = std::max(moved_frame - grid_width, 0);
                int max_column = std::min(static_cast<int>(current->shape[row].size()), moved_frame);
                for (int column = start_column; column < max_column; column++) {
                    if(current->shape[row][column]) {
                        int grid_row = current->starting_row + row;
                        int grid_column = grid_width - moved_frame + column;
                        int grid_value = space_grid[grid_row][grid_column];

                        if(grid_value == PROJECTILE_ID && current->object_type == ASTEROID) {
                            current_score += PROJECTILE_HIT_ASTEROID_POINT;
                            current->shape[row][column] = false;
                            space_grid[grid_row][grid_column] = 0;
                            for (int i = 0; i < projectile_positions.size(); i++) {
                                if(projectile_positions[i][0] == grid_row && projectile_positions[i][1] == grid_column) {
                                    projectile_positions.erase(projectile_positions.begin() + i);
                                    break;
                                }
                            }

                            bool all_cleared = true;
                            for (int i = 0; i<current->shape.size(); i++) {
                                for (int j = 0; j<current->shape[i].size(); j++) {
                                    if (current->shape[i][j]) {
                                        all_cleared = false;
                                        break;
                                    }
                                }
                                if(!all_cleared) break;
                            }

                            if(all_cleared) {
                                current->is_active = false;
                                current_score+=current->total_cell_count * 100;
                            }else {
                                CelestialObject::delete_rotations(current);

                                if(row % 2 == 1 && (current->shape.size() - 1) / 2 == row) {
                                    //no rotation
                                }
                                else if(row < current->shape.size() / 2) {
                                    CelestialObject::rotate_clockwise(current);
                                }
                                else {
                                    CelestialObject::rotate_counter_clockwise(current);
                                }

                                CelestialObject::setup_rotations(current);
                            }

                            collided = true;
                            break;
                        }

                    }
                }
                if(collided) break;
            }
            if(moved_frame < grid_width + current->shape[0].size()) {
                for (int row = 0; row < current->shape.size(); row++) {
                    int start_column = std::max(moved_frame - grid_width, 0);
                    int max_column = std::min(static_cast<int>(current->shape[row].size()), moved_frame);

                    for (int column = start_column; column < max_column; column++) {
                        if(current->shape[row][column]) {
                            int grid_row = current->starting_row + row;
                            int grid_column = grid_width - moved_frame + column;
                            space_grid[grid_row][grid_column] = 1;
                        }
                    }
                }
            }
        }

        current = current->next_celestial_object;
    }

    // Convert grid from my convention to expected output format
    for (int i = 0; i< grid_height; i++) {
        for (int j = 0; j < grid_width; j++) {
            if(space_grid[i][j] > 0) {
                space_grid[i][j] = 1;
            }
        }
    }

    if(!player_collided_asteroid) current_score++;

}

// Corresponds to the SHOOT command.
// It should shoot if the player has enough ammo.
// It should decrease the player's ammo
void AsteroidDash::shoot() {
    if (player->current_ammo > 0) {
        player->current_ammo--;

        // Calculate the row and column for the projectile's position
        int projectile_row = player->position_row + (player->spacecraft_shape.size() / 2);
        // I think starting projectile inside spaceship just for getting 1 from test is kinda bad but that's how it works apparently :)
        int projectile_column = player->position_col + player->spacecraft_shape[0].size() - 1;


        // Add the projectile's position to the list of projectile positions
        vector<int> projectilePosition = { projectile_row, projectile_column };
        projectile_positions.push_back(projectilePosition);
    }
}

// Destructor. Remove dynamically allocated member variables here.
AsteroidDash::~AsteroidDash() {
    delete player;
    player = nullptr;

    // Iterate through the celestial objects linked list and delete each node
    CelestialObject* current = celestial_objects_list_head;
    while (current != nullptr) {
        CelestialObject* next = current->next_celestial_object; // Save the pointer to the next node

        CelestialObject::delete_rotations(current);

        delete current; // Delete the current node
        current = next; // Move to the next node
    }
    celestial_objects_list_head = nullptr;
}