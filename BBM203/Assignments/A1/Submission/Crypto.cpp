#include "Crypto.h"
#include "GrayscaleImage.h"


// Extract the least significant bits (LSBs) from SecretImage, calculating x, y based on message length
std::vector<int> Crypto::extract_LSBits(SecretImage& secret_image, int message_length) {
    std::vector<int> LSB_array;

    // 1. Reconstruct the SecretImage to a GrayscaleImage.
    GrayscaleImage image = secret_image.reconstruct();

    // 2. Calculate the image dimensions.
    int width = image.get_width();
    int height = image.get_height();
    int total_pixels = width * height;

    // 3. Determine the total bits required based on message length.
    int total_bits_required = message_length * 7;

    // 4. Ensure the image has enough pixels; if not, throw an error.
    if (total_bits_required > total_pixels) {
        throw std::invalid_argument("Image does not have enough pixels for the message.");
    }

    // 5. Calculate the starting pixel from the message_length knowing that the last LSB to extract is in the last pixel of the image.
    int start_pixel = total_pixels - total_bits_required;
    int start_row = start_pixel / width;
    int start_col = start_pixel % width;

    // 6. Extract LSBs from the image pixels and return the result.
    for (int row = start_row; row < height; row++) {
        // For the first row, the column can be not fully equipped
        for (int col = (row == start_row ? start_col : 0); col < width; col++) {
            int pixel_value = image.get_pixel(row, col);
             // Extract LSB by ANDing with 1
             int LSB = pixel_value & 1;
            LSB_array.push_back(LSB);
        }
    }
    return LSB_array;
}


// Decrypt message by converting LSB array into ASCII characters
std::string Crypto::decrypt_message(const std::vector<int>& LSB_array) {
    std::string message;

    // 1. Verify that the LSB array size is a multiple of 7, else throw an error.
    if (LSB_array.size() % 7 != 0) {
        throw std::invalid_argument("LSB array size is not a multiple of 7");
    }

    // 2. Convert each group of 7 bits into an ASCII character
    for (int i = 0; i < LSB_array.size(); i += 7) {
        std::bitset<7> binary_char;

        // Extract 7 bits and form a bitset
        for (int j = 0; j < 7; j++) {
            binary_char[6 - j] = LSB_array[i + j];
        }

        // 3. Collect the characters to form the decrypted message.
        message += static_cast<char>(binary_char.to_ulong());
    }

    // 4. Return the resulting message.
    return message;
}

// Encrypt message by converting ASCII characters into LSBs
std::vector<int> Crypto::encrypt_message(const std::string& message) {
    std::vector<int> LSB_array;

    // 1. Convert each character of the message into a 7-bit binary representation.
    // (using std::bitset)
    for (char ch : message) {
        std::bitset<7> binary(ch);

        // 2. Collect the bits into the LSB array.
        for (int i = 6; i >= 0; --i) { // Iterate from MSB to LSB in each 7-bit binary
            LSB_array.push_back(binary[i]);
        }
    }

    // 3. Return the array of bits.
    return LSB_array;
}

// Embed LSB array into GrayscaleImage starting from the last bit of the image
SecretImage Crypto::embed_LSBits(GrayscaleImage& image, const std::vector<int>& LSB_array) {
    int width = image.get_width();
    int height = image.get_height();
    int total_pixels = width * height;

    if (LSB_array.size() > total_pixels) {
        throw std::invalid_argument("Image does not have enough pixels to store the LSB array.");
    }

    int start_pixel = total_pixels - LSB_array.size();
    int start_row = start_pixel / width;
    int start_col = start_pixel % width;

    int bits_embedded = 0;
    for (int row = start_row; row < height && bits_embedded < LSB_array.size(); row++) {
        for (int col = (row == start_row ? start_col : 0); col < width && bits_embedded < LSB_array.size(); col++) {
            int pixel_value = image.get_pixel(row, col);

            // Clear the least significant bit and set it to the current bit
            pixel_value = (pixel_value & ~1) | LSB_array[bits_embedded];
            image.set_pixel(row, col, pixel_value);

            ++bits_embedded;
        }
    }

    return SecretImage(image);
}
