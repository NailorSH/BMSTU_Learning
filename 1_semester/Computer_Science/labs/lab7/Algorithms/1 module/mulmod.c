#include <stdio.h>

int main() {
	unsigned long long int a, b, m, masbin[64], binar_b[64], result = 0;
	scanf("%llu", &a);
	scanf("%llu", &b);
	scanf("%llu", &m);
/*	printf("a = %llu\n", a);
	printf("b = %llu\n", b);
	printf("m = %llu\n", m);*/
	
	unsigned long n = sizeof(long long) * 8;
    while (n) {
        binar_b[n] = (b >> --n) & 1;
	}
	
	result = ((a%m)*(binar_b[63]%m))%m + ((a%m)*(binar_b[62]%m))%m;
	for (int i = 61; i >= 0; i--) {
        result = (result%m*2%m + (a%m)*(binar_b[i]%m)%m)%m;
	}
	result %= m;
	printf("%llu", result);
//    printf("(a*b) mod m = %llu \n", result);
}
/*
	unsigned long long bi = b;
	
	for(int i = 0; i < 64; i++) {
		binar_b[i]= bi%2;
		bi/=2;
	}
*/
