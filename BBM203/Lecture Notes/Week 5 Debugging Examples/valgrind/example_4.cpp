#include <iostream>
#include <string>

using namespace std;

void my_leaker() {
     int * my_leaky_array = new int[500];
     my_leaky_array[1] = 3;
     cout << "I have leaked." << endl;
}

// Memory leak
int main(int argc, char ** argv) {
     my_leaker();
     return 0;    
}

/*
     $ valgrind ./example_4
     ==401== Memcheck, a memory error detector
     ==401== Copyright (C) 2002-2017, and GNU GPL'd, by Julian Seward et al.
     ==401== Using Valgrind-3.15.0 and LibVEX; rerun with -h for copyright info
     ==401== Command: ./example_4
     ==401==
     I have leaked.
     ==401==
     ==401== HEAP SUMMARY:
     ==401==     in use at exit: 2,000 bytes in 1 blocks
     ==401==   total heap usage: 3 allocs, 2 frees, 75,728 bytes allocated
     ==401==
     ==401== LEAK SUMMARY:
     ==401==    definitely lost: 2,000 bytes in 1 blocks
     ==401==    indirectly lost: 0 bytes in 0 blocks
     ==401==      possibly lost: 0 bytes in 0 blocks
     ==401==    still reachable: 0 bytes in 0 blocks
     ==401==         suppressed: 0 bytes in 0 blocks
     ==401== Rerun with --leak-check=full to see details of leaked memory
     ==401==
     ==401== For lists of detected and suppressed errors, rerun with: -s
     ==401== ERROR SUMMARY: 0 errors from 0 contexts (suppressed: 0 from 0)
*/

// No detailed information regarding the leak. Let's try again.

/*
     $ valgrind --leak-check=full ./example_4
     ==402== Memcheck, a memory error detector
     ==402== Copyright (C) 2002-2017, and GNU GPL'd, by Julian Seward et al.
     ==402== Using Valgrind-3.15.0 and LibVEX; rerun with -h for copyright info
     ==402== Command: ./example_4
     ==402==
     I have leaked.
     ==402==
     ==402== HEAP SUMMARY:
     ==402==     in use at exit: 2,000 bytes in 1 blocks
     ==402==   total heap usage: 3 allocs, 2 frees, 75,728 bytes allocated
     ==402==
     ==402== 2,000 bytes in 1 blocks are definitely lost in loss record 1 of 1
     ==402==    at 0x483C583: operator new[](unsigned long) (in /usr/lib/x86_64-linux-gnu/valgrind/vgpreload_memcheck-amd64-linux.so)
     ==402==    by 0x1091DE: my_leaker() (example_4.cpp:7)
     ==402==    by 0x109233: main (example_4.cpp:15)
     ==402==
     ==402== LEAK SUMMARY:
     ==402==    definitely lost: 2,000 bytes in 1 blocks
     ==402==    indirectly lost: 0 bytes in 0 blocks
     ==402==      possibly lost: 0 bytes in 0 blocks
     ==402==    still reachable: 0 bytes in 0 blocks
     ==402==         suppressed: 0 bytes in 0 blocks
     ==402==
     ==402== For lists of detected and suppressed errors, rerun with: -s
     ==402== ERROR SUMMARY: 1 errors from 1 contexts (suppressed: 0 from 0)
*/

// Now we now where the leak is, we can just free it in an appropriate spot. 