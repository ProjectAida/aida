Package containing Model classes that represent various types of images.


Image.java has three main functions: findColumnBreaks, showColumns, and convertPageToSnippets. 
* findColumnBreaks takes in a binarized image and finds the column breaks
* showColumns outputs an image with red lines where the found columns are located
* convertPageToSnippets takes the original raw image plus the column breaks found from the binary image and uses the info from the latter to create snippets from the former. All snippets are stored in a file hierarchy that associates the snippets with their parent image.
