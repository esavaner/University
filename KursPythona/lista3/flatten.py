
def flatten(l):
    #dla kazdego elementu listy
    for item in l:

        #jesli element jest lista to dla kazdego jego podelementu ponownie wykonac flatten
        if isinstance(item, list):
            for nitem in flatten(item):
                yield nitem

        #w przeciwnym przypadku zwrocic go
        else:
            yield item

if __name__ == "__main__":
    l = [
        [1, 2, ["a", 4, "b", 5, 5, 5]], [4, 5, 6 ], 
        7, [[9, [123, [[123]]]], 10]
    ]
    print(list(flatten(l)))