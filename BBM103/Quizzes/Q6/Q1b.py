def print_tree(n:int):
    for i in range(1,n+1):
        for j in range(i):
            print('*', end='')
        print()

print_tree(8)