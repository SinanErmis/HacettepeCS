import math
b = float(input('Enter b: '))
c = float(input('Enter c: '))

d = (b**2) - (4*c)  
  
root1 = (-b-math.sqrt(d))/(2)  
root2 = (-b+math.sqrt(d))/(2)  

print('The roots are {0} and {1}'.format(root1,root2))