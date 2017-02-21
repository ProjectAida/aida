## About ##
batch_retrieval.py downloads newspaper page images from Chronicling America. It works in three steps:

1. Creates the master manifest of every newspaper page image in the Chronicling America collection
2. Downloads requested images. The getImages function takes in two integers that correspond to the beginning year and ending year that you want to download images for (publication dates of newspapers).
3. Converts downloaded images from JP2000 to .jpg

All downloaded images are stored in a file hierarchy based on the Chronicling America file hierarchy.

## Requirements ##
* Java version 7 or higher  
* Python version 2.7 (not Python 3)  
* Python image dependencies:  
  * JasPer (JP200 Python encoder/decoder)  
  * Pgmagick (Python image library)

## Running ##
To run the retrieval program:  
`python batch_retrieval.py <flag> <begin year> <end year>`

Use one of the following `<flag>` values to specify which function(s) to run:
* flag = 1: Run all steps. Build manifest, get images from a specific year or years, and convert to .jpg.  
**Example:** `python batch_retrieval.py 1 1924 1924` [Creates complete manifest, downloads all images from 1924, and converts those images to .jpg]  
**Example:** `python batch_retrieval.py 1 1836 1840` [Creates complete manifest, downloads all images from 1836-1840, inclusive, and converts those images to .jpg]  
* flag = 2: Get images from a specific year or years and convert images to .jpg (can only be run independently if manifest already exists).    
**Example:** `python batch_retrieval.py 2 1924 1924` [Manifest already exists; downloads all images from 1924 and converts those images to .jpg]  
**Example:** `python batch_retrieval.py 2 1836 1840` [Manifest already exists; downloads all images from 1836-1840, inclusive, and converts those images to .jpg]
* flag = 3: Build manifest (do not download and convert images). Do not specify a begin year or end year, as the manifest builder always compiles the complete manifest for all images in the Chronicling America collection.  
**Example:** `python batch_retrieval.py 1` [Creates a complete manifest all of newspaper page images in Chronicling America and saves manifest for later use.]
