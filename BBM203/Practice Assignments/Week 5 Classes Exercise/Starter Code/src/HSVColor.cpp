/// HSV values range from 0-360 for Hue and 0-100 for Saturation and Value

/// Color space name for HSV is "HSV"

/// HSV to RGB Convertion
/*  Convertion to RGB from HSV is done with this formula:
*  // If saturation is 0, return a shade of gray
    if (saturation == 0) {
        r = g = b = value; // All are equal
    } else {
        float chroma = value * saturation / 100.0f; // Chroma
        float X = chroma * (1 - std::fabs(std::fmod(hue / 60.0f, 2) - 1));
        float m = value/100.0f - chroma;

        // Determine the HSV components based on the hue
        if (hue >= 0 && hue < 60) {
            r = chroma; g = X; b = 0;
        } else if (hue >= 60 && hue < 120) {
            r = X; g = chroma; b = 0;
        } else if (hue >= 120 && hue < 180) {
            r = 0; g = chroma; b = X;
        } else if (hue >= 180 && hue < 240) {
            r = 0; g = X; b = chroma;
        } else if (hue >= 240 && hue < 300) {
            r = X; g = 0; b = chroma;
        } else {
            r = chroma; g = 0; b = X;
        }

        // Adjust the HSV values to the range of 0-255
        r = (r + m) * 255;
        g = (g + m) * 255;
        b = (b + m) * 255;
    }
*/

/// HSV Color Blending
// HSV color blending is done by taking average of all values among themseles
/*  // Calculate hue difference in degrees, considering circular wrapping
*   double hueDiff = std::abs(hue1 - hue2);
*   double shortestDistance = (hueDiff > 180) ? (360 - hueDiff) : hueDiff;
*
*   // Calculate the new hue by moving along the shortest distance
*   double blendedHue = (hue1 < hue2) ? (hue1 + shortestDistance / 2) : (hue2 + shortestDistance / 2);
*   if (blendedHue >= 360) blendedHue -= 360;  // Wrap hue back to 0-360 range if needed
*
*   // Average saturation and value
*   double blendedSaturation = (s1 + s2) / 2.0;
*   double blendedValue = (v1 + v2) / 2.0;
*/