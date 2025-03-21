N = int(input('Write a number N:'))

sumOfOdds = 0
for odd in range(1,N,2):
    sumOfOdds += odd

sumOfEvens = 0
evenCount = 0
for even in range(2,N+1,2):
    sumOfEvens += even
    evenCount+=1

averageOfEvens = sumOfEvens / evenCount

print('Sum of odds: ', str(sumOfOdds))
print('Average of evens: ', str(averageOfEvens))