//
// Created by Yusuf İpek on 14.10.2023.
//
#include "iostream"
#ifndef EXERCISES_COUNTER_H
#define EXERCISES_COUNTER_H

class Counter{
public:
    Counter();
    Counter(int count);
    Counter(const Counter& counter);
    //~Counter();
    Counter operator++(int);
    friend std::ostream& operator<<(std::ostream& stream, Counter& counter);
    int count;
};

#endif //EXERCISES_COUNTER_H
