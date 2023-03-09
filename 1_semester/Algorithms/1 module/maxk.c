#include <stdio.h>

int main() {
	long long int size, k, locmaxk, maxk;

	scanf("%lld", &size);

	long long int mas[size];
	for(int i = 0; i < size; i++) {
		scanf("%lld", &mas[i]);
	}

	scanf("%lld", &k);

	maxk = -9223372036854775807;

	for(int i = 0; (i < size && (i + k - 1) < size); i++) {
		locmaxk = 0;
		for(int j = 0; j < k; j++) {
			locmaxk += mas[i+j];
		}

		if(locmaxk > maxk) maxk = locmaxk;
	}

	printf("%lld", maxk);
}
