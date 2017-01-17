CONTENTS
----------------------
* Introduction
* Requirements
* Installation
* Directories and Files
* Notes


INTRODUCTION
------------
AIDA Project procedurally analyzes image archives of newspaper and 
determines if that particular image contains a poem using a Multilayer Perception
Artificial Neural Network

REQUIREMENTS
------------
* Java Runtime 1.5 or higher
* Unix based system architecture

INSTALLATION
------------
1. Move directory 'aida/' into desired location.

2. Select desired options using command line arguments in the script files (*.sh):  
Find the line containing : `java execute/RunProgram <flag1> <flag2> $i`  
	* flag1 is used to indicate which version of blurring to use (357, or 5)
	* flag2 is used to indicate whether to use regular blurring or custom blurring

3. To run entire program, run the script by running the command:  
`./RunAll.sh`  
in a terminal while in the parent directory. 

4. To run a different version for a more specific purpose run these scripts
	* `./runImageProcess.sh`
	* `./RunPageSegementation.sh` -- Runs through the file hierarchy containing the jpg images and runs the page segmentation process on each image individually

Directories and Files
-----------------
aida  
+ data  
    - Analysis : Folder containing .arff files  
    - Output_Snippets : Folder contains snippets generated from segmentation algorithm  
    - Output_Custom : Folder contains custom blurred images  
    - Output_Binary : Folder contains binary images  
    - FullPages : Folder where Full newspaper images downloaded from CA  
    - TrainImages : Images used for Training set.  
+ src : Folder containing all source code.  
    - blurring : Package containing classes responsible for different types of blurring  
    - execute : Package containing classes responsible execution of various processes.  
    - featureExtraction: Package containing classes responsible for analyzing features.  
    - global : Package contains Constants class that holds the filepaths for various locations.  
    - models : Package contains Model classes that represents various types of images.  
    - processHelpers : Package contains classes responsible for determining thresholds.  
+ weka : Package containing classes resposible for weka ANN and results.  
    - weka_jar : directory contains the .jar files for weka.  
+ RunAll.sh : Runs all processes  
+ runImageProcess.sh : Runs Maanas' Image Process only.  
+ runSegmentation.sh : Runs Spencer's Segmentation algorithm only.  

NOTES
-----
* Snippets for images in data/FullImages are placed in data/Output_Snippets/
* A list of filepaths for these files is created and placed in SnippetPathList.txt
* .arff outputs are stored in data/Analysis.
* Standard out is used for final results.
* If using IDE, run the command line script first before running the file in
  the IDE.
