package main

import . "fmt"

func add(a, b []int32, p int) []int32 {
	var add_result []int32

	var transfer int32 = 0

	var maxsize int
	if len(a) > len(b) {
		maxsize = len(a)
	} else {
		maxsize = len(b)
	}

	for i := 0; i < maxsize; i++ {
		var current_sum int32

		if i >= len(a) {
			current_sum = b[i] + transfer
		} else if i >= len(b) {
			current_sum = a[i] + transfer
		} else {
			current_sum = a[i] + b[i] + transfer
		}

		add_result = append(add_result, current_sum%int32(p))
		transfer = current_sum / int32(p)

	}

	if transfer != 0 {
		add_result = append(add_result, transfer)
	}

	return add_result
}

func main() {
	var a = []int32{0, 1}
	var b = []int32{0, 1, 1}
	var p int = 10

	add_result := add(a, b, p)

	Println(add_result)
}