#include <stdio.h>

int main() {
    long long int n, x0, P, dP, i;
    scanf("%lld", &n);
    scanf("%lld", &x0);
    
    long long int a[n + 1];

    for (i = n; i >= 0; i--){
        scanf("%lld", &a[i]);
    }

	P = a[n]*x0 + a[n-1];
	for (i = (n - 2); i >= 0; i -= 1) {
        P = P*x0 + a[i];
	}
	printf("%lld ", P);
//    printf("P(x0) = %lld \n", P);

    dP = n*a[n]*x0 + (n-1)*a[n-1];
    for (i = (n - 2); i > 0; i -= 1) {
        dP = dP*x0 + a[i]*i;
	}
	printf("%lld", dP);
//    printf("dP(x0) = %lld ", dP);

    return 0;
}
