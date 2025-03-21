def create_num_to_stars_dictionary(n:int):
    dict = {i: ['*' for j in range(i)] for i in range(1, n + 1)} # Some basic python comprehension sorcery
    return dict

num_to_stars = create_num_to_stars_dictionary(8)
for key in num_to_stars.keys():
    print(str(num_to_stars[key])
          .removeprefix('[') # Apparently these 2 functions are way cheaper than replace 
          .removesuffix(']')
          .replace(',' , '')
          .replace('\'', '')
          .replace(' ', '')
          ) 