package main

import (
	"fmt"
	"sync"
)

var taskNumber = 1
var wg sync.WaitGroup
var addingMachines []chan machineTask
var multMachines []chan machineTask

type task struct {
	num       int
	x         int
	y         int
	operation string
	result    int
}

type item struct {
	val int
}

type getTask struct {
	resp chan task
}

type getItem struct {
	resp chan item
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
	addingMachines = createAddingMachines()
	multMachines = createMultiplyingMachines()
	presidentChannel := make(chan task)
	workerTasksChannel := make(chan getTask)
	workerChannel := make(chan item)
	clientChannel := make(chan getItem)
	magazineChannel := make(chan bool)
	tasksListChannel := make(chan bool)
	go tasks(workerTasksChannel, presidentChannel, tasksListChannel)
	go magazine(workerChannel, clientChannel, magazineChannel)
	go manager(presidentChannel)
	go client(clientChannel)

	workerStateChannels := make([]chan bool, employeesNumber)
	for i := 0; i < employeesNumber; i++ {
		workerStateChannels[i] = make(chan bool)
		wg.Add(1)
		go worker(i, workerTasksChannel, workerChannel, workerStateChannels[i])
	}
	if !mode {
		go startInteraction(magazineChannel, tasksListChannel, workerStateChannels)
	}
	wg.Wait()
}

func scan() string {
	var s string
	fmt.Printf("\nWyswietl stan magazynu (m), stan listy zadan (z), statystyki pracownikow (d) lub przelacz na tryb gadatliwy (g)\n")
	fmt.Scanf("%s", &s)
	return s
}

func startInteraction(magazineChannel chan <- bool, taskListChannel chan <- bool, workerStateChannels[] chan bool) {
	for {
		var s = scan()
		switch s {
		case "m": magazineChannel <- true
		case "z": taskListChannel <- true
		case "g":
			mode = true
		case "d":
			for _, s := range workerStateChannels {
				s <- true
			}
		default:
			scan()
		}
	}
}

