#include <iostream>
#include <vector>
#include <cmath>

using namespace std;

class VandermondeMatrix {
public:
    // Конструктор принимает вектор коэффициентов αi и размерность матрицы m x n
    VandermondeMatrix(const vector<double>& coefficients, size_t m, size_t n)
            : coefficients_(coefficients), m_(m), n_(n) {
        computeElements();
    }

    // Оператор () для доступа к элементам матрицы по индексам (i, j)
    double operator()(size_t i, size_t j) const {
        return elements_[i][j];
    }

    // Константный двунаправленный итератор для перебора всех элементов матрицы
    class ConstIterator {
    public:
        ConstIterator(const VandermondeMatrix& matrix, size_t row, size_t col)
                : matrix_(matrix), row_(row), col_(col) {}

        // Операторы сравнения для итераторов
        bool operator!=(const ConstIterator& other) const {
            return row_ != other.row_ || col_ != other.col_;
        }

        bool operator==(const ConstIterator& other) const {
            return row_ == other.row_ && col_ == other.col_;
        }

        // Оператор инкремента для перемещения итератора вперед
        ConstIterator& operator++() {
            if (col_ < matrix_.n_ - 1) {
                ++col_;
            } else {
                ++row_;
                col_ = 0;
            }
            return *this;
        }

        // Оператор декремента для перемещения итератора назад
        ConstIterator& operator--() {
            if (col_ > 0) {
                --col_;
            } else {
                --row_;
                col_ = matrix_.n_ - 1;
            }
            return *this;
        }

        // Оператор разыменования для получения текущего элемента матрицы
        double operator*() const {
            return matrix_(row_, col_);
        }

    private:
        const VandermondeMatrix& matrix_;
        size_t row_;
        size_t col_;
    };

    // Методы для получения начального итератора итератора и окончания матрицы
    ConstIterator begin() const {
        return ConstIterator(*this, 0, 0);
    }

    ConstIterator end() const {
        return ConstIterator(*this, m_, 0); // Не включая последний элемент
    }

private:
    vector<double> coefficients_;
    size_t m_;
    size_t n_;
    vector<vector<double>> elements_;

    // Метод для вычисления всех элементов матрицы Вандермонда
    void computeElements() {
        elements_.resize(m_, vector<double>(n_));
        for (size_t i = 0; i < m_; ++i) {
            for (size_t j = 0; j < n_; ++j) {
                elements_[i][j] = pow(coefficients_[i], j);
            }
        }
    }
};

int main() {
    vector<double> coefficients = {2, 3, 4}; // Пример коэффициентов αi
    size_t m = 3; // Количество строк
    size_t n = 4; // Количество столбцов

    VandermondeMatrix matrix(coefficients, m, n);

    // Итерирование по всем элементам матрицы с использованием итератора с начала
    for (auto it = matrix.begin(); it != matrix.end(); ++it) {
        cout << *it << " ";
    }

    cout << endl;

    // Итерирование по всем элементам матрицы с использованием итератора с конца
    for (auto it = --matrix.end(); it != --matrix.begin(); --it) {
        cout << *it << " ";
    }
    return 0;
}
