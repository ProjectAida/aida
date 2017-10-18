package blurring;

import global.Constants;
import models.BlurredImage;
import models.AdjustedImage;
import models.BinaryImage;
import models.Image;
import featureExtraction.*;
import processHelpers.SingleThresholdFinder;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.imageio.ImageIO;


/**
 * This class handles all the blurring algorithms employed by AIDA project.
 * @author mdatla
 *
 */
public class ImageBlurrer {
    
    private final int DILATION_NUM = 6;
    private final int EROSION_NUM = 8;

	/**
	 * This method imports the features (attributes) of the image passed as a parameter, and outputs a Attributes.csv with its values
	 * It first converts the image to a BinaryImage and then calculates all attributes. The header is printed to the output file
	 * if the *.csv file is empty. if not the attributes are appended to it.	
	 * @param im
	 * @throws IOException
	 */
	public void importFeatures(Image im) throws IOException{
		System.out.println("Extracting Features...");		
		BinaryImage bni = new BinaryImage(im);

		FeatureExtraction fe = new FeatureExtraction(bni);
		fe.computeColumnWidths();
		fe.computeRowDepths();
		fe.computeMarginStats();
		fe.computeJaggedStats();
//		fe.printRowDepths();
		fe.computeStanzaStats();
		fe.computeLength();
		fe.computeLengthStats();
		
	
		bni.setMarginMean(fe.getMarginMEAN()); 
		bni.setMarginStdDev(fe.getMarginSTDEV()); 
		bni.setMarginMax(fe.getMarginMAX());
		bni.setMarginMin(fe.getMarginMIN());
		bni.setMarginRange(fe.getMarginRANGE());
		
		
		bni.setJaggedLineMean(fe.getJaggedMEAN()); 
		bni.setJaggedLineStandardDeviation(fe.getJaggedSTDEV());
		bni.setJaggedMax(fe.getJaggedMAX());
		bni.setJaggedMin(fe.getJaggedMIN());
		bni.setJaggedRange(fe.getJaggedRANGE());
		 
		bni.setStanzaMean(fe.getStanzaMEAN());
		bni.setStanzaStandardDeviation(fe.getStanzaSTDEV());
		bni.setStanzaMax(fe.getStanzaMAX());
		bni.setStanzaMin(fe.getStanzaMIN());
		bni.setStanzaRange(fe.getStanzaRANGE());
		
//		System.out.println(fe.getStanzaMAX());
//		System.out.println(fe.getStanzaMIN());
//		System.out.println(fe.getStanzaMEAN());
//		System.out.println(fe.getStanzaSTDEV());
		
		bni.setLengthMean(fe.getLengthMEAN());
		bni.setLengthStdDev(fe.getLengthSTDEV());
		bni.setLengthMin(fe.getLengthMIN());
		bni.setLengthMax(fe.getLengthMAX());
		bni.setLengthRange(fe.getLengthRANGE());
						
		im.setMarginMean(fe.getMarginMEAN()); 
		im.setMarginStdDev(fe.getMarginSTDEV());
		im.setMarginMin(fe.getMarginMIN());
		im.setMarginMax(fe.getMarginMAX());
		im.setMarginRange(fe.getMarginRANGE());
		
		im.setJaggedLineMean(fe.getJaggedMEAN());
		im.setJaggedLineStandardDeviation(fe.getJaggedSTDEV());
		im.setJaggedMin(fe.getJaggedMIN());
		im.setJaggedMax(fe.getJaggedMAX());
		im.setJaggedRange(fe.getJaggedRANGE());
		
		im.setStanzaMean(bni.getStanzaMean());
		im.setStanzaStandardDeviation(bni.getStanzaStdDev());
		im.setStanzaMin(fe.getStanzaMIN());
		im.setStanzaMax(fe.getStanzaMAX());
		im.setStanzaRange(fe.getStanzaRANGE());
		
		im.setLengthMean(fe.getLengthMEAN());
		im.setLengthStdDev(fe.getLengthSTDEV());
		im.setLengthMin(fe.getLengthMIN());
		im.setLengthMax(fe.getLengthMAX());
		im.setLengthRange(fe.getLengthRANGE());
		/*
		BufferedWriter outStream = new BufferedWriter(new FileWriter(Constants.attributeOutput, true));
		BufferedReader br = new BufferedReader(new FileReader(Constants.attributeOutput));


		if(br.readLine() == null){
			StringBuilder sb = new StringBuilder();
			sb.append("Name").append(",");
			sb.append("Check value").append(",");
			sb.append("JaggedLineMean").append(",");
			sb.append("JaggedLineStdDev").append(",");
			sb.append("MarginMean").append(",");
			sb.append("MarginStdDev").append(",");
			sb.append("StanzaMean").append(",");
			sb.append("StanzaStdDev").append('\n');

			outStream.append(sb.toString());

		}


		StringBuilder sb = new StringBuilder();
		sb.append(bni.getName()).append(",");
		sb.append(bni.getCheckValue()).append(",");
		sb.append(bni.getJaggedLineMean()).append(",");
		sb.append(bni.getJaggedLineStandardDeviation()).append(",");
		sb.append(bni.getMarginMean()).append(",");
		sb.append(bni.getMarginStdDev()).append(",");
		sb.append(bni.getStanzaMean()).append(",");
		sb.append(bni.getStanzaStdDev()).append('\n');



		outStream.append(sb.toString());
		outStream.close();	

		br.close();
		*/


	}

	/**
	 * This method is responsible for the custom-blurring feature of the program. This method converts the 
	 * image passed as a parameter to a BinaryImage and uses Joe's code to custom-blur the image. It is then output to the Output_custom
	 * directory.
	 * @param im
	 */
	public void customBlur(Image im,  boolean shouldOutput){
		int w = im.getHorizontal(), h = im.getVertical();

		BinaryImage bni = new BinaryImage(im);

		CustomBlurrerEngine cbe = new CustomBlurrerEngine();
		bni.setByteImage((cbe.consolidateImage(bni)).getByteImage());

		BufferedImage OutputImage = new BufferedImage(w,h,BufferedImage.TYPE_INT_RGB);
		int[][] pixels3 = bni.getByteImage();
		for (int y = 0; y < bni.getVertical(); y++) {
			for (int x = 0; x < bni.getHorizontal(); x++) {
				//The following line offsets the pixels' values to fix the 'blue problem'
				int value = pixels3[y][x] << 16 | pixels3[y][x] << 8 | pixels3[y][x];
				OutputImage.setRGB(x, y, value);
			}
		}



		if(shouldOutput){
			System.out.println("Writing Custom Blurred Image..");
			outputImage(OutputImage, Constants.customOutput,im.getName());
		}
		
		im.setByteImage(bni.getByteImage());

	}

	/**
	 * This method is responsible for binarizing the image passed as a parameter. It converts the passes image to a 
	 * Blurred Image so it can be used with Joe's binarizing algorithm.
	 * The Binarized images are output to Output_Binary/ directory
	 * @param im
	 */
	public void binarizeImage(Image im, boolean shouldOutput){
		int w = im.getHorizontal(), h = im.getVertical();

		BlurredImage bli = new BlurredImage(im);
		SingleThresholdFinder tf = new SingleThresholdFinder();
		BinaryImage bni = tf.generateBinaryImage(im);
		BufferedImage OutputImage = new BufferedImage(w,h,BufferedImage.TYPE_INT_RGB);

		/***** This Segment is meant for printing out Binary images*****/
		int[][] pixels2 = bni.getByteImage();
		for (int y = 0; y < bni.getVertical(); y++) {
			for (int x = 0; x < bni.getHorizontal(); x++) {
				//The following line offsets the pixels' values to fix the 'blue problem'
				int value = pixels2[y][x] << 16 | pixels2[y][x] << 8 | pixels2[y][x];
				OutputImage.setRGB(x, y, value);
			}
		}

		
		if(shouldOutput){
			System.out.println("Writing Binary Blurred Image..");
			outputImage(OutputImage, Constants.binaryOutput,im.getName());
		}

		im.setByteImage(bni.getByteImage());
		im.setByteImage2(bni.getByteImage());

	}
	
    /**
     *Despite the name, this function is used for binarizing a full page image in preparation for segmentation.
     *This function also takes in three Boolean flags that allow for the output of each of the three intermediary
     *stages.
    */
	public void binarizeSegment(Image im, boolean shouldOutputContrasted, boolean shouldOutputBinary, boolean shouldOutputBinaryCleaned){
        
		int w = im.getHorizontal(), h = im.getVertical();
		
		BlurredImage bli = new BlurredImage(im);
		SingleThresholdFinder stf = new SingleThresholdFinder();
		BinaryImage bni;
        
		long points = 0;
		for(int a = 0; a < h; a++){
			for(int b = 0; b < w; b++){
				points += im.getByteImage2()[a][b]; 
			}
		}
		long average = points/(h*w);
		
		if(average > 120){
			int pixels[][] = new int[h][w];
			double a = 2, b = 1;
			
			for(int i = 0; i < h; i++){
				for(int j = 0; j < w; j++){
					int newPixel = (int) (a * (im.getByteImage2()[i][j] - 128) + 128 + b);
					if(newPixel < 0){
						newPixel = 0;
					}else if(newPixel > 255){
						newPixel = 255;
					}
					pixels[i][j] = newPixel;
				}
			}
			
            if(shouldOutputContrasted) {
                BufferedImage OutputImage = new BufferedImage(w,h,BufferedImage.TYPE_INT_RGB);
                
                /***** This Segment is meant for printing out Binary images*****/
                
                for (int y = 0; y < h; y++) {
                    for (int x = 0; x < w; x++) {
                        //The following line offsets the pixels' values to fix the 'blue problem'
                        int value = pixels[y][x] << 16 | pixels[y][x] << 8 | pixels[y][x];
                        OutputImage.setRGB(x, y, value);
                    }
                }
                outputImage(OutputImage, Constants.binaryOutput,"contrasted.jpg");
            }
			
			bli.setByteImage(pixels);
			bni = stf.generateBinaryImage(bli);
		}
		else{
			bni = stf.generateBinaryImage(bli);
		}
        
        if(shouldOutputBinary) {
            BufferedImage OutputImage = new BufferedImage(w,h,BufferedImage.TYPE_INT_RGB);
            
            /***** This Segment is meant for printing out Binary images*****/
            int[][] pixels2 = bni.getByteImage();
            for (int y = 0; y < bni.getVertical(); y++) {
                for (int x = 0; x < bni.getHorizontal(); x++) {
                    //The following line offsets the pixels' values to fix the 'blue problem'
                    int value = pixels2[y][x] << 16 | pixels2[y][x] << 8 | pixels2[y][x];
                    OutputImage.setRGB(x, y, value);
                }
            }
            outputImage(OutputImage, Constants.binaryOutput,"binary.jpg");
        }

		//Perform Erosion/Dilation on binary image before outputting
        //To change the number of times Erosion or Dilation runs, simply change the constants
        //located at the top of this file.
		Morphology morph = new Morphology();
		int[][] erodedPixels = bni.getByteImage();
		for(int i = 0; i < DILATION_NUM; i++){
			erodedPixels = morph.Dilation(erodedPixels);
		}
		for(int i = 0; i < EROSION_NUM; i++){
			erodedPixels = morph.Erosion(erodedPixels);
		}
		
		bni.setByteImage(erodedPixels);
		
        if(shouldOutputBinaryCleaned) {
            BufferedImage OutputImage2 = new BufferedImage(w,h,BufferedImage.TYPE_INT_RGB);
            for (int y = 0; y < bni.getVertical(); y++) {
                for (int x = 0; x < bni.getHorizontal(); x++) {
                    //The following line offsets the pixels' values to fix the 'blue problem'
                    int value = erodedPixels[y][x] << 16 | erodedPixels[y][x] << 8 | erodedPixels[y][x];
                    OutputImage2.setRGB(x, y, value);
                }
            }
            String baseName = im.getName().substring(0, im.getName().length()-4);
            outputImage(OutputImage2, Constants.binaryOutput, baseName+"_cleaned.jpg");
        }
		
		im.setByteImage(bni.getByteImage());

	}
	
	/**
	 * This method is responsible for adjust the image passed as a parameter, and output the image to OutPut_Adjusted/ directory.
	 * @param im
	 * @param shouldOutput
	 */
	public void adjustImage(Image im, boolean shouldOutput) {
		AdjustedImage adjIm = new AdjustedImage(im);
		
		System.out.println("Adjusting Contrast..");
		adjIm.AdjustContrast();

		if(shouldOutput){
			BufferedImage OutputImage = new BufferedImage(adjIm.getHorizontal(),
					adjIm.getVertical(),
					BufferedImage.TYPE_INT_RGB);
			
			int[][] pixels2 = adjIm.getByteImage();
			for (int y = 0; y < adjIm.getVertical(); y++) {
				for (int x = 0; x < adjIm.getHorizontal(); x++) {
					//The following line offsets the pixels' values to fix the 'blue problem'
					int value = pixels2[y][x] << 16 | pixels2[y][x] << 8 | pixels2[y][x];
					OutputImage.setRGB(x, y, value);
				}
			}
			
			System.out.println("Writing Binary Adjusted Image..");
			outputImage(OutputImage, Constants.adjustedOutput,im.getName());
		}
		
		im.setByteImage(adjIm.getByteImage());
		
	}

	/**
	 * This method is responsible for blurring the image passed as a parameter, and output the image to Output_Blurred/ directory.
	 * It calls a secondary method 'getAverage' to compute the average pixel value of the grid around the pixel depending on the BlurLevel
	 * indicated by the image.
	 * @param im
	 */
	public void blurImage(Image im, boolean shouldOutput){

		int w = im.getHorizontal(), h = im.getVertical();

		System.out.println("Blurring..");
		int[][] blurredByteImg = new int[h][w];
		for(int i=0;i<h;i++){
			for(int j=0;j<w;j++){
				blurredByteImg[i][j] = this.getAverage(im,i,j,im.getBlurLevel());
			}
		}
		/*
		 * problematic
		 */
		im.setByteImage(blurredByteImg);
		
		BufferedImage OutputImage = new BufferedImage(w,h,BufferedImage.TYPE_INT_RGB);
		System.out.println("Preparing Image...");

		int[][] pixels = im.getByteImage();
		for (int y = 0; y < h; y++) {
			for (int x = 0; x < w; x++) {
				//The following line offsets the pixels' values to fix the 'blue problem'
				int value = pixels[y][x] << 16 | pixels[y][x] << 8 | pixels[y][x];
				OutputImage.setRGB(x, y, value);
			}
		}

		

		if(shouldOutput){
			System.out.println("Writing Blurred Image..");
			outputImage(OutputImage, Constants.BlurredOutput,im.getName());
		}
			


	}


	// These variables keep track of the corners of the desired grid of pixels depending on the required depth of blurring

	/**
	 * this method computes the average of the value of the pixel at (j,i), i.e. j represents the x axis and 
	 * i represents the y axis. It first finds the corners of pixel grid around the pixel and checks if the pixel is a corner 
	 * or an edge or a body pixel. It averages the pixel accordingly by calling the appropriate method.
	 * See Program Description for more details //++(TODO)++//
	 * @param im
	 * @param i
	 * @param j
	 * @param d
	 * @return
	 */
	public int getAverage(Image im, int i,  int j, int d){
		boolean lowerRC=true, //these variables were meant to be used for checking, but i found a better option.
				lowerLC=true, //but these variable may be useful later if and when efficiency needs to improve
				upperRC=true,
				upperLC=true;
		int countFalse = 0;   // this variable keeps track of the number of corners that are out of bounds.

		int[][] img = im.getByteImage();
		double result=0;

		// This cluster checks if the corner is out of bounds. Will be set to false if pixel is out of bounds.
		if((i+(d/2) > img.length-1 ) || (j+(d/2) > img[i].length-1)){
			lowerRC = false;
			countFalse++;
		} if ((i+(d/2) > img.length-1) || (j-(d/2) < 0)){
			lowerLC = false;
			countFalse++;
		} if ((i-(d/2) < 0) || (j+(d/2) > img[i].length-1)){
			upperRC = false;
			countFalse++;
		} if ((i-(d/2) < 0) || (j-(d/2) < 0)){
			upperLC = false;
			countFalse++;
		}

		if (countFalse==2){			 // If the number of corners out of bounds is 2, it means the pixel is at the edge of the image
			result=edgeAverage(img, i, j, d);
		} else if (countFalse==3){	 // If the No. of corners out of bounds is 3, it means the pixel is at the corner of the image
			result=cornerAverage(img, i, j, d);
		} else if (countFalse == 0){ //	If no corners are out of bounds, the pixel is a body pixel.
			result=Average(img, i, j, d);
		} else {					 // Any other case means something went wrong in the process. countFalse can only be 3,2 or 0.
			System.err.println("Error Occured.\n Location: Corner Checking (blurrer.ImageBlurrer)");
			System.exit(1);
		}

		return (int)result;
	}
	/**
	 * This method is meant to calculate the average of the pixel if the pixel is a body pixel. It takes the values of all the
	 * pixels in the required grid size, and averages it into the current pixel.
	 * @param img
	 * @param i
	 * @param j
	 * @param d
	 * @return
	 */
	private double Average(int[][] img, int i, int j, int d) {
		double total = 0.0;
		int x1 = j-(d/2),
				x2 = j+(d/2),
				y1 = i-(d/2),
				y2 = i+(d/2);
		int countPixel = 0;
		for (int a=y1; a<=y2;a++){
			for(int b=x1; b<=x2; b++){
				total+=img[a][b];
				countPixel++;
			}
		}

		return total/countPixel;
	}

	/**
	 * This method is meant to calculate the average of the grid around the pixel if it is a corner pixel.
	 * it checks which corner is the pixel present in and sets up bounds for the for loop so it can be iterated through and
	 * the average can be computed.
	 * @param img
	 * @param i
	 * @param j
	 * @param d
	 * @return
	 */
	private double cornerAverage(int[][] img, int i, int j, int d) {
		double total = 0.0;
		int x1=0,
				x2=0,
				y1=0,
				y2=0;
		if(i==0){
			y1 = i;
			y2 = i+(d/2);
		}else if (i == img.length-1){
			y1 = i-(d/2);
			y2 = i;		
		}

		if(j==0){
			x1 = j;
			x2 = j+(d/2);
		}else if(j == img[i].length-1){
			x1 = j-(d/2);
			x2 = j;
		}
		int countPixel = 0;
		for (int a=y1; a<=y2;a++){
			for(int b=x1; b<=x2; b++){
				total+=img[a][b];
				countPixel++;
			}
		}

		return total/countPixel;
	}

	/**
	 * This method is meant to calculate the average of the grid around the pixel if it is an edge pixel.
	 * it checks which edge is the pixel present in and sets up bounds for the for loop so it can be iterated through and
	 * the average can be computed.
	 * @param img
	 * @param i
	 * @param j
	 * @param d
	 * @return
	 */
	private double edgeAverage(int[][] img, int i, int j, int d) {
		double total = 0.0;
		int x1=0,
				x2=0,
				y1=0,
				y2=0;
		if(j==0){
			y1 = i-(d/2);
			y2 = i+(d/2);
			x1 = j;
			x2 = j+(d/2);
		} else if (j == img[i].length-1){
			y1 = i-(d/2);
			y2 = i+(d/2);
			x1 = j-(d/2);
			x2 = j;
		}else if (i==0){
			y1 = i;
			y2 = i+(d/2);
			x1 = j-(d/2);
			x2 = j+(d/2);
		} else if (i == img.length-1){
			y1 = i-(d/2);
			y2 = i;
			x1 = j-(d/2);
			x2 = j+(d/2);
		}
		int countPixel = 0;
		for (int a=y1; a<=y2;a++){
			for(int b=x1; b<=x2; b++){
				total+=img[a][b];
				countPixel++;
			}
		}

		return total/countPixel;
	}

	public void outputImage(BufferedImage image, String path, String name){
		File outputFile = new File(path,name);

		try {
			ImageIO.write(image, "png", outputFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
