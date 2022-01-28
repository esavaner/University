package main

import "fmt"

func newTaskGuard(b bool, c <-chan task) <-chan task {
	if !b {
		return nil
	}
	return c
}

func getTaskGuard(b bool, c <-chan getTask) <-chan getTask {
	if !b {
		return nil
	}
	return c
}

func tasks(taskGet <-chan getTask, taskNew <-chan task, state <-chan bool) {
	taskList := make([]task, 0)
	for {
		select {
		case getTask := <- getTaskGuard(len(taskList) > 0, taskGet):
			t := taskList[0]
			taskList = taskList[1:]
			getTask.resp <- t
		case newTask := <- newTaskGuard(len(taskList) < taskListSize, taskNew):
			taskList= append(taskList, newTask)
		case <-state:
			fmt.Print("Stan listy zadan: ")
			fmt.Println(taskList)
		}
	}
}




func storingGuard(b bool, c <- chan item) <- chan item {
	if !b {
		return nil
	}
	return c
}

func buyingGuard(b bool, c <- chan getItem) <- chan getItem {
	if !b {
		return nil
	}
	return c
}

func magazine(store <- chan item, buy <- chan getItem, state <- chan bool) {
	items := make([]item, 0)
	for {
		select {
		case store := <- storingGuard(len(items) < magazineSize, store):
			items = append(items, store)
		case buy := <- buyingGuard(len(items) > 0, buy):
			buy.resp <- items[0]
			items = items[1:]
		case <- state:
			fmt.Print("Stan magazynu: ")
			fmt.Println(items)
		}
	}
}