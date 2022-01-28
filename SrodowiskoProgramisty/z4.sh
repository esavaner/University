#!/bin/bash
search () {
    for file in "$1"/* 
    do
        if [[ -d "$file" ]]; then
            search "$file"
        elif [[ -f "$file" ]]; then
            i=1
            while IFS= read -r line
            do
                for word in $line
                do
                    echo "$file $i $word"
                done
                i=$((i+1))
            done < $file
        fi
    done
}

search "$1"