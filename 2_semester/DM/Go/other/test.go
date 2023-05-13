package main

import "fmt"

func main() {
    baseArray := [10]int{0, 1, 2, 3, 4, 5, 6, 7, 8, 9}
    fmt.Printf("Базовый массив: %v\n", baseArray)

    baseSlice := baseArray[3:7]
    fmt.Printf(
        "Срез, основанный на базовом массиве длиной %d и емкостью %d: %v\n",
        len(baseSlice),
        cap(baseSlice),
        baseSlice,
    )
    baseSlice = append(baseSlice, 125)
    fmt.Printf(
        "Изменённый срез, основанный на базовом массиве длиной %d и емкостью %d: %v\n",
        len(baseSlice),
        cap(baseSlice),
        baseSlice,
    )
    fmt.Printf("Базовый массив: %v\n", baseArray)
}

func fibonacci(n int) int {
    fibo_numbers := []int {1, 1}
    if i < 3 {
        return 1
    } else {
        for i := 2; i < n; i++ {
           fibo_numbers = append(fibo_numbers, fibo_numbers[i-1]+fibo_numbers[i-2])
       }
   }
}








