package main

import (
	"fmt"
	"math"
)

func GenDisplaceFn(acceleration, velocity, initDisplacement float64) func(float64) float64 {
	fn := func(time float64) float64 {
		firstPart := 1.0 / 2.0 * acceleration * math.Pow(time, 2)
		secondPart := velocity * time
		return firstPart + secondPart + initDisplacement
	}
	return fn
}

func main() {
	var acceleration, velocity, initDisplacement, time float64

	fmt.Print("Enter an acceleration: ")
	fmt.Scan(&acceleration)

	fmt.Print("Enter a velocity: ")
	fmt.Scan(&velocity)

	fmt.Print("Enter an initial displacement: ")
	fmt.Scan(&initDisplacement)

	fmt.Print("Enter a time: ")
	fmt.Scan(&time)

	fn := GenDisplaceFn(acceleration, velocity, initDisplacement)

	fmt.Println(fn(time))
}
