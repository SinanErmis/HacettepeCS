import sys

def insertion_sort(lst):
    # Check if the list is already sorted
    if len(lst) == 0 or lst == sorted(lst):
        output_file_insertion.write("Already sorted!\n")
        return
    
    n = len(lst)
    step_number = 1
    for i in range(1, n):
        made_a_change = False
        key = lst[i]
        j = i - 1
        # Move elements greater than key to one position ahead
        while j >= 0 and key < lst[j]:
            lst[j + 1] = lst[j]
            j -= 1
            made_a_change = True
        # Insert key into the correct position
        if made_a_change:
            lst[j + 1] = key
            output_file_insertion.write(f"Pass {step_number}: {' '.join(map(str, lst))}\n")
            step_number += 1
    
    # Delete the last character (newline) from the file to exactly match sample output
    output_file_insertion.seek(0, 2)
    output_file_insertion.truncate(output_file_insertion.tell() - 2)

def bubble_sort(lst):
    # Check if the list is already sorted
    if len(lst) == 0 or lst == sorted(lst):
        output_file_bubble.write("Already sorted!\n")
        return
    
    step_number = 1
    n = len(lst)

    for i in range(n):
        made_a_change = False
        # Traverse through the list and swap adjacent elements if they are in the wrong order
        for j in range(0, n - i - 1):
            if lst[j] > lst[j + 1]:
                lst[j], lst[j + 1] = lst[j + 1], lst[j]
                made_a_change = True
        # Write the current pass to the output file
        if made_a_change:
            output_file_bubble.write(f"Pass {step_number}: {' '.join(map(str, lst))}\n")
            step_number += 1
    # Delete the last character (newline) from the file to exactly match sample output
    output_file_bubble.seek(0, 2)
    output_file_bubble.truncate(output_file_bubble.tell() - 2)
def read_input_from_file(file_name):
    try:
        with open(file_name, 'r') as file:
            input_list = list(map(int, file.read().split()))
        return input_list
    except FileNotFoundError:
        print(f"Error: File '{file_name}' not found.")
        sys.exit(1)

if __name__ == "__main__":
    # Check if the correct number of command line arguments is provided
    if len(sys.argv) != 4:
        print("Usage: python3 sort.py input.txt output_bubble.txt output_insertion.txt")
        sys.exit(1)

    # Get input and output file names from command line arguments
    input_file = sys.argv[1]
    output_file_bubble_name = sys.argv[2]
    output_file_insertion_name = sys.argv[3]

    # Read input list from the file
    input_list = read_input_from_file(input_file)

    # Open output files for writing
    output_file_bubble = open(output_file_bubble_name, 'w')
    output_file_insertion = open(output_file_insertion_name, 'w')

    # Perform bubble sort and write the steps to the output file
    bubble_sort(input_list.copy())

    # Perform insertion sort and write the steps to the output file
    insertion_sort(input_list.copy())

    # Close the output files
    output_file_bubble.close()
    output_file_insertion.close()
