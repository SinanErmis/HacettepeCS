#include "Queue.h"
#include <iostream>

// Constructor to initialize an empty queue
Queue::Queue() {
    front = 0;
    rear = 0;
}

// Adds a province to the end of the queue
void Queue::enqueue(int province) {
    if(isFull()){
        std::cerr << "Queue is full!";
        return;
    }
    data[rear] = province;
    rear = (rear + 1) % MAX_QUEUE_SIZE; // Circular increment
}

// Removes and returns the front province from the queue
int Queue::dequeue() {
    if(isEmpty()){
      std::cerr<<"Queue is empty!";
      return -1;
    }
    int value = data[front];
    front = (front + 1) % MAX_QUEUE_SIZE; // Circular increment
    return value;
}

// Returns the front province without removing it
int Queue::peek() const {
    if(isEmpty()){
        std::cerr<<"Queue is empty!";
        return -1;
    }
    return data[front];
}

// Checks if the queue is empty
bool Queue::isEmpty() const {
    return rear == front;
}

// Checks if the queue is empty
bool Queue::isFull() const {
    return ((rear + 1) % MAX_QUEUE_SIZE) == front;
}

// Add a priority neighboring province in a way that will be dequeued and explored before other non-priority neighbors
void Queue::enqueuePriority(int province) {
    if(isFull()){
      std::cerr << "Queue is full!";
      return;
    }
    front--;
    if(front < 0){
      front += MAX_QUEUE_SIZE;
    }
    data[front] = province;
}

