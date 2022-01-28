package main

import (
	"fmt"
	"math/rand"
	"time"
)

type machine struct {
	id int
	task chan machineTask
	free chan bool
	working bool
	backdoor chan bool
}

type machineTask struct {
	task *task
	notify chan int
}

func (mach *machine) runAddingMachine() {
	for {
		if mach.working && rand.Float64() > machineProbability {
			mach.working = false
			fmt.Printf("Zepsula sie maszyna nr %d\n", mach.id)
		}
		t := <-mach.task
		if t.task.operation == "*" {
			continue
		}
		if mode {
			fmt.Printf("M: Maszyna %d dodaje %d i %d\n", mach.id, t.task.x, t.task.y)
		}
		time.Sleep(addingMachineDelay)
		if mach.working {
			t.notify <- t.task.x + t.task.y
		} else {
			t.notify <- 0
		}
	}
}

func createAddingMachines() []machine {
	channels := make([]machine, addingMachineNumber)
	for i := 0; i < addingMachineNumber; i++ {
		channels[i] = machine{i,make(chan machineTask), make(chan bool), true, make(chan bool)}
		go channels[i].runAddingMachine()
	}
	return channels
}

func (mach *machine) runMultiplyingMachine() {
	for {
		if mach.working && rand.Float64() > machineProbability {
			mach.working = false
		}
		t := <-mach.task
		if t.task.operation != "*" {
			continue
		}
		if mode {
			fmt.Printf("M: Maszyna %d mnozy %d i %d\n", mach.id, t.task.x, t.task.y)
		}
		time.Sleep(multMachineDelay)
		if mach.working {
			t.notify <- t.task.x * t.task.y
		} else {
			t.notify <- 0
		}
	}
}

func createMultiplyingMachines() []machine {
	channels := make([]machine, multMachineNumber)
	for i := 0; i < multMachineNumber; i++ {
		channels[i] = machine{i,make(chan machineTask), make(chan bool), true, make(chan bool)}
		go channels[i].runMultiplyingMachine()
	}
	return channels
}