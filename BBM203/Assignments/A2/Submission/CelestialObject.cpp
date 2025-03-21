#include "CelestialObject.h"

// Function to rotate shape 90 degrees clockwise
vector<vector<bool>> rotateShape90Clockwise(const vector<vector<bool>>& shape) {
    int n = shape.size();    // Number of rows
    int m = shape[0].size(); // Number of columns
    vector<vector<bool>> rotated(m, vector<bool>(n));

    for (int i = 0; i < n; ++i) {
        for (int j = 0; j < m; ++j) {
            rotated[j][n - i - 1] = shape[i][j];
        }
    }

    return rotated;
}

// Function to check if two shapes are identical
bool areShapesEqual(const vector<vector<bool>>& shape1, const vector<vector<bool>>& shape2) {
    // First check sizes
    if (shape1.size() != shape2.size() || shape1[0].size() != shape2[0].size()) {
        return false;
    }

    // Then every single value
    for (size_t i = 0; i < shape1.size(); ++i) {
        for (size_t j = 0; j < shape1[0].size(); ++j) {
            if (shape1[i][j] != shape2[i][j]) {
                return false;
            }
        }
    }
    return true;
}

// Function to create rotations for a CelestialObject and link them in a circular linked list
void CelestialObject::setup_rotations(CelestialObject* original) {
    vector<vector<bool>> shape = original->shape;
    vector<vector<bool>> rotatedShape = rotateShape90Clockwise(shape);

    CelestialObject* current = original;

    // Generate and link unique rotations
    while (!areShapesEqual(rotatedShape, shape)) {
        CelestialObject* newRotation = new CelestialObject(original);
        newRotation->shape = rotatedShape;

        // Link to the previous rotation
        current->right_rotation = newRotation;
        newRotation->left_rotation = current;
        current = newRotation;

        // Generate the next rotation
        rotatedShape = rotateShape90Clockwise(rotatedShape);
    }

    // Complete the circular link
    current->right_rotation = original;
    original->left_rotation = current;
}

// Constructor to initialize CelestialObject with essential properties
CelestialObject::CelestialObject(const vector<vector<bool>> &shape,ObjectType type, int start_row,
                                 int time_of_appearance)
        : shape(shape), object_type(type), starting_row(start_row), time_of_appearance(time_of_appearance) {
    total_cell_count = 0;
    for (int i = 0; i < shape.size(); i++) {
        for (int j = 0; j < shape[0].size(); j++) {
            if (shape[i][j]) total_cell_count++;
        }
    }
}


// Copy constructor for CelestialObject
CelestialObject::CelestialObject(const CelestialObject *other)
        : shape(other->shape),  // Copy the 2D vector shape
          object_type(other->object_type),  // Copy the object type
          starting_row(other->starting_row),  // Copy the starting row
          time_of_appearance(other->time_of_appearance),  // Copy the time of appearance
          next_celestial_object(other->next_celestial_object), // Copy the next celestial object
          total_cell_count(other->total_cell_count)
{
}

// Function to delete rotations of a given celestial object. It should free the dynamically allocated
// memory for each rotation.
void CelestialObject::delete_rotations(CelestialObject *target) {
    if (!target) return;

    CelestialObject* current = target->right_rotation;
    while (current != target && current != nullptr) {
        CelestialObject* next = current->right_rotation;
        delete current;
        current = next;
    }

    target->right_rotation = target;
    target->left_rotation = target;
}

void CelestialObject::rotate_clockwise(CelestialObject *original) {
    original->shape = rotateShape90Clockwise(original->shape);
}

void CelestialObject::rotate_counter_clockwise(CelestialObject *original) {
    original->shape = rotateShape90Clockwise(rotateShape90Clockwise(rotateShape90Clockwise(original->shape)));
}

