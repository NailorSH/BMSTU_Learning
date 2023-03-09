#include <stdio.h>
#include <stdlib.h>

struct Task {
    int low, high;
};

struct Stack {
	size_t cap, top;
	struct Task *data;
};

void InitStack(struct Stack *s, size_t len){
	s->data = (struct Task *)malloc(len * sizeof(struct Task));
	s->cap = len;
	s->top = 0;
}

int StackEmpty(struct Stack *s) {
    return s->top == 0;
}

void stackPush(struct Stack *s, struct Task item) {
	if (s->top == s->cap) {
        printf("Stack overflow\n");
        return;
    }
    s->data[s->top] = item;
    s->top++;
}

struct Task stackPop(struct Stack *s) {
    s->top--;
    return s->data[s->top];
}

void swap(int *a, int i, int j) {
	int tmp = a[i];
	a[i] = a[j];
	a[j] = tmp;
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

void NonrecQuickSort(int *arr, int n) {
	struct Stack tasks;
	InitStack(&tasks, n);
	struct Task first_task, cur_task;
	first_task.low = 0;
	first_task.high = n - 1;
	
	stackPush(&tasks, first_task);
	
	while(!StackEmpty(&tasks)){
		cur_task = stackPop(&tasks);
		int low = cur_task.low;
		int high = cur_task.high;
		if (low < high){
			struct Task new_task;
			int q = Partition(arr, low, high);
			
			new_task.low = low;
            new_task.high = q - 1;
            stackPush(&tasks, new_task);
            
            new_task.low = q + 1;
            new_task.high = high;
            stackPush(&tasks, new_task);
		}
	}
	
	free(tasks.data);
}


int main() {
	int n;
	scanf("%d", &n);

	int array[n];
	for(int i = 0; i < n; i++) scanf("%d", &array[i]);

	NonrecQuickSort(array, n);

	for (int i = 0; i < n; i++) {
		printf("%d ", array[i]);
	}

	return 0;
}
