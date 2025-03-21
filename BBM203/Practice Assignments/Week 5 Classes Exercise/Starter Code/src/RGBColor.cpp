/// RGB values range from 0-255

/// Color space name for RGB is "RGB"

/// RGB to RGB Convertion should return a copy of itself

/// To print colored text to the terminal this formula is used:
//(result might change with OS but keep the function same)
/*
*   std::cout << "\033[38;2;" << r << ";" << g << ";" << b << "m" << text << "\033[0m" << std::endl;
*
*   !!!Any other class that wants to print colored text first should convert to RGB before using this formula!!!
*/

/// RGB Color Blending
// RGB color blending is done by taking average of all values among themseles
// i.e. new_r = (r1 + r2)/2 etc