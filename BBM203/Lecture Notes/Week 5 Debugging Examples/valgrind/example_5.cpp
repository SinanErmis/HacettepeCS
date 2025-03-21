#include <iostream>
#include <string>

using namespace std;

int * gimme_array(int size) {
     int * my_uninit_arr = new int[size];
     return my_uninit_arr;
}

// Uninitialized Values
int main(int argc, char ** argv) {
     int * arr = gimme_array(5);
     for (int i = 0; i < 5; i++) {
          cout << arr[i] << endl;
     }
     return 0;    
}

/*
     $ valgrind ./example_5
     ==438== Memcheck, a memory error detector
     ==438== Copyright (C) 2002-2017, and GNU GPL'd, by Julian Seward et al.
     ==438== Using Valgrind-3.15.0 and LibVEX; rerun with -h for copyright info
     ==438== Command: ./example_5
     ==438==
     ==438== Conditional jump or move depends on uninitialised value(s)
     ==438==    at 0x497A402: std::ostreambuf_iterator<char, std::char_traits<char> > std::num_put<char, std::ostreambuf_iterator<char, std::char_traits<char> > >::_M_insert_int<long>(std::ostreambuf_iterator<char, std::char_traits<char> >, std::ios_base&, char, long) const (in /usr/lib/x86_64-linux-gnu/libstdc++.so.6.0.28)
     ==438==    by 0x4988D5E: std::ostream& std::ostream::_M_insert<long>(long) (in /usr/lib/x86_64-linux-gnu/libstdc++.so.6.0.28)
     ==438==    by 0x10927A: main (example_5.cpp:15)
     ==438==
     ==438== Use of uninitialised value of size 8
     ==438==    at 0x497A10B: ??? (in /usr/lib/x86_64-linux-gnu/libstdc++.so.6.0.28)
     ==438==    by 0x497A42C: std::ostreambuf_iterator<char, std::char_traits<char> > std::num_put<char, std::ostreambuf_iterator<char, std::char_traits<char> > >::_M_insert_int<long>(std::ostreambuf_iterator<char, std::char_traits<char> >, std::ios_base&, char, long) const (in /usr/lib/x86_64-linux-gnu/libstdc++.so.6.0.28)
     ==438==    by 0x4988D5E: std::ostream& std::ostream::_M_insert<long>(long) (in /usr/lib/x86_64-linux-gnu/libstdc++.so.6.0.28)
     ==438==    by 0x10927A: main (example_5.cpp:15)
     ==438==
     ==438== Conditional jump or move depends on uninitialised value(s)
     ==438==    at 0x497A11D: ??? (in /usr/lib/x86_64-linux-gnu/libstdc++.so.6.0.28)
     ==438==    by 0x497A42C: std::ostreambuf_iterator<char, std::char_traits<char> > std::num_put<char, std::ostreambuf_iterator<char, std::char_traits<char> > >::_M_insert_int<long>(std::ostreambuf_iterator<char, std::char_traits<char> >, std::ios_base&, char, long) const (in /usr/lib/x86_64-linux-gnu/libstdc++.so.6.0.28)
     ==438==    by 0x4988D5E: std::ostream& std::ostream::_M_insert<long>(long) (in /usr/lib/x86_64-linux-gnu/libstdc++.so.6.0.28)
     ==438==    by 0x10927A: main (example_5.cpp:15)
     ==438==
     ==438== Conditional jump or move depends on uninitialised value(s)
     ==438==    at 0x497A462: std::ostreambuf_iterator<char, std::char_traits<char> > std::num_put<char, std::ostreambuf_iterator<char, std::char_traits<char> > >::_M_insert_int<long>(std::ostreambuf_iterator<char, std::char_traits<char> >, std::ios_base&, char, long) const (in /usr/lib/x86_64-linux-gnu/libstdc++.so.6.0.28)
     ==438==    by 0x4988D5E: std::ostream& std::ostream::_M_insert<long>(long) (in /usr/lib/x86_64-linux-gnu/libstdc++.so.6.0.28)
     ==438==    by 0x10927A: main (example_5.cpp:15)
     ==438==
     0
     0
     0
     0
     0
     ==438==
     ==438== HEAP SUMMARY:
     ==438==     in use at exit: 20 bytes in 1 blocks
     ==438==   total heap usage: 3 allocs, 2 frees, 73,748 bytes allocated
     ==438==
     ==438== LEAK SUMMARY:
     ==438==    definitely lost: 20 bytes in 1 blocks
     ==438==    indirectly lost: 0 bytes in 0 blocks
     ==438==      possibly lost: 0 bytes in 0 blocks
     ==438==    still reachable: 0 bytes in 0 blocks
     ==438==         suppressed: 0 bytes in 0 blocks
     ==438== Rerun with --leak-check=full to see details of leaked memory
     ==438==
     ==438== Use --track-origins=yes to see where uninitialised values come from
     ==438== For lists of detected and suppressed errors, rerun with: -s
     ==438== ERROR SUMMARY: 20 errors from 4 contexts (suppressed: 0 from 0)
*/

// So we have a ton of information, but we still don't know where the uninitialized values come from. Let's try again.

/*
     $ valgrind --track-origins=yes ./example_5
     ==440== Memcheck, a memory error detector
     ==440== Copyright (C) 2002-2017, and GNU GPL'd, by Julian Seward et al.
     ==440== Using Valgrind-3.15.0 and LibVEX; rerun with -h for copyright info
     ==440== Command: ./example_5
     ==440==
     ==440== Conditional jump or move depends on uninitialised value(s)
     ==440==    at 0x497A402: std::ostreambuf_iterator<char, std::char_traits<char> > std::num_put<char, std::ostreambuf_iterator<char, std::char_traits<char> > >::_M_insert_int<long>(std::ostreambuf_iterator<char, std::char_traits<char> >, std::ios_base&, char, long) const (in /usr/lib/x86_64-linux-gnu/libstdc++.so.6.0.28)
     ==440==    by 0x4988D5E: std::ostream& std::ostream::_M_insert<long>(long) (in /usr/lib/x86_64-linux-gnu/libstdc++.so.6.0.28)
     ==440==    by 0x10927A: main (example_5.cpp:15)
     ==440==  Uninitialised value was created by a heap allocation
     ==440==    at 0x483C583: operator new[](unsigned long) (in /usr/lib/x86_64-linux-gnu/valgrind/vgpreload_memcheck-amd64-linux.so)
     ==440==    by 0x10921E: gimme_array(int) (example_5.cpp:7)
     ==440==    by 0x109245: main (example_5.cpp:13)
     ==440==
     ==440== Use of uninitialised value of size 8
     ==440==    at 0x497A10B: ??? (in /usr/lib/x86_64-linux-gnu/libstdc++.so.6.0.28)
     ==440==    by 0x497A42C: std::ostreambuf_iterator<char, std::char_traits<char> > std::num_put<char, std::ostreambuf_iterator<char, std::char_traits<char> > >::_M_insert_int<long>(std::ostreambuf_iterator<char, std::char_traits<char> >, std::ios_base&, char, long) const (in /usr/lib/x86_64-linux-gnu/libstdc++.so.6.0.28)
     ==440==    by 0x4988D5E: std::ostream& std::ostream::_M_insert<long>(long) (in /usr/lib/x86_64-linux-gnu/libstdc++.so.6.0.28)
     ==440==    by 0x10927A: main (example_5.cpp:15)
     ==440==  Uninitialised value was created by a heap allocation
     ==440==    at 0x483C583: operator new[](unsigned long) (in /usr/lib/x86_64-linux-gnu/valgrind/vgpreload_memcheck-amd64-linux.so)
     ==440==    by 0x10921E: gimme_array(int) (example_5.cpp:7)
     ==440==    by 0x109245: main (example_5.cpp:13)
     ==440==
     ==440== Conditional jump or move depends on uninitialised value(s)
     ==440==    at 0x497A11D: ??? (in /usr/lib/x86_64-linux-gnu/libstdc++.so.6.0.28)
     ==440==    by 0x497A42C: std::ostreambuf_iterator<char, std::char_traits<char> > std::num_put<char, std::ostreambuf_iterator<char, std::char_traits<char> > >::_M_insert_int<long>(std::ostreambuf_iterator<char, std::char_traits<char> >, std::ios_base&, char, long) const (in /usr/lib/x86_64-linux-gnu/libstdc++.so.6.0.28)
     ==440==    by 0x4988D5E: std::ostream& std::ostream::_M_insert<long>(long) (in /usr/lib/x86_64-linux-gnu/libstdc++.so.6.0.28)
     ==440==    by 0x10927A: main (example_5.cpp:15)
     ==440==  Uninitialised value was created by a heap allocation
     ==440==    at 0x483C583: operator new[](unsigned long) (in /usr/lib/x86_64-linux-gnu/valgrind/vgpreload_memcheck-amd64-linux.so)
     ==440==    by 0x10921E: gimme_array(int) (example_5.cpp:7)
     ==440==    by 0x109245: main (example_5.cpp:13)
     ==440==
     ==440== Conditional jump or move depends on uninitialised value(s)
     ==440==    at 0x497A462: std::ostreambuf_iterator<char, std::char_traits<char> > std::num_put<char, std::ostreambuf_iterator<char, std::char_traits<char> > >::_M_insert_int<long>(std::ostreambuf_iterator<char, std::char_traits<char> >, std::ios_base&, char, long) const (in /usr/lib/x86_64-linux-gnu/libstdc++.so.6.0.28)
     ==440==    by 0x4988D5E: std::ostream& std::ostream::_M_insert<long>(long) (in /usr/lib/x86_64-linux-gnu/libstdc++.so.6.0.28)
     ==440==    by 0x10927A: main (example_5.cpp:15)
     ==440==  Uninitialised value was created by a heap allocation
     ==440==    at 0x483C583: operator new[](unsigned long) (in /usr/lib/x86_64-linux-gnu/valgrind/vgpreload_memcheck-amd64-linux.so)
     ==440==    by 0x10921E: gimme_array(int) (example_5.cpp:7)
     ==440==    by 0x109245: main (example_5.cpp:13)
     ==440==
     0
     0
     0
     0
     0
     ==440==
     ==440== HEAP SUMMARY:
     ==440==     in use at exit: 20 bytes in 1 blocks
     ==440==   total heap usage: 3 allocs, 2 frees, 73,748 bytes allocated
     ==440==
     ==440== LEAK SUMMARY:
     ==440==    definitely lost: 20 bytes in 1 blocks
     ==440==    indirectly lost: 0 bytes in 0 blocks
     ==440==      possibly lost: 0 bytes in 0 blocks
     ==440==    still reachable: 0 bytes in 0 blocks
     ==440==         suppressed: 0 bytes in 0 blocks
     ==440== Rerun with --leak-check=full to see details of leaked memory
     ==440==
     ==440== For lists of detected and suppressed errors, rerun with: -s
     ==440== ERROR SUMMARY: 20 errors from 4 contexts (suppressed: 0 from 0)
*/

// Now we know. :)