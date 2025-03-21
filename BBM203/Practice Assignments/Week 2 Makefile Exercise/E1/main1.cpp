//// <DO NOT CHANGE>
#include "GMM.h"

/*
* Gym Meal Machine (GMM) is a specilized vending machine that allows people to choose foods 
* not just with slot numbers but by the food's nutritional values (protein, calorie, etc.) as well.
* This program aims to check whether the machine has enough variety in its food sellection.
* This is done by calculating standart deviation of the calorie values of the all the products in the machine.
* 
* GMM internally stores its product information in a matrix format, 
* however it sends that information to this program in a list.
* 
* Since input only includes list of the products that are currently in the slots of the machine, 
* the input list should not have more items than the total number of slots in the GMM.
* The number of rows and columns of slots (representing the machine's layout) is defined in the GMM.h file.
*
* Format of the input file is: Name[tab]Price[tab]Protein[space]Carbohydrate[space]Fat
* Only Protein, Carbohydrate and Fat values are important for this program.
*/

int main()
{
    std::string fileName = "Product.txt";
    std::vector<std::string> products = read_file(fileName);
    double* calories = parse_calorie_input(products);
    double standard_deviation = calculate_standard_deviation(calories, TOTAL_CAPACITY);

    if(standard_deviation > MIN_DIVERSTY){
        std::cout <<"INFO: Acceptaple diversity:"<< standard_deviation << std::endl;
        
    }else{
        std::cout <<"INFO: Not enough variety!" << std::endl;
    }
    return 0;
}
//// </DO NOT CHANGE>