#include <stdio.h>
#include <stdlib.h>
#include <string.h>

long max(long a, long b) {
	return (a > b) ? a : b;
}

struct MaxElem {
	long val, max;
};

struct DoubleStack {
	size_t cap, top1, top2, max_top;
	struct MaxElem *data;
};

void InitDoubleStack(struct DoubleStack *s, size_t n) {
	s->data = (struct MaxElem *)malloc(n * sizeof(struct MaxElem));
	s->cap = n;
	s->top1 = 0;
	s->top2 = n-1;
}

int StackEmpty1(struct DoubleStack *s) {
	return s->top1 == 0;
}

int StackEmpty2(struct DoubleStack *s) {
	return s->top2 == s->cap-1;
}

void stackPush1(struct DoubleStack *s, long item) {
	s->data[s->top1].val = item;
	if (StackEmpty1(s)) s->data[s->top1].max = item;	
	else s->data[s->top1].max = max(s->data[s->top1-1].max, item);
	++s->top1;
}

void stackPush2(struct DoubleStack *s, long item) {
	s->data[s->top2].val = item;

	if (StackEmpty2(s)) s->data[s->top2].max = item;
	else s->data[s->top2].max = max(s->data[s->top2+1].max, item);

	--s->top2;
}

long stackPop1(struct DoubleStack *s) {
	return s->data[--s->top1].val;
}

long stackPop2(struct DoubleStack *s) {
	return s->data[++s->top2].val;
}

void InitQueue(struct DoubleStack *q, size_t n) {
	InitDoubleStack(q, n);
}

int QueueEmpty(struct DoubleStack *q) {
	return StackEmpty1(q) && StackEmpty2(q);
}

void Enqueue(struct DoubleStack *q, long item) {
	stackPush1(q, item);
}

long Dequeue(struct DoubleStack *q) {
	if(StackEmpty2(q)) {
		while(!StackEmpty1(q)) {
			stackPush2(q, stackPop1(q));
		}
	}
	long x = stackPop2(q);
	return x;
}

long Maximum(struct DoubleStack *q) {
	if(StackEmpty1(q)) {
		return q->data[q->top2+1].max;
	} else if (StackEmpty2(q)) {
		return q->data[q->top1-1].max;
	} else {
		return max(q->data[q->top1-1].max, q->data[q->top2+1].max);
	}
}

int main() {
	struct DoubleStack qmax;
	InitQueue(&qmax, 1000000);
	char *st_commands[] = {"ENQ", "DEQ", "EMPTY", "MAX"};
	char command[6];


	while(1) {
		scanf("%s", command);

		if(strcmp(command, "END") == 0) break;

		size_t com_key;
		for(int j = 0; j < 4; ++j) {
			if (strcmp(st_commands[j], command) == 0) com_key = j;
		}

		long x;

		switch (com_key) {
			case 0:
				scanf("%ld", &x);
				Enqueue(&qmax, x);
				break;
			case 1:
				printf("%ld\n", Dequeue(&qmax));
				break;
			case 2:
				printf("%s\n", QueueEmpty(&qmax) ? "true" : "false");
				break;
			case 3:
				printf("%ld\n", Maximum(&qmax));
		}
	}
	free(qmax.data);
}
