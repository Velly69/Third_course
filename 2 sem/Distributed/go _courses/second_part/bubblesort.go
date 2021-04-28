package main

import "fmt"

func BubbleSort(array []int) {
	for i := 0; i < len(array)-1; i++ {
		for j := 0; j < len(array)-i-1; j++ {
			if array[j] > array[j+1] {
				Swap(array, j)
			}
		}
	}
}

func Swap(array []int, index int) {
	temp := array[index]
	array[index] = array[index+1]
	array[index+1] = temp
}

func main() {
	var number int
	numberArray := []int{}
	for i := 0; i < 10; i++ {
		fmt.Print("Enter a number: ")
		fmt.Scan(&number)
		numberArray = append(numberArray, number)
	}
	BubbleSort(numberArray)
	fmt.Println(numberArray)
}

// package main

// import (
// 	"fmt"
// 	"os"
// 	"strconv"
// 	"strings"
// )

// func main() {
// 	fmt.Println("----------- Bubble Sort ----------\nNOTE: Number of input limited to 10. And press 'X' to stop")
// 	fmt.Println("Enter the numbers to be sorted  - ")
// 	input := make([]int64, 0, 10)
// 	var numStr string
// 	for {
// 		_, err := fmt.Scan(&numStr)
// 		if err != nil {
// 			exitProgram(fmt.Sprintf("Error while reading input. Err - %v", err), 100)
// 		}
// 		if "x" == strings.ToLower(numStr) {
// 			break
// 		}
// 		num, err := strconv.ParseInt(strings.Trim(numStr, " "), 10, 32)
// 		if err != nil {
// 			fmt.Println("Not a valid integer. Please enter integers only")
// 			continue
// 		}
// 		input = append(input, num)
// 		if len(input) == cap(input) {
// 			fmt.Println("Input size limit reached.")
// 			break
// 		}
// 	}
// 	fmt.Println("Sorting...")
// 	BubbleSort(input)
// 	fmt.Println("---------- Input Sorted in ascending order ----------")
// 	fmt.Printf("%v", input)
// }

// func exitProgram(msg string, code int) {
// 	fmt.Println(msg)
// 	os.Exit(code)
// }

// func BubbleSort(arr []int64) {
// 	var i int
// 	for i < len(arr) {
// 		j := i + 1
// 		for j < len(arr) {
// 			if arr[i] > arr[j] {
// 				swap(arr, i, j)
// 			}
// 			j++
// 		}
// 		i++
// 	}
// }

// func swap(arr []int64, i, j int) {
// 	k := arr[i]
// 	arr[i] = arr[j]
// 	arr[j] = k
// }
