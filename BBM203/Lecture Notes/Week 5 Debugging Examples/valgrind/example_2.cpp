#include <iostream>
#include <string>

using namespace std;

// Invalid read
int main(int argc, char ** argv) {
     int * my_array = new int[5];
     int value = my_array[60];
     cout << value << endl;
     return 0;    
}
/*
     $ valgrind ./example_2
     ==371== Memcheck, a memory error detector
     ==371== Copyright (C) 2002-2017, and GNU GPL'd, by Julian Seward et al.
     ==371== Using Valgrind-3.15.0 and LibVEX; rerun with -h for copyright info
     ==371== Command: ./example_2
     ==371==
     ==371== Invalid read of size 4
     ==371==    at 0x1091EE: main (example_2.cpp:9)
     ==371==  Address 0x4da9d70 is 144 bytes inside an unallocated block of size 4,121,344 in arena "client"
     ==371==
     0
     ==371==
     ==371== HEAP SUMMARY:
     ==371==     in use at exit: 20 bytes in 1 blocks
     ==371==   total heap usage: 3 allocs, 2 frees, 73,748 bytes allocated
     ==371==
     ==371== LEAK SUMMARY:
     ==371==    definitely lost: 20 bytes in 1 blocks
     ==371==    indirectly lost: 0 bytes in 0 blocks
     ==371==      possibly lost: 0 bytes in 0 blocks
     ==371==    still reachable: 0 bytes in 0 blocks
     ==371==         suppressed: 0 bytes in 0 blocks
     ==371== Rerun with --leak-check=full to see details of leaked memory
     ==371==
     ==371== For lists of detected and suppressed errors, rerun with: -s
     ==371== ERROR SUMMARY: 1 errors from 1 contexts (suppressed: 0 from 0)
*/