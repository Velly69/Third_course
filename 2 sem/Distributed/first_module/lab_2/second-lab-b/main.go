package main

import (
	"fmt"
)

var amountOfItems = 20

func takeAwayByIvanov(takenChan chan int) {
	for i := 0; i < amountOfItems; i++ {
		fmt.Println("Ivanov took from storage.")
		takenChan <- 1
	}
}

func loadToLorryByPetrov(takenChan chan int, loadedChan chan int) {
	for i := 0; i < amountOfItems; i++ {
		<-takenChan
		fmt.Println("Petrov loaded to lorry.")
		loadedChan <- 1
	}
}

func countTotalByNechiporchuk(loadedChan chan int) {
	for i := 0; i < amountOfItems; i++ {
		<-loadedChan
		fmt.Println("Nechiporchuk counted total price.")
	}
}

func main() {
	var takenChan = make(chan int, 1)
	var loadedChan = make(chan int, 1)

	go takeAwayByIvanov(takenChan)
	go loadToLorryByPetrov(takenChan, loadedChan)
	go countTotalByNechiporchuk(loadedChan)

	fmt.Scanln()
}
