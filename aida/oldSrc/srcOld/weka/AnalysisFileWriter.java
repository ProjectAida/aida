package weka;

import global.Constants;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;

import models.BinaryImage;

/**
 * this class is responsible for generating *.arff files needed for WekaANN training and testing.
 * @author maanas
 *
 */
public class AnalysisFileWriter {

	private File outFile;
	private BufferedWriter outStream;
	private String Filenames;

	/**
	 * this method imports the names of all the image files to a string delimited by commas which is used later to generate the 
	 * field containing the filenames in the dataset (*.arff file)
	 * @throws IOException
	 */
	public void importFilenames() throws IOException{

		BufferedReader br = new BufferedReader(new FileReader(Constants.List));
		StringBuilder sb = new StringBuilder();
		String line, result;
		while((line = br.readLine())!=null){
			sb.append(line);
			sb.append(",");
		}
		sb.replace(sb.length()-1, sb.length(), "");
		result = sb.toString();

		this.Filenames = result;

		br.close();

	}

	/**
	 * This is called once when file is created to insert the heading into the analysis file. More specifically this prints out the 
	 * heading for a 6x1 attributes which is used in Approach 2 (see project documents for reference)
	 * @throws IOException
	 */
	public void writeSingleHead() throws IOException{
		this.importFilenames();

		BufferedReader br = new BufferedReader(new FileReader(this.outFile));

		if(br.readLine() == null){
			StringBuilder sb = new StringBuilder();
			sb.append("@relation PoemFinder\n");
			sb.append("\n");
//			sb.append("@attribute name {"+this.Filenames+"}\n");

			
			sb.append("@attribute jaggedMean numeric\n");
			sb.append("@attribute jaggedSD numeric\n");
			sb.append("@attribute jaggedMin numeric\n");
			sb.append("@attribute jaggedMax numeric\n");
			sb.append("@attribute jaggedRange numeric\n");
			
			sb.append("@attribute marginMean numeric\n");
			sb.append("@attribute marginSD numeric\n");
			sb.append("@attribute marginMin numeric\n");
			sb.append("@attribute marginMax numeric\n");
			sb.append("@attribute marginRange numeric\n");
			
			sb.append("@attribute stanzaMean numeric\n");
			sb.append("@attribute stanzaSD numeric\n");
			sb.append("@attribute stanzaMin numeric\n");
			sb.append("@attribute stanzaMax numeric\n");
			sb.append("@attribute stanzaRange numeric\n");
				
			sb.append("@attribute lengthMean numeric\n");
			sb.append("@attribute lengthSD numeric\n");
			sb.append("@attribute lengthMin numeric\n");
			sb.append("@attribute lengthMax numeric\n");
			sb.append("@attribute lengthRange numeric\n");
			
			sb.append("@attribute label {false,true}\n");

			sb.append("\n");
			sb.append("@data\n");		
			this.outStream.append(sb.toString());
		}
	}

	
	/**
	 * similar to writeSingleHead() but this method prints out the heading for a 6x3 attribute heading used for approach 1&3
	 * @throws IOException
	 */
	public void writeTripleHead() throws IOException{
		this.importFilenames();

		BufferedReader br = new BufferedReader(new FileReader(this.outFile));

		if(br.readLine() == null){

			StringBuilder sb = new StringBuilder();
			sb.append("@relation PoemFinder\n");
			sb.append("\n");
		//	sb.append("@attribute name {"+this.Filenames+"}\n");
					
			sb.append("@attribute jaggedMean3 numeric\n");
			sb.append("@attribute jaggedSD3 numeric\n");
			sb.append("@attribute jaggedMin3 numeric\n");
			sb.append("@attribute jaggedMax3 numeric\n");
			sb.append("@attribute jaggedRange3 numeric\n");
			
			sb.append("@attribute marginMean3 numeric\n");
			sb.append("@attribute marginSD3 numeric\n");
			sb.append("@attribute marginMin3 numeric\n");
			sb.append("@attribute marginMax3 numeric\n");
			sb.append("@attribute marginRange3 numeric\n");
			
			sb.append("@attribute stanzaMean3 numeric\n");
			sb.append("@attribute stanzaSD3 numeric\n");
			sb.append("@attribute stanzaMin3 numeric\n");
			sb.append("@attribute stanzaMax3 numeric\n");
			sb.append("@attribute stanzaRange3 numeric\n");
			
			sb.append("@attribute lengthMean3 numeric\n");
			sb.append("@attribute lengthSD3 numeric\n");
			sb.append("@attribute lengthMin3 numeric\n");
			sb.append("@attribute lengthMax3 numeric\n");
			sb.append("@attribute lengthRange3 numeric\n");
			
//---------------------------------------------------------------------------------------------------------------------

			
			sb.append("@attribute jaggedMean5 numeric\n");
			sb.append("@attribute jaggedSD5 numeric\n");
			sb.append("@attribute jaggedMin5 numeric\n");
			sb.append("@attribute jaggedMax5 numeric\n");
			sb.append("@attribute jaggedRange5 numeric\n");
			
			sb.append("@attribute marginMean5 numeric\n");
			sb.append("@attribute marginSD5 numeric\n");
			sb.append("@attribute marginMin5 numeric\n");
			sb.append("@attribute marginMax5 numeric\n");
			sb.append("@attribute marginRange5 numeric\n");
			
			sb.append("@attribute stanzaMean5 numeric\n");
			sb.append("@attribute stanzaSD5 numeric\n");
			sb.append("@attribute stanzaMin5 numeric\n");
			sb.append("@attribute stanzaMax5 numeric\n");
			sb.append("@attribute stanzaRange5 numeric\n");
			
			sb.append("@attribute lengthMean5 numeric\n");
			sb.append("@attribute lengthSD5 numeric\n");
			sb.append("@attribute lengthMin5 numeric\n");
			sb.append("@attribute lengthMax5 numeric\n");
			sb.append("@attribute lengthRange5 numeric\n");

//---------------------------------------------------------------------------------------------------------------------			
									
			sb.append("@attribute jaggedMean7 numeric\n");
			sb.append("@attribute jaggedSD7 numeric\n");
			sb.append("@attribute jaggedMin7 numeric\n");
			sb.append("@attribute jaggedMax7 numeric\n");
			sb.append("@attribute jaggedRange7 numeric\n");
			
			sb.append("@attribute marginMean7 numeric\n");
			sb.append("@attribute marginSD7 numeric\n");
			sb.append("@attribute marginMin7 numeric\n");
			sb.append("@attribute marginMax7 numeric\n");
			sb.append("@attribute marginRange7 numeric\n");
			
			sb.append("@attribute stanzaMean7 numeric\n");
			sb.append("@attribute stanzaSD7 numeric\n");
			sb.append("@attribute stanzaMin7 numeric\n");
			sb.append("@attribute stanzaMax7 numeric\n");
			sb.append("@attribute stanzaRange7 numeric\n");

			
			sb.append("@attribute lengthMean7 numeric\n");
			sb.append("@attribute lengthSD7 numeric\n");
			sb.append("@attribute lengthMin7 numeric\n");
			sb.append("@attribute lengthMax7 numeric\n");
			sb.append("@attribute lengthRange7 numeric\n");
			
			sb.append("@attribute class {false,true}\n");

			sb.append("\n");
			sb.append("@data\n");		
			this.outStream.append(sb.toString());
			outStream.flush();
		}
	}
	
	/**
	 * This method write the data and its stats to the appropriate file that corresponds to the BinaryImage passed as an argument.
	 * Specifically, it is used for a 6x1 attribute output.
	 * @param binImg
	 * @throws IOException
	 */
	public void writeSingleLine(BinaryImage binImg) throws IOException{

		StringBuilder sb = new StringBuilder();
//		sb.append(binImg.getName()).append(",");
		
		sb.append(binImg.getJaggedLineMean()+",");
		sb.append(binImg.getJaggedLineStandardDeviation()+",");
		sb.append(binImg.getJaggedMin()+",");
		sb.append(binImg.getJaggedMax()+",");
		sb.append(binImg.getJaggedRange()+",");
		
		sb.append(binImg.getMarginMean()+",");
		sb.append(binImg.getMarginStdDev()+",");
		sb.append(binImg.getMarginMin()+",");
		sb.append(binImg.getMarginMax()+",");
		sb.append(binImg.getMarginRange()+",");

		
		sb.append(binImg.getStanzaMean()+",");
		sb.append(binImg.getStanzaStdDev()+",");
		sb.append(binImg.getStanzaMin()+",");
		sb.append(binImg.getStanzaMax()+",");
		sb.append(binImg.getStanzaRange()+",");
		
		sb.append(binImg.getLengthMean()+",");
		sb.append(binImg.getLengthStdDev()+",");
		sb.append(binImg.getLengthMin()+",");
		sb.append(binImg.getLengthMax()+",");
		sb.append(binImg.getLengthRange()+",");

		
		
		sb.append(binImg.getCheckValue()).append('\n');
		this.outStream.append(sb.toString());
		outStream.flush();

	}

	/**
	 * similar to writeSingleLine() but this image outputs 6x3 attributes for 3 binary images instead of one, typically each image 
	 * blurred more aggressively than the previous.
	 * @param img3
	 * @param img5
	 * @param img7
	 * @throws IOException
	 */
	public void writeTripleLine(BinaryImage img3, BinaryImage img5, BinaryImage img7) throws IOException{
		StringBuilder sb = new StringBuilder();
	//	sb.append(img3.getName()).append(",");

		sb.append(img3.getJaggedLineMean()+",");
		sb.append(img3.getJaggedLineStandardDeviation()+",");
		sb.append(img3.getJaggedMin()+",");
		sb.append(img3.getJaggedMax()+",");
		sb.append(img3.getJaggedRange()+",");
		          
		sb.append(img3.getMarginMean()+",");
		sb.append(img3.getMarginStdDev()+",");
		sb.append(img3.getMarginMin()+",");
		sb.append(img3.getMarginMax()+",");
		sb.append(img3.getMarginRange()+",");
                            
		sb.append(img3.getStanzaMean()+",");
		sb.append(img3.getStanzaStdDev()+",");
		sb.append(img3.getStanzaMin()+",");
		sb.append(img3.getStanzaMax()+",");
		sb.append(img3.getStanzaRange()+",");
		          
		sb.append(img3.getLengthMean()+",");
		sb.append(img3.getLengthStdDev()+",");
		sb.append(img3.getLengthMin()+",");
		sb.append(img3.getLengthMax()+",");
		sb.append(img3.getLengthRange()+",");
//-----------------------------------------------------------------------
		
		sb.append(img5.getJaggedLineMean()+",");
		sb.append(img5.getJaggedLineStandardDeviation()+",");
		sb.append(img5.getJaggedMin()+",");
		sb.append(img5.getJaggedMax()+",");
		sb.append(img5.getJaggedRange()+",");
		
		sb.append(img5.getMarginMean()+",");
		sb.append(img5.getMarginStdDev()+",");
		sb.append(img5.getMarginMin()+",");
		sb.append(img5.getMarginMax()+",");
		sb.append(img5.getMarginRange()+",");
		
    	sb.append(img5.getStanzaMean()+",");
		sb.append(img5.getStanzaStdDev()+",");
		sb.append(img5.getStanzaMin()+",");
		sb.append(img5.getStanzaMax()+",");
		sb.append(img5.getStanzaRange()+",");
		
		sb.append(img5.getLengthMean()+",");
		sb.append(img5.getLengthStdDev()+",");
		sb.append(img5.getLengthMin()+",");
		sb.append(img5.getLengthMax()+",");
		sb.append(img5.getLengthRange()+",");
//------------------------------------------------------------------------------------		          
		
		sb.append(img7.getJaggedLineMean()+",");
		sb.append(img7.getJaggedLineStandardDeviation()+",");
		sb.append(img7.getJaggedMin()+",");
		sb.append(img7.getJaggedMax()+",");
		sb.append(img7.getJaggedRange()+",");
		
		sb.append(img7.getMarginMean()+",");
		sb.append(img7.getMarginStdDev()+",");
		sb.append(img7.getMarginMin()+",");
		sb.append(img7.getMarginMax()+",");
		sb.append(img7.getMarginRange()+",");
		
    	sb.append(img7.getStanzaMean()+",");
		sb.append(img7.getStanzaStdDev()+",");
		sb.append(img7.getStanzaMin()+",");
		sb.append(img7.getStanzaMax()+",");
		sb.append(img7.getStanzaRange()+",");
		
		sb.append(img7.getLengthMean()+",");
		sb.append(img7.getLengthStdDev()+",");
		sb.append(img7.getLengthMin()+",");
		sb.append(img7.getLengthMax()+",");
		sb.append(img7.getLengthRange()+",");
		
		sb.append(img3.getCheckValue()).append('\n');

		this.outStream.append(sb.toString());
		outStream.flush();

	}

	/**
	 * This method is called to create, change and also indicate the nature of the analyis file that will be created. The boolean
	 * indicates whether the method is called for a single image(which will result in a 6x1 output) or not(resulting in a 6x3) output.
	 * @param filename
	 * @param singleFile
	 */
	public void changeOutput(String filename, boolean singleFile){
		this.outFile = new File(Constants.analysisFileDirectory, filename);
		try {
			outStream = new BufferedWriter(new FileWriter(this.outFile, true));
			if (singleFile){
				this.writeSingleHead();
			} else {
				this.writeTripleHead();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}

}
