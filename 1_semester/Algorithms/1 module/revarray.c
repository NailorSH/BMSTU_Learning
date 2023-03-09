#include <stdio.h>
#include <stddef.h>

void revarray(void *base, size_t nel, size_t width) {
	char tmp = *(char*)base;

	for(int i = 0; i < nel/2; i++) {		
		for(int j = 0; j < width; j++) {
			tmp = *(((char*)base) + width*i + j);
			*(((char*)base) + width*i + j) = *(((char*)base) + width*((nel - 1) - i) + j);
			*(((char*)base) + width*((nel - 1) - i) + j) = tmp;
		}
	}
}

//test
int main(int argc, char **argv)
{
	int n;
	scanf("%d", &n);
    long long int array[n]; 
	for(int i = 0; i < n; i++) scanf("%lld", &array[i]);
    
    revarray(array, n, sizeof(long long int));

    int i;
    for (i = 0; i < n; i++) {
        printf("%lld ", array[i]);
    }

    return 0;
}

