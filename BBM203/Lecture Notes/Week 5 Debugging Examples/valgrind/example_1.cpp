#include <iostream>
#include <string>

using namespace std;

// Invalid delete
int main(int argc, char ** argv) {
     int my_array[3];
     delete &my_array[6];
     return 0;    
}

/*
     $ valgrind ./example_1
     ==359== Memcheck, a memory error detector
     ==359== Copyright (C) 2002-2017, and GNU GPL'd, by Julian Seward et al.
     ==359== Using Valgrind-3.15.0 and LibVEX; rerun with -h for copyright info
     ==359== Command: ./example_1
     ==359==
     ==359== Invalid free() / delete / delete[] / realloc()
     ==359==    at 0x483CFBF: operator delete(void*) (in /usr/lib/x86_64-linux-gnu/valgrind/vgpreload_memcheck-amd64-linux.so)
     ==359==    by 0x1091DA: main (example_1.cpp:9)
     ==359==  Address 0x1ffefffcc4 is on thread 1's stack
     ==359==  in frame #1, created by main (example_1.cpp:7)
     ==359==
     ==359==
     ==359== HEAP SUMMARY:
     ==359==     in use at exit: 0 bytes in 0 blocks
     ==359==   total heap usage: 1 allocs, 2 frees, 72,704 bytes allocated
     ==359==
     ==359== All heap blocks were freed -- no leaks are possible
     ==359==
     ==359== For lists of detected and suppressed errors, rerun with: -s
     ==359== ERROR SUMMARY: 1 errors from 1 contexts (suppressed: 0 from 0)
*/