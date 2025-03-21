#include <iostream>
#include "swapValues.h"
#include "reverseArray.h"
#include "minMaxArray.h"
#include "dynamic2DArray.h"
#include "swapRows.h"

int main() {
    // Task 1: Reverse Array
    int arr[] = {1, 2, 3, 4, 5};
    int size = sizeof(arr) / sizeof(arr[0]);
    reverseArray(arr, size);
    std::cout << "Reversed array: ";
    for (int i = 0; i < size; i++) {
        std::cout << arr[i] << " ";
    }
    std::cout << std::endl;

    // Task 2: Find Min and Max
    int min, max;
    findMinMax(arr, size, &min, &max);
    std::cout << "Min: " << min << ", Max: " << max << std::endl;

    // Task 3: Dynamic 2D Array Creation and Deletion
    int rows = 3, cols = 4;
    int** arr2D = create2DArray(rows, cols);
    std::cout << "Dynamic 2D array:" << std::endl;
    for (int i = 0; i < rows; i++) {
        for (int j = 0; j < cols; j++) {
            std::cout << arr2D[i][j] << " ";
        }
        std::cout << std::endl;
    }
    delete2DArray(arr2D, rows);

    // Task 4: Swap Rows in 2D Array
    arr2D = create2DArray(rows, cols);
    std::cout << "2D array before swapping rows:" << std::endl;
    for (int i = 0; i < rows; i++) {
        for (int j = 0; j < cols; j++) {
            std::cout << arr2D[i][j] << " ";
        }
        std::cout << std::endl;
    }
    swapRows(arr2D, 0, 2, cols);
    std::cout << "2D array after swapping row 0 and row 2:" << std::endl;
    for (int i = 0; i < rows; i++) {
        for (int j = 0; j < cols; j++) {
            std::cout << arr2D[i][j] << " ";
        }
        std::cout << std::endl;
    }
    delete2DArray(arr2D, rows);

    // Task 5: swapValues
    int a = 10, b = 20;
    swapValues(&a, &b);
    std::cout << "swapValues result: a = " << a << ", b = " << b << std::endl;

    return 0;
}