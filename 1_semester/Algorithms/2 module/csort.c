#include <stdio.h>
#include <stdlib.h>
#include <string.h>

char **split_substrings(char *s, size_t *str_num) {
	char **substrings = NULL, *substr = NULL, c = 1;
	size_t num = 0, chars_num = 0;
	while(c != '\0') {
		c = *(s++);
		if(c != ' ' && c != '\0') {
			if(chars_num == 0) substr = (char *)malloc(sizeof(char));
			else substr = (char *)realloc(substr, sizeof(char) * (chars_num + 1));
			*(substr + chars_num) = c;
			chars_num++;
		} else if(chars_num != 0) {
			substr = (char *)realloc(substr, sizeof(char) * (chars_num + 1));
			substr[chars_num] = '\0';
			chars_num = 0;
			substrings = (char **)realloc(substrings, sizeof(char *) * (num + 1));
			*(substrings + num) = substr;
			num++;
		}
	}
	*str_num = num;
	return substrings;
}

void csort(char *src, char *dest) {
	size_t str_num = 0;
	char **substrings = split_substrings(src, &str_num);

	size_t count[str_num];
	for(int i = 0; i < str_num; i++) count[i] = 0;

	for(int i = 0; i < str_num-1; i++) {
		for(int j = i+1; j < str_num; ++j) {
			if(strlen(substrings[i]) <= strlen(substrings[j])) {
				count[j]++;
			} else {
				count[i]++;
			}
		}
	}

	char **sorted_str = malloc(str_num * sizeof(char*));
	for(int i = 0; i < str_num; i++) {
		sorted_str[i] = malloc(1000 * sizeof(char));
		strcpy(sorted_str[i], "");
	}
	for(int i = 0; i < str_num; i++) {
		for(int j = 0; j < str_num; j++) {
			if(count[j] == i) {
				strcpy(sorted_str[i], substrings[j]);
			}
		}
	}

	strcpy(dest, "");

	for (int i = 0; i < str_num; i++) {
		strcat(dest, sorted_str[i]);
		if (i != str_num - 1) strcat(dest, " ");
	}

	for(int i = 0; i < str_num; i++) {
		free(substrings[i]);
		free(sorted_str[i]);
	}

	free(substrings);
	free(sorted_str);
}

void get_string(char *s) {
	char c;
	int i = 0;
	scanf("%c", &c);
	while (c != '\n' && c != EOF && c != '\0') {
		s[i] = c;
		scanf("%c", &c);
		i++;
	}
	s[i] = '\0';
}

int main() {
	char *str = (char *)malloc(1000 * sizeof(char));

	get_string(str);

	char *res = (char *)malloc(1000 * sizeof(char));
	csort(str, res);
	printf("%s", res);

	free(str);
	free(res);

	return 0;
}


// testing program

/*extern void csort(char *src, char *dest);


#define MEM(p) must_be_not_null((p), #p, __FILE__, __LINE__)

static void *must_be_not_null(void *p, const char *expr,
		const char *file, int line) {
	if (p) return p;

	fprintf(stderr, "%s:%d: '%s' returns NULL\n", file, line, expr);
	abort();
}


enum { MAX_WORD_LEN = 12, NVOWELS = 6, NCONSONANTS = 20 };

static const char VOWELS[] = "aeiouy";
static const char CONSONANTS[] = "bcdfghjklmnpqrstvwxz";


static int randint(int limit)
{
	return (int) (rand() / ((double) RAND_MAX + 1) * limit);
}

#define RANDLETTER(type) (type[randint(N ## type)])


struct Word {
	char letters[MAX_WORD_LEN];
	size_t len;
	size_t spaces_after;
	size_t order;
};


static void init_word(struct Word *word, size_t order)
{
	size_t len = MAX_WORD_LEN / 2 + randint(MAX_WORD_LEN / 2);
	size_t i = 0;

	while (i < len - 2) switch (randint(4)) {
	case 0:
		word->letters[i++] = RANDLETTER(VOWELS);
		break;

	case 1:
		word->letters[i++] = RANDLETTER(CONSONANTS);
		break;

	case 2:
		word->letters[i++] = RANDLETTER(CONSONANTS);
		word->letters[i++] = RANDLETTER(VOWELS);
		break;

	case 3:
		word->letters[i++] = RANDLETTER(VOWELS);
		word->letters[i++] = RANDLETTER(CONSONANTS);
		break;
	}
	word->letters[i] = '\0';

	word->len = i;
	word->spaces_after = 1 + randint(5);
	word->order = order;
}


static int word_compare(const void *vleft, const void *vright)
{
	const struct Word *left = vleft;
	const struct Word *right = vright;

	return left->len != right->len
		? (int) left->len - (int) right->len
		: (int) left->order - (int) right->order;

}


static int random_test(size_t nwords)
{
	// init
	struct Word words[nwords];
	size_t source_len = 0, dest_len = 0;

	for (size_t i = 0; i < nwords; ++i) {
		init_word(&words[i], i);
		source_len += words[i].len + words[i].spaces_after;
		dest_len += words[i].len + 1;
	}

	source_len -= words[nwords - 1].spaces_after;
	dest_len -= 1;

	char *source = MEM(malloc(source_len + 1));
	char *dest = MEM(malloc(dest_len + 1));
	char expected[dest_len + 1];

	// init destination buffer with garbage
	for (size_t i = 0; i < dest_len + 1; ++i) dest[i] = 1 + randint(255);

	char *p = source;
	for (size_t i = 0; i < nwords; ++i) {
		memcpy(p, words[i].letters, words[i].len);
		p += words[i].len;
		if (i != nwords - 1) {
			for (size_t j = 0; j < words[i].spaces_after; ++j) *p++ = ' ';
		}
	}
	*p = '\0';

	printf("Source string:\n%s\n", source);

	qsort(words, nwords, sizeof(words[0]), word_compare);

	p = expected;
	for (size_t i = 0; i < nwords; ++i) {
		memcpy(p, words[i].letters, words[i].len);
		p += words[i].len;
		*p++ = ' ';
	}
	p[-1] = '\0';

	printf("Expected result:\n%s\n", expected);
	fflush(stdout);

	char source_copy[source_len + 1];
	strcpy(source_copy, source);

	// call
	csort(source, dest);


	// check
	printf("Result after sorting:\n%s\n", dest);
	int mismatched_result = strcmp(dest, expected) != 0;
	if (mismatched_result) printf("TEST FAILED! Strings are not equal!\n\n");


	int source_touched = memcmp(source, source_copy, source_len + 1);
	if (source_touched) printf("TEST FAILED! Source string is changed!\n\n");

	int failed = mismatched_result || source_touched;
	if (! failed) printf("Test passed!\n\n");


	free(dest);
	free(source);
	return failed;
}


int main()
{
	int failed = 0;

	failed += random_test(1);
	failed += random_test(2);
	failed += random_test(3);
	failed += random_test(10);
	failed += random_test(30);
	failed += random_test(10 + randint(10));
	failed += random_test(200);

	return failed == 0 ? EXIT_SUCCESS : EXIT_FAILURE;
}*/

/* vim: set sw=0 ts=4 noet: */

