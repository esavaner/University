package main

import (
	"fmt"
	"time"
)

type machineTask struct {
	task *task
	notify chan bool
}

func addingMachine(id int, addTasks <-chan machineTask) {
	for t := range addTasks {
		if t.task.operation != "+" {
			continue
		}
		if mode {
			fmt.Printf("Maszyna %d dodaje %d i %d\n", id, t.task.x, t.task.y)
		}
		t.task.result = t.task.x + t.task.y
		time.Sleep(addingMachineDelay)
		t.notify <- true
	}
}

func createAddingMachines() []chan machineTask {
	channels := make([]chan machineTask, addingMachineNumber)
	for i := 0; i < addingMachineNumber; i++ {
		channels[i] = make(chan machineTask)
		go addingMachine(i, channels[i])
	}
	return channels
}

func multiplyingMachine(id int, multiplyTasks <-chan machineTask) {
	for t := range multiplyTasks {
		if t.task.operation != "*" {
			continue
		}
		if mode {
			fmt.Printf("Maszyna %d mnozy %d i %d\n", id, t.task.x, t.task.y)
		}
		t.task.result = t.task.x * t.task.y
		time.Sleep(multMachineDelay)
		t.notify <- true
	}
}

func createMultiplyingMachines() []chan machineTask {
	channels := make([]chan machineTask, multMachineNumber)
	for i := 0; i < multMachineNumber; i++ {
		channels[i] = make(chan machineTask)
		go multiplyingMachine(i, channels[i])
	}
	return channels
}