/*#include <stdbool.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>


extern void hsort(void *base, size_t nel, size_t width,
		int (*compare)(const void *a, const void *b));


#define MEM(p) must_be_not_null((p), #p, __FILE__, __LINE__)

static void *must_be_not_null(void *p, const char *expr,
		const char *file, int line) {
	if (p) return p;

	fprintf(stderr, "%s:%d: '%s' returns NULL\n", file, line, expr);
	abort();
}


static void *memdup(const void *mem, size_t size) {
	void *result = malloc(size);
	if (result) memcpy(result, mem, size);
	return result;
}


static size_t nel, width;
static const void *vbase;


static bool valid_ptr(const void *vptr)
{
	const char *base = vbase;
	const char *ptr = vptr;
	return ptr && (base <= ptr) && (ptr < base + nel * width)
		&& (ptr - base) % width == 0;
}


#define CHECK_CMP_PARAM(a, b, ptr, pos_name) \
	if (! valid_ptr(ptr)) { \
		printf("%s:%d: %p - %s parameter of %s(%p, %p) is invalid\n", \
				__FILE__, __LINE__, (ptr), (pos_name), __func__, (a), (b)); \
		printf("base %p\nnel = %zu\nwidth = %zu\n", vbase, nel, width); \
		exit(EXIT_FAILURE); \
	}

#define CHECK_CMP_PARAMS(a, b) \
	{ \
		CHECK_CMP_PARAM(a, b, a, "first"); \
		CHECK_CMP_PARAM(a, b, b, "second"); \
	}


static int compare_int(const void *va, const void *vb)
{
	CHECK_CMP_PARAMS(va, vb);
	const int *a = va;
	const int *b = vb;

	return *a < *b ? -1 : *a > *b ? +1 : 0;
}


static int test_ints(size_t nelems)
{
	// init
	int failed = 0;
	int origin[nelems];
	printf("// sort int's\n\n");
	printf("// source array\nint int_array[%zu] = {\n", nelems);
	for (size_t i = 0; i < nelems; ++i) {
		int value = rand() % 1000;
		bool first = i % 14 == 0;
		bool last = i % 14 == 13 || i == nelems - 1;
		printf("%s%3d,%s", first ? "\t" : "", value, last ? "\n" : " ");
		origin[i] = value;
	}
	printf("};\n\n"
			"int compare_int(const void *va, const void *vb) {\n"
			"\tconst int *a = va, *b = vb;\n"
			"\treturn *a < *b ? -1 : *a > *b ? +1 : 0;\n"
			"};\n\n");
	fflush(stdin);

	int *input = MEM(memdup(origin, sizeof(origin)));

	// call
	printf("// calling...\n");
	nel = nelems;
	width = sizeof(int);
	vbase = origin;
	qsort(origin, nel, width, compare_int);

	vbase = input;
	hsort(input, nel, width, compare_int);

	// check
	for (size_t i = 0; i < nelems; ++i) {
		if (origin[i] != input[i]) {
			printf("// int_array[%zu] is invalid: expected %d but actual %d\n",
					i, origin[i], input[i]);
			failed = 1;
		}
	}

	printf("// test %s\n\n\n", failed ? "failed" : "passed");
	free(input);
	return failed;
}


struct Point3D {
	double x, y, z;
};


static int compare_point3d(const void *va, const void *vb)
{
	CHECK_CMP_PARAMS(va, vb);
	const struct Point3D *a = va;
	const struct Point3D *b = vb;

	return
		a->x < b->x ? -1 : a->x > b->x ? +1 :
		a->y < b->y ? -1 : a->y > b->y ? +1 :
		a->z < b->z ? -1 : a->z > b->z ? +1 : 0;

}


static int test_points(size_t nelems)
{
	// init
	int failed = 0;
	struct Point3D origin[nelems];
	printf("// sort Points's\n\n");
	printf("struct Point3D {\n\tdouble x, y, z;\n};\n\n");
	printf("// source array\nstruct Point3D point_array[%zu] = {\n", nelems);
	for (size_t i = 0; i < nelems; ++i) {
		struct Point3D value;
		value.x = rand() % 1000 / 100.0;
		value.y = rand() % 1000 / 100.0;
		value.z = rand() % 1000 / 100.0;
		bool first = i % 3 == 0;
		bool last = i % 3 == 2 || i == nelems - 1;
		printf("%s{ %4.2f, %4.2f, %4.2f},%s", first ? "\t" : "",
				value.x, value.y, value.z, last ? "\n" : " ");
		origin[i] = value;
	}
	printf("};\n\n"
			"static int compare_point3d(const void *va, const void *vb)\n"
			"{\n"
			"\tconst struct Point3D *a = va;\n"
			"\tconst struct Point3D *b = vb;\n"
			"\n"
			"\treturn\n"
			"\t\ta->x < b->x ? -1 : a->x > b->x ? +1 :\n"
			"\t\ta->y < b->y ? -1 : a->y > b->y ? +1 :\n"
			"\t\ta->z < b->z ? -1 : a->z > b->z ? +1 : 0;\n"
			"};\n\n");
	fflush(stdin);

	struct Point3D *input = MEM(memdup(origin, sizeof(origin)));

	// call
	printf("// calling...\n");
	nel = nelems;
	width = sizeof(struct Point3D);
	vbase = origin;
	qsort(origin, nel, width, compare_point3d);

	vbase = input;
	hsort(input, nel, width, compare_point3d);

	// check
	for (size_t i = 0; i < nelems; ++i) {
		if (origin[i].x != input[i].x || origin[i].y != input[i].y
				|| origin[i].z != input[i].z) {
			printf("// point_array[%zu] is invalid: "
					"expected { %4.2f, %4.2f, %4.2f } "
					"but actual { %4.2f, %4.2f, %4.2f }\n",
					i, origin[i].x, origin[i].y, origin[i].z,
					input[i].x, input[i].y, input[i].z);
			failed = 1;
		}
	}

	printf("// test %s\n\n\n", failed ? "failed" : "passed");
	free(input);
	return failed;
}


static int compare_char(const void *va, const void *vb)
{
	CHECK_CMP_PARAMS(va, vb);
	const char *a = va;
	const char *b = vb;

	return *a - *b;
}


static int test_chars(size_t nelems)
{
	// init
	int failed = 0;
	char origin[nelems];
	printf("// sort Points's\n\n");
	printf("char {\n\tdouble x, y, z;\n};\n\n");
	printf("// source array\nchar char_array[%zu] = {\n", nelems);
	for (size_t i = 0; i < nelems; ++i) {
		char value = 33 + rand() % (127 - 33);
		bool first = i % 14 == 0;
		bool last = i % 14 == 13 || i == nelems - 1;
		printf("%s'%c',%s", first ? "\t" : "", value, last ? "\n" : " ");
		origin[i] = value;
	}
	printf("};\n\n"
			"static int compare_char(const void *va, const void *vb)\n"
			"{\n"
			"\tconst char *a = va, *b = vb;\n"
			"\treturn *a - *b\n"
			"};\n\n");
	fflush(stdin);

	char *input = MEM(memdup(origin, sizeof(origin)));

	// call
	printf("// calling...\n");
	nel = nelems;
	width = sizeof(char);  // 1 by Standard definition of sizeof
	vbase = origin;
	qsort(origin, nel, width, compare_char);

	vbase = input;
	hsort(input, nel, width, compare_char);

	// check
	for (size_t i = 0; i < nelems; ++i) {
		if (origin[i] != input[i]) {
			printf("// char_array[%zu] is invalid: "
					"expected '%c' but actual '%c'\n",
					i, origin[i], input[i]);
			failed = 1;
		}
	}

	printf("// test %s\n\n\n", failed ? "failed" : "passed");
	free(input);
	return failed;
}


int main()
{
	int failed = 0;

	failed += test_ints(1);
	failed += test_ints(2);
	failed += test_ints(10);
	failed += test_ints(100);
	failed += test_ints(128);
	failed += test_ints(500 + rand() % 500);

	failed += test_points(1);
	failed += test_points(2);
	failed += test_points(10);
	failed += test_points(100);
	failed += test_points(128);
	failed += test_points(500 + rand() % 500);

	failed += test_chars(1);
	failed += test_chars(2);
	failed += test_chars(10);
	failed += test_chars(100);
	failed += test_chars(128);
	failed += test_chars(256);
	failed += test_chars(500 + rand() % 500);

	return failed == 0 ? EXIT_SUCCESS : EXIT_FAILURE;
}*/

/* vim: set sw=0 ts=4 noet: */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

void swap(void *base, size_t width, size_t i, size_t j) {
    void *tmp = malloc(width);
    memmove(tmp, ((char*)base + i * width), width);
    memmove(((char*)base + i * width), ((char*)base + j * width), width);
    memmove(((char*)base + j * width), tmp, width);
    free(tmp);
}

int CountChars(const char* string, char ch) {
	int count = 0;
	for(; *string; count += (*string++ == ch)) ;
	return count;
}

int comp(const void *va, const void *vb) {
	char *a = *(char**)va;
	char *b = *(char**)vb;
	
	int num_a = CountChars(a, 'a');
	int num_b = CountChars(b, 'a');
	
	return num_a - num_b;
}

void Heapify(void *base, size_t n, size_t width,
             int (*compare)(const void *a, const void *b), size_t i) {
	while(1) {
		size_t l = 2*i + 1;
		size_t r = l + 1;
		size_t j = i;
		if(l < n && compare((((char*)base) + width*i), (((char*)base) + width*l)) < 0) {
			i = l;
		}
		if(r < n && compare((((char*)base) + width*i), (((char*)base) + width*r)) < 0) {
			i = r;
		}
		
		if(i == j) break;

		swap(base, width, i, j);
	}
}

void BuildHeap(void *base, size_t nel, size_t width,
               int (*compare)(const void *a, const void *b)) {
	for(long i = nel/2 - 1; i >= 0; i--) Heapify(base, nel, width, compare, i);
}

void hsort(void *base, size_t nel, size_t width,
           int (*compare)(const void *a, const void *b)) {

	BuildHeap(base, nel, width, compare);

	size_t i = nel - 1;
	
	while(i > 0) {
		swap(base, width, 0, i);
		Heapify(base, i, width, compare, 0);
		i--;
	}
}

int main() {
	int n;
	scanf("%d", &n);

	char **strs = malloc(n * sizeof(char*));
	for (int i = 0; i < n; i++) {
		char *str = malloc(1000 * sizeof(char));
		scanf("%s", str);
		strs[i] = str;
	}

	hsort(strs, n, sizeof(char*), comp);
	for (int i = 0; i < n; i++) {
		printf("%s\n", strs[i]);
		free(strs[i]);
	}
	free(strs);

	return 0;
}

