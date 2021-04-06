package main

import (
	"fmt"
	"sort"
	"strconv"
)

func main() {
	sortedArray := make([]int, 0, 3)
	var s string
	for {
		fmt.Print("Enter a number: ")
		_, err := fmt.Scan(&s)
		num, err := strconv.Atoi(s)
		if err != nil && s == "X" {
			break
		}
		sortedArray = append(sortedArray, num)
		sort.Ints(sortedArray)
		fmt.Println("Sorted array: ", sortedArray)
	}
}
