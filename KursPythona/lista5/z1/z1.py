import pandas as pd
import numpy as numpy
data = pd.read_csv('ratings.csv')
users = [row[0] for row in data[data['movieId'] == 1].to_numpy()]
m = 10


#X = []
#for user in users:
#    X.append(row)
m = 10
userrating = [row for row in data[['userId', 'movieId', 'rating']].to_numpy() if row[0] in users and row[1] < m]

print(users)

#X = [rating for i in range(m) for user in range(users)]