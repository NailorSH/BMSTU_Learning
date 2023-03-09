#include <stdio.h>
#include <limits.h>
 
#define bindump(ptr, size) ({ \
    unsigned long n = (size) * CHAR_BIT; \
    while ( n ) \
        printf("%d", ( *(ptr) >> --n ) & 1); \
})
 
int main(void) {
    char c1 = 1, c2 = -1;
    int i1 = 1, i2 = -1;
    long l1 = 1L, l2 = -1L;
    unsigned long ul = 1UL;
    long long ll1 = 1LL, ll2 = -1LL;
    unsigned long long ull = 1ULL;
    
    printf("C1: ");
    bindump(&c1, sizeof(char));
    printf("\nC2: ");
    bindump(&c2, sizeof(char));
    printf("\nI1: ");
    bindump(&i1, sizeof(int));
    printf("\nI2: ");
    bindump(&i2, sizeof(int));
    printf("\nL1: ");
    bindump(&l1, sizeof(long));
    printf("\nL2: ");
    bindump(&l2, sizeof(long));
    printf("\nUL: ");
    bindump(&ul, sizeof(long));
    printf("\nLL1: ");
    bindump(&ll1, sizeof(long long));
    printf("\nLL2: ");
    bindump(&ll2, sizeof(long long));
    printf("\nULL: ");
    bindump(&ull, sizeof(long long));
    printf("\n");
    
    return 0;
}
