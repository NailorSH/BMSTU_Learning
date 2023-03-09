#include <stdio.h>
#include <string.h>
#include <stdlib.h>

void find_prefix(int n, const char* str, int* prefixes) {
	prefixes[0] = 0;
	for (int i = 1, j = 0; i < n; i++) {
		while (j > 0 && str[i] != str[j])
			j = prefixes[j - 1];
		if (str[i] == str[j])
			j++;
		prefixes[i] = j;
	}
}

void pword(char *s, int len_s, char *t, int len_t) {
	int *s_prefixes = (int *)malloc(len_s * sizeof(int));
	find_prefix(len_s, s, s_prefixes);

	int q = 0;

	for (int k = 0; k < len_t; ++k) {
		while(q > 0 && s[q] != t[k])
			q = s_prefixes[q - 1];

		if (s[q] == t[k])
			++q;

		if (q == 0) {
			printf("no\n");
			free(s_prefixes);
			return;
		}

		if(q == len_s)
			q = s_prefixes[q - 1];
	}
	printf("yes\n");
	free(s_prefixes);
}

int main(int argc, char** argv) {
	if (argc != 3) {
		printf("Usage: %s <string S> <string T>\n", argv[0]);
		return 1;
	}

	char *s = argv[1];
	char *t = argv[2];
	int len_s = strlen(s);
	int len_t = strlen(t);

	pword(s, len_s, t, len_t);

	return 0;
}
