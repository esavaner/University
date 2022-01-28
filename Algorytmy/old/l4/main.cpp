#include <iostream>
#include <string>
#include <cstdlib>
#include <ctime>
#include "bsttree.h"
#include "rbtree.h"
#include "splaytree.h"
#include <time.h>
#include <chrono>
#include <fstream>

using namespace std;
using namespace std::chrono;


void removeSpecial(string line) {
    for(int i = 0; i < line.size(); ++i) {
        if (!((line[i] >= 'a' && line[i]<='z') || (line[i] >= 'A' && line[i]<='Z'))) {
            line[i] = '\0';
        }
    }
}

int main() {
    string filename = "C:\\Users\\Filip\\Documents\\CLionProjects\\zad4\\";
    high_resolution_clock::time_point t1, t2;
    string type;
    cout << "Typ drzewa:" << endl;
    cin >> type;
    cout << "Instrukcje: " << endl;
    string s;
    string inst;
    int i = 0;
    if(type == "bst") {
        bsttree bst;
        node *root = nullptr;
        while(i++ < 1000) {
            cin >> inst >> s;
            removeSpecial(s);
            if(inst == "insert") {
                bst.insertBST(root, s);
            } else if (inst == "delete") {
                bst.deleteBST(root, s);
            } else if (inst == "search") {
                node *n = bst.searchBST(root, s);
                if (n->value == s)
                    cout << "1";
                else
                    cout << "0";
            } else if (inst == "load") {
                bst.load(root, filename + s);
            } else if (inst == "inorder") {
                bst.inorderBST(root);
            } else {
                cout << "Nie ma takiej instrujkcji" << endl;
            }
        }
    } else if (type == "splay") {
        splaytree splay;
        nodeS *rootS = nullptr;
        while(i++ < 1000) {
            cin >> inst >> s;
            removeSpecial(s);
            if(inst == "insert") {
                rootS = splay.insertSplay(rootS, s);
            } else if (inst == "delete") {
                rootS = splay.deleteSplay(rootS, s);
            } else if (inst == "search") {
                if(s == splay.search(rootS, s)->value)
                    cout << "1" << endl;
                else
                    cout << "0" << endl;
            } else if (inst == "load") {
                splay.load(rootS, filename + s);
            } else if (inst == "inorder") {
                cout << "Inorder: " << endl;
                splay.inorderSplay(rootS);
                cout << endl;
            } else {
                cout << "Nie ma takiej instrujkcji" << endl;
            }
        }
    } else if (type == "rbt") {
        rbtree rbt;
        nodeRB *rootRB = nullptr;
        while(i++ < 1000) {
            cin >> inst >> s;
            removeSpecial(s);
            if(inst == "insert") {
                rbt.insertRB(rootRB, s);
            } else if (inst == "delete") {
                rbt.deleteRB(rootRB, rbt.findRBT(rootRB, s));
            } else if (inst == "search") {
                rbt.search(rootRB, s);
            } else if (inst == "load") {
                rbt.load(rootRB, filename + s);
            } else if (inst == "inorder") {
                rbt.inorderRB(rootRB);
            } else {
                cout << "Nie ma takiej instrujkcji" << endl;
            }
        }
    } else if (type == "test") {
        ifstream file;

        bsttree bst;
        node *root = nullptr;


        t1 = high_resolution_clock::now();
        bst.load(root, filename + "aspell_wordlist.txt");
        t2 = high_resolution_clock::now();
        cout << "Bst load aspell = " << duration_cast<milliseconds>( t2 - t1 ).count() << endl;

        file.open(filename + "aspell_wordlist.txt");
        t1 = high_resolution_clock::now();
        string s1;
        while(file >> s1) {
            cout << s1;
            bst.searchBST(root, s1);
        }
        file.close();
        t2 = high_resolution_clock::now();
        cout << "Bst search aspell = " << duration_cast<milliseconds>( t2 - t1 ).count() << endl;



        t1 = high_resolution_clock::now();
        bst.load(root, filename + "lotr.txt");
        t2 = high_resolution_clock::now();
        cout << "Bst load lotr = " << duration_cast<milliseconds>( t2 - t1 ).count() << endl;

        file.open(filename + "lotr.txt");
        t1 = high_resolution_clock::now();
        while(file >> s) {
            bst.searchBST(root, s);
        }
        t2 = high_resolution_clock::now();
        file.close();
        cout << "Bst search aspell = " << duration_cast<milliseconds>( t2 - t1 ).count() << endl;



        t1 = high_resolution_clock::now();
        bst.load(root, filename + "aspell_wordlist.txt");
        bst.load(root, filename + "lotr.txt");
        t2 = high_resolution_clock::now();
        cout << "Bst load += " << duration_cast<milliseconds>( t2 - t1 ).count() << endl;

        file.open(filename + "aspell_wordlist.txt");
        t1 = high_resolution_clock::now();
        while(file >> s) {
            bst.searchBST(root, s);
        }
        file.close();
        file.open(filename + "lotr.txt");
        while(file >> s) {
            bst.searchBST(root, s);
        }
        t2 = high_resolution_clock::now();
        file.close();
        cout << "Bst search + = " << duration_cast<milliseconds>( t2 - t1 ).count() << endl;


    } else
        cout << "Nie ma takiego typu";
    return 0;
}