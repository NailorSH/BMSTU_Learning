#include <stdio.h>
#include <stdlib.h>

void InsertionSort(int *arr, int low, int high){
	int elem, loc;
	for(int i = low; i < high+1; i++) {
		elem = arr[i];
		loc = i - 1;
		for(; loc >= 0 && abs(arr[loc]) > abs(elem); loc--)
			arr[loc+1] = arr[loc];
		arr[loc+1] = elem;
	}
}

void Merge(int *arr, int k, int l, int m) {
	int t[m-k+1];
	int i = k;
	int j = l + 1;
	int h = 0;
	while(h < m-k+1){
		if(j <= m && (i == l+1 || abs(arr[j]) < abs(arr[i]) )){
			t[h] = arr[j];
			j++;
		} else {
			t[h] = arr[i];
			i++;
		}
		h++;
	}
	for(int i = 0; i < h; i++) 
		arr[i+k] = t[i];
}

void MergeSortRec(int *arr, int low, int high){
	if(high-low+1 < 5){
		InsertionSort(arr, low, high);
		return;
	}
	
	if(low < high){
		int med = (low + high) / 2;
		MergeSortRec(arr, low, med);
		MergeSortRec(arr, med+1, high);
		Merge(arr, low, med, high);
	}
}

void MergeSort(int *arr, int n){
	MergeSortRec(arr, 0, n-1);
}

int main() {
	int n;
	scanf("%d", &n);
	
    int array[n]; 
	for(int i = 0; i < n; i++) scanf("%d", &array[i]);
    
    MergeSort(array, n);

    for (int i = 0; i < n; i++) {
        printf("%d ", array[i]);
    }

    return 0;
}

