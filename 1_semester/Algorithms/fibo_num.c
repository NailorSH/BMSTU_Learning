#include <stdio.h>

int main() {
	unsigned long long int x;
	scanf("%llu", &x);

		unsigned long long int fibo_numbers[100];
		fibo_numbers[0] = 1;
		fibo_numbers[1] = 1;

		for (unsigned long long int i = 2; i <= x; i++) {
			fibo_numbers[i] = fibo_numbers[i - 1] + fibo_numbers[i - 2];
		}
		printf("%llu", fibo_numbers[x]);
}
