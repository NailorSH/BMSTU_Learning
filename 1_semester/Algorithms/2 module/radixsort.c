#include <stdio.h>

union Int32 {
	int x;
	unsigned char bytes[4];
};

int get_key(union Int32 num, int k) {
    if (k == 3)
        if (num.x >= 0)
            return (int)num.bytes[k] + 128;
        else
            return (int)num.bytes[k] - 128;
    return num.bytes[k];
}

void DistributionSort(int key, union Int32 *arr, int n){
	int m = 256;
	int count[256] = {};
	
	int k;
    for (int j = 0; j < n; j++) {
        k = get_key(arr[j], key);
		count[k]++;
    }
    for (int i = 1; i < m; i++)
        count[i] += count[i - 1];
        
    union Int32 buf[n];
    
    for(int j = n - 1; j >= 0; j--) {
    	k = get_key(arr[j], key);
        buf[--count[k]] = arr[j];
    }
    for (int i = 0; i < n; i++) arr[i] = buf[i];
}

void RadixSort(union Int32 *arr, int n){
	for(int i = 0; i < 4; i++) {
        DistributionSort(i, arr, n);
    }
}

int main() {
	int n;
	scanf("%d", &n);
	
	union Int32 numbers[n];
	
	for(int i = 0; i < n; i++) scanf("%d", &numbers[i].x);
		
	RadixSort(numbers, n);
	
	for(int i = 0; i < n; i++) printf("%d ", numbers[i].x);
	
	return 0;
}

