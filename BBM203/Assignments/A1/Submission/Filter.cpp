#include "Filter.h"
#include <algorithm>
#include <cmath>
#include <vector>
#include <numeric>
#include <math.h>

// Mean Filter
void Filter::apply_mean_filter(GrayscaleImage& image, int kernelSize) {
    GrayscaleImage original(image);
    int width = image.get_width();
    int height = image.get_height();

    // Calculate the padding needed for the kernel
    int pad = kernelSize / 2;

    //For each pixel in image
    for (int y = 0; y < height; ++y) {
        for (int x = 0; x < width; ++x) {
            double sum = 0.0;

            // Iterate for pixels around current pixel
            for (int dy = -pad; dy <= pad; ++dy) {
                for (int dx = -pad; dx <= pad; ++dx) {

                    int current_x = x + dx;
                    int current_y = y + dy;

                    // Ensure the neighbor coordinates are within image bounds
                    if (current_x >= 0 && current_x < width && current_y >= 0 && current_y < height) {
                        sum += original.get_pixel(current_x, current_y);
                    }
                }
            }

            int mean_value = static_cast<int>(sum / (kernelSize * kernelSize)); //This also floors the value

            // Update the current pixel in the image with the mean value
            image.set_pixel(x, y, mean_value);
        }
    }
}

// Gaussian Smoothing Filter
void Filter::apply_gaussian_smoothing(GrayscaleImage& image, int kernelSize, double sigma) {
    GrayscaleImage original(image);
    int width = image.get_width();
    int height = image.get_height();

    int pad = kernelSize / 2;
    //Create a kernel to fill it with weights
    std::vector<std::vector<double>> kernel(kernelSize, std::vector<double>(kernelSize));
    double sum = 0.0;

    // Fill the kernel
    for (int y = -pad; y <= pad; ++y) {
        for (int x = -pad; x <= pad; ++x) {
            //This took forever to get right...
            double value = (1.0 / (2.0 * M_PI * sigma * sigma)) * std::exp(-(x * x + y * y) / (2.0 * sigma * sigma));
            kernel[y + pad][x + pad] = value;
            sum += value;
            // Keep track of the total sum to normalize later
            // There is a good explanation about it here: https://homepages.inf.ed.ac.uk/rbf/HIPR2/gsmooth.htm
        }
    }

    // Normalizea
    for (int y = 0; y < kernelSize; ++y) {
        for (int x = 0; x < kernelSize; ++x) {
            kernel[y][x] /= sum;
        }
    }

    for (int y = 0; y < height; ++y) {
        for (int x = 0; x < width; ++x) {
            double sum = 0.0;

            // Iterate over the kernel
            for (int dy = -pad; dy <= pad; ++dy) {
                for (int dx = -pad; dx <= pad; ++dx) {

                    int current_x = x + dx;
                    int current_y = y + dy;

                    //if in bounds
                    if (current_x >= 0 && current_x < width && current_y >= 0 && current_y < height) {
                        //! Multiply the pixel value by the corresponding kernel weight
                        double pixelValue = original.get_pixel(current_x, current_y);
                        sum += pixelValue * kernel[dy + pad][dx + pad];
                    }
                }
            }

            // casting to int also floors the value
            image.set_pixel(x, y, static_cast<int>(sum));
        }
    }
}

// Unsharp Masking Filter
void Filter::apply_unsharp_mask(GrayscaleImage& image, int kernelSize, double amount) {
    GrayscaleImage original_image(image); // reference
    GrayscaleImage blurred_image(image);

    // 1. Blur the image using Gaussian smoothing
    apply_gaussian_smoothing(blurred_image, kernelSize);

    int width = image.get_width();
    int height = image.get_height();

    // 2. Apply unsharp mask formula: original + amount * (original - blurred)
    for (int y = 0; y < height; ++y) {
        for (int x = 0; x < width; ++x) {
            int original_pixel = original_image.get_pixel(x, y);
            int blurred_pixel = blurred_image.get_pixel(x, y);

            //Formula applied here
            int sharpenedPixel = static_cast<int>(original_pixel + amount * (original_pixel - blurred_pixel));

            // Clamp
            if(sharpenedPixel > 255) {
                sharpenedPixel = 255;
            }
            else if (sharpenedPixel < 0) {
                sharpenedPixel = 0;
            }

            image.set_pixel(x, y, sharpenedPixel);
        }
    }
}
