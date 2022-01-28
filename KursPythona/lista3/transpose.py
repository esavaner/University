
def transpose(m):
    #rozdziela elementy dla kazdej spacji
    #laczy kazdy j-ta pozycje i-tego elementu listy
    return [
        ' '.join(
            [r.split(' ') for r in m][i][j] for i in range(len(m))
        ) for j in range(len(m))
    ]

if __name__ == "__main__":
    print(transpose(['1.1 2.2 3.3', '4.4 5.5 6.6', '7.7 8.8 9.9']))