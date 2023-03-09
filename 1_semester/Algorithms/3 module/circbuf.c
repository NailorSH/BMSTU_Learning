#include <stdio.h>
#include <stdlib.h>
#include <string.h>

struct Queue {
	size_t cap, count, head, tail;
	long *data;
};

void InitQueue(struct Queue *q, size_t len) {
	q->data = (long *)malloc(len * sizeof(long));
	q->cap = len;
	q->count = 0;
	q->head = 0;
	q->tail = 0;
}

int QueueEmpty(struct Queue *q) {
	return q->count == 0;
}

void Enqueue(struct Queue *q, long item) {
	if(q->tail == q->head && !QueueEmpty(q)) {

		//   divide the queue into two parts:
		// up to the read index ("head") and
		// from "head" to the end of the allocated size ("cap")
		int *new_part = (long*)malloc(q->head * sizeof(long));
		size_t len_old_part = q->cap - q->head;
		int *old_part = (long*)malloc(len_old_part * sizeof(long));

		// writing new arrays
		for(int i = 0; i < q->head; ++i) new_part[i] = q->data[i];
		for(int i = q->head; i < q->cap; ++i) old_part[i-q->head] = q->data[i];

		// "gluing" arrays
		for(int i = 0; i < len_old_part; ++i)
			q->data[i] = old_part[i];
		for(int i = len_old_part; i < q->cap; ++i)
			q->data[i] = new_part[i - len_old_part];

		q->head = 0;
		q->tail = q->cap;
		free(new_part);
		free(old_part);
	}

	if (q->count == q->cap) {
		q->cap *= 2;
		q->data = realloc(q->data, q->cap * sizeof(long));
	}
	if (q->tail == q->cap) q->tail = 0;

	q->data[q->tail] = item;
	++q->tail;
	++q->count;
}

long Dequeue(struct Queue *q) {
	long x = q->data[q->head++];
	if (q->head == q->cap) q->head = 0;
	--q->count;
	return x;
}

int main() {

	int n;
	scanf("%d", &n);

	struct Queue circbuff;
	InitQueue(&circbuff, 4);
	char command[6];
	char *st_commands[] = {"ENQ", "DEQ", "EMPTY"};

	while(1) {
		scanf("%s", command);

		if(strcmp(command, "END") == 0) break;

		size_t com_key;
		for(int j = 0; j < 3; ++j) {
			if (strcmp(st_commands[j], command) == 0) com_key = j;
		}

		long x;

		switch (com_key) {
			case 0:
				scanf("%ld", &x);
				Enqueue(&circbuff, x);
				break;
			case 1:
				printf("%ld\n", Dequeue(&circbuff));
				break;
			case 2:
				printf("%s\n", QueueEmpty(&circbuff) ? "true" : "false");
				break;
		}
	}

	free(circbuff.data);
}


// FOR TESTING
//	char *st_commands[] = {"ENQ", "DEQ", "EMPTY", "HEAD", "TAIL", "COUNT", "CAP"};
//			case 3:
//				printf("%lu\n", circbuff.head);
//				break;
//			case 4:
//				printf("%lu\n", circbuff.tail);
//				break;
//			case 5:
//				printf("%lu\n", circbuff.count);
//				break;
//			case 6:
//				printf("%lu\n", circbuff.cap);
//				break;
