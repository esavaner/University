#!/bin/bash
'url=$(curl -s https://api.thecatapi.com/v1/images/search?format=json | jq -r '.[0].url')
wget -0 kot ${url1:1:-1} && convert -density 300 -depth 8 -strip -background white -alpha off file.tiff
tesseract file.tiff output.txt'
curl -s http://api.icndb.com/jokes/random | jq '.value.joke'
