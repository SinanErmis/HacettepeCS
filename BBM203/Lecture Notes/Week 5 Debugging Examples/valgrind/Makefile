CXX = g++ # Compiler
CXXFLAGS = -std=c++11 -Wall -g # Compiler flags

# List of source files and their corresponding binary targets
SOURCES = example_1.cpp example_2.cpp example_3.cpp example_4.cpp example_5.cpp
TARGETS = $(SOURCES:.cpp=)

all: $(TARGETS)

%: %.cpp
	$(CXX) $(CXXFLAGS) -o $@ $<

clean:
	rm -f $(TARGETS)
