package processHelpers;

import binarizator.BinarizationEngine;
import models.BlurredImage;
import utility.HistToolkit;

public class ThresholdFinder {

	private long[] histogram;
	private double mean;
	private final int MAXINTENSITY = 256;
	
	public ThresholdFinder(){
		
		this.histogram = new long[MAXINTENSITY];
		for (int i = 0; i < MAXINTENSITY; i++){
			histogram[i] = 0;
		}
		this.mean = 0;
	}

	/**
	 * Creates a histogram of the blurred image pixel values. Histogram needed for
	 * bi-guassian processing.
	 * @param blurImg
	 */
	public void createImageHistogram(BlurredImage blurImg){
		int[][] blurredPixels = blurImg.getBlurredImagePixels();
		int horiz = blurImg.getHorizontal();
		int vert = blurImg.getVertical();

		for (int i = 0; i < vert; i++){
			for(int j= 0; j < horiz; j++){
				int value = blurredPixels[i][j];
				if((value >= 0)&&(value < MAXINTENSITY)){
					this.histogram[value]++;
				}
			}
		}
		this.computeMean();
		//System.out.println(this.mean);
	}
	
	private void computeMean(){
		long count = 0;
		long sum = 0;
		for(int i = 0; i < this.histogram.length; i++){
			//System.out.println("histogram["+i+"] "+this.histogram[i]);
			sum += (i * this.histogram[i]);
			count += (this.histogram[i]);
		}
		setMean((sum/count));
	}
	private double computeMean(long[] hist){
		long count = 0;
		long sum = 0;
		for(int i = 0; i < hist.length; i++){
			//System.out.println("histogram["+i+"] "+this.histogram[i]);
			sum += (i * hist[i]);
			count += (hist[i]);
		}
		return (((double)sum/(double)count));
	}
	/**
	 * Method determines the threshold for the blurred image. Threshold value found by
	 * processing the image's histogram using bi-gaussian processing.
	 * @return threshold
	 */
	public int determineThreshold(BlurredImage blurredImage){
		//D-Lib solution (old solution)
//				return BinarizationEngine.dlibBinMethod(computeMean(this.histogram), this.histogram);
		
		//Otsu's method (new solution)
				return BinarizationEngine.otsuMethod(HistToolkit.computeRDF(this.histogram));
		
		//3-mean method (foreground-bleed_through-background signals)
//				return BinarizationEngine.threeMeanBinMethod(this.histogram);
	}
	
	/******* Getters and Setters *******/
	public long[] getHistogram() {
		return histogram;
	}

	public void setHistogram(long[] histogram) {
		this.histogram = histogram;
	}
	
	public double getMean() {
		return mean;
	}

	public void setMean(double mean) {
		this.mean = mean;
	}
	
	
}
