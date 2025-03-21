#include <iostream>
#include <string>

using namespace std;

// Invalid write
int main(int argc, char ** argv) {
     int * my_array = new int[5];
     my_array[50] = 1;
     return 0;    
}

/*
     $ valgrind ./example_3
     ==383== Memcheck, a memory error detector
     ==383== Copyright (C) 2002-2017, and GNU GPL'd, by Julian Seward et al.
     ==383== Using Valgrind-3.15.0 and LibVEX; rerun with -h for copyright info
     ==383== Command: ./example_3
     ==383==
     ==383== Invalid write of size 4
     ==383==    at 0x1091B4: main (example_3.cpp:8)
     ==383==  Address 0x4da9d48 is 104 bytes inside an unallocated block of size 4,121,344 in arena "client"
     ==383==
     ==383==
     ==383== HEAP SUMMARY:
     ==383==     in use at exit: 20 bytes in 1 blocks
     ==383==   total heap usage: 2 allocs, 1 frees, 72,724 bytes allocated
     ==383==
     ==383== LEAK SUMMARY:
     ==383==    definitely lost: 20 bytes in 1 blocks
     ==383==    indirectly lost: 0 bytes in 0 blocks
     ==383==      possibly lost: 0 bytes in 0 blocks
     ==383==    still reachable: 0 bytes in 0 blocks
     ==383==         suppressed: 0 bytes in 0 blocks
     ==383== Rerun with --leak-check=full to see details of leaked memory
     ==383==
     ==383== For lists of detected and suppressed errors, rerun with: -s
     ==383== ERROR SUMMARY: 1 errors from 1 contexts (suppressed: 0 from 0)
*/