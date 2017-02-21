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

## Running ##
1. Move directory 'aida/' into desired location.

2. Determine which script you want to run. There are three possible scripts to run:
  * RunAll.sh: segments full page images of historic newspapers, processes snippets to prepare them for feature extraction, extracts feature values, and classifies image snippets as True (contains poetic content) or False (does not contain poetic content)
  * RunPageSegementation.sh: segments full page images of historic newspapers and saves snippets for later processing
  * runImageProcess.sh: processes existing snippets to prepare them for feature extraction, extracts feature values, and classifies image snippets as True (contains poetic content) or False (does not contain poetic content)

3. Specify desired options, if you will be running either **RunAll.sh** or **runImageProcess.sh**:   
In the script you want to run (either **RunAll.sh** or **runImageProcess.sh**), find the line containing `java execute/RunProgram <flag1> <flag2> $i`  
  * update flag1 to one of two values, '357' or '5', to specify which blurring to use
  * update flag2 to one of two values, 'C' or 'R', to specify whether to use regular blurring or the consolidation technique
**Example**: `java execute/RunProgram 5 C $i`

4. Run desired script:
  * To run entire program, run the script with the command `./RunAll.sh` in a terminal while in the parent directory.
  * To run only the subset of the program that segments full page images of historic newspapers and saves snippets for later processing, run the script with the command `./RunPageSegementation.sh` in a terminal while in the parent directory.
  * To run only the subset of the program that processes existing snippets by performing blurring and/or consolidation, feature extraction, and classification processes, run the script with the command `./runImageProcess.sh` in a terminal while in the parent directory.

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
+ weka : Package containing classes responsible for weka ANN and results.  
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