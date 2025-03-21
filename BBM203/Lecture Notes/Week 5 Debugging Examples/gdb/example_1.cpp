#include <iostream>
#include <string>

using namespace std;

// Segmentation fault
int main(int argc, char ** argv) {
     int *foo = nullptr;
     int *foo2 = new int;
     *foo2 = 4;
     *foo = 123;
     return 0;    
}

/*
     $ gdb example_1
     (gdb) run # We can go with "r" for short
     Starting program: /mnt/c/Users/PC/Desktop/debugging_examples/gdb/example_1

     Program received signal SIGSEGV, Segmentation fault.
     0x00005555555551c0 in main (argc=1, argv=0x7fffffffde68) at example_1.cpp:11
     11           *foo = 123;
     (gdb) quit # We can go with "q" for short
*/