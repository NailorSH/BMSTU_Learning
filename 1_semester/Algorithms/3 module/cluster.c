#include <stdio.h>
#include <stdlib.h>

typedef struct {
	int t1;
	int t2;
} Task;

typedef struct {
	int* heap;
	int cap, count;
} PriorityQueue;

void InitPriorityQueue(PriorityQueue *q, int n) {
	q->heap = (int *)malloc(n * sizeof(int));
	q->cap = n;
	q->count = 0;
}

char QueueEmpty(PriorityQueue *q) {
	return q->count == 0;
}

void swap(PriorityQueue *q, int i, int j) {
	int temp = q->heap[i];
	q->heap[i] = q->heap[j];
	q->heap[j] = temp;
}

void Insert(PriorityQueue *q, int ptr) {
	int i = q->count;
	if(i == q->cap) {
		printf("Insert: overflow\n");
		return;
	}
	++q->count;
	q->heap[i] = ptr;
	while (i > 0 && q->heap[(i-1)/2] > q->heap[i]) {
		swap(q, (i-1)/2, i);
		i = (i-1)/2;
	}
}

void Heapify(PriorityQueue* q, int n, int i) {
	int l, r, j;

	while(1) {
		l = 2*i+1;
		r = l+1;
		j = i;
		if (l < n && q->heap[i] > q->heap[l]) i = l;
		if (r < n && q->heap[i] > q->heap[r]) i = r;
		if(i == j) break;

		swap(q, i, j);
	}
}

int ExtractMin(PriorityQueue *q) {
	int res = q->heap[0];
	--q->count;
	if(q->count > 0) {
		q->heap[0] = q->heap[q->count];
		Heapify(q, q->count, 0);
	}

	return res;
}

int main() {
	int n, m;
	scanf("%d", &n);
	scanf("%d", &m);
	Task *cluster = (Task*)malloc(m * sizeof(Task));
	PriorityQueue queue;
	InitPriorityQueue(&queue, n);

	for (int i = 0; i < n; ++i) {
		scanf("%d %d", &cluster[i].t1, &cluster[i].t2);
		Insert(&queue, cluster[i].t1 + cluster[i].t2);
	}

	for (int i = n; i < m; ++i) {
		scanf("%d %d", &cluster[i].t1, &cluster[i].t2);
		int temp = ExtractMin(&queue);
		if (temp < cluster[i].t1) {
			Insert(&queue, cluster[i].t1 + cluster[i].t2);
		} else {
			Insert(&queue, temp + cluster[i].t2);
		}
	}

	//    The highest value in the priority queue is
	// the last of the smallest

	for (int i = 0; i < n-1; ++i) ExtractMin(&queue);

	printf("%d", ExtractMin(&queue));

	free(cluster);
	free(queue.heap);
	return 0;
}
