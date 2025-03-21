/// CMYK values range from 0-100

/// Color space name for CMYK is "CMYK"

/// CMYK to RGB Convertion
/*  Convertion to RGB from CMYK is done with this formula:
*  r = 255 * (1 - c/100.0) * (1 - k/100.0);
*  g = 255 * (1 - m/100.0) * (1 - k/100.0);
*  b = 255 * (1 - y/100.0) * (1 - k/100.0);
*/

/// CMYK Color Blending
// CMYK color blending is done by taking average of all values among themseles
// i.e. new_c = (c1 + c2)/2 etc