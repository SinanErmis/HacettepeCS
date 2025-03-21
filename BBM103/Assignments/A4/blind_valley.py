import sys


# Utility function to print solution
def print_solution(board, M, N, output_file):
    string = ''
    for i in range(M):
        for j in range(N):
            string = string + str(board[i][j])
            if j != N -1:
                string = string + ' '
        if i != M-1:
            string = string + '\n'
    output_file.write(string)


# Count the total number of characters in current column
def count_in_column(board, char, column_index, M):
    count = 0
    for i in range(M):
        if board[i][column_index] == char:
            count = count + 1
    return count


# Count the total number of characters in current row
def count_in_row(board, char, row_index, N):
    count = 0
    for j in range(N):
        if board[row_index][j] == char:
            count = count + 1
    return count


# Function to check if it is safe to put given char at 'board[row][col]'
def is_safe(board, row, col, char, constraints, M, N):
    # check for adjacent cells
    if ((row - 1 >= 0 and board[row - 1][col] == char) or
            (col + 1 < N and board[row][col + 1] == char) or
            (row + 1 < M and board[row + 1][col] == char) or
            (col - 1 >= 0 and board[row][col - 1] == char)):
        return False

    # count character `ch` in the current row
    row_count = count_in_row(board, char, row, N)

    # count character `ch` in the current column
    column_count = count_in_column(board, char, col, M)

    # if the given character is 'H', then:
    # check constraints[0] for max H per row and
    # constraints[2] for max H per column
    if char == 'H':

        # check constraints[2] for max H per column
        if constraints[2][col] != -1 and column_count >= constraints[2][col]:
            return False

        # check constraints[0] for max H per row
        if constraints[0][row] != -1 and row_count >= constraints[0][row]:
            return False

    # if the given character is 'B', then
    # check constraints[1] for max B per row and
    # constraints[1] for max B per column
    if char == 'B':

        # check constraints[1] for max B per column
        if constraints[3][col] != -1 and column_count >= constraints[3][col]:
            return False

        # check constraints[1] for max B per row
        if constraints[1][row] != -1 and row_count >= constraints[1][row]:
            return False

    return True


# Function to validate the configuration of an output board
def validate_configuration(board, constraints, M, N):
    # check constraints[2] for max H per column
    for i in range(N):
        if constraints[2][i] != -1 and count_in_column(board, 'H', i, M) != constraints[2][i]:
            return False

    # check constraints[0] for max H per row
    for j in range(M):
        if constraints[0][j] != -1 and count_in_row(board, 'H', j, N) != constraints[0][j]:
            return False

    # check constraints[1] for max B per column
    for i in range(N):
        if constraints[3][i] != -1 and count_in_column(board, 'B', i, M) != constraints[3][i]:
            return False

    # check constraints[1] for max B per row
    for j in range(M):
        if constraints[1][j] != -1 and count_in_row(board, 'B', j, N) != constraints[1][j]:
            return False

    return True


# The main function to solve the puzzle
def solve_blind_valley_puzzle(board, row, col, constraints, rules, M, N):
    # if the last cell is reached
    if row >= M - 1 and col >= N - 1:
        return validate_configuration(board, constraints, M, N)

    # if the last column of the current row is already processed,
    # go to the next row, the first column
    if col >= N:
        col = 0
        row = row + 1

    # if the current cell contains 'R' or 'D' (end of horizontal or vertical slot), recur for the next cell
    if rules[row][col] == 'R' or rules[row][col] == 'D':

        if solve_blind_valley_puzzle(board, row, col + 1, constraints, rules, M, N):
            return True

    # if the horizontal slot contains 'L' and 'R'
    if rules[row][col] == 'L' and rules[row][col + 1] == 'R':

        # put ('H', 'B') pair and recur
        if (is_safe(board, row, col, 'H', constraints, M, N) and
                is_safe(board, row, col + 1, 'B', constraints, M, N)):
            board[row][col] = 'H'
            board[row][col + 1] = 'B'

            if solve_blind_valley_puzzle(board, row, col + 2, constraints, rules, M, N):
                return True

            # if it doesn't lead to a solution, backtrack
            board[row][col] = 'N'
            board[row][col + 1] = 'N'

        # put ('B', 'H') pair and recur
        if (is_safe(board, row, col, 'B', constraints, M, N) and
                is_safe(board, row, col + 1, 'H', constraints, M, N)):
            board[row][col] = 'B'
            board[row][col + 1] = 'H'

            if solve_blind_valley_puzzle(board, row, col + 2, constraints, rules, M, N):
                return True

            # if it doesn't lead to a solution, backtrack
            board[row][col] = 'N'
            board[row][col + 1] = 'N'

    # if the vertical slot contains 'U' and 'D'
    if rules[row][col] == 'U' and rules[row + 1][col] == 'D':

        # put ('H', 'B') pair and recur
        if (is_safe(board, row, col, 'H', constraints, M, N) and
                is_safe(board, row + 1, col, 'B', constraints, M, N)):
            board[row][col] = 'H'
            board[row + 1][col] = 'B'

            if solve_blind_valley_puzzle(board, row, col + 1, constraints, rules, M, N):
                return True

            # if it doesn't lead to a solution, backtrack
            board[row][col] = 'N'
            board[row + 1][col] = 'N'

        # put ('B', 'H') pair and recur
        if (is_safe(board, row, col, 'B', constraints, M, N) and
                is_safe(board, row + 1, col, 'H', constraints, M, N)):
            board[row][col] = 'B'
            board[row + 1][col] = 'H'

            if solve_blind_valley_puzzle(board, row, col + 1, constraints, rules, M, N):
                return True

            # if it doesn't lead to a solution, backtrack
            board[row][col] = 'N'
            board[row + 1][col] = 'N'

    # ignore the current cell and recur
    if solve_blind_valley_puzzle(board, row, col + 1, constraints, rules, M, N):
        return True

    # if no solution is possible, return false
    return False


def blind_valley(constraints, rules, M, N, output_file_name):
    # to store the result
    # initialize all cells by `X`
    board = [['N' for x in range(N)] for y in range(M)]

    output_file = open(output_file_name, 'w')

    # start from `(0, 0)` cell
    if not solve_blind_valley_puzzle(board, 0, 0, constraints, rules, M, N):
        output_file.write("No solution!")
        return

    # print result if the given configuration is solvable
    print_solution(board, M, N, output_file)

    output_file.close()


def read_input(input_file_name):
    input_file = open(input_file_name, 'r')

    # [0] -> max H per ROW
    # [1] -> max B per ROW
    # [2] -> max H per COLUMN
    # [3] -> max B per COLUMN
    constraints = []
    for i in range(4):
        constraints.append([int(s) for s in input_file.readline().split()])

    board = []
    # Since constraints[0] refers to max number of high per row
    # length of it corresponds to number of total rows
    for i in range(len(constraints[0])):
        row = input_file.readline().split()
        board.append(row)

    return (constraints, board)


if __name__ == '__main__':

    # Check if the command line argument for the input file is provided
    if len(sys.argv) != 3:
        print("Usage: python blind_valley.py <input_file> <output_file>")
        sys.exit(1)

    input_file_name = sys.argv[1]
    output_file_name = sys.argv[2]

    (constraints, rules) = read_input(input_file_name)

    # `M × N` matrix
    (column_length, row_length) = (len(rules), len(rules[0]))

    blind_valley(constraints, rules, column_length, row_length, output_file_name)
