#!/bin/bash
cd src/
javac -cp ../tif_jar/jai_imageio.jar:./ execute/RunPageSegmentation.java
cd ../

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
    #if [ "$year" -ge "$beginYear" ] && [ "$year" -le "$endYear" ]
    if [ 1 ]
    then
        cd $j
        echo $j
        for k in $( ls ); do
            totalCount=$((totalCount + 1))
            current=$(pwd)
            cd $src
#java execute/RunPageSegmentation $current/$k
            java -cp ../tif_jar/jai_imageio.jar:./ execute/RunPageSegmentation $current/$k
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

