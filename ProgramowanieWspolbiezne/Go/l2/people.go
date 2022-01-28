package main

import (
	"fmt"
	"math/rand"
	"time"
)

func client(getI chan <- getItem) {
	for {
		m := getItem{resp: make(chan item)}
		getI <- m
		i := <-m.resp
		if mode {
			fmt.Printf("K: Klient kupil produkt o wartosci: %d\n", i.val)
		}
		time.Sleep(clientDelay * time.Second)
	}
}

func calculateTask(workerType string, t* task, machines []chan machineTask) int{
	if workerType == "cierpliwy" {
		machine := machines[rand.Intn(len(machines))]
		machineResponse := make(chan bool)
		machine <- machineTask{t, machineResponse}
		for {
			select {
			case <-machineResponse:
				return t.result
			}
		}
	} else {
		for _, machine := range machines {
			machineResponse := make(chan bool)
			machine <- machineTask{t, machineResponse}
			for {
				select {
				case <-machineResponse:
					return t.result
				case <-time.After(impatientWorkerDelay):
					break
				}
			}
		}
	}
	return 0
}

func worker(n int, getTasks chan <- getTask, items chan <- item, state <- chan bool) {
	var done = 0
	var workerType = "niecierpliwy"
	for {
		select {
		case <-state:
			fmt.Printf("Pracownik %s nr %d wykonal %d zadan\n", workerType, n, done)
		default:
		}
		var o = rand.Intn(2)
		switch o {
		case 0:
			workerType = "cierpliwy"
		case 1:
			workerType = "niecierpliwy"
		}
		task := getTask{resp: make(chan task)}
		getTasks <- task
		t := <- task.resp
		var w int
		switch t.operation {
		case "+":
			w = calculateTask(workerType, &t, addingMachines)
		case "*":
			w = calculateTask(workerType, &t, multMachines)
		}
		items <- item{val: w}
		if mode {
			fmt.Printf("P: Pracownik nr %d wykonal zadanie %d z wynikiem: %d\n", n, t.num, w)
		}
		time.Sleep(workerDelay * time.Millisecond)
		done++
	}
}

func manager(tasks chan <- task) {
	for {
		var operation string
		var o = rand.Intn(3)
		switch o {
		case 0:
			operation = "+"
		case 1:
			operation = "-"
		case 2:
			operation = "*"
		}
		t := task{taskNumber, rand.Intn(100) + 1, rand.Intn(100) + 101, operation, -1}
		taskNumber++
		tasks <- t
		if mode {
			fmt.Printf("Prezes stworzyl nowe zadanie o numerze: %d z wartosciami: %d i %d dla operacji: %s\n", t.num, t.x, t.y, t.operation)
		}
		time.Sleep(menagerDelay * time.Millisecond)
	}
}
