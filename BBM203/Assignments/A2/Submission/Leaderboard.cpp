#include "Leaderboard.h"

// Read the stored leaderboard status from the given file such that the "head_leaderboard_entry" member
// variable will point to the highest all-times score, and all other scores will be reachable from it
// via the "next_leaderboard_entry" member variable pointer.
void Leaderboard::read_from_file(const string &filename) {
    ifstream file(filename);
    if (!file.is_open()) {
        head_leaderboard_entry = nullptr;
        return;
    }

    head_leaderboard_entry = nullptr;
    LeaderboardEntry *current = nullptr;

    unsigned long score;
    time_t lastPlayed;
    string playerName;

    while (file >> score >> lastPlayed >> ws && getline(file, playerName)) {
        LeaderboardEntry *new_entry = new LeaderboardEntry(score, lastPlayed, playerName);
        if (!head_leaderboard_entry) {
            head_leaderboard_entry = new_entry;
        } else {
            current->next = new_entry;
        }
        current = new_entry;
    }
}

// Write the latest leaderboard status to the given file in the format specified in the PA instructions
void Leaderboard::write_to_file(const string &filename) {
    ofstream file(filename);
    if (!file.is_open()) {
        cerr << "Failed to open file for writing: " << filename << endl;
        return;
    }

    LeaderboardEntry *current = head_leaderboard_entry;
    while (current) {
        file << current->score << " " << current->last_played << " " << current->player_name << endl;
        current = current->next;
    }
}

// Print the current leaderboard status to the standard output in the format specified in the PA instructions
void Leaderboard::print_leaderboard() {
    cout << "Leaderboard" << endl;
    cout << "-----------" << endl;

    LeaderboardEntry *current = head_leaderboard_entry;
    int rank = 1;

    while (current) {
        char timeBuffer[20];
        strftime(timeBuffer, sizeof(timeBuffer), "%H:%M:%S/%d.%m.%Y", localtime(&current->last_played));

        cout << rank << ". " << current->player_name << " " << current->score << " " << timeBuffer << endl;
        current = current->next;
        ++rank;
    }
}

// //  Insert a new LeaderboardEntry instance into the leaderboard, such that the order of the high-scores
// //  is maintained, and the leaderboard size does not exceed 10 entries at any given time (only the
// //  top 10 all-time high-scores should be kept in descending order by the score).
void Leaderboard::insert(LeaderboardEntry *new_entry) {
    if (!new_entry) return;

    if (!head_leaderboard_entry || new_entry->score > head_leaderboard_entry->score) {
        new_entry->next = head_leaderboard_entry;
        head_leaderboard_entry = new_entry;
    } else {
        LeaderboardEntry *current = head_leaderboard_entry;
        while (current->next &&
               current->next->score >= new_entry->score) {
            current = current->next;
        }
        new_entry->next = current->next;
        current->next = new_entry;
    }

    // Trim the leaderboard to only keep the top 10 entries
    LeaderboardEntry *current = head_leaderboard_entry;
    int count = 1;
    while (current && current->next) {
        if (++count > 10) {
            LeaderboardEntry *to_delete = current->next;
            current->next = nullptr;
            delete to_delete;
            break;
        }
        current = current->next;
    }
}

// Free dynamically allocated memory used for storing leaderboard entries
Leaderboard::~Leaderboard() {
    LeaderboardEntry *current = head_leaderboard_entry;
    while (current) {
        LeaderboardEntry *to_delete = current;
        current = current->next;
        delete to_delete;
    }
}
