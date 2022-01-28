package main

import "time"

//zad2
var addingMachineNumber = 3
var multMachineNumber = 3

var addingMachineDelay time.Duration = 2000
var multMachineDelay time.Duration = 2000

const impatientWorkerDelay = 1000 * time.Millisecond



var mode = true
//rozmiary list
const taskListSize = 10
const magazineSize = 10

//liczba pracownikow
var employeesNumber = 5

//czasy opoznienia
var menagerDelay time.Duration = 1000
var workerDelay time.Duration = 5000
var clientDelay time.Duration = 10
