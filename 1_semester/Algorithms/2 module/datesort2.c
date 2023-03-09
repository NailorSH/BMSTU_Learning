#include <stdio.h>
#include <stdlib.h>

struct Date {
    int Day, Month, Year;
};

void datesort(struct Date *arr, int n){
	int counts_d[32] = {};
	int counts_m[13] = {};
	int counts_y[62] = {};
	struct Date buf[n];
	
	for (int j = 0; j < n; j++) {
        counts_d[arr[j].Day]++;
        counts_m[arr[j].Month]++;
        counts_y[arr[j].Year - 1970]++;
    }
    
    for (int i = 1; i < 32; i++) counts_d[i] += counts_d[i - 1];
    for (int i = 1; i < 13; i++) counts_m[i] += counts_m[i - 1];
    for (int i = 1; i < 62; i++) counts_y[i] += counts_y[i - 1];
    
    for (int j = n - 1; j >= 0; --j) {
        buf[--counts_d[arr[j].Day]] = arr[j];
    }
    for (int j = n - 1; j >= 0; --j) {
        buf[--counts_m[arr[j].Month]] = arr[j];
    }
    for (int j = n - 1; j >= 0; --j) {
        buf[--counts_y[arr[j].Year - 1970]] = arr[j];
    }
    
    for (int i = 0; i < n; i++)
        arr[i] = buf[i];
}

int main() {
	int n;
	scanf("%d", &n);
	
	struct Date dates[n];
	
	for(int i = 0; i < n; i++) 
		scanf("%d %d %d", &dates[i].Year, &dates[i].Month, &dates[i].Day);
	
	datesort(dates, n);
	
	for(int i = 0; i < n; i++) 
		printf("%04d %02d %02d\n", dates[i].Year, dates[i].Month, dates[i].Day);
}
