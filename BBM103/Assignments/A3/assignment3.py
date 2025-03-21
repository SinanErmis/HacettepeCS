import sys

# Function to read a file and create a game board from it
def read_file_and_create_board(input_file: str):
    file = open(input_file, 'r')
    return [[int(s) for s in line.split()] for line in file.readlines()]

# Function to check if the game is over by checking for matching adjacent elements
def is_game_over(board: list[list[int]]):
    curr = -1
    # Check rows for matching adjacent elements
    for i in range(len(board)):
        for j in range(len(board[i])):
            if board[i][j] == -1:
                curr = board[i][j]
                continue
            if board[i][j] == curr:
                return False
            curr = board[i][j]
    curr = -1
    # Check columns for matching adjacent elements
    for i in range(len(board[0])):
        for j in range(len(board)):
            if board[j][i] == -1:
                curr = board[j][i]
                continue
            if board[j][i] == curr:
                return False
            curr = board[j][i]
    return True

# Function to print the game board in a readable format
def print_board(board):
    string = str(board).replace('], [', '\n').replace(',', '').removeprefix('[[').removesuffix(']]').replace('-1', ' ')
    print(string)

# Function to destroy adjacent neighbors with the same value recursively
def destroy_neighbors(board: list[list[int]], value: int, line: int, column: int):
    # Check array bounds
    if line < 0 or column < 0 or line >= len(board) or column >= len(board[0]):
        return 0
    
    if board[line][column] != value:
        return 0
    
    board[line][column] = -1
    
    score = value
    score += destroy_neighbors(board, value, line - 1, column)
    score += destroy_neighbors(board, value, line + 1, column)
    score += destroy_neighbors(board, value, line, column - 1)
    score += destroy_neighbors(board, value, line, column + 1)
    
    return score

# Function to rearrange the board after destroying adjacent neighbors
def rearrange_board(board: list[list[int]]):
    
    # Remove rows with all members set to -1
    # Idk why but this didn't work
    # board = [row for row in board if not all(x == -1 for x in row)]
    for row in board:
        is_all_minus_one = True
        for element in row:
            if element != -1:
                is_all_minus_one = False
                break
        if is_all_minus_one:
            board.remove(row)
    
    for i in range(len(board[0])-1, -1, -1):
        stack = []
        
        # Fill the stack with numbers in that column
        for j in range(len(board)):
            if board[j][i] != -1:
                stack.append(board[j][i])
        
        # If the column is empty, then remove that column
        if len(stack) == 0:
            for j in range(len(board)):
                for i1 in range(i, (len(board[j]) - 1)):
                    board[j][i1] = board[j][i1+1]
                board[j].pop()
        else:
            # Move in reverse to write values from bottom to top
            for j in range(len(board) - 1, -1, -1):
                if len(stack) > 0:
                    board[j][i] = stack.pop()
                else:
                    board[j][i] = -1

if __name__ == '__main__':
    # Check if the command line argument for the input file is provided
    if len(sys.argv) != 2:
        print("Usage: python assignment3.py <input_file>")
        sys.exit(1)

    # Get the input file name from the command line argument
    input_file_name = sys.argv[1]

    # Read the initial game board from the specified input file
    try:
        board = read_file_and_create_board(input_file_name)
    except FileNotFoundError:
        print(f"Error: File '{input_file_name}' not found.")
        sys.exit(1)
    
    # Initialize the score variable
    score = 0
    
    # Continue playing until the game is over
    while not is_game_over(board):
        # Print the current state of the board
        print_board(board)
        # Display the current score
        print('Your score is: ' + str(score))
        
        # Get user input for the row and column to perform an action
        (line, column) = [(int(s) - 1) for s in input('Please enter a row and a column number: ').split()]
        
        # Check if the user input is within the valid range
        if line >= len(board) or column >= len(board[0]):
            print('Please enter a correct size!')
            continue
        
        # Get the value at the selected position on the board
        value = board[line][column]
        
        # Destroy adjacent neighbors and calculate the score
        new_score = destroy_neighbors(board, value, line, column)
        
        # Check if no movement happened, and update the board accordingly
        if value == new_score:
            board[line][column] = value
            print('No movement happened, try again')
        else:
            # Update the total score
            score += new_score
        
        # Rearrange the board after destroying adjacent neighbors
        rearrange_board(board)
    
    # Print the final state of the board and the total score
    print_board(board)
    print('Your score is: ' + str(score))
    print('Game over')
