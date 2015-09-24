package execute;

import featureExtraction.Morphology;
import global.Constants;

import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.imageio.ImageIO;

import weka.AnalysisFileWriter;
import models.BinaryImage;
import models.BlurredImage;
import models.Image;
import blurring.ImageBlurrer;

/**
 * The method that is responsible for all processes. It calls methods from blurring.ImageBlurrer to blur, binarize and use 
 * custom blurring. The output data is stored in the "data" folder.
 * 
 * filename is the path to the image to be processed.
 * @author maanas
 *
 */
public class RunProgram {
	public static void main(String args[]) throws IOException{
		int d = Integer.parseInt(args[0]);
		final int tripleRegular = 1,consolidated = 2,train = 3,test = 4;

		String flag2 = args[1];
		boolean isCustom = false;
		// Error Proofing the second flag. User error minimized. 
		if(flag2.equalsIgnoreCase("C") || flag2.equalsIgnoreCase("Custom")){
			isCustom = true;
		} else if(flag2.equalsIgnoreCase("R") || flag2.equalsIgnoreCase("Regular")){
			isCustom = false;
		} else {
			System.out.println("Error. Invalid Variable.");
			System.exit(1);
		}
		String inputFilename = args[2];
		ImageBlurrer imb = new ImageBlurrer();

		// Manual Controls
		int blurMode = consolidated;	// tripleRegular or consolidated
		int whatSet = test;			// 'test' for testing set of 7500+ snippets. 'train' for 400 training snippets
		boolean outCustom = true;		// Set true output the image, false when output of images themselves is not necessary
		boolean outBinary = true;
		
		if (blurMode == consolidated){
			isCustom = true;
		} else {
			isCustom = false;
		}

		/*
		 * July 23rd 2014: Recent Changes results in the code no longer using the first argument for depth since we need all three 
		 * depths for generating a 6x3 attribute dataset.
		 * 
		 * All the following methods are repreted on two other images with diff blur levels to generate statistics. In approach 2, 
		 * since only one is needed, the other two images and its processes are skipped.
		 */

		/*
		 * instanciating an image with appropriate name
		 */
		Image img3 = null;
		Image img5 = null;
		Image img7 = null;

		if (blurMode == tripleRegular){
			img3 = importImage(inputFilename);
			img7 = importImage(inputFilename); 
		} 
		img5 = importImage(inputFilename); 


		if (blurMode == tripleRegular){
			img3.setBlurLevel(3);
			img7.setBlurLevel(7);
		}
		img5.setBlurLevel(5);





		/* 
		 * To read in the check value of the image and assign it to the object.
		 */




		if(inputFilename.contains("true.jpg")){

			img5.setCheckValue(true);

			if(blurMode == tripleRegular){
				img3.setCheckValue(true);
				img7.setCheckValue(true);
			}

		}else{

			img5.setCheckValue(false);

			if(blurMode == tripleRegular){
				img3.setCheckValue(false);
				img7.setCheckValue(false);
			}
		}




		/*
		 * Calls to methods that blur, binarize and custom blur followed by feature extraction.
		 */



		//		---------------------------------------------------------------------------

		imb.blurImage(img5 ,true);
		imb.binarizeImage(img5, outBinary);
		if(isCustom){
			imb.customBlur(img5,outCustom);
		}



		//		img5.setByteImage(m.Dilation(img5.getByteImage()));
		//		img5.setByteImage(m.Erosion( img5.getByteImage()));
		//		img5.printImage(global.Constants.morphOutFolder);

		imb.importFeatures(img5);

		//		-----------------------------------------------------------------------------

		if(blurMode == tripleRegular){
			
			imb.blurImage(img3 ,false);
			imb.binarizeImage(img3, outBinary);
			if(isCustom){
				imb.customBlur(img3,outCustom);
			}

			//		Morphology m = new Morphology();
			//		img3.setByteImage(m.Dilation(img3.getByteImage()));
			//		img3.setByteImage(m.Erosion( img3.getByteImage()));
			//		img3.printImage(global.Constants.morphOutFolder);

			imb.importFeatures(img3);

			//		-----------------------------------------------------------------------------

			imb.blurImage(img7 ,false);
			imb.binarizeImage(img7, outBinary);
			if(isCustom){
				imb.customBlur(img7,outCustom);
			}

			//		img7.setByteImage(m.Dilation(img7.getByteImage()));
			//		img7.setByteImage(m.Erosion( img7.getByteImage()));
			//		img7.printImage(global.Constants.morphOutFolder);
			//		
			imb.importFeatures(img7);
			
		}

		/*
		 * creating an instance of the class responsible for the analysis file output.
		 */

		AnalysisFileWriter afw = new AnalysisFileWriter();
		afw.importFilenames();
		if(whatSet==train){
			afw.changeOutput("TrainList.arff", blurMode==consolidated);
		} else if(whatSet==test){
			afw.changeOutput("TestList.arff", blurMode==consolidated);
		}
		 

		//		
		BlurredImage bli5 = new BlurredImage(img5);
		BinaryImage bni5 = new BinaryImage(bli5);

		if(blurMode==tripleRegular){
			BlurredImage bli3 = new BlurredImage(img3);
			BinaryImage bni3 = new BinaryImage(bli3);

			BlurredImage bli7 = new BlurredImage(img7);
			BinaryImage bni7 = new BinaryImage(bli7);
			
			afw.writeTripleLine(bni3, bni5, bni7);

		} else {
			afw.writeSingleLine(bni5); //writes the stats to the respective *.arff file
		}

				
		



			
	}

	/**
	 * This method imports the image whose filepath is passed as a parameter and returns an models.Image object.
	 * @param inputFilename
	 * @return models.Image
	 */
	public static Image importImage(String inputFilename){
		Image img = new Image();
		int w=0,h=0;
		try {
			System.out.println("Loading Image..");
			File inputImageFile = new File(Constants.data, inputFilename);
			BufferedImage inputImage = ImageIO.read(inputImageFile);

			Raster raster = inputImage.getData();
			w = raster.getWidth();
			h = raster.getHeight();

			img.setVertical(h);
			img.setHorizontal(w);
			int pixels[][] = new int[h][w];

			//read the pixels from the input image
			for (int i = 0; i < h; i++) {
				for (int j = 0; j < w; j++) {
					pixels[i][j] = raster.getSample(j, i, 0);

				}
			}
			img.setByteImage(pixels);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String[] s = inputFilename.split("/");
		img.setName(s[s.length-1]);
		img.setParentName(s[1]);
		img.setHorizontal(img.byteImage[0].length);
		img.setVertical(img.byteImage.length);
		return img;
	}
}
