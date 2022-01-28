#!/bin/sh
echo 'PID\tPPID\tUID\tFILES\tNAME'
FILES=/proc/*
for f in $FILES
do
    pid=$(cat $f/status 2>/dev/null | grep -E "\<Pid\>" | sed 's/[A-Za-z:   ]*//g')
    ppid=$(cat $f/status 2>/dev/null | grep -E "\<PPid\>" | sed 's/[A-Za-z: ]*//g')
    uid=$(cat $f/status 2>/dev/null | grep -E "\<Uid\>" | awk '{print $2}')
    if [ -d "$f/fd" ]; then
        openedfiles=$(sudo ls -l $f/fd | wc -l)
    fi
    name=$(cat $f/status 2>/dev/null | grep -E "Name" | sed 's/\Name\>[:]*//g')
    if [ ! -z "$pid" ]; then
        echo $ppid"\t"$pid"\t"$uid"\t"$openedfiles"\t"$name
    fi
done
