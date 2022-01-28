#include <string>
#include <vector>
#include <iostream>
#include "exceptionhelper.h"

class LiczbyPierwsze {
    public:
	int i = 0, j = 0, d = 0, s = 0;
	std::vector<int> pierwsze;
	LiczbyPierwsze(int n);
	virtual int Liczba(int k);
};
class Test {
	static void main(std::vector<std::wstring> &args);
};
LiczbyPierwsze::LiczbyPierwsze(int n) {
	pierwsze = std::vector<int>(n);
	for (i = 2; i < n; i++) {
		d = 0;
		for (j = 1; j < i; j++) {
			if (i % j == 0) {
				d++;
			}
		}
		if (d == 1) {
			pierwsze[s] = i;
			s++;
		}
	}
}

int LiczbyPierwsze::Liczba(int k) {
	return pierwsze[k];
}

void Test::main(std::vector<std::wstring> &args) {
	int n = std::stoi(args[0]);
	if (n >= 0) {
		int k;
		LiczbyPierwsze *pierwsze = new LiczbyPierwsze(n);
		for (int i = 1; i < args.size(); i++) {
			try {
				k = std::stoi(args[i]);
				if (k < 0) {
					std::wcout << args[i] << (L"; Nie jest dodatnia") << std::endl;
				}
				else if (k >= pierwsze->s) {
					std::wcout << args[i] << (L"; Liczba spoza zakresu") << std::endl;
				}
				else {
					std::wcout << args[i] << (L"; ") << pierwsze->Liczba(k) << std::endl;
				}
			}
			catch (const NumberFormatException &ex) {
				std::wcout << args[i] << (L"; Nie jest liczba calkowita") << std::endl;
			}
		}
	}
	else if (n < 0) {
		try {
			n = std::stoi(args[0]);
			std::wcout << args[0] << (L"; Nie jest dodatnia") << std::endl;
			return;
		}
		catch (const NumberFormatException &ex) {
			std::wcout << args[0] << (L"; Nie jest liczba calkowita") << std::endl;
			return;
		}
	}
}
