#include <stdio.h>
#include <string.h>
#include <malloc.h>

char *concat(char **s, int n) {
	int len = 0;
	for(int i = 0; i < n; i++) len += strlen(s[i]);
	
	char *concat_str;
	concat_str = (char*)malloc((len+1) * sizeof(char));

	int ind = 0;
	for(int i=0; i < n; i++) {
		for(int j = 0; j < strlen(s[i]); j++) {
			concat_str[ind] = s[i][j];
			ind++;
		}
	}
	concat_str[ind] = 0;

	return concat_str;
}

int main() {
	int n;
	scanf("%d ", &n);
	char **arr = malloc(n * sizeof(char*));
	for (int i = 0; i < n; i++)
		arr[i] = malloc(1000 * sizeof(char));

	for (int i = 0; i < n; i++) scanf("%s", arr[i]);
	
	char *s = concat(arr, n);
	printf("%s", s);
	
	for(int i = 0; i < n; i++) free(arr[i]);
	free(arr);
	free(s);
	return 0;
}
