## Contents ##
* Introduction
* Requirements
* Installation
* Directories and Files
* Notes

## Introduction ##
Creates image snippets from page images of historic newspaper pages, processes those snippets to prepare them for feature extraction, extracts feature values, and then determines if an image snippet contains poetic content using a Multilayer Perception Artificial Neural Network trained on feature values.

## Requirements ##
* Java Runtime 1.5 or higher
* Unix based system architecture

## Installation ##
1. Move directory 'aida/' into desired location.

2. Select desired options using command line arguments in the script files (.sh files):  
Find the line containing: `java execute/RunProgram <flag1> <flag2> $i`  
	* flag1 is used to indicate which version of blurring to use (357, or 5)
	* flag2 is used to indicate whether to use regular blurring or custom blurring

3. To run entire program, run the script by running the command `./RunAll.sh` in a terminal while in the parent directory.

4. To run a subset of the full program for a single purpose, choose from these scripts  
  * `./RunPageSegementation.sh` [Runs through the file hierarchy containing the jpg images and runs the page segmentation process on each image to create image snippets]
  * `./runImageProcess.sh`

## Directories and Files ##
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
+ runImageProcess.sh : image process only  
+ runSegmentation.sh : segmentation algorithm only  

## Notes ##
* Snippets created from images in data/FullPages are placed in data/Output_Snippets/
* A list of filepaths for these files is created and placed in SnippetPathList.txt
* .arff outputs are stored in data/Analysis.
* Standard out is used for final results.
* If using IDE, run the command line script first before running the file in
  the IDE.
