#!/bin/bash
search () {
    for file in "$1"/* 
    do
        if [[ -d "$file" ]]; then
            search "$file"
        elif [[ -f "$file" ]]; then
            echo "$file"
        fi
    done
}

search "$1"