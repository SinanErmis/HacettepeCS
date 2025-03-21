#include <vector>
#include <string>
#include <Student.h>

/*
Calculates simple statistics of a class, using vector of average scores, writing information
to the output file with a title.
(part 1 and part 2 outputs.)
*/
void analize_averages_to_the_file(std::vector<double>& averages, std::string& fileName, std::string& title){

    //Fill this method.

}

/*
Creates simple statistics output for the part 3.
*/
void analize_part_3(std::vector<Student>& students){

}


/*
Applies curve type 1 (increasing average score to 50) to the grades and 
returns a new vector of curved scores. Does not changes the original grades.
*/
std::vector<double> curve_type_1(std::vector<double> averages){

}


/*
Applies curve type 2 (increasing maximum score to 100) to the grades and 
returns a new vector of curved scores. Does not changes the original grades.
*/
std::vector<double> curve_type_2(std::vector<double> averages){

}


/*
Applies curve type 3 (fail exactly n student) to the grades and 
returns a new vector of curved scores. Does not changes the original grades.
*/
std::vector<double> curve_type_3(std::vector<double> averages, int will_fail){

}
