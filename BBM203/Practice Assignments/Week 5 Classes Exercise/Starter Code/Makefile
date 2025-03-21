# Compiler and flags
CXX = g++
CXXFLAGS = -g -std=c++11 -Wall -Iinclude
OBJ_DIR = obj
SRC_DIR = src
BIN_DIR = bin
INCLUDE_DIR = include

# List all source files
SRCS = $(SRC_DIR)/RGBColor.cpp $(SRC_DIR)/HSVColor.cpp $(SRC_DIR)/CMYKColor.cpp $(SRC_DIR)/Color.cpp main.cpp

# Convert each source file into a corresponding object file in the obj/ directory
OBJS = $(patsubst $(SRC_DIR)/%.cpp, $(OBJ_DIR)/%.o, $(SRCS))

# Target executable
TARGET = $(BIN_DIR)/color_spaces

# Ensure directories exist
$(OBJ_DIR):
	mkdir -p $(OBJ_DIR)

$(BIN_DIR):
	mkdir -p $(BIN_DIR)

all: $(TARGET)

# Main build rule
$(TARGET): $(BIN_DIR) $(OBJS)
	$(CXX) $(CXXFLAGS) -o $(TARGET) $(OBJS)

# Compile .cpp files into .o files in obj/ directory
$(OBJ_DIR)/%.o: $(SRC_DIR)/%.cpp | $(OBJ_DIR)
	$(CXX) $(CXXFLAGS) -c $< -o $@

# Rule to compile main.cpp separately
$(OBJ_DIR)/main.o: main.cpp | $(OBJ_DIR)
	$(CXX) $(CXXFLAGS) -c main.cpp -o $(OBJ_DIR)/main.o

# Clean rule to remove generated files
clean:
	rm -rf $(OBJ_DIR) $(BIN_DIR)

.PHONY: clean
