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

words () {
    for r in `cat $result`
    do 
        echo "$r"
    done | sort -u
}

for w in $(words)
do 
    if [[ -f "$w" ]]; then
        grep -l "$w" "$1"/* | wc -l | tr '\n' ' '
        echo "$w"
    fi
done