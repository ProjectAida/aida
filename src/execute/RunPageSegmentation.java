package execute;

import global.Constants;

import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.awt.image.Raster;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileWriter;
import java.io.BufferedWriter;

import javax.imageio.ImageIO;

import models.Image;
import blurring.ImageBlurrer;

import models.ReadIni;
import models.EnumCollection;


public class RunPageSegmentation {
	/**
	 * This main function is responsible for running the Full-page segmentation algorithm.
	 * It reads in either a single .jpg image or a text list of .jpg images and performs segmentation.
	 * If no argument given it runs on all images. Placement of these images is different from .txt or .jpg options.
	 * @param args
	 */
	public static void main(String[] args) {
                if(args.length > 0){
            //process images from a text file list
			if(args[0].contains(".txt")){
				String imageList = args[0];
				File inputImages = new File(Constants.imageLists,imageList);
				BufferedReader br = null;
				try {
					br = new BufferedReader(new FileReader(inputImages));
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}

				try {
					String line = br.readLine();
					int i = 1;
					while(line != null){
						System.out.println("Image "+i);
						Image img = importImage(line);
						try{
							segmentImage(img, false);
						}catch(RuntimeException r){
							System.out.println("ERROR: Unable to segment "+img.getName()+"\nPlease make sure that the image isn't rotated and has good contrast");
						}catch(Exception e){
							System.out.println("ERROR: Unable to segment "+img.getName()+"\nPlease make sure that the page isn't rotated and has good contrast");
						}
						line = br.readLine();
						i++;
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
            //process only one image
			}else if(args[0].contains(".jpg") || args[0].contains(".tif")){
                                Image img = importImage(args[0]);
				try{
					segmentImage(img, true);
				} catch(Exception e){
					e.printStackTrace();
					//System.out.println("ERROR: Unable to segment "+img.getName()+"\nPlease make sure that the page isn't rotated");
				}
			}
        //Process all images in the AIDA file structure created by the image retrieval script.
        //Currently legacy code as we now have a script that will do this process using a bash script
        //that repeatedly calls the process single image option.
		}else{
			File start = new File(Constants.fullPagePath);
			File successFile = new File(Constants.successSegment);
			BufferedWriter successStream = null;
			try{
				successStream = new BufferedWriter(new FileWriter(successFile,false));
			}catch(Exception e){
				System.out.println("Failed to create BufferedWriter");
			}
			StringBuilder sb = new StringBuilder();
			File[] newspapers = start.listFiles(new FileFilter(){
				@Override
				public boolean accept(File file){
					return !file.isHidden();
				}
			});
			int numOfNewspapers = newspapers.length;
			int currentPaper = 0;
			System.out.println("Segmenting Images...");
			for(File file : newspapers){
				currentPaper++;
				File[] issues = file.listFiles(new FileFilter(){
					@Override
					public boolean accept(File file){
						return !file.isHidden();
					}
				});
				int numOfIssues = issues.length;
				int currentIssue = 0;
				for(File issue : issues){
					currentIssue++;
					File[] images = issue.listFiles(new FileFilter(){
						@Override
						public boolean accept(File file){
							return !file.isHidden();
						}
					});
					int numOfImages = images.length;
					int currentImage = 0;
					for(File image : images){
						if(image.getName().contains(".jpg") || image.getName().contains(".tif")){
							currentImage++;
							String path = image.getAbsolutePath();
							Image img = importImage(path);
							try{
								segmentImage(img, false);
								System.out.print("\rSegmented: Newspaper "+currentPaper+"/"+numOfNewspapers+" Issue "+currentIssue+"/"+numOfIssues+" Image "+currentImage+"/"+numOfImages+" in "+file.getName()+"       ");
								sb.append(img.getName()+"\n");
							}catch(RuntimeException r){
								System.out.println();
								System.out.println("ERROR: Unable to segment "+img.getName()+"\nPlease make sure that the image isn't rotated and has good contrast");
	            		            			r.printStackTrace();
										File output = new File(Constants.data,"FailedList.txt");    
	            		            					try {
	            		            						if(!output.exists())
	            		            							output.createNewFile();
	            		            						FileWriter writer = new FileWriter(output,true);
	            		            						writer.write("Runtime Exception: "+img.getName()+"\n");
											writer.close();
	            		            					} catch (IOException e) {
	            		            						// TODO Auto-generated catch block
	            		            						e.printStackTrace();
	            		            					}
	            		            
							}catch(Exception e){
								System.out.println();
								System.out.println("ERROR: Unable to segment "+img.getName()+"\nPlease make sure that the page isn't rotated and has good contrast");
								
										File output = new File(Constants.data,"FailedList.txt");
										try {
		        		            					if(!output.exists())
		        		            						output.createNewFile();
		        		            					FileWriter writer = new FileWriter(output,true);
		        		            					writer.write("Normal  Exception: "+img.getName()+"\n");
											writer.close();
		        		            				} catch (IOException i) {
		        		            					// TODO Auto-generated catch block
		        		            					e.printStackTrace();
		        		            					}
								
							}
						}
					}
				}
			}
			try{
				successStream.append(sb.toString());
				successStream.flush();
				successStream.close();
			}catch(Exception e){
				System.out.println("Writing to file Failed. Unexpected error");
			}
		}
	}

	
	/**
	 * This method imports the image whose filepath is passed as a parameter and returns an models.Image object.
	 * @param inputFilename
	 * @return models.Image
	 */
	public static Image importImage(String inputFilename){
		BufferedImage inputImage = null;
		int w=0,h=0;
		try {
			//System.out.println("Loading Image..");
			File inputImageFile = new File(inputFilename);
			inputImage = ImageIO.read(inputImageFile);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
			
		Raster raster = inputImage.getData();
		w = raster.getWidth();
		h = raster.getHeight();
		Image img = new Image(h,w);
		int pixels[][] = new int[h][w];

		//read the pixels from the input image
		for (int i = 0; i < h; i++) {
			for (int j = 0; j < w; j++) {
				pixels[i][j] = raster.getSample(j, i, 0);
			}
		}
		
                // Copy Yi's code
                /***start***/
                Boolean isBin = true;
                /***Safety check***/
                for(int i = 0; i < h; i++){
                        for(int j = 0; j < w; j++) {
                                if(pixels[i][j] != 1) {
                                        isBin = false;
                                }
                                if(pixels[i][j] == 0) {
                                        isBin = true;
                                }
                                if(!isBin) { break; }
                        }
                        if(!isBin) { break; }
                }
                if(isBin) {
                        for(int i = 0; i < h; i++){
                                for(int j = 0; j < w; j++) {
                                        if(pixels[i][j] == 1){
                                                pixels[i][j] = 255;
                                        }
                                }
                        }
                }
                /***end***/
		img.setByteImage(pixels);
		img.setByteImage2(pixels);
               		
		img.setName(inputFilename.substring(inputFilename.lastIndexOf("/")+1));
			
		return img;
	}
	
	/**
	 * A helper method for grouping together the function calls for image segmentation.
	 * @param img
	 */
	public static void segmentImage(Image img, boolean shouldShowColumns){
            
            ImageBlurrer imb = new ImageBlurrer();
        
        //boolean values indicate if we want to output the intermediate stages of binarizing the image
        //Stages: contrasted, binary, binary with Morphology
                
                // 9.17.2017. Added this branch to skip binarization if image is already binarized
                //if(myConfig.GetNeedBinarizing() == myEnums.GetIntOfTrueFalse("TRUE")){
                    imb.binarizeSegment(img, false, false, false);
                //}
		
		int shouldContinue = img.findColumnBreaks();
        System.out.println(shouldContinue);
        
        //Continue the process if image exited with no error
        //Otherwise stop processing the image and output the custom error message to a file
        if(shouldContinue == 0){
            File output = new File(Constants.data, "imagePassed.txt");
            try {
                if(!output.exists()) {
                    output.createNewFile();
                }
                FileWriter writer = new FileWriter(output, true);
                writer.write(img.getName()+", "+img.getColumnBreaks()+"\n");
                writer.close();
            } catch(IOException ioe) {
                ioe.printStackTrace();
            }
            
            if(shouldShowColumns) {
                img.showColumnBreaks();
            }
            
            img.convertPageToSnippets(true);
        } else {
            if(shouldShowColumns) {
                img.showColumnBreaks();
            }
            String error = "";
            switch(shouldContinue){
                case 1:
                    error = "No columns found, "+img.getColumnBreaks();
                    break;
                case 2:
                    error = "Only one or two columns found, "+img.getColumnBreaks();
                    break;
                case 3:
                    error = "Columns are only on half of the page, "+img.getColumnBreaks();
                    break;
                case 4:
                	error = "Std Dev Above 150, "+img.getColumnBreaks();
                	break;
            }
            File output = new File(Constants.data, "imageFailedNeedHuman.txt");
            try {
                if(!output.exists()) {
                    output.createNewFile();
                }
                FileWriter writer = new FileWriter(output, true);
                writer.write(img.getName()+", "+error+"\n");
                writer.close();
            } catch(IOException ioe) {
                ioe.printStackTrace();
            }
        }
	}
}
