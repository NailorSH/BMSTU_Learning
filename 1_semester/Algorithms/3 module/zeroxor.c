#include <stdio.h>
#include <stdlib.h>

#define InitList (List *)malloc(sizeof(List))

typedef struct List {
	long value, key;
	struct List *next;
} List;

void InitHash(List **table, size_t n) {
	for (size_t i = 0; i < n; ++i) {
		List *l = InitList;
		l->key = 0, l->value = 0, l->next = NULL;
		table[i] = l;
	}
}

void Insert(List *table, long elem) {
	List* temp = InitList;
	temp->key = elem;
	temp->value = (elem == 0);
	temp->next = table->next;
	table->next = temp;
}

List* MapSearch(List **t, long k, size_t n) {
	long hash = abs(k) % n;
	List* result = t[hash];

	while (result != NULL && result->key != k)
		result = result->next;

	return result;
}

void destroy(List *list) {
	while (list != NULL) {
		List *tmp = list->next;
		free(list);
		list = tmp;
	}
}

size_t CountZeroXor(List **t, size_t n) {
	size_t result = 0;
	for (size_t i = 0; i < n; ++i) {
		List *list = t[i];
		while (list != NULL) {
			long count = list->value;
			result += count * (count + 1) / 2;
			list = list->next;
		}
		destroy(t[i]);
	}
	free(t);

	return result;
}

int main() {
	size_t n;
	scanf("%lu", &n);

	List **table = (List **)calloc(n, sizeof(List *)), *stock;
	InitHash(table, n);
	long mas[n];
	for(size_t i = 0; i < n; ++i) {
		long val;
		scanf("%ld", &val);

		if (i <= 0)  mas[i] = val;
		else mas[i] = mas[i-1]^val;

		stock = MapSearch(table, mas[i], n);
		if (stock == NULL) {
			long hash = abs(mas[i]) % n;
			Insert(table[hash], mas[i]);
		} else stock->value++;
	}

	printf("%lu", CountZeroXor(table, n));
	return 0;
}
