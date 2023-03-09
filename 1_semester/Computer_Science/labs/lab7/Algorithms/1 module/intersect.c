#include <stdio.h>

int main() {
	long int A = 0, B = 0;
	int nA, nB, el;
	
	scanf("%d", &nA);	
	for(int i = 0; i < nA; i++){
		scanf("%d", &el);
		A |= (1 << el);
	}
	
	scanf("%d", &nB);
	for(int i = 0; i < nB; i++){
		scanf("%d", &el);
		B |= (1 << el);
	}

	/*unsigned long n = (sizeof(long int)) * 8;
	while (n){
        printf("%ld", ( A >> --n ) & 1);
	}
	printf("\n");
	n = (sizeof(long int)) * 8;
	while (n){
        printf("%ld", ( B >> --n ) & 1);
	}
	
	printf("\n");*/
	
	for(int i = 0; i < 32; i++){
		if(((A >> i) & 1) && ((B >> i) & 1)){
			printf("%d ", i);
		}
	}
}
