#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>


extern void bubblesort(unsigned long nel,
                       int (*compare)(unsigned long i, unsigned long j),
                       void (*swap)(unsigned long i, unsigned long j));


#define MSGF(msg, ...) \
	printf("%s:%d: %s(): " msg "\n", __FILE__, __LINE__, __func__, __VA_ARGS__)


struct Record {
	int key;
	unsigned long source_order;
};


static unsigned long nel;
static struct Record *array, *backup;


static void dump_arrays(void) {
	printf("Source order of items:\n");
	for (unsigned long i = 0; i < nel; ++i) {
		printf("\tarray[%lu] = { %d, %lu }\n", i, backup[i].key,
		       backup[i].source_order);
	}

	printf("Current order of items:\n");
	for (unsigned long i = 0; i < nel; ++i) {
		printf("\tarray[%lu] = { %d, %lu }\n", i, array[i].key,
		       array[i].source_order);
	}
}


#define CHECK_INDICES(i, j) \
	if (nel == 0) { \
		printf("%s:%d: you try to %s elements of empty array\n", \
		       __FILE__, __LINE__, __func__); \
		exit(EXIT_FAILURE); \
	} else if ((i) >= nel || (j) >= nel) { \
		printf("%s:%d: %s(%lu, %lu): indices out of range (nel == %lu)\n", \
		       __FILE__, __LINE__, __func__, (i), (j), nel); \
		dump_arrays(); \
		exit(EXIT_FAILURE); \
	}


static int compare(unsigned long i, unsigned long j) {
	CHECK_INDICES(i, j);
	return
	    array[i].key < array[j].key ? -1 :
	    array[i].key > array[j].key ? +1 : 0;
}


static unsigned long swap_calls;

static void swap(unsigned long i, unsigned long j) {
	CHECK_INDICES(i, j);
	struct Record old_i = array[i];
	array[i] = array[j];
	array[j] = old_i;
	++swap_calls;
}


#define rand_sign() (rand() < RAND_MAX / 2 ? -1 : +1)


static int random_test(int maxval) {
	int failed = 0;

// prepare
	nel = 1000 + rand() % 500;
	printf("Sorting of random array (item keys is %d..%d), size %lu\n",
	       -maxval, maxval, nel);
	struct Record loc_array[nel], loc_backup[nel];
	array = loc_array;
	backup = loc_backup;

	for (unsigned long i = 0; i < nel; ++i) {
		array[i].key = rand_sign() * (rand() % maxval);
		array[i].source_order = i;
	}

	memcpy(backup, array, sizeof(struct Record) * nel);


// call
	bubblesort(nel, compare, swap);


// check
	for (unsigned long i = 0; i < nel - 1; ++i) {
		if (array[i].key > array[i + 1].key) {
			MSGF("array[%lu] (%d) > array[%lu] (%d)\n",
			     i, array[i].key, i + 1, array[i + 1].key);
			failed = 1;
		}

		if (array[i].key == array[i + 1].key
		        && array[i].source_order > array[i + 1].source_order) {
			MSGF("Relative order of equal elements "
			     "array[%lu] (%d, source pos %lu) and "
			     "array[%lu] (%d, source pos %lu) is corrpted, "
			     "you sorting is not stable\n",
			     i, array[i].key, array[i].source_order,
			     i + 1, array[i + 1].key, array[i + 1].source_order);
			failed = 1;
		}
	}

	if (failed) dump_arrays();

	printf("\ttest %s\n", failed ? "failed" : "passed");
	return failed;
}


static int equal_test(void) {
	int failed = 0;

// prepare
	nel = 1000 + rand() % 500;
	printf("Sorting of equal array, size %lu\n", nel);
	struct Record loc_array[nel], loc_backup[nel];
	array = loc_array;
	backup = loc_backup;

	for (unsigned long i = 0; i < nel; ++i) {
		array[i].key = 1;
		array[i].source_order = i;
	}

	memcpy(backup, array, sizeof(struct Record) * nel);


// call
	swap_calls = 0;
	bubblesort(nel, compare, swap);


// check
	if (swap_calls) {
		printf("Unexpected swaps in array with equal items, " \
		       "swap called %lu times\n", swap_calls);
		failed = 1;
	}

	for (unsigned long i = 0; i < nel - 1; ++i) {
		if (array[i].source_order > array[i + 1].source_order) {
			MSGF("Relative order of equal elements "
			     "array[%lu] (%d, source pos %lu) and "
			     "array[%lu] (%d, source pos %lu) is corrpted, "
			     "you sorting is not stable\n",
			     i, array[i].key, array[i].source_order,
			     i + 1, array[i + 1].key, array[i + 1].source_order);
			failed = 1;
		}
	}

	if (failed) dump_arrays();
	printf("\ttest %s\n", failed ? "failed" : "passed");
	return failed;
}

static void one_test(void) {
// prepare
	printf("Sorting of array with one item\n");
	struct Record loc_array[1] = { { 1, 1 } };
	array = loc_array;
	backup = loc_array;
	nel = 1;


// call
	bubblesort(nel, compare, swap);


// check - do nothing, test checks indices overflow in compare() and swap()
}


static void empty_test(void) {
// prepare
	nel = 0;
	array = NULL;
	backup = NULL;

// call
	bubblesort(nel, compare, swap);

// check - do nothing, test checks indices overflow in compare() and swap()
}


int main() {
	int failed = 0;

	srand(time(NULL));

	failed += random_test(1000000);
	failed += random_test(10);

	failed += equal_test();

	one_test();
	empty_test();

	return failed == 0 ? EXIT_SUCCESS : EXIT_FAILURE;
}

void bubblesort(unsigned long nel,
                int (*compare)(unsigned long i, unsigned long j),
                void (*swap)(unsigned long i, unsigned long j)) {
	if (nel < 2) return;
	unsigned long t = nel - 1, m = 0;
	unsigned long higher_bound = t, lower_bound = m;
	unsigned long i;
	char direct_order = 1;

	while (lower_bound < higher_bound) {
		if (direct_order == 1) {
			higher_bound = t;
			t = lower_bound;
			i = lower_bound;
			while (i < higher_bound) {
				if(compare(i+1, i) == -1) {
					swap(i+1, i);
					t = i;
				}
				i++;
			}
			direct_order = 0;
		} else {
			lower_bound = m;
			m = higher_bound;
			i = higher_bound;
			while (i > lower_bound) {
				if(compare(i, i-1) == -1) {
					swap(i, i-1);
					m = i;
				}
				i--;
			}
			direct_order = 1;
		}
	}
}
