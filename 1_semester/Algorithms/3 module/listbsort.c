#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define InitElem (Elem*)malloc(sizeof(Elem))

typedef struct Elem {
	struct Elem *next;
	char *word;
} Elem;

char **split_substrings(char *s, size_t *str_num) {
	char **substrings = NULL, *substr = NULL, c = 1;
	size_t num = 0, chars_num = 0;
	while(c != '\0') {
		c = *(s++);
		if(c != ' ' && c != '\0') {
			if(chars_num == 0) substr = (char *)malloc(sizeof(char));
			else substr = (char *)realloc(substr, sizeof(char) * (chars_num + 1));
			*(substr + chars_num) = c;
			chars_num++;
		} else if(chars_num != 0) {
			substr = (char *)realloc(substr, sizeof(char) * (chars_num + 1));
			substr[chars_num] = '\0';
			chars_num = 0;
			substrings = (char **)realloc(substrings, sizeof(char *) * (num + 1));
			*(substrings + num) = substr;
			num++;
		}
	}
	*str_num = num;
	return substrings;
}

void get_string(char *s) {
	char c;
	int i = 0;
	scanf("%c", &c);
	while (c != '\n' && c != EOF && c != '\0') {
		s[i] = c;
		scanf("%c", &c);
		i++;
	}
	s[i] = '\0';
}

void swap(Elem *i, Elem *j) {
	char *temp = i->word;
	i->word = j->word;
	j->word = temp;
}

Elem* InitSingleLinkedList() {
	Elem *l = InitElem;
	l->next = NULL;
	l->word = NULL;

	return l;
}

int ListEmpty(Elem *l) {
	return l == NULL;
}

size_t ListLength(Elem* l) {
	if (ListEmpty(l)) return 0;
	int len = 0;
	Elem *x = l;
	while (x->next != NULL) {
		++len;
		x = x->next;
	}
	return len;
}

void InsertAfter(Elem *x, Elem *y) {
	Elem *z = x->next;
	x->next = y;
	y->next = z;
}

Elem *bsort(Elem *list) {
	size_t n = ListLength(list);
	if (n < 2) return list;

	Elem *x;
	for(int i = 0; i < n; ++i) {
		x = list;

		while (!ListEmpty(x)) {
			if (x->word != NULL && x->next != NULL) {
				if (strlen(x->word) > strlen(x->next->word))
					swap(x, x->next);
			}
			x = x->next;
		}
	}

	return list;
}

int main() {
	char *str = (char *)malloc(1000 * sizeof(char)), *word;
	get_string(str);

	size_t words_num = 0;
	char **substrings = split_substrings(str, &words_num);

	Elem *list = InitSingleLinkedList(), *temp = list;
	for(int i = 0; i < words_num; ++i) {
		word = substrings[i];
		Elem *cur_elem = InitSingleLinkedList();
		cur_elem->word = word;
		temp->next = cur_elem;
		temp = cur_elem;
	}

	Elem *sorted_list = bsort(list);

	Elem *head = sorted_list;

	while (head) {
		if (head->word) printf("%s ", head->word);
		head = head->next;
	}

	free(str);
	for(int i = 0; i < words_num; ++i) free(substrings[i]);
	free(substrings);
	
	Elem *cur_elem, *next_elem = list;
	while (next_elem) {
		cur_elem = next_elem;
		next_elem = next_elem->next;
		free(cur_elem);
	}
	return 0;
}
