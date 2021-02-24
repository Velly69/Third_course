package main

import (
	"fmt"
	"time"
)

type Ivanov struct { }

type Petrov struct { }

type Nechiporchuk struct { }

type Item struct {
	cost int
}

type Storage struct {
	items []Item
}

func (Ivanov) takeFromStorage(st Storage, fromStorage chan Item)  {
	for i:=0; i<len(st.items); i++ {
		fromStorage <- st.items[i]
		fmt.Println("Ivanov took out from the storage.")
		time.Sleep(1 * time.Microsecond)
	}
}

func (Petrov) loadToLorry(fromStorage chan Item, toLorry chan Item)  {
	for i:=0; i<cap(fromStorage); i++ {
		item:= <- fromStorage
		fmt.Println("Petrov took from Ivanov.")
		toLorry <- item
		fmt.Println("Petrov gave to Nechiporchuk.")
		time.Sleep(1 * time.Microsecond)
	}
}

func (Nechiporchuk) countFromLorry(toLorry chan Item, income chan int)  {
	var total = 0
	for i:=0; i<cap(toLorry); i++ {
		item:= <- toLorry
		fmt.Println("Nechiporchuk took from Petrov.")
		total += item.cost
		time.Sleep(1 * time.Microsecond)
	}
	income <- total
}


func main() {
	var storage = Storage{
		[]Item{
					{100}, {230}, {740}, {290},
					{160}, {340}, {815}, {130}}}

	var fromStorage = make(chan Item, len(storage.items))
	var fromLorryToCount = make(chan Item, cap(fromStorage))
	var income = make(chan int)

	ivanov := Ivanov{}
	petrov := Petrov{}
	nechiporchuk := Nechiporchuk{}

	go ivanov.takeFromStorage(storage, fromStorage)
	go petrov.loadToLorry(fromStorage, fromLorryToCount)
	go nechiporchuk.countFromLorry(fromLorryToCount, income)

	fmt.Println("TOTAL: ", <-income)
}

