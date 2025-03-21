#include <string>
#include <vector>
#include <iostream>

class Student {
public:
    // Constructor to initialize the student's name, id, grades, midterm score, and final exam score
    Student(const std::string& studentName, int studentId, const std::vector<int>& studentGrades, int midtermScore, int finalExamScore) 
	: name(studentName), id(studentId), assignment_grades(studentGrades), midterm_score(midtermScore), final_exam_score(finalExamScore)
	 {}

    // Getter methods for name, id, grades, midterm score, and final exam score
    std::string getName() const {
        return name;
    }

    int getId() const {
        return id;
    }

    std::vector<int> getGrades() const {
        return assignment_grades;
    }

    int getMidtermScore() const {
        return midterm_score;
    }

    int getFinalExamScore() const {
        return final_exam_score;
    }


    // Calculate and return GPA (round to 2 digits)
    double getGpa() const {

        //Fill this method

    }

    // Check if the student will get F1 grade
    bool isF1() const {

        // Fill this method

    }

    // Check if the student has an F2 grade
    bool isF2() const {
  
        //Fill this method

    }

    // Check if the student has an F3 grade
    bool isF3() const {
        
        // Fill this method

    }

    // Get the letter grade based on GPA
    std::string getLetter() const {

        //Fill this method.

    }

    /*
    Make the operator overloding for your student class, that will
    give you the output of each Student for part 3, which is necessary for printing
    Student's information to the output file. "student.name\tstudent.id\tstudent.getLetter"
    (Somehow like toString() method in Java)
    What does the keyword 'friend' mean?
    */
    friend std::ostream& operator<<(std::ostream& os, const Student& student) {
        
        //Fill this method
    }

private:
    std::string name;            
    int id;                                 
    std::vector<int> assignment_grades;      
    int midterm_score;           
    int final_exam_score;        
};



void sort_by_name(std::vector<Student>& students){
	
	//Sorts the students by their names.
	
}

void sort_by_gpa(std::vector<Student>& student){
	
	//Sorts the students by their gpa
}

