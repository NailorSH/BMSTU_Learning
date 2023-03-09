//#include <stdio.h>

// (val & (val - 1)) == 0   является ли число степенью двойки


//void count_pow2(int tabs, int *counter, long *arr, int n, long sum, int k){
//	for(int j = 0; j < tabs; j++) printf("\t");
//	printf("CONNECT\n");
//	for(int j = 0; j < tabs; j++) printf("\t");
//    printf("sum = %d\n", sum);
//    for(int j = 0; j < tabs; j++) printf("\t");
//    printf("k = %d\n", k);
//	if(!(sum & (sum - 1)) && sum > 0) (*counter)++;
//	for(int j = 0; j < tabs; j++) printf("\t");
//	printf("counter = %d\n", *counter);
//	if(k >= n) {
//		for(int j = 0; j < tabs; j++) printf("\t");
//		printf("RETURN\n");
//		tabs--;
//		return;
//	}
//
//	for(int i = k; i < n; i++){
//		for(int j = 0; j < tabs; j++) printf("\t");
//		printf("i = %d\n\n", i);
//
//		count_pow2(i+1, counter, arr, n, sum + arr[i], i+1);
//	}
//}

#include <stdio.h>

long check_power2(long item) {
	return !(item & (item - 1));
}

void count_pow2(long (*checker)(long item), int *counter, long *arr, int n, long sum, int k) {
	if(checker(sum) && sum > 0) (*counter)++;
	if(k >= n) {
		return;
	}
	for(int i = k; i < n; i++) {
		count_pow2(checker, counter, arr, n, sum + arr[i], i+1);
	}
}

int main() {
	int n, counter = 0;
	scanf("%d", &n);
	long arr[n];
	for(int i = 0; i < n; i++) scanf("%ld", &arr[i]);
	count_pow2(check_power2, &counter, arr, n, 0, 0);

	printf("%d", counter);
	return 0;
}
