package main

import (
	"fmt"
	"math"
)

func main() {
	var floatNumber float64

	fmt.Println("Input the digit number: ")
	fmt.Scan(&floatNumber)
	res := math.Trunc(floatNumber)
	fmt.Println(res)
}
