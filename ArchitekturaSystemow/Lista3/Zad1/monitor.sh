#!/bin/bash

function check() {
    line=`cat /proc/net/dev | sed -e 's/[A-Za-z|:-]*//g'`;
    received=`echo $line | awk '{print $1}'`
    trans=`echo $line | awk '{print $9}'`
    stan1=$(($received+$trans))
    sleep 1
    line=`cat /proc/net/dev | sed -e 's/[A-Za-z|:-]*//g'`;
    received=`echo $line | awk '{print $1}'`
    trans=`echo $line | awk '{print $9}'`
    stan2=$(($received+$trans-$stan1))
    echo $stan2 >> monitor.txt


    var1=$(cat /sys/class/power_supply/BAT0/uevent | grep -E "ENERGY_NOW" | sed 's/[A-Za-z=_ ]*//g')
    var2=$(cat /sys/class/power_supply/BAT0/uevent | grep -E "ENERGY_FULL_DESIGN" | sed 's/[A-Za-z=_ ]*//g')
    bat=`echo "scale=2;$var1/$var2 * 100" | bc -l`
    echo $bat >> monitor.txt


    var4=$(cat /proc/loadavg | cut -f 1 -d " ")
    cpu=`echo "$var4 * 100" | bc -l`
    echo $cpu >> monitor.txt
}
check
counter=0
echo "NET_SPEED      BATTERY%       CPU%           UPTIME"    
while IFS=" " read -r line2 || [[ -n "$line2" ]]; do
    line1=${line2%.*}
    if [[ "$line1" -gt 90 ]]; then 
        echo -ne "[##########]   "
    elif [[ "$line1" -gt 80 ]]; then
        echo -ne "[#########-]   "
    elif [[ "$line1" -gt 70 ]]; then
        echo -ne "[########--]   "
    elif [[ "$line1" -gt 60 ]]; then
        echo -ne "[#######---]   "
    elif [[ "$line1" -gt 50 ]]; then
        echo -ne "[######----]   "
    elif [[ "$line1" -gt 40 ]]; then
        echo -ne "[#####-----]   "
    elif [[ "$line1" -gt 30 ]]; then
        echo -ne "[####------]   "
    elif [[ "$line1" -gt 20 ]]; then
        echo -ne "[###-------]   "
    elif [[ "$line1" -gt 10 ]]; then
        echo -ne "[##--------]   "  
    elif [[ "$line1" -gt 0 ]]; then
        echo -ne "[#---------]   "
    elif [[ "$line1" -ge 0 ]]; then
        echo -ne "[----------]   "
    fi
    ((counter++))
    if [[ "$counter" -gt 2 ]]; then
        time1=$(cat /proc/uptime | awk '{print $1}')
        time=${time1%.*}
        mod1=$(($time%86400))
        time=$((time-mod1))
        days=$((time/86400))
        mod2=$(($mod1%3600))
        mod1=$((mod1-mod2))
        hours=$((mod1/3600))
        mod3=$(($mod2%60))
        mod2=$((mod2-mod3))
        minutes=$((mod2/60))
        echo -ne "${days}d ${hours}h ${minutes}s ${mod3}s"
        counter=0
        printf "\n"
        check
    fi
done < monitor.txt
