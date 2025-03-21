import random

lowerBound = 1
upperBound = 25

correctAnswer = random.randint(lowerBound, upperBound)

print('Guess a number between ', str(lowerBound), ' and ' ,str(upperBound))

#initial guess
guess = int(input('Please enter a number: '))

while guess != correctAnswer:
    if guess > correctAnswer:
        guess = int(input('Decrease your number: '))
    #since 2 numbers can be equal or one is higher or lower than other and 
    #we handled equality case, no need to:
    #else if guess < correctAnswer
    else:
        guess = int(input('Increase your number: '))
        
print('You won!')