package main

import (
	"bufio"
	"encoding/json"
	"fmt"
	"os"
)

func main() {
	var name string
	var adress string

	var mapObject = make(map[string]string)

	scanner := bufio.NewScanner(os.Stdin)

	fmt.Print("Enter a name: ")
	for scanner.Scan() {
		name = scanner.Text()
		break
	}

	fmt.Print("Enter an adress: ")
	for scanner.Scan() {
		adress = scanner.Text()
		break
	}

	mapObject["name"] = name
	mapObject["adress"] = adress

	jsonObject, err := json.Marshal(mapObject)
	if err == nil {
		fmt.Println(string(jsonObject))
	}
}
