#include <stdio.h>
#include <stdlib.h>

struct Three {
	int index, k, v;
};

struct PriorityQueue {
	int cap, count;
	struct Three *heap;
};

void swapThrees(struct Three *a, struct Three *b) {
	struct Three tmp = *a;
	*a = *b;
	*b = tmp;
}

void InitThree(struct Three *th, int ind, int priority, int value) {
    th->index = ind;
    th->k = priority;
    th->v = value;
}

void InitPriorityQueue(struct PriorityQueue *q, int n) {
	q->heap = (struct Three*)malloc(n * sizeof(struct Three));
	q->cap = n;
	q->count = 0;
}

char QueueEmpty(struct PriorityQueue *q) {
	return q->count == 0;
}

void Insert(struct PriorityQueue *q, struct Three ptr) {
	int i = q->count;
	if(i == q->cap) {
		printf("Insert: overflow\n"); 
		return;
	}
	++q->count;
	q->heap[i] = ptr;
	while (i > 0 && q->heap[(i-1)/2].k < q->heap[i].k) {
		swapThrees(&q->heap[i], &q->heap[(i-1)/2]);
		i = (i-1)/2;
	}
}

void Heapify(struct Three *heap, int n, int i) {
	int l, r, j;
	
	while(1) {
		l = 2*i+1;
		r = l+1;
		j = i;
		if (l < n && heap[i].k < heap[l].k) i = l;
		if (r < n && heap[i].k < heap[r].k) i = r;
		if(i == j) break;
		
		swapThrees(&heap[i], &heap[j]);
	}
}

struct Three ExtractMax(struct PriorityQueue *q) {
	struct Three res = {0, 0, 0};
	if(QueueEmpty(q)) {	
		printf("ExtractMax: empty\n");
		return res;
	}
	
	res = q->heap[0];
	--q->count;
	if(q->count > 0) {
		q->heap[0] = q->heap[q->count];
		Heapify(q->heap, q->count, 0);
	}
	
	return res;
}

int main() {
	int k, res_size = 0;
	struct PriorityQueue q;
	struct Three helper;
	
	scanf("%d", &k);
	
	InitPriorityQueue(&q, k);
	int *sizes = malloc(k * sizeof(int));
	int *inds = malloc(k * sizeof(int));
	int **arrays = malloc(k * sizeof(int*));
	
	for(int i = 0; i < k; ++i) {
		scanf("%d", &sizes[i]);
		res_size += sizes[i];
		arrays[i] = malloc(sizes[i] * sizeof(int));
		inds[i] = 1;
	}

	for(int i = 0; i < k; ++i) {
		for(int j = 0; j < sizes[i]; ++j){
			scanf("%d", &arrays[i][j]);
		}
		InitThree(&helper, i, -arrays[i][0], arrays[i][0]);
		Insert(&q, helper);
	}
	
	for(int i = 0; i < res_size; ++i){
		helper = ExtractMax(&q);
		printf("%d ", helper.v);
		
		if(sizes[helper.index] > inds[helper.index]){
			helper.v = arrays[helper.index][inds[helper.index]];
			++inds[helper.index];
			
			helper.k = -helper.v;
			Insert(&q, helper);
		}
	}
	
	free(q.heap);
	
	free(sizes);
	for(int i = 0; i < k; ++i) {
		free(arrays[i]);
	}
	free(arrays);
	free(inds);
}
