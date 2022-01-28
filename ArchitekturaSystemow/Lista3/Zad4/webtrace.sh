#!/bin/bash
while true
do
    lynx -dump $1 > old.txt
    sleep $2
    lynx -dump $1 > new.txt
    web=$(diff old.txt new.txt)
    if [ -z $web ]; then
        echo "Not changed"  
    else
        echo "Website changed"
    fi
done
