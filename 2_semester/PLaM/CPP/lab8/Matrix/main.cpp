#include "Matrix.cpp"

#include <iostream>
using namespace std;

int main() {
    const int M = 3;
    const int N = 3;
    
    Matrix<M,N> matrix1;
    matrix1[0][0] = 1;
    matrix1[0][1] = 2;
    matrix1[0][2] = 3;
    matrix1[1][0] = 4;
    matrix1[1][1] = 5;
    matrix1[1][2] = 6;
    matrix1[2][0] = 7;
    matrix1[2][1] = 8;
    matrix1[2][2] = 9;

    if (M == N) {
    	cout << "Square of the matrix:\n";
        matrix1.square();
    } else {
    	cout << "The square() function is not available\n";
    	return 0;
    }
    
    matrix1.print();
    
    return 0;
}
