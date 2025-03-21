#include "SecretImage.h"


// Constructor: split image into upper and lower triangular arrays
SecretImage::SecretImage(const GrayscaleImage& image) {

    width = image.get_width();
    height = image.get_height();
    upper_triangular = new int[(width * (width + 1))/2];
    lower_triangular = new int[(width * (width - 1))/2];

    int lower_index = 0, upper_index = 0;

    for (int i = 0; i < height; i++) {
        for (int j = 0; j < width; j++) {
            //Check if the pixel is in upper triangle by comparing it's x and y coordinates
            if(j >= i) {
                upper_triangular[upper_index] = image.get_pixel(i,j);
                upper_index++;
            }
            else {
                lower_triangular[lower_index] = image.get_pixel(i,j);
                lower_index++;
            }
        }
    }
}

// Constructor: instantiate based on data read from file
SecretImage::SecretImage(int w, int h, int * upper, int * lower) {
    height = h;
    width = w;
    upper_triangular = upper;
    lower_triangular = lower;
}


// Destructor: free the arrays
SecretImage::~SecretImage() {
    //Array delete
    delete[] upper_triangular;
    delete[] lower_triangular;
}

// Reconstructs and returns the full image from upper and lower triangular matrices.
GrayscaleImage SecretImage::reconstruct() const {
    GrayscaleImage image(width, height);
    int lower_index = 0, upper_index = 0;
    for (int i = 0; i < height; i++) {
        for (int j = 0; j < width; j++) {
            //Check if the pixel is in upper triangle by comparing it's x and y coordinates
            if(j>=i) {
                image.set_pixel(i,j, upper_triangular[upper_index]);
                upper_index++;
            }
            else {
                image.set_pixel(i,j, lower_triangular[lower_index]);
                lower_index++;
            }
        }
    }

    return image;
}

// Save the filtered image back to the triangular arrays
void SecretImage::save_back(const GrayscaleImage& image) {
    int lower_index = 0, upper_index = 0;

    for (int i = 0; i < height; i++) {
        for (int j = 0; j < width; j++) {
            //Check if the pixel is in upper triangle by comparing it's x and y coordinates
            if(j >= i) {
                upper_triangular[upper_index] = image.get_pixel(i,j);
                upper_index++;
            }
            else {
                lower_triangular[lower_index] = image.get_pixel(i,j);
                lower_index++;
            }
        }
    }
}

// Save the upper and lower triangular arrays to a file
void SecretImage::save_to_file(const std::string& filename) {

    //Open file and throw exception if it's not open
    std::ofstream file(filename);
    if (!file.is_open()) {
        throw std::runtime_error("Could not open file for writing");
    }

    // 1. Write width and height on the first line
    file << width << " " << height << "\n";

    // 2. Write the upper_triangular array on the second line
    int upper_size = (width * (width + 1))/2;
    int lower_size = (width * (width - 1))/2;
    for (int i = 0; i < upper_size; ++i) {
        file << upper_triangular[i];
        if (i < upper_size - 1) file << " ";
    }
    file << "\n";

    // 3. Write the lower_triangular array on the third line
    for (int i = 0; i < lower_size; ++i) {
        file << lower_triangular[i];
        if (i < lower_size - 1) file << " ";
    }
    file << "\n";

    file.close();
}

// Static function to load a SecretImage from a file
SecretImage SecretImage::load_from_file(const std::string& filename) {

    //Open file as an ifstream and throw exception if it's not open
    std::ifstream file(filename);
    if (!file.is_open()) {
        throw std::runtime_error("Could not open file for reading");
    }

    int width, height;

    // 1. Read width and height from the first line (I absolutely hate this double arrow syntax, it makes no sense)
    file >> width >> height;

    // 2. Calculate the sizes of the upper and lower triangular arrays.
    int upper_size = (width * (width + 1)) / 2;
    int lower_size = (width * (width - 1)) / 2;

    // 3. Allocate memory for both arrays.
    int* upper_triangular = new int[upper_size];
    int* lower_triangular = new int[lower_size];

    // 4. Read the upper_triangular array from the second line
    for (int i = 0; i < upper_size; ++i) {
        file >> upper_triangular[i];
    }

    // 5. Read the lower_triangular array from the third line
    for (int i = 0; i < lower_size; ++i) {
        file >> lower_triangular[i];
    }

    file.close();
    SecretImage secret_image(width, height, upper_triangular, lower_triangular);
    return secret_image;
}

// Returns a pointer to the upper triangular part of the secret image.
int * SecretImage::get_upper_triangular() const {
    return upper_triangular;
}

// Returns a pointer to the lower triangular part of the secret image.
int * SecretImage::get_lower_triangular() const {
    return lower_triangular;
}

// Returns the width of the secret image.
int SecretImage::get_width() const {
    return width;
}

// Returns the height of the secret image.
int SecretImage::get_height() const {
    return height;
}
