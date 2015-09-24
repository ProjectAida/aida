package execute;

import global.Constants;

import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.imageio.ImageIO;

import models.Image;
import blurring.ImageBlurrer;

public class RunPageSegmentaion {

	/**
	 * This main function is responsible for running the Full-page segmentation algorithm.
	 * It reads in either a single .jpg image or a text list of .jpg images and performs segmentation.
	 * If no argument given it runs on all images. Placement of these images is different from .txt or .jpg options.
	 * @param args
	 */
	public static void main(String[] args) {
		if(args.length > 0){
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
							segmentImage(img);
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
			}else if(args[0].contains(".jpg")){
				Image img = importImage(args[0]);
				try{
					segmentImage(img);	
				} catch(Exception e){
					e.printStackTrace();
					//System.out.println("ERROR: Unable to segment "+img.getName()+"\nPlease make sure that the page isn't rotated");
				}
			}
		}else{
			File start = new File(Constants.fullPagePath);
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
						if(image.getName().contains(".jpg")){
							currentImage++;
							String path = image.getAbsolutePath();
							Image img = importImage(path);
							try{
								segmentImage(img);
								System.out.print("\rSegmented: Newspaper "+currentPaper+"/"+numOfNewspapers+" Issue "+currentIssue+"/"+numOfIssues+" Image "+currentImage+"/"+numOfImages+" in "+file.getName()+"       ");
							}catch(RuntimeException r){
								System.out.println();
								System.out.println("ERROR: Unable to segment "+img.getName()+"\nPlease make sure that the image isn't rotated and has good contrast");
							}catch(Exception e){
								System.out.println();
								System.out.println("ERROR: Unable to segment "+img.getName()+"\nPlease make sure that the page isn't rotated and has good contrast");
							}
						}
					}
				}
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
			
		
		img.setByteImage(pixels);
		img.setByteImage2(pixels);
		
		img.setName(inputFilename.substring(inputFilename.lastIndexOf("/")+1));
			
		return img;
	}
	
	/**
	 * A helper method for grouping together the function calls for image segmentation.
	 * @param img
	 */
	public static void segmentImage(Image img){
		ImageBlurrer imb = new ImageBlurrer();
		imb.binarizeSegment(img, true);
		
		img.findColumnBreaks();
		//System.out.println(img.getColumnBreaks());
		img.showColumnBreaks();
		
		img.convertPageToSnippets(true);
	}
}
