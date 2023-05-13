#include "FractionSequence.cpp"
#include <iostream>

using namespace std;

int main() {
    FractionSequence sequence({
        Fraction(1, 2),
        Fraction(1, 3),
    });
    sequence.add(3, 4);

    for (int i = 0; i < 3; i++){
        cout << sequence[i] << "  ";
    }
    cout << endl;


    for (auto it = sequence.begin(); it != sequence.end(); it++) {
        cout << *it << "  ";
    }
    cout << endl;


    for (auto it = sequence.begin(); it != sequence.end(); it++){
        it += {1, 3};
        cout << *it << "  ";
    }
    cout << endl;


    for (int i = 0; i < 3; i++) {
        cout << sequence[i] << "  ";
    }

    return 0;
}


