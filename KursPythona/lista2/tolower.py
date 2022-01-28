import os

#domyslny start ustalony na ten folder
for root, dirs, files in os.walk(".", topdown=True):
    
    #dla kazdego pliku
    for name in files:
        #dodaje do sciezki plik i zmienia jego nazwe
        os.rename(os.path.join(root, name), os.path.join(root, name.lower()))

    #dla kazdego folderu
    for name in dirs:
        #dodaje do sciezki folder i zmienia jego nazwe
        os.rename(os.path.join(root, name), os.path.join(root, name.lower()))