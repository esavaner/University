
#czytanie pliku i zapisywanie go jako ciag 0 i 1
def read(infile):
    with open(infile, 'rb') as f:
        data = f.read()
    result = ''
    for d in data:
        result += '{0:08b}'.format(d)
    return result


#zapisywanie do pliku
def write(outfile, data):
    with open(outfile, 'wb') as f:
        f.write(data)
