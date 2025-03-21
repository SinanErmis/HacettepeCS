# Makefile for Morse Code Converter

# Compiler
CXX = g++
# Compiler flags
CXXFLAGS = -std=c++11 -Wall -Wextra -Werror -Wno-unused-variable -Wno-unused-parameter

# Executable name
TARGET = program

# Source files
SRCS = main.cpp MorseConverter.cpp DataLoad.cpp

# Input files for running the program with "morse" and "english" flags
MORSE_INPUT_FILES = input_201_instructors.txt to_do_list.txt selma_hoca.txt input_aykut_elmas.txt about_ahmet.txt
ENGLISH_INPUT_FILES = future.txt joke.txt python.txt berber.txt secret_message.txt

# Output files produced by the program
OUTPUT_FILES = \
    about_ahmet_morse.txt \
    input_aykut_elmas_morse.txt \
    system_data_dll_acoh_amles_morse.txt \
    system_data_dll_srotcurtsni_102_tupni_morse.txt \
    system_data_dll_tsil_od_ot_morse.txt \
    system_data_dll_erutuf_morse.txt \
    joke_morse.txt \
    python_morse.txt \
    berber_morse.txt \
    secret_message_morse.txt

# Default target
all: $(TARGET)

# Rule to compile the program
$(TARGET): $(SRCS)
	$(CXX) $(CXXFLAGS) $(SRCS) -o $(TARGET)

# Rule to run the program with both "morse" and "english" flags
run: $(TARGET)
	@for file in $(MORSE_INPUT_FILES); do \
		./$(TARGET) $$file morse; \
	done
	@for file in $(ENGLISH_INPUT_FILES); do \
		./$(TARGET) $$file english; \
	done

# Clean rule to remove the executable and the output files
clean:
	rm -f $(TARGET) $(OUTPUT_FILES)

.PHONY: all run clean
