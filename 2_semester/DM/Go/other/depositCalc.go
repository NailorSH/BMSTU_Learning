/*
    Calculation of how many years after the deposit
    in the amount of x rubles and with a percentage p 
    will be at least y rubles.

    Usage: x p y
*/ 

package main

import "fmt"

func main() {
    fmt.Println(`
    Calculation of how many years after the deposit
    in the amount of x rubles and with a percentage p 
    will be at least y rubles.

    Usage: x p y
    `)

    var x, p, y, ans int
    fmt.Scan(&x, &p, &y)

    for ; x < y; x += x*p/100 {
        ans++
    }
    
    fmt.Println(ans)
}