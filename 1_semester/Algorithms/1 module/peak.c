#include <stdio.h>

unsigned long peak (unsigned long nel, int (*less)(unsigned long i, unsigned long j)) {
	for(unsigned long i = 1; i < nel-1; i++){
		if(less(i-1, i) && !less(i, i+1)) return i;
	}
}

int main() {

}

