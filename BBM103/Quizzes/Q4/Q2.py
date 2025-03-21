mailInput = input('Enter your e-mail address:')

def check(mailAddress):
    containsAt = False
    containsFullstop = False
    for character in mailAddress:
        if character == '@':
            containsAt = True
        if character == '.':
            containsFullstop = True
    return containsAt and containsFullstop
    

if(check(mailInput)):
    print('This is a valid e-mail address')
else:
    print('This is not a valid e-mail address')