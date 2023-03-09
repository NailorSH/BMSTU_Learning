#include <stdio.h>
#include <stdlib.h>

#define InitElem (struct Elem*)malloc(sizeof(struct Elem))

struct Elem {
	struct Elem *prev, *next;
	int v;
};

struct Elem* InitDoubleLinkedList() {
	struct Elem *l = InitElem;
	l->prev = l;
	l->next = l;
}

int ListEmpty(struct Elem *l) {
	return l->next == l;
}

void InsertAfter(struct Elem *x, struct Elem *y) {
	struct Elem *z = x->next;
	x->next = y;
	y->prev = x;
	y->next = z;
	z->prev = y;
}

void Delete(struct Elem *x) {
	struct Elem *y = x->prev;
	struct Elem *z = x->next;
	y->next = z;
	z->prev = y;

	x->prev = NULL;
	x->next = NULL;
}

void InsertSort(struct Elem *root) {
	struct Elem *elem = root->next, *loc;

	while(elem != root) {
		loc = elem->prev;

		while (loc != root && loc->v > elem->v) {
			loc = loc->prev;
		}

		Delete(elem);
		InsertAfter(loc, elem);

		elem = elem->next;
	}
}


int main() {
	int n;
	scanf("%d", &n);

	struct Elem *list = InitDoubleLinkedList();
	struct Elem *x;

	for(int i = 0; i < n; ++i) {
		x = InitElem;
		scanf("%d", &(x->v));
		InsertAfter(list, x);
	}

	InsertSort(list);
	x = list->next;

	while(x != list) {
		printf("%d ", x->v);
		
		struct Elem *temp = x; 
		x = x->next;
		free(temp);	
	}

	free(list);

	return 0;
}
