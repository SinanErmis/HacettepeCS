#include "RGBColor.h"
#include "HSVColor.h"
#include "CMYKColor.h"
#include <iostream>

int main() {
    // Parse colors from the input file and store them in a vector of Color pointers.
    // The parseColorsFromFile function dynamically allocates objects for each color in the file.
    std::vector<Color*> colors = Color::parseColorsFromFile("input/colors.txt");

    // Loop through each color in the vector to print their information and check for blending opportunities.
    for (size_t i = 0; i < colors.size(); ++i) {
        // Get the name of the current color's color space ("RGB", "HSV" or "CMYK").
        const char * name = colors[i]->getColorSpaceName();
        
        // Print the color's name along with a colored text to indicate the color visually.
        colors[i]->printColoredText(name);

        // Check if the current color and the previous color are from the same color space.
        if (i != 0 && name == colors[i-1]->getColorSpaceName()) {
            // If the color spaces are the same, compare the two colors using the overloaded operator==.
            // If the colors are not equal, blend them together using the overloaded operator+.
            if (!(*colors[i] == *colors[i - 1])) {
                // Create a new blended color by adding the current color to the previous one.
                // The blended color is dynamically allocated using the overloaded operator+.
                Color* blendedColor = *(colors[i]) + *(colors[i-1]);
                
                // Print the name of the blended color's color space along with a colored text.
                blendedColor->printColoredText(std::string("Blended ") + blendedColor->getColorSpaceName());

                // Deallocate the memory for the dynamically created blended color.
                delete blendedColor;
            }
        }
    }

    // Clean up and deallocate all the dynamically allocated colors in the vector.
    Color::cleanupColorVectors(colors);

    // Return 0 to indicate successful program termination.
    return 0;
}
