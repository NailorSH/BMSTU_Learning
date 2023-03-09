#include <stdio.h>
#include <stdlib.h>
#include <string.h>

struct Stack {
	size_t cap, top;
	long *data;
};

void InitStack(struct Stack *s, size_t len) {
	s->data = (long *)malloc(len * sizeof(long));
	s->cap = len;
	s->top = 0;
}

int StackEmpty(struct Stack *s) {
	return s->top == 0;
}

void stackPush(struct Stack *s, int item) {
	s->data[s->top] = item;
	s->top++;
}

long stackPop(struct Stack *s) {
	s->top--;
	return s->data[s->top];
}

void ADD(struct Stack *s) {
	long a = stackPop(s);
	long b = stackPop(s);
	stackPush(s, a + b);
}

void SUB(struct Stack *s) {
	long a = stackPop(s);
	long b = stackPop(s);
	stackPush(s, a - b);
}

void MUL(struct Stack *s) {
	long a = stackPop(s);
	long b = stackPop(s);
	stackPush(s, a * b);
}

void DIV(struct Stack *s) {
	long a = stackPop(s);
	long b = stackPop(s);
	stackPush(s, a / b);
}

void MAX(struct Stack *s) {
	long a = stackPop(s);
	long b = stackPop(s);
	stackPush(s, a > b ? a : b);
}

void MIN(struct Stack *s) {
	long a = stackPop(s);
	long b = stackPop(s);
	stackPush(s, a < b ? a : b);
}

void NEG(struct Stack *s) {
	long a = stackPop(s);
	stackPush(s, -a);
}

void DUP(struct Stack *s) {
	long a = stackPop(s);
	stackPush(s, a);
	stackPush(s, a);
}

void SWAP(struct Stack *s) {
	long a = stackPop(s);
	long b = stackPop(s);
	stackPush(s, a);
	stackPush(s, b);
}


int main() {
	struct Stack s;
	InitStack(&s, 100000);
	char command[5];
	char *st_commands[] = {"CONST", "ADD", "SUB", "MUL", "DIV", "MAX", "MIN", "NEG", "DUP", "SWAP"};

	while(1) {
		scanf("%s", command);

		if(strcmp(command, "END") == 0) {
			printf("%ld\n", stackPop(&s));
			break;
		}

		int com_key;
		for(int j = 0; j < 10; ++j) {
			if (strcmp(st_commands[j], command) == 0) com_key = j;
		}

		long x;

		switch (com_key) {
			case 0:
				scanf("%ld", &x);
				stackPush(&s, x);
				break;
			case 1:
				ADD(&s);
				break;
			case 2:
				SUB(&s);
				break;
			case 3:
				MUL(&s);
				break;
			case 4:
				DIV(&s);
				break;
			case 5:
				MAX(&s);
				break;
			case 6:
				MIN(&s);
				break;
			case 7:
				NEG(&s);
				break;
			case 8:
				DUP(&s);
				break;
			case 9:
				SWAP(&s);
				break;
		}
	}

	free(s.data);

	return 0;
}
