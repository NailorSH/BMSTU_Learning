#include <stdio.h>
#include <string.h>

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

int main(int argc, char** argv) {
    if (argc < 2) {
        printf("Error: expected string as command line argument\n");
        return 1;
    }

    char* str = argv[1];
    int n = strlen(str);
    int prefix_values[n];
    find_prefix(n, str, prefix_values);

    for (int i = 0; i < n; i++) {
        int len = i + 1;
        int divisor = len - prefix_values[i];
        if (prefix_values[i] != 0 && (i + 1) % divisor == 0)
            printf("%d %d\n", i + 1, (i + 1) / divisor);
    }
    return 0;
}
