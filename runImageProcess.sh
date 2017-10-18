
# cd data/
# find Output_Snippets/ -type f > PathWithLabel.txt
# find Output_Snippets/ -not -path '*/\.*' -type f -printf "%f\n" > SnippetNameList.txt
# cd ../
t=`date`
NOME=$1
c=0
a=1
#+++
if grep  "$NOME" data/PathWithLabel.txt ; then
        echo "CREATING ARRAY"
        while read line
        do
                echo $c
                myArray[$c]=$line # store line
                c=$(expr $c + 1) # increase counter by 1
        done < data/PathWithLabel.txt

else
        echo "Name not found"
fi


# javac execute/RunProgram.java
javac -classpath "weka_jar/weka.jar;../weka_jar/weka-src.jar;lib/jai_imageio.jar;" -d classes @JavaFileList.txt

cd classes/ 

for i in ${myArray[@]} ;
        do
		echo "Processing Image $a"
		java execute/RunProgram 5 C $i
		a=$(expr $a + 1)
	done
echo $t
echo `date`
read -p "Press enter to continue"