#include <stdio.h>

unsigned long binsearch(unsigned long nel, int (*compare)(unsigned long i)) {
	unsigned long left = 0, right = nel-1;
	
	while (left <= right) {
		unsigned long mid = (left + right) / 2;
		if(compare(mid) > 0) right = mid - 1;
                else if(compare(mid) < 0) left = mid + 1;
                else if(compare(mid) == 0) return mid;
	}

	return nel;
}

