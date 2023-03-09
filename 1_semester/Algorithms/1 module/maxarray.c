#include <stdio.h>

int maxarray(void *base, size_t nel, size_t width,
			 int (*compare)(void *a, void *b)) {
	unsigned char *max = base;
	int ind = 0;

	for(int i = 0; i < nel; i++) {
		int flag = compare((((unsigned char*)base) + width*i), max);
		
		if(flag > 0) {
			max = (((unsigned char*)base) + width*i);
			ind = i;
		}
	}
	
	return ind;
}
