package main

import (
	"fmt"
	"time"
)

type service struct {
	machineType string
	machine int
}

type serviceRequest struct {
	response chan service
}


func serviceGuard(b bool, c <-chan serviceRequest) <-chan serviceRequest {
	if !b {
		return nil
	}
	return c
}

func serviceWorker(serviceRequests chan<- serviceRequest, servicesDone chan <- service) {
	for {
		serviceChan := make(chan service)
		serviceRequests <- serviceRequest{serviceChan}
		s := <-serviceChan

		if mode {
			fmt.Println("S: Pracownik serwisu naprawia maszyne nr ", s.machine)
		}

		time.Sleep(serviceWorkerDelay)
		if s.machineType == "+" {
			addingMachines[s.machine].backdoor <- true
		} else {
			multMachines[s.machine].backdoor <- true
		}
	}
}

func machineService(newService <-chan service, servicesDone <-chan service, serviceRequests <-chan serviceRequest) {
	servicesCurr := make([]service, 0)
	serviceQueue := make([]service, 0)
	for {
		select {
		case s := <-newService:
			if findService(servicesCurr, s) == -1 {
				servicesCurr = append(servicesCurr, s)
				serviceQueue = append(serviceQueue, s)
			}
		case done := <-servicesDone:
			i := findService(servicesCurr, done)
			servicesCurr = append(servicesCurr[:i], servicesCurr[i+1:]...)
		case request := <-serviceGuard(len(serviceQueue) > 0, serviceRequests):
			request.response<- serviceQueue[0]
			serviceQueue = serviceQueue[1:]
		}
	}
}

func findService(services []service, searched service) int {
	for i, s := range services {
		if s.machine == searched.machine && s.machineType == searched.machineType {
			return i
		}
	}
	return -1
}