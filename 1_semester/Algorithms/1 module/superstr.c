#include <stdio.h>
#include <string.h>
#include <malloc.h>

// > 3
// > hfhhffhhf
// > dadhffjdj
// > hfdhfsdsj
// ... hf

int shortest_superstr(char **s, int n) {
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


char *superstr(char **s, int n) {
	int len_1 = strlen(s[i]);
	char *substr = (char *)malloc(1000);
	strcpy(substr, s[i]);
	for(int i = 0; i < len_1; i++) {
		if()
		for(int j = 0; j < n)
	}
}

char *substr(char *s, int len) {
	for(int i = 0; i < strlen(s); i++){
		
	}
}

int main() {
	int n;
	scanf("%d ", &n);
	char **arr = malloc(n * sizeof(char*));
	for (int i = 0; i < n; i++)
		arr[i] = malloc(1000 * sizeof(char));
		scanf("%s", arr[i]);
	
	int len = shortest_superstr(arr, n);
	printf("%d", len);
	
	for(int i = 0; i < n; i++) free(arr[i]);
	free(arr);
}

