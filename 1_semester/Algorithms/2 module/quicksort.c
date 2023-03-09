#include <stdio.h>
#include <stdlib.h>

void swap(int *a, int i, int j) {
	int tmp = a[i];
	a[i] = a[j];
	a[j] = tmp;
}

void SelectSort(int *arr, int low, int high) {
	for (int i = low; i <= high; i ++)
		for (int j = i + 1; j <= high; j++)
			if (arr[i] > arr[j]) swap(arr, i, j);
}

int Partition(int *arr, int low, int high) {
	int i = low, j = low;
	for(; j < high; j++) {
		if(arr[j] < arr[high]) {
			swap(arr, i, j);
			i++;
		}
	}
	swap(arr, i, high);
	return i;
}

void QuickSortRec(int *arr, int m, int low, int high) {
	while(low < high) {
		if(high-low+1 < m) {
			SelectSort(arr, low, high);
			break;
		}

		int q = Partition(arr, low, high);

		if (q - low < high - q) {
			QuickSortRec(arr, m, low, q-1);
			low = q + 1;
		} else {
			QuickSortRec(arr, m, q+1, high);
			high = q - 1;
		}
	}
}

void QuickSort(int *arr, int n, int m) {
	QuickSortRec(arr, m, 0, n-1);
}

int main() {

	int n, m;
	scanf("%d %d", &n, &m);

	int array[n];
	for(int i = 0; i < n; i++) scanf("%d", &array[i]);

	QuickSort(array, n, m);

	for (int i = 0; i < n; i++) {
		printf("%d ", array[i]);
	}

	return 0;
}
