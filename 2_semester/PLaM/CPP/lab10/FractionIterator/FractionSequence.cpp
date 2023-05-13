#include <iostream>
#include <utility>
#include <vector>

using namespace std;

class Fraction {
private:
    int numerator, denominator;

    void simplify() {
        int t = GCD(numerator, denominator);
        this->numerator = numerator / t;
        this->denominator = denominator / t;
    };
public:
    Fraction() : numerator(1), denominator(1) {};

    Fraction(int numerator, int denominator) : numerator(numerator), denominator(denominator) {
        simplify();
    }

    static int GCD(int a, int b) {
        return b ? GCD(b, a % b) : a;
    }

    static int LCM(int a, int b) {
        return a / GCD(a, b) * b;
    }

    friend Fraction sum(Fraction a, Fraction b) {
        int numerator1 = a.numerator, denominator1 = a.denominator;
        int numerator2 = b.numerator, denominator2 = b.denominator;
        int lcm = LCM(denominator1, denominator2);
        numerator1 *= lcm / denominator1;
        numerator2 *= lcm / denominator2;
        int x = numerator1 + numerator2;
        return {x, lcm};
    }

    friend Fraction operator+(const Fraction &a, const Fraction &b) {
        return sum(a, b);
    }

    friend ostream &operator<<(ostream &output, const Fraction &frac) {
        output << frac.numerator << "/" << frac.denominator;
        return output;
    }

    friend istream &operator>>(istream &in, Fraction &frac) {
        in >> frac.numerator >> frac.denominator;
        frac.simplify();
        return in;
    }
};

class FractionSequence {
private:
    vector<Fraction> sequence;

    class Iterator {
        vector<Fraction>::iterator cur;
    public:
        Iterator(vector<Fraction>::iterator first) : cur(first) {}

        Iterator operator++(int) {
            Iterator temp = *this;
            cur++;
            return temp;
        }

        bool operator!=(const Iterator &it) {
            return cur != it.cur;
        }

        bool operator==(const Iterator &it) {
            return cur == it.cur;
        }

        Iterator operator+=(Fraction x) {
            *cur = *cur + x;
            return *this;
        }

        Fraction operator*() {
            return *cur + *(cur + 1);
        }
    };

public:
    explicit FractionSequence(vector<Fraction> s) : sequence(std::move(s)) {}

    void add(int numerator, int denominator) {
        sequence.emplace_back(numerator, denominator);
    }

    Fraction &operator[](int index) {
        return sequence[index];
    }

    Iterator begin() {
        return {sequence.begin()};
    }

    Iterator end() {
        return {sequence.end() - 1};
    }
};