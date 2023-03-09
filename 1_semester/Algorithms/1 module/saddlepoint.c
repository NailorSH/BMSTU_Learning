#include <stdio.h>
#include <malloc.h>

int main() {
	long long *mas;
	int flag = 0, n, m, x_sad = 0, y_sad = 0, i, j, k;

	scanf("%d %d", &n, &m);

	mas = (long long*)malloc(n*m*sizeof(long long));

	for(i = 0; i < n; i++) {
		for(j = 0; j < m; j++) {
			scanf("%lld", (mas + i*m + j));
		}
	}

	long long lmax, lmin, ind_lmax;

	for(i = 0; i < n; i++) {
		lmax = *(mas + i*m + 0);
		ind_lmax = 0;

		lmax = *(mas + i*m + 0);

		for(j = 0; j < m; j++) {
			if(*(mas + i*m + j) > lmax) {
				lmax = *(mas + i*m + j);
				ind_lmax = j;
			}
		}

		lmin = *(mas + i*m + ind_lmax);

		for(k = 0; k < n; k++) {
			if(*(mas + k*m + ind_lmax) < lmin) {
				lmin = *(mas + k*m + ind_lmax);
			}
		}

		if(lmin == lmax) {
			flag = 1;
			x_sad = ind_lmax;
			y_sad = i;
			break;
		}
	}

	free(mas);

	if(flag) printf("%d %d", y_sad, x_sad);
	else printf("none");
}
