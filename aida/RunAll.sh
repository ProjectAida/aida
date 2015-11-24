    #!/bin/bash

echo "AIDA: Poem Identifier"
./RunPageSegmentation.sh

cd data/
find Output_Snippets/ -type f > snippetPathList.txt
cd ../
NOME=$1
c=0
a=1
+++
if grep  "$NOME" data/snippetPathList.txt ; then
        echo "CREATING ARRAY"
        while read line
        do
                myArray[$c]=$line # store line
                c=$(expr $c + 1) # increase counter by 1
        done < data/snippetPathList.txt

else
        echo "Name not found"
fi

cd src/ 
javac execute/RunProgram.java


for i in ${myArray[@]} ;
        do
		echo "Processing Image $a"
		java execute/RunProgram 5 C $i
		a=$(expr $a + 1)
	done
