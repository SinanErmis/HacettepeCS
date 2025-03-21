#include <iostream>
#include "Counter.h"

using std::cout;
using std::endl;
using std::cin;

void increment_by_copy(Counter counter){
    counter++;
}

void increment_by_pointer(Counter * counter){
    (*counter)++;
}

void increment_by_reference(Counter & counter){
    counter++;
}

int main() {
    Counter counter_1(0), counter_2(11), counter_3(51);
    
    cout << "initial counter 1: " << counter_1 << endl;
    cout << "initial counter 2: " << counter_2 << endl;
    cout << "initial counter 3: " << counter_3 << endl;

    increment_by_copy(counter_1);
    increment_by_pointer(&counter_2);
    increment_by_reference(counter_3);

    cout << "after increment counter 1: " << counter_1 << endl;
    cout << "after increment counter 2: " << counter_2 << endl;
    cout << "after increment counter 3: " << counter_3 << endl;

    return 0;
}