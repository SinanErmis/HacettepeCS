#ifndef STUDENT_H
#define STUDENT_H

#include <string>
#include <vector>
#include <iostream>

class Student {
public:
    // Constructor to initialize the student's name, id, grades, midterm score, and final exam score
    Student(const std::string& studentName, int studentId, const std::vector<int>& studentGrades, int midtermScore, int finalExamScore);

    // Getter methods for name, id, grades, midterm score, and final exam score
    std::string getName() const;
    int getId() const;
    std::vector<int> getGrades() const;
    int getMidtermScore() const;
    int getFinalExamScore() const;

    // Calculate and return GPA (round to 2 digits)
    double getGpa() const;

    // Check if the student will get F1 grade
    bool isF1() const;

    // Check if the student has an F2 grade
    bool isF2() const;

    // Check if the student has an F3 grade
    bool isF3() const;

    // Get the letter grade based on GPA
    std::string getLetter() const;

    // Operator overloading for printing Student information
    friend std::ostream& operator<<(std::ostream& os, const Student& student);

private:
    std::string name;
    int id;
    std::vector<int> assignment_grades;
    int midterm_score;
    int final_exam_score;
};

// Function to sort students by name
void sort_by_name(std::vector<Student>& students);

// Function to sort students by GPA
void sort_by_gpa(std::vector<Student>& students);

#endif // STUDENT_H
