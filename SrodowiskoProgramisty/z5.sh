#!/bin/bash
search () {
    for file in "$1"/* 
    do
        if [[ -d "$file" ]]; then
            search "$file"
        elif [[ -f "$file" ]]; then
            sed -i 's/a/A/g' "$file"
        fi
    done
}

search "$1"