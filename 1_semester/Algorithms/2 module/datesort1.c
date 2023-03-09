#include <stdio.h>
#include <stdlib.h>

struct Date {
    int Day, Month, Year;
};

void DaySort(struct Date *arr, int n){
	int m = 32;
	int count[32] = {};
    for (int j = 0; j < n; j++) {
        count[arr[j].Day]++;
    }
    for (int i = 1; i < m; i++)
        count[i] += count[i - 1];
        
    struct Date buf[n];
    for (int j = n - 1; j >= 0; --j) {
        buf[--count[arr[j].Day]] = arr[j];
    }
    for (int i = 0; i < n; i++)
        arr[i] = buf[i];
}

void MonthSort(struct Date *arr, int n){
	int m = 13;
	int count[13] = {};
    for (int j = 0; j < n; j++) {
        count[arr[j].Month]++;
    }
    for (int i = 1; i < m; i++)
        count[i] += count[i - 1];
        
    struct Date buf[n];
    for (int j = n - 1; j >= 0; --j) {
        buf[--count[arr[j].Month]] = arr[j];
    }
    for (int i = 0; i < n; i++)
        arr[i] = buf[i];
}
void YearSort(struct Date *arr, int n){
	int m = 62;
	int count[62] = {};
    for (int j = 0; j < n; j++) {
        count[arr[j].Year - 1970]++;
    }
    for (int i = 1; i < m; i++)
        count[i] += count[i - 1];
        
    struct Date buf[n];
    for (int j = n - 1; j >= 0; --j) {
        buf[--count[arr[j].Year - 1970]] = arr[j];
    }
    for (int i = 0; i < n; i++)
        arr[i] = buf[i];
}

void datesort(struct Date *mas, int num){
	DaySort(mas, num);
	MonthSort(mas, num);
	YearSort(mas, num);
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
