package main

import (
	"bufio"
	"fmt"
	"os"
	"strings"
)

func main() {
	var promptedString string

	fmt.Println("Enter a string:")

	scanner := bufio.NewScanner(os.Stdin)
	for scanner.Scan() {
		promptedString = scanner.Text()
		break
	}

	lowercasedString := strings.ToLower(promptedString)

	if strings.HasPrefix(lowercasedString, "i") &&
		strings.HasSuffix(lowercasedString, "n") &&
		strings.Contains(lowercasedString, "a") {
		fmt.Println("Found!")
	} else {
		fmt.Println("Not Found!")
	}
}
