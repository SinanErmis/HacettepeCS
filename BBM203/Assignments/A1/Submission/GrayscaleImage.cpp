#include "GrayscaleImage.h"
#include <iostream>
#include <cstring>  // For memcpy
#define STB_IMAGE_IMPLEMENTATION
#include "stb_image.h"
#define STB_IMAGE_WRITE_IMPLEMENTATION
#include "stb_image_write.h"
#include <stdexcept>


// Constructor: load from a file
GrayscaleImage::GrayscaleImage(const char* filename) {

    // Image loading code using stbi
    int channels;
    unsigned char* image = stbi_load(filename, &width, &height, &channels, STBI_grey);

    if (image == nullptr) {
        std::cerr << "Error: Could not load image " << filename << std::endl;
        exit(1);
    }

    //Allocate memory
    data = new int *[height];
    for (int i = 0; i < height; i++) {
        data[i] = new int[width];
    }

    //Fill it
    for (int i = 0; i < height; i++) {
        for (int j = 0; j < width; j++) {
            data[i][j] = static_cast<int>(image[i * width + j]);
        }
    }

    // Free the dynamically allocated memory of stbi image
    stbi_image_free(image);
}

// Constructor: initialize from a pre-existing data matrix
GrayscaleImage::GrayscaleImage(int** inputData, int h, int w) {

    height = h;
    width = w;

    //Allocate
    data = new int *[height];
    for (int i = 0; i < height; i++) {
        data[i] = new int[width];
    }

    //Copy
    for (int i = 0; i < height; i++) {
        for (int j = 0; j < width; j++) {
            data[i][j] = inputData[i][j];
        }
    }
}

// Constructor to create a blank image of given width and height
GrayscaleImage::GrayscaleImage(int w, int h) : width(w), height(h) {

    //Allocate, all values defaults to zero (0)
    data = new int *[height];
    for (int i = 0; i < height; i++) {
        data[i] = new int[width];
    }
}

// Copy constructor
GrayscaleImage::GrayscaleImage(const GrayscaleImage& other) {
    height = other.get_height();
    width = other.get_width();

    //Allocate
    data = new int *[height];
    for (int i = 0; i < height; i++) {
        data[i] = new int[width];
    }

    //Copy every single pixel
    for (int i = 0; i < height; i++) {
        for (int j = 0; j < width; j++) {
            data[i][j] = other.get_pixel(i,j);
        }
    }
}

// Destructor
GrayscaleImage::~GrayscaleImage() {
    //No need to explain
    for (int i = 0; i < height; ++i) {
        delete[] data[i];
    }
    delete[] data;
}

// Equality operator
bool GrayscaleImage::operator==(const GrayscaleImage& other) const {

    //Check euqality using guard clauses
    //Some finds this a bad practice, but in my opinion it vastly improves readability
    //Find more information here: https://www.youtube.com/watch?v=CFRhGnuXG-4
    if(height != other.get_height()) {
        return false;
    }

    if(width != other.get_width()) {
        return false;
    }

    if(data == nullptr || other.get_data() == nullptr) {
        return false;
    }

    for (int i = 0; i < height; i++) {
        for (int j = 0; j < width; j++) {
            if(data[i][j] != other.get_pixel(i,j)) {
                return false;
            }
        }
    }

    return true;
}

// Addition operator
GrayscaleImage GrayscaleImage::operator+(const GrayscaleImage& other) const {
    // Create a new image for the result
    GrayscaleImage result(width, height);

    for (int i = 0; i < height; i++) {
        for (int j = 0; j < width; j++) {
            int value = get_pixel(i,j) + other.get_pixel(i,j);

            //Clamp
            if(value > 255) {
                value = 255;
            }

            //No need to clamp for lower bounds since it's addition
            result.data[i][j] = value;
        }
    }

    return result;
}

// Subtraction operator
GrayscaleImage GrayscaleImage::operator-(const GrayscaleImage& other) const {
    GrayscaleImage result(width, height);

    for (int i = 0; i < height; i++) {
        for (int j = 0; j < width; j++) {
            int value = get_pixel(i,j) - other.get_pixel(i,j);

            //No need to clamp for upper bounds since it's subtraction

            if(value < 0) {
                value = 0;
            }
            result.data[i][j] = value;
        }
    }
    return result;
}

// Get a specific pixel value
int GrayscaleImage::get_pixel(int row, int col) const {
    return data[row][col];
}

// Set a specific pixel value
void GrayscaleImage::set_pixel(int row, int col, int value) {
    data[row][col] = value;
}

// Function to save the image to a PNG file
void GrayscaleImage::save_to_file(const char* filename) const {
    // Create a buffer to hold the image data in the format stb_image_write expects
    unsigned char* imageBuffer = new unsigned char[width * height];

    // Fill the buffer with pixel data (convert int to unsigned char)
    for (int i = 0; i < height; ++i) {
        for (int j = 0; j < width; ++j) {
            imageBuffer[i * width + j] = static_cast<unsigned char>(data[i][j]);
        }
    }

    // Write the buffer to a PNG file
    if (!stbi_write_png(filename, width, height, 1, imageBuffer, width)) {
        std::cerr << "Error: Could not save image to file " << filename << std::endl;
    }

    // Clean up the allocated buffer
    delete[] imageBuffer;
}
