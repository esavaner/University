import numpy as np
from math import sqrt


#definicje funkcji sigmoid i relu

def sigmoid(x):
    return 1.0/(1 + np.exp(-x))

def sigmoid_derivative(x):
    return x*(1.0 - x)

def relu(x):
    return np.maximum(0, x)

def relu_derivative(x):
    return 1.*(x > 0)



class NeuralNetwork:
    def __init__(self, x, y, f1, f1d, f2, f2d):
        #tworzenie sieci 
        self.input = x
        self.weights1 = np.random.rand(4, self.input.shape[1])
        self.weights2 = np.random.rand(1, 4)
        self.y = y
        self.output = np.zeros(self.y.shape)
        self.eta = 0.5

        #zapisywanie funkcji i ich pochodnych
        self.f1 = f1
        self.f1_deriv = f1d
        self.f2 = f2
        self.f2_deriv = f2d



    def feedforward(self):
        #wyjscia pierwszej wartswy
        self.layer1 = self.f1(np.dot(self.input, self.weights1.T))

        #wyjscia drugiej wartswy
        self.output = self.f2(np.dot(self.layer1, self.weights2.T))



    def backprop(self):
        #obliczenie zamian polaczen miedzy jednostkami ukrytymi a wyjsciowymi
        delta2 = (self.y - self.output) *  self.f2_deriv(self.output)
        d_weights2 = self.eta * np.dot(delta2.T, self.layer1)

        #obliczenie zmian polaczen miedzy jednostaki wejsciowymi a ukrytymi
        delta1 = self.f1_deriv(self.layer1) * np.dot(delta2, self.weights2)
        d_weights1 = self.eta * np.dot(delta1.T, self.input)

        #dodawanie obliczonych zmian
        self.weights1 += d_weights1
        self.weights2 += d_weights2


    def propagate(self):
        for _ in range(5000):
            self.feedforward()
            self.backprop()


    #drukowanie wynikow
    #norma wektora wejsciowego y i wyjsciowego output
    def err(self):
        return sqrt(sum(i**2 for i in self.y - self.output))


#testowanie roznych kombinacji funkcji aktywacji
def compare(X, y):
    nn = NeuralNetwork(X, y, sigmoid, sigmoid_derivative, relu, relu_derivative)
    nn.propagate()
    print('sigmoid - relu', nn.err())

    
    nn = NeuralNetwork(X, y, relu, relu_derivative, sigmoid, sigmoid_derivative)
    nn.propagate()
    print('relu - sigmoid', nn.err())

    
    nn = NeuralNetwork(X, y, sigmoid, sigmoid_derivative, sigmoid, sigmoid_derivative)
    nn.propagate()
    print('sigmoid - sigmoid', nn.err())
    
    nn = NeuralNetwork(X, y, relu, relu_derivative, relu, relu_derivative)
    nn.propagate()
    print('relu - relu', nn.err())


def test():
    #ostatnia kolumna jedynek odpowiada za bias
    #sluzy do zmienienia minimalnej wartosci sum wag, przy ktorej neuron staje sie aktywny
    X = np.array([[0, 0, 1],
                  [0, 1, 1],
                  [1, 0, 1],
                  [1, 1, 1]])

    #testowanie dla operacji xor, and, or

    print('XOR')
    y = np.array([[0], [1], [1], [0]])
    compare(X, y)

    print('\nAND')
    y = np.array([[0], [0], [0], [1]])
    compare(X, y)

    print('\nOR')
    y = np.array([[0], [1], [1], [1]])
    compare(X, y)



if __name__ == "__main__":
    test()