    #!/bin/bash

#cd src/
#echo "AIDA: Poem Identifier"
#javac execute/PrepareProgram.java
#java execute/PrepareProgram
#javac execute/RunPageSegmentaion.java
#java execute/RunPageSegmentaion
#cd ../

cd data/
find OutputSnippets_36-37/ -type f > snippetPathList2.txt
cd ../
NOME=$1
c=0
a=1
+++
if grep  "$NOME" data/snippetPathList2.txt ; then
        echo "CREATING ARRAY"
        while read line
        do
                myArray[$c]=$line # store line
                c=$(expr $c + 1) # increase counter by 1
        done < data/snippetPathList2.txt

else
        echo "Name not found"
fi

cd src_copy/ 
javac execute/RunProgram.java


for i in ${myArray[@]} ;
        do
		echo "Processing Image $a"
		java execute/RunProgram 5 C $i
		a=$(expr $a + 1)
	done
