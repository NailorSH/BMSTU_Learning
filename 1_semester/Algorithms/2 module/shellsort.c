#include <stdlib.h>
#include <stdio.h>

void shellsort(unsigned long nel,
               int (*compare)(unsigned long i, unsigned long j),
               void (*swap)(unsigned long i, unsigned long j)) {
	if (nel < 2) return;
	unsigned long *fibo_numbers, last_el = 1;
	int ind = 0;
	fibo_numbers = (unsigned long*)malloc(2 * sizeof(unsigned long));
	fibo_numbers[0] = 1;
	fibo_numbers[1] = 1;

	for (unsigned long i = 2; last_el <= nel; i++) {
		if (fibo_numbers[i - 1] + fibo_numbers[i - 2] <= nel) {
			fibo_numbers = (unsigned long*)realloc(fibo_numbers, (i + 1) * sizeof(unsigned long));
			fibo_numbers[i] = fibo_numbers[i - 1] + fibo_numbers[i - 2];
			last_el = fibo_numbers[i];
			ind = i;
		} else break;
	}
	
	if(fibo_numbers[ind] >= nel) ind--;
	
	//shellsort
	for (int k = ind; k >= 0; k--) {
		int d = fibo_numbers[k];
		
		for (int i = 0; i < nel; i++)
			for (int loc = i - d; loc >= 0 && compare(loc, loc + d) == 1; loc -= d)
				swap(loc, loc + d);
	}
	
	free(fibo_numbers);
}

// for test

int *array;

int compare(unsigned long i, unsigned long j) {
	if (i <= j) {
		printf("COMPARE?%d?%d\n", i, j);
		printf("There are: %d %d\n", array[i], array[j]);
	} else {
		printf("COMPARE?%d?%d\n", j, i);
		printf("There are: %d %d\n", array[j], array[i]);
	}

	if (array[i] == array[j]) return 0;
	return array[i] < array[j] ? -1 : 1;
}

void swap(unsigned long i, unsigned long j) {
	if (i <= j) {
		printf("SWAP?%d?%d\n", i, j);
		printf("There are: %d %d\n", array[i], array[j]);
	} else {
		printf("SWAP?%d?%d\n", j, i);
		printf("There are: %d %d\n", array[j], array[i]);
	}

	int t = array[i];
	array[i] = array[j];
	array[j] = t;
}

void shellsort(unsigned long,
               int (*)(unsigned long, unsigned long),
               void (*)(unsigned long, unsigned long));

int main(int argc, char **argv) {
	int i, n;
	scanf("%d", &n);

	array = (int*)malloc(n * sizeof(int));
	for (i = 0; i < n; i++) scanf("%d", array+i);

	shellsort(n, compare, swap);
	for (i = 0; i < n; i++) printf("%d?", array[i]);
	printf("\n");

	free(array);
	return 0;
}
