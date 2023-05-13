#include <iostream>
using namespace std;


template <int M, int N>
class Matrix {
private:
    int matrix[M][N];

public:
    Matrix() {
        for (int i = 0; i < M; ++i) {
            for (int j = 0; j < N; ++j) {
                matrix[i][j] = 0;
            }
        }
    }

    int* operator[](int index) {
        return matrix[index];
    }
    
    void print() {
        for (int i = 0; i < M; ++i) {
            for (int j = 0; j < N; ++j) {
                cout << matrix[i][j] << " ";
            }
            cout << '\n';
        }
    }
    
    template <int K>
    Matrix<M,K> operator*(Matrix<N,K> other) {
        Matrix<M,K> result;
        for (int i = 0; i < M; ++i) {
            for (int j = 0; j < K; ++j) {
                for (int k = 0; k < N; ++k) {
                    result[i][j] += matrix[i][k] * other[k][j];
                }
            }
        }
        return result;
    }

    void square() {
        *this = operator*(*this);
    }
    
};
