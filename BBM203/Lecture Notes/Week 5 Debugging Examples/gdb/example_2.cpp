#include <iostream>
#include <string>

using namespace std;

// Exception
/*
terminate called after throwing an instance of 'std::out_of_range'
  what():  basic_string::at: __n (which is 15) >= this->size() (which is 13)
Aborted
*/
int main(int argc, char ** argv) {
     std::string myString = "Hello, World!";
     char character = myString.at(15);
     cout << character;
     return 0;   
}

/*
     $ gdb example_2
     (gdb) run # We can go with "r" for short
     Starting program: /mnt/c/Users/PC/Desktop/debugging_examples/gdb/example_2
     terminate called after throwing an instance of 'std::out_of_range'
     what():  basic_string::at: __n (which is 15) >= this->size() (which is 13)

     Program received signal SIGABRT, Aborted.
     __GI_raise (sig=sig@entry=6) at ../sysdeps/unix/sysv/linux/raise.c:50
     50      ../sysdeps/unix/sysv/linux/raise.c: No such file or directory.
     (gdb) backtrace
     #0  __GI_raise (sig=sig@entry=6) at ../sysdeps/unix/sysv/linux/raise.c:50
     #1  0x00007ffff7bef859 in __GI_abort () at abort.c:79
     #2  0x00007ffff7e78911 in ?? () from /lib/x86_64-linux-gnu/libstdc++.so.6
     #3  0x00007ffff7e8438c in ?? () from /lib/x86_64-linux-gnu/libstdc++.so.6
     #4  0x00007ffff7e843f7 in std::terminate() () from /lib/x86_64-linux-gnu/libstdc++.so.6
     #5  0x00007ffff7e846a9 in __cxa_throw () from /lib/x86_64-linux-gnu/libstdc++.so.6
     #6  0x00007ffff7e7b3ab in ?? () from /lib/x86_64-linux-gnu/libstdc++.so.6
     #7  0x00007ffff7f1da33 in std::__cxx11::basic_string<char, std::char_traits<char>, std::allocator<char> >::at(unsigned long) () from /lib/x86_64-linux-gnu/libstdc++.so.6
     #8  0x00005555555552cc in main (argc=1, argv=0x7fffffffde68) at example_2.cpp:14
     (gdb) quit # We can go with "q" for short
*/