package main

import "time"

var work1speed = "1"

//rozmiary list
const taskListSize =  4
const magazineSize = 2

//liczba pracownikow
var employeesNumber = 5

//czasy opoznienia
var menagerDelay time.Duration = 1000
var workerDelay time.Duration = 15000
var clientDelay time.Duration = 50
