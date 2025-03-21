#include <iostream>
#include "Counter.h"

using std::cout;
using std::endl;
using std::cin;

int main() {

    Counter * counter_dynamic = new Counter(67);
    int number;
    std::cout << "Enter the array size: ";
    std::cin >> number;
    Counter * counter_array_dynamic = new Counter[number];

    // After you are DONE with those variables.
    delete counter_dynamic;
    delete [] counter_array_dynamic;
}