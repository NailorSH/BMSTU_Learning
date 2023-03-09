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

void kmp(char *s, int len_s, char *t, int len_t) {
	int *pi = (int *)malloc(len_s * sizeof(int));
	find_prefix(len_s, s, pi);
	int q = 0;
	
	for (int k = 0; k < len_t; ++k) {
		while(q > 0 && s[q] != t[k])
			q = pi[q - 1];
		
		if (s[q] == t[k]) 
			++q;
		
		if (q == len_s) {
			printf("%d ", (k - q + 1));
		}
	}
	free(pi);
}

int main(int argc, char *argv[]) {
	if (argc != 3) {
		printf("Usage: %s <string T> <string S>\n", argv[0]);
		return 1;
	}
	char *s = argv[1];
	char *t = argv[2];
	int len_s = strlen(s);
	int len_t = strlen(t);

	kmp(s, len_s, t, len_t);
	return 0;
}
