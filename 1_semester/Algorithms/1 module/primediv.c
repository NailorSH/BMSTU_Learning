#include <stdio.h>
#include <malloc.h>
#include <math.h>

int main() {
	long long x, t;
	scanf("%ld", &x);
	x = x < 0 ? -x : x;
	t = ceil(sqrt(x)+1);

	char *prime;
	prime = (char*)calloc(t, sizeof(char));
	prime[0] = 0;
	prime[1] = 0;
	prime[2] = 1;
	for(long i = 3; i < t; i++) {
		prime[i] = i%2;
	}

	for (long i=3; i < t; ++i) {
		if (prime[i]) {
			for (long j=i*i; j<t; j+=i)
				prime[j] = 0;
		}
	}

	for (long i=2; i < t; i++) {
		if (prime[i] != 0) {
			while (((x % i) == 0) && ((x / i) > 1)) {
				x /= i;
			}
		}
	}

	printf("%ld", x);
	free(prime);
}
