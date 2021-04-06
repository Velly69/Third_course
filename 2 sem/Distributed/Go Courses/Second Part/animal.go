package main

import (
	"bufio"
	"fmt"
	"os"
	"strings"
)

type Animal struct {
	food       string
	locomotion string
	noise      string
}

func (animal Animal) Eat() {
	fmt.Println(animal.food)
}

func (animal Animal) Move() {
	fmt.Println(animal.locomotion)
}

func (animal Animal) Speak() {
	fmt.Println(animal.noise)
}

func main() {
	animals := map[string]Animal{
		"cow":   {food: "grass", locomotion: "walk", noise: "moo"},
		"bird":  {food: "worms", locomotion: "fly", noise: "peep"},
		"snake": {food: "mice", locomotion: "slither", noise: "hsss"},
	}
	var line string
	fmt.Println("FIRST WORD -> name of animal, SECOND WORD -> action, X -> stop the program")
	for {
		fmt.Print(">")
		scanner := bufio.NewScanner(os.Stdin)
		for scanner.Scan() {
			line = scanner.Text()
			break
		}
		if line == "X" {
			break
		}
		info := strings.Split(line, " ")
		animalName := info[0]
		animalAction := info[1]
		switch animalAction {
		case "eat":
			animals[animalName].Eat()
		case "move":
			animals[animalName].Move()
		case "speak":
			animals[animalName].Speak()
		}
	}

}
