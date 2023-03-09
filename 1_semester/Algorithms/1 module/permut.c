#include <stdio.h>

int main() {
	long long int A[8], B[8], sumA = 0, sumB = 0;
	char equal = 0;
	
	for(int i = 0; i < 8; i++){
		scanf("%lld", &A[i]);
		sumA += A[i];
	} 
	for(int i = 0; i < 8; i++){
		scanf("%lld", &B[i]);
		sumB += B[i];
	}
	
	for(int i = 0; i < 8; i++){
		for(int j = 0; j < 8; j++){
			if(A[i] == B[j]){
				equal = 1;
				break;
			}
		}
		if(equal == 0) break;
	}
	
	(equal && sumA == sumB) ? printf("yes") : printf("no");
}
