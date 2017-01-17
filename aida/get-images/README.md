## About ##
batch_retrieval.py downloads images from Chronicling America. It works in three steps:

1. Create the master manifest of every newspaper image in the Chronicling America collection
2. Download the images. The getImages function can take in two integers that correspond to the beginning year and ending year that you want to download from (dates of newspapers).
3. Convert images from JP2000 to .jpg

All images are stored in a file hierarchy based on the Chronicling America file hierarchy.

## Running ##
Run the retrieval by running:  
`python Batch_Retrieval.py <flag> <begin year> <end year>`
 	
use <flag> to specify which functions to run:  
* flag = 1 for run build manifest, get images and convert to *.jpg
* flag = 2 for get images and convert to *.jpg (use if manifest already exists)
* flag = 3 for build manifest only 
