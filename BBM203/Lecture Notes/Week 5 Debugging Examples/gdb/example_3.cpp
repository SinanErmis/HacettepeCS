#include <iostream>
#include <string>

using namespace std;


// Logic error
int main(int argc, char ** argv) {

     int a = stoi(argv[1]);
     int b = stoi(argv[2]);

     // Let's say we wanted to print "Great success." when a + b is less than i.
     // The expected output is:
     /*
          Great success.
          Great success.
          Great success.
          Great success.
          Great success.
          Great success.
          Great success.
     */
     // But we mistakenly typed the condition incorrectly.
     // So the current output is:
     /*
          Great success.
          Great success.
          Great success.
          Great success.
          Great success.
          Great success.
          Great success.
          Great success.
          Great success.
          Great success.
          Great success.
          Great success.
          Great success.
          Great success.
          Great success.
          Great success.
     */
     // This is too successful for us.
     for (int i = 0; i < 16; i++) {
         if (a + b > i) {
            cout << "Great success." << endl;
            a ++;
         }
     }   
     return 0;
}

// Setting a breakpoint would save us.
/*
     (gdb) break example_3.cpp:47    # We can go with "b 47" for short
     Breakpoint 1 at 0x13dc: file example_3.cpp, line 47.
     (gdb) run # We can go with "r" for short
     Starting program: /mnt/c/Users/PC/Desktop/debugging_examples/gdb/example_3 3 5

     Breakpoint 1, main (argc=3, argv=0x7fffffffde48) at example_3.cpp:47
     47                  cout << "Great success." << endl;
     (gdb) print {a,b,i} # We can go with "p {a,b,i}" for short
     $1 = {3, 5, 0}
     (gdb) continue # We can go with "c" for short
     (gdb) print {a,b,i}
     $2 = {4, 5, 1}
     (gdb) info break # We can go with "b" for short
     Num     Type           Disp Enb Address            What
     1       breakpoint     keep y   0x00005555555553dc in main(int, char**) at example_3.cpp:47
             breakpoint already hit 2 times
*/

// We would see at the breakpoint, 3+5 is greater than zero. This is not what we wanted.

// You can add or remove multiple breakpoints like you would do in a IDE.
/*
     (gdb) b 48
     Breakpoint 2 at 0x555555555404: file example_3.cpp, line 48.
     (gdb) info b
     Num     Type           Disp Enb Address            What
     1       breakpoint     keep y   0x00005555555553dc in main(int, char**) at example_3.cpp:47
             breakpoint already hit 2 times
     2       breakpoint     keep y   0x0000555555555404 in main(int, char**) at example_3.cpp:48
     (gdb) delete 2
     (gdb) info b
     Num     Type           Disp Enb Address            What
     1       breakpoint     keep y   0x00005555555553dc in main(int, char**) at example_3.cpp:47
             breakpoint already hit 2 times
*/