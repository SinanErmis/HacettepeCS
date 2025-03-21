#include <iostream>
#include "Counter.h"

using std::cout;
using std::endl;
using std::cin;

// Counter& make_counter_ref_bad(){
//     Counter counter(0);
//     return counter;
// }

// Counter* make_counter_ptr_bad(){
//     Counter counter(0);
//     return &counter;
// }

// Counter* make_counter(){
//     Counter * counter = new Counter;
//     return counter;
// }

Counter make_counter(){
    Counter counter(0);
    return counter;
}

int main() {
    // Counter * my_counter = make_counter_ptr_bad();
    // (*my_counter)++;
    // cout << *my_counter << endl;

    Counter my_counter = make_counter();
    my_counter++;
    cout << my_counter << endl;

    // Counter * my_counter = make_counter();
    // (*my_counter)++;
    // cout << *my_counter << endl;
    // delete my_counter;
    
    return 0;
}