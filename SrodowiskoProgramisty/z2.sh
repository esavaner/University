#!/bin/bash

result=""
search () {
    for file in "$1"/* 
    do
        if [[ -d "$file" ]]; then
            search "$file"
        elif [[ -f "$file" ]]; then
            echo "$file"
            result+="$file "
        fi
    done
}

search "$1"

for r in `cat $result`
do 
    echo "$r"
done | sort | uniq -c
