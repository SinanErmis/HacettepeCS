# Sinan Ermiş 2220356143
# Challenging part was knowing built-in error names. Since I don't know most of them, I just threw some empty Exception()'s and I think it's not cool at all.
import sys

def check_command_line_arguments():
    if len(sys.argv) != 3:
        raise ValueError("ERROR: This program needs two command line arguments to run, where the first one is the input file and the second one is the output file!\nSample run command is as follows: python3 calculator.py input.txt output.txt\nProgram is going to terminate!")

def check_input_file(input_file):
    try:
        file = open(input_file, 'r')
        file.close()
    except FileNotFoundError:
        raise FileNotFoundError(f'ERROR: There is either no such a file namely {input_file} or this program does not have permission to read it!\nProgram is going to terminate!')

def process_input_file(input_file, output_file):
    infile = open(input_file, 'r')
    outfile = open(output_file, 'w')

    # Handle exceptions per line
    for line in infile:
        trimmed_line = line.strip()
        if not trimmed_line:
            continue  # Skip empty lines

        # Handle line format
        try:
            arr = trimmed_line.split()
            if len(arr) != 3:
                raise ValueError()
            
            operand1 = arr[0].strip()
            operator = arr[1].strip()
            operand2 = arr[2].strip()
        except Exception as e:
            outfile.write(f"{trimmed_line}\nERROR: Line format is erroneous!\n")
            continue
        
        # Handle operand 1 being a number
        try:
            operand1 = float(operand1)
        except Exception as e:
            outfile.write(f"{trimmed_line}\nERROR: First operand is not a number!\n")
            continue
        
        # Handle operand 2 being a number
        try:
            operand2 = float(operand2)
        except Exception as e:
            outfile.write(f"{trimmed_line}\nERROR: Second operand is not a number!\n")
            continue

        # Handle operator correctness and even check for division by zero case!
        try:
            result = calculate(operand1, operator, operand2)
            outfile.write(f"{trimmed_line}\n={result:.2f}\n")

        except ValueError as e:
            outfile.write(f"{trimmed_line}\nERROR: {str(e)}\n")
            continue
        except ZeroDivisionError:
            outfile.write(f"{trimmed_line}\nERROR: Division by zero!\n")
            continue

    # Delete last newline character
    outfile.seek(0, 2)
    outfile.truncate(outfile.tell() - 2)

    infile.close()
    outfile.close()

def calculate(operand1, operator, operand2):
    if operator == '+':
        return operand1 + operand2
    elif operator == '-':
        return operand1 - operand2
    elif operator == '*':
        return operand1 * operand2
    elif operator == '/':
        if operand2 == 0:
            raise ZeroDivisionError
        return operand1 / operand2
    else:
        raise ValueError("There is no such an operator!")

if __name__ == "__main__":

    # These exceptions are about input and stuff and not gonna printed out to output file
    try:
        check_command_line_arguments()

        input_file = sys.argv[1]
        output_file = sys.argv[2]
        
        check_input_file(input_file)
    except Exception as e:
        print(str(e))
        sys.exit(-1)
    
    process_input_file(input_file, output_file)
