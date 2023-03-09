#include <stdio.h>

int main() {
	unsigned long long int x, ind;
	scanf("%llu", &x);

		unsigned long long int fibo_numbers[100];
		fibo_numbers[0] = 1;
		fibo_numbers[1] = 1;

		for (unsigned long long int i = 2; fibo_numbers[i-1] <= x; i++) {
			if (fibo_numbers[i - 1] + fibo_numbers[i - 2] <= x) {
				fibo_numbers[i] = fibo_numbers[i - 1] + fibo_numbers[i - 2];
				ind = i;
			} else break;
		}
		printf("%llu", ind);
}
