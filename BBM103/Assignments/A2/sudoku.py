# Sinan Ermiş 2220356143

# This algorithm is somewhat similar to 'Wave Function Collapse' algorithm.
# It basically 'collapses' each cell into one possible answer, then updates other cells' possibilities according to it.
# For more information, check out this video by The Coding Train: https://youtu.be/rI_y2GAlQFM

import sys

def generate_board_from_file(file):
    # Read lines from the file and initialize a 9x9 Sudoku board
    lines = file.readlines()
    board = [[0 for _ in range(9)] for _ in range(9)]

    # Populate the board with values from the file
    for line_index, line in enumerate(lines):
        element_arr_as_str = line.split()
        for column_index, element_as_str in enumerate(element_arr_as_str):
            board[line_index][column_index] = int(element_as_str)
    return board

def print_step(output_file, board, step_count, row_index, column_index, value):
    # Print the current step information and the updated Sudoku board
    string = "------------------\nStep {0} - {1} @ R{2}C{3}\n------------------\n".format(step_count, value, row_index, column_index)
    string += str(board).replace("], [", "\n").replace("[", "").replace("]", "").replace(",", "")
    string += "\n"
    output_file.write(string)

def is_solved(board):
    # Check if the Sudoku board is solved
    for column_index in range(9):
        for row_index in range(9):
            if board[row_index][column_index] == 0:
                return False
    return True

def calculate_possibilities_for_other_cells(board_with_possibilities, row_index, column_index, value):
    # Update possibilities for other cells in the same row, column, and 3x3 grid
    for index in range(9):
        if value in board_with_possibilities[row_index][index]:
            board_with_possibilities[row_index][index].remove(value)
        if value in board_with_possibilities[index][column_index]:
            board_with_possibilities[index][column_index].remove(value)

    grid_index_x = column_index // 3
    grid_index_y = row_index // 3
    for y in range(3):
        for x in range(3):
            row = (grid_index_y * 3) + y
            column = (grid_index_x * 3) + x

            if value in board_with_possibilities[row][column]:
                board_with_possibilities[row][column].remove(value)

def calculate_initial_possibilities(board_with_possibilities, board):
    # Initialize possibilities for each cell based on initial values in the board
    for row_index in range(9):
        for column_index in range(9):
            value = board[row_index][column_index]
            if value == 0:
                continue
            calculate_possibilities_for_other_cells(board_with_possibilities, row_index, column_index, value)

def print_last_separator(output_file):
    # Print a separator line to mark the end of the output
    output_file.write("------------------")

def solve(board, output_file):
    # Solve the Sudoku puzzle
    step_count = 1
    board_with_possibilities = [[[x for x in range(1, 10)] for _ in range(9)] for _ in range(9)]

    calculate_initial_possibilities(board_with_possibilities, board)
    while not is_solved(board):
        for row_index in range(9):
            break_outer_loop = False
            for column_index in range(9):
                if board[row_index][column_index] != 0:
                    continue
                if len(board_with_possibilities[row_index][column_index]) == 1:
                    # If only one possibility, update the board and possibilities
                    value = board_with_possibilities[row_index][column_index][0]
                    board[row_index][column_index] = value
                    calculate_possibilities_for_other_cells(board_with_possibilities, row_index, column_index, value)
                    # Since we need to start from up and leftmost once again, we need to break outer loop too.
                    break_outer_loop = True
                    print_step(output_file, board, step_count, row_index + 1, column_index + 1, value)
                    step_count += 1
                    break
            if break_outer_loop:
                break
    print_last_separator(output_file)
    
def main():
    # Main function to read input file, solve Sudoku, and write output file
    if len(sys.argv) != 3:
        print("Usage: python3 sudoku.py input.txt output.txt")
        sys.exit(1)
    file = open(sys.argv[1], mode="r")
    output = open(sys.argv[2], mode="w")

    board = generate_board_from_file(file)
    solve(board, output)

    file.close()
    output.close()

if __name__ == "__main__":
    main()
