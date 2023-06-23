package main

import "fmt"

func Partition(low, high int,
	less func(i, j int) bool,
	swap func(i, j int)) int {
	i, j := low, low

	for ; j < high; j++ {
		if less(j, high) {
			swap(i, j)
			i++
		}
	}

	swap(i, high)
	return i
}

func QuickSortRec(low, high int,
	less func(i, j int) bool,
	swap func(i, j int)) {
	for low < high {
		q := Partition(low, high, less, swap)

		if q-low < high-q {
			QuickSortRec(low, q-1, less, swap)
			low = q + 1
		} else {
			QuickSortRec(q+1, high, less, swap)
			high = q - 1
		}
	}
}

func qsort(n int,
	less func(i, j int) bool,
	swap func(i, j int)) {

	QuickSortRec(0, n-1, less, swap)
}

func main() {
	var items = []int{
		847, 939, 773, 748, 765, 796, 846, 925, 842, 880,
		916, 932, 967, 759, 783, 989, 975, 854, 949, 975,
		861, 900, 826, 834, 882, 869, 812, 748, 959, 865,
		892, 932, 804, 737, 747, 829, 766, 959, 868, 823,
		819, 837, 837, 940, 766, 985, 876, 790, 727, 755,
		907,
	}

	qsort(51,
		func(i, j int) bool {
			return items[i] < items[j]
		},
		func(i, j int) {
			items[i], items[j] = items[j], items[i]
		})

	fmt.Println(items)
}