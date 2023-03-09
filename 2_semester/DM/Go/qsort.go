package main

import "fmt"

func Partition(low, high int,
               less func(i, j int) bool,
               swap func(i, j int)) int {
    i, j := low, low

    for ; j < high; j++ {
        if(less(j, high)) {
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
    QuickSortRec(0, n-1, less, swap);
}

func main() {
    data := []int{0, 2, -9, 1, 5, 6, 3}
    qsort(len(data), func(i, j int) bool {
        return data[i] < data[j]
    }, func(i, j int) {
        data[i], data[j] = data[j], data[i]
    })
    fmt.Println(data)
}