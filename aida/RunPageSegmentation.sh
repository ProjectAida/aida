#!/bin/bash
beginYear=$1
endYear=$2
src=$(pwd)/src
cd data/FullPages/
totalCount=0
for i in $( ls ); do
    cd $i
    echo $i
    for j in $( ls ); do
    year=`echo $j | awk -F "_|-" {'print $2'}`
    if [ "$year" -ge "$beginYear" ] && [ "$year" -le "$endYear" ]
    then
        cd $j
        echo $j
        for k in $( ls ); do
            totalCount=$((totalCount + 1))
            current=$(pwd)
            cd $src
            java execute/RunPageSegmentaion $current/$k
            cd $current
        done
            cd ..
    fi
    done
    cd ..
done
cd ..
echo total images: $totalCount
failedCount=$(cat imageFailedNeedHuman.txt | wc -l)
passed=$((totalCount - failedCount))
echo $passed

