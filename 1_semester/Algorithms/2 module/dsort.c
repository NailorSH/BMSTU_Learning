#include <stdio.h>
#include <stdlib.h>
#include <string.h>

void dsort(char *s){
	size_t count[26] = {0};
	int n = strlen(s), m = 26;
	int j = 0, k = 0;
	while(j < n) {
		k = s[j] - 'a';
		count[k]++;
		j++;
	}
	
	for(int i = 0; i < m; i++){
		while(count[i]){
			*s = (char)i + 'a';
			s++;
			count[i]--;
		}
	}
}

int main() {
	char *str;
	str = (char*)malloc(1000005 * sizeof(char));
	scanf("%s", str);
	
	dsort(str);
    printf("%s", str);

	free(str);
	return 0;
}
