package main

import (
	"fmt"
	"math/rand"
	"sync"
	"time"
)

var tasks = make(chan task, taskListSize)
var magazine = make(chan int, magazineSize)
var taskList[] task
var magazineList[] int
var taskNumber = 1
var wg sync.WaitGroup
var mode = true

type task struct {
	n int
	x int
	y int
	operation string
}

func start() {
	var s string
	fmt.Printf("Wybierz tryb pracy: spokojny (s) lub gadatliwy (g)\n")
	fmt.Scanf("%s", &s)
	switch s {
	case "g" : mode = true
	case "s" : mode = false
	default : start()
	}
}

func main() {
	start()
	if !mode {
		go startInteraction()
	}
	go startMenager()
	go startClient()
	for i := 1; i <= employeesNumber; i++ {
		wg.Add(1)
		go startWorker(i)
	}
	wg.Wait()
}

func scan() string {
	var s string
	fmt.Printf("\nWyswietl stan magazynu (m) stan listy zadan (z) lub przelacz na tryb gadatliwy (g)\n")
	fmt.Scanf("%s", &s)
	return s
}

func calculate(t task) int {
	switch t.operation {
	case "+":
		return t.x + t.y
	case "-":
		return t.x - t.y
	case "*":
		return t.x * t.y
	}
	return 0
}

func startInteraction() {
	for {
		var s = scan()
		switch s {
		case "m": fmt.Println("Stan magazynu: ", magazineList)
		case "z": fmt.Println("Stan listy zadan: ", taskList)
		case "g":
			mode = true
		default:
			scan()
		}
	}
}

func startClient() {
	for {
		m:= <- magazine
		if mode {
			fmt.Printf("K: Klient kupil produkt o wartosci: %d\n", m)
		}
		magazineList = magazineList[:len(magazineList)-1]
		time.Sleep(clientDelay * time.Second)
	}
}

func startWorker(n int) {
	for {
		t := <- tasks
		w :=calculate(t)
		if mode {
			fmt.Printf("P: Pracownik nr %d wykonal zadanie %d z wynikiem: %d\n", n, t.n, w)
		}
		taskList = taskList[:len(taskList)-1]
		magazineList = append(magazineList, w)
		magazine <- w
		time.Sleep(workerDelay * time.Millisecond)
	}
}

func startMenager() {
	for {
		var operation string
		var o= rand.Intn(3)
		switch o {
		case 0:
			operation = "+"
		case 1:
			operation = "-"
		case 2:
			operation = "*"
		}
		t := task{taskNumber, rand.Intn(100) + 1, rand.Intn(100) + 101, operation}
		taskNumber++
		if mode {
			fmt.Printf("Prezes stworzyl nowe zadanie o numerze: %d z wartosciami: %d i %d dla operacji: %s\n", t.n, t.x, t.y, t.operation)
		}
		taskList = append(taskList, t)
		tasks <- t
		time.Sleep(menagerDelay * time.Millisecond)
	}
}