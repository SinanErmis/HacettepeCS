//
// Created by Yusuf İpek on 14.10.2023.
//
#include "iostream"
#include "Counter.h"

Counter::Counter() {
    count = 0;
}

Counter::Counter(int count) {
    this->count = count;
}

Counter::Counter(const Counter& counter){
    this->count = counter.count;
}

Counter Counter::operator++(int) {
    this->count++;
    return *this;
}

std::ostream& operator<<(std::ostream&stream, Counter& counter){
    return stream << counter.count;
}