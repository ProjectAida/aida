package featureExtraction;

import java.util.Scanner;import models.BinaryImage;
/** * This class analyzes blurred images and creates numerical summaries of image attributes. * @author mdatla * */
public class FeatureExtraction {

	public double getMarginMEAN() {
		return marginMEAN;
	}

	public void setMarginMEAN(double marginMEAN) {
		this.marginMEAN = marginMEAN;
	}

	public double getMarginSTDEV() {
		return marginSTDEV;
	}

	public void setMarginSTDEV(double marginSTDEV) {
		this.marginSTDEV = marginSTDEV;
	}

	public double getJaggedMEAN() {
		return jaggedMEAN;
	}

	public void setJaggedMEAN(double jaggedMEAN) {
		this.jaggedMEAN = jaggedMEAN;
	}

	public double getJaggedSTDEV() {
		return jaggedSTDEV;
	}

	public void setJaggedSTDEV(double jaggedSTDEV) {
		this.jaggedSTDEV = jaggedSTDEV;
	}


	private int WIDTH;
	private int DEPTH; 
	private static int WOFFSET;  // 30 pixels 		private static int DOFFSET;
	private int BACKGROUND = 255;
	private int OBJECT = 0;
	private BinaryImage image;
	private int[] leftColumnWidths;
	private int[] rightColumnWidths;
	private int[] lineLength;
	private int[][] rowDepths;

	public int[][] getRowDepths() {		return rowDepths;	}	public void setRowDepths(int[][] rowDepths) {		this.rowDepths = rowDepths;	}	private double marginMEAN;
	private double marginSTDEV;
	private double marginMIN;
	private double marginMAX;
	private double marginRANGE;

	private double jaggedMEAN;
	private double jaggedSTDEV;
	private double jaggedMIN;
	private double jaggedMAX;
	private double jaggedRANGE;

	private double stanzaMEAN;
	private double stanzaSTDEV;
	private double stanzaMIN;
	private double stanzaMAX;
	private double stanzaRANGE;

	private double lengthMEAN;
	private double lengthSTDEV;
	private double lengthMIN;
	private double lengthMAX;
	private double lengthRANGE;
	/**	 * Constructor that uses a BinaryImage to initialize variables needed for creating a nummerical summary.	 * @param inputImage	 */
	public FeatureExtraction(BinaryImage inputImage)  {

		image = inputImage;
		WIDTH = image.getHorizontal();
		DEPTH = image.getVertical();				WOFFSET = (int) (WIDTH*0.1);				DOFFSET = (int) (DEPTH*0.1);
		leftColumnWidths = new int[DEPTH];
		rightColumnWidths = new int[DEPTH];
		lineLength = new int[DEPTH];
		rowDepths = new int[DEPTH][WIDTH];
		for (int i = 0; i < DEPTH; i++){
			lineLength[i] = 0; // initialization
			for (int j = 0; j < WIDTH; j++)
				rowDepths[i][j] = -1;  // initialization
		}

	}
	public void printRowDepths(){		for(int i=0; i<DEPTH;i++){			for(int j=0;j<WIDTH;j++){			}			System.out.println();		}	}
	public void computeColumnWidths()  {

		boolean stillBackground;
		int numPixSoFarFromLeft;
		int numPixSoFarFromRight;

		for (int i = 0; i < DEPTH; i++)  {

			// going from left to right
			numPixSoFarFromLeft = 0;
			stillBackground = true;
			int j = WOFFSET;  
			while  (stillBackground && j < WIDTH - WOFFSET)  {
				if (image.getBinaryImagePixels()[i][j] == OBJECT)
					stillBackground = false;
				else
					numPixSoFarFromLeft++;
				j++;
			}

			// going from right to left
			numPixSoFarFromRight = 0;
			stillBackground = true;
			j = WIDTH - WOFFSET;  
			while  (stillBackground && j >= WOFFSET)  {
				if (image.getBinaryImagePixels()[i][j] == OBJECT)
					stillBackground = false;
				else
					numPixSoFarFromRight++;
				j--;
			}

			leftColumnWidths[i] = numPixSoFarFromLeft;
			rightColumnWidths[i] = numPixSoFarFromRight;			
		}

	}  // end computeColumnWidths

	/**
	 * This method goes through each column of the image.  For each column, 
	 * it goes from top to bottom, and collects every sequence of continuous
	 * background pixels.  For each sequence, it counts the number of pixels
	 * -- the "height" or "thickness" of each sequence.  It saves the "height"
	 * or "thickness" into a 2D array -- i.e., rowDepths[][].  
	 * The 2D array has been initialied with -1 in the constructor. 
	 * Note that it is possible for a column to have only background pixels! 
	 * In that case, if that column is at j, then rowDepths[][j] will have 
	 * -1 as their values.
	 * 
	 * So, if a column j has the following sequences:  10 background pixels,
	 * 5 object pixels, 13 background pixels, 4 object pixels, 12 background
	 * pixels, etc., then we will have:
	 *
	 *  rowDepths[0][j] = 10
	 *  rowDepths[1][j] = 13
	 *  rowDepths[2][j] = 12
	 *  ...
	 *  ...
	 *  ...
	 *  rowDepths[i][j] = -1
	 *  rowDepths[i+1][j] = -1
	 *  ...
	 *  ...
	 */

	public void computeRowDepths()  {

		int numPixSoFar;
		int k;  // to hold the index to store each gap's depth
		for (int j = WOFFSET; j < WIDTH - WOFFSET; j++)  {
			k = 0;  // first index

			// going from top to bottom
			numPixSoFar = 0;
			int i = DOFFSET;  
			while  (i < DEPTH - DOFFSET)  {
				if (image.getBinaryImagePixels()[i][j] == OBJECT){
					// we have found the first break of background pixels gap
					if (numPixSoFar > 0) {  // only update if we have accumulated
						rowDepths[k][j] = numPixSoFar;						//						System.out.println(k+","+j+","+numPixSoFar);
						numPixSoFar = 0; // reset for the next gap
						k++;    // update index for the next gap
					}				}
				else
					numPixSoFar++;
				i++;
			}

		}  // end for j loop
	}  // end computeRowDepths


	public void computeMarginStats()  {

		// First compute the mean
		int max= Integer.MIN_VALUE;
		int min=Integer.MAX_VALUE;

		double sum = 0;
		for (int i = 0; i < DEPTH; i++){
			sum+= leftColumnWidths[i];
			if(leftColumnWidths[i]<min)
				min=leftColumnWidths[i];
			if(leftColumnWidths[i]>max)
				max=leftColumnWidths[i];
		}

		marginMEAN = sum/(1.0*(DEPTH-(2*WOFFSET)));
		marginMAX=max;
		marginMIN=min;
		marginRANGE=Math.abs(min-max);

		// Then use the mean to compute the standard deviation

		sum = 0.0;
		for (int i = 0; i < DEPTH; i++)  {
			sum += (leftColumnWidths[i] - marginMEAN)*(leftColumnWidths[i] - marginMEAN);
		}

		marginSTDEV = Math.sqrt(sum/(1.0*(DEPTH-(2*WOFFSET))));

	}  // end computeMarginStats


	public void computeJaggedStats()  {

		// First compute the mean
		int max= 0;//Integer.MIN_VALUE;
		int min=0;//Integer.MAX_VALUE;

		double sum = 0;
		for (int i = 0; i < DEPTH; i++){
			sum+= rightColumnWidths[i];
//			if(rightColumnWidths[i]<min)//
//				min=rightColumnWidths[i];//
//			if(rightColumnWidths[i]>max)//
//				max=rightColumnWidths[i];			if(i<DEPTH-1){				if(rightColumnWidths[i]!=rightColumnWidths[i+1]){					max+=Math.abs( (rightColumnWidths[i]-rightColumnWidths[i+1]) );				}				if(leftColumnWidths[i]!=leftColumnWidths[i+1]){					min+=Math.abs( (leftColumnWidths[i]-leftColumnWidths[i+1]) );				}			}
		}

		jaggedMEAN = sum/(1.0*(DEPTH-(2*WOFFSET)));				
		jaggedMAX=max;
		jaggedMIN=min;
		//jaggedRANGE=Math.abs(max-min);

		// Then use the mean to compute the standard deviation

		sum = 0.0;
		for (int i = 0; i < DEPTH; i++)  {
			sum += (rightColumnWidths[i] - jaggedMEAN)*(rightColumnWidths[i] - jaggedMEAN);
		}

		jaggedSTDEV = Math.sqrt(sum/(1.0*(DEPTH-(2*WOFFSET))));

	}  // end computeJaggedStats

	public void computeStanzaStats()  {

		// First compute the mean
		int max= Integer.MIN_VALUE;
		int min=Integer.MAX_VALUE;


		int n = 0;  // to count the number of sequences
		double sum = 0;
		for (int j = WOFFSET; j < WIDTH - WOFFSET; j++)
			for (int i = 0; i < DEPTH; i++)
				if (rowDepths[i][j] != -1)  {
					sum+= rowDepths[i][j];					//					System.out.print(rowDepths[i][j]);
					n++;
					if(rowDepths[i][j]<min)
						min = rowDepths[i][j];
					if(rowDepths[i][j]>max)
						max = rowDepths[i][j];
				}
		if(n==0){			stanzaMEAN = 0;		} else{
			stanzaMEAN = sum/(1.0*n);		}
		stanzaMAX = max;
		stanzaMIN = min;
		stanzaRANGE = Math.abs(max-min);

		// Then use the mean to compute the standard deviation

		sum = 0.0;
		for (int j = WOFFSET; j < WIDTH - WOFFSET; j++)
			for (int i = 0; i < DEPTH; i++)
				if (rowDepths[i][j] != -1)  {
					sum+= (rowDepths[i][j] - stanzaMEAN)*(rowDepths[i][j] - stanzaMEAN);
				}
		
		if(n==0){			stanzaSTDEV = 0;		} else{			stanzaSTDEV = Math.sqrt(sum/(1.0*n));					}
		

	}  // end computeStanzaStats

	public void computeLength(){
		boolean isConsecutive = true;
		for(int i=DOFFSET; i<DEPTH-DOFFSET;i++){
			isConsecutive=true;
			int j = WOFFSET;
			while (j < WIDTH-WOFFSET && isConsecutive)  {
				if (image.getBinaryImagePixels()[i][j] == OBJECT)  {
					lineLength[i]+=1;
					j++;
				}
				else{
					isConsecutive = false;
				}

			}

		}
	}


	public void computeLengthStats(){
		double sum=0.0;
		int count=0;
		int max= Integer.MIN_VALUE;
		int min=Integer.MAX_VALUE;
		for (int i=DOFFSET; i<DEPTH-DOFFSET;i++){
			sum+=lineLength[i];
			count++;
			if(lineLength[i]>max){
				max = lineLength[i];
			} if(lineLength[i]<min){
				min = lineLength[i];
			}
		}

		lengthMEAN = sum/count;
		lengthMAX = max;
		lengthMIN = min;
		lengthRANGE = Math.abs(max-min);

		sum = 0;
		for(int i = 0; i < DEPTH; i++){
			sum+= (lineLength[i]-lengthMEAN)*(lineLength[i]-lengthMEAN);
		}

		lengthSTDEV = Math.sqrt(sum/(DEPTH-(2*WOFFSET)));
	}


	public int[] getLineLength() {
		return lineLength;
	}

	public void setLineLength(int[] lineLength) {
		this.lineLength = lineLength;
	}

	public double getMarginMIN() {
		return marginMIN;
	}

	public void setMarginMIN(double marginMIN) {
		this.marginMIN = marginMIN;
	}

	public double getMarginMAX() {
		return marginMAX;
	}

	public void setMarginMAX(double marginMAX) {
		this.marginMAX = marginMAX;
	}

	public double getMarginRANGE() {
		return marginRANGE;
	}

	public void setMarginRANGE(double marginRANGE) {
		this.marginRANGE = marginRANGE;
	}

	public double getJaggedMIN() {
		return jaggedMIN;
	}

	public void setJaggedMIN(double jaggedMIN) {
		this.jaggedMIN = jaggedMIN;
	}

	public double getJaggedMAX() {
		return jaggedMAX;
	}

	public void setJaggedMAX(double jaggedMAX) {
		this.jaggedMAX = jaggedMAX;
	}

	public double getJaggedRANGE() {
		return jaggedRANGE;
	}

	public void setJaggedRANGE(double jaggedRANGE) {
		this.jaggedRANGE = jaggedRANGE;
	}

	public double getStanzaMEAN() {
		return stanzaMEAN;
	}

	public void setStanzaMEAN(double stanzaMEAN) {
		this.stanzaMEAN = stanzaMEAN;
	}

	public double getStanzaSTDEV() {
		return stanzaSTDEV;
	}

	public void setStanzaSTDEV(double stanzaSTDEV) {
		this.stanzaSTDEV = stanzaSTDEV;
	}

	public double getStanzaMIN() {
		return stanzaMIN;
	}

	public void setStanzaMIN(double stanzaMIN) {
		this.stanzaMIN = stanzaMIN;
	}

	public double getStanzaMAX() {
		return stanzaMAX;
	}

	public void setStanzaMAX(double stanzaMAX) {
		this.stanzaMAX = stanzaMAX;
	}

	public double getStanzaRANGE() {
		return stanzaRANGE;
	}

	public void setStanzaRANGE(double stanzaRANGE) {
		this.stanzaRANGE = stanzaRANGE;
	}

	public double getLengthMEAN() {
		return lengthMEAN;
	}

	public void setLengthMEAN(double lengthMEAN) {
		this.lengthMEAN = lengthMEAN;
	}

	public double getLengthSTDEV() {
		return lengthSTDEV;
	}

	public void setLengthSTDEV(double lengthSTDEV) {
		this.lengthSTDEV = lengthSTDEV;
	}

	public double getLengthMIN() {
		return lengthMIN;
	}

	public void setLengthMIN(double lengthMIN) {
		this.lengthMIN = lengthMIN;
	}

	public double getLengthMAX() {
		return lengthMAX;
	}

	public void setLengthMAX(double lengthMAX) {
		this.lengthMAX = lengthMAX;
	}

	public double getLengthRANGE() {
		return lengthRANGE;
	}

	public void setLengthRANGE(double lengthRANGE) {
		this.lengthRANGE = lengthRANGE;
	}


}


