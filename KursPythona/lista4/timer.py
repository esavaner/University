import time

#definicja dekoratora
def measure(f):
    def inner():
        
        #oznacza aktualny czas jako start
        start = time.time()

        #wywoluje funckje f
        f()

        #oznacza czas po funkcji jako end
        end = time.time()

        print("Function time:", end - start)

    return inner


#definicja funkcji wraz z dekoratorem
@measure
def test():
    total = sum(i for i in range(10000))
    print(total)


test()