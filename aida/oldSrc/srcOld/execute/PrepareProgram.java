package execute;

import java.io.File;

import global.Constants;

public class PrepareProgram {
	
	/**
	 * This method is an secondary method that is used to set up the directories and files for the process to run. Any process,
	 * that is meant to be out of the control flow of the RunProgram class can be put here. This method is called once before 
	 * running RunProgram with all the images in archive_list.txt 
	 * @author maanas
	 *
	 */

	public static void main(String args[]){
		File OutputBlurred = new File(Constants.BlurredOutput);
		File OutputBinary = new File(Constants.binaryOutput);
		File OutputCustom = new File(Constants.customOutput);
		File OutputAttribute = new File(Constants.attributeOutputFolder);
		File OutputAnalysis = new File(Constants.analysisFileDirectory);
		File OutputSnippet = new File(Constants.Snippets);
		deleteFolderContent(OutputBinary);
		deleteFolderContent(OutputSnippet);
		deleteFolderContent(OutputCustom);
		deleteFolderContent(OutputAttribute);
		deleteFolderContent(OutputBlurred);
		deleteFolderContent(OutputAnalysis);
		System.out.println("Program Ready...");
		
	}
	/**
	 * secondary method to delete the contents of the given parameter which represents a folder.
	 * @param folder
	 */
	public static void deleteFolderContent(File folder) {
		File[] files = folder.listFiles();
		if(files!=null) { //some JVMs return null for empty dirs
			for(File f: files) {
				f.delete();
			}
		}
	}
}
