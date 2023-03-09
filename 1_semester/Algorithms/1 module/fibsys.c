#include <stdio.h>
#include <malloc.h>

int main() {
	unsigned long long int x, *fibo_numbers, last_el = 1;
	int ind = 0;
	scanf("%llu", &x);

	if (x == 0) printf("0");
	else {
		fibo_numbers = (long long int*)malloc(2 * sizeof(long long int));
		fibo_numbers[0] = 1;
		fibo_numbers[1] = 1;

		for (unsigned long long int i = 2; last_el <= x; i++) {
			if (fibo_numbers[i - 1] + fibo_numbers[i - 2] <= x) {
				fibo_numbers = (long long int*)realloc(fibo_numbers, (i + 1) * sizeof(long long int));
				fibo_numbers[i] = fibo_numbers[i - 1] + fibo_numbers[i - 2];
				last_el = fibo_numbers[i];
				ind = i;
			} else break;
		}

		int pr = ind, p = ind-1;

		x -= fibo_numbers[ind];
		printf("1");

		while (x >= 0) {
			if (p <= 0) break;
			else if (fibo_numbers[p] <= x && pr-1 > p) {
				x -= fibo_numbers[p];
				printf("1");
				pr = p;
			} else {
				printf("0");
			}
			p--;
		}
		free(fibo_numbers);
	}
}
