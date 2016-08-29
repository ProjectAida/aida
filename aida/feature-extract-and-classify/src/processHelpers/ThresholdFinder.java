package processHelpers;

import models.BlurredImage;

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
	
	/**
	 * Method determines the threshold for the blurred image. Threshold value found by
	 * processing the image's histogram using bi-gaussian processing.
	 * @return threshold
	 */
	public int determineThreshold(){
		int threshold = 0;
		 
		int localMean = (int)this.mean;
		//System.out.println(localMean);
		    
		int start = 0;
		long sum = 0;
		long totalPoints = 0;
		for (int i = start; i < localMean; i++) {
			long points = this.histogram[i];
			if(i==0){
				sum = points;
			}else{
		       sum += i*points;
			}
		       //System.out.println("["+i+"]"+points);
		       totalPoints += points;
		}  /* end for */

		double little_mean = (1.0*sum)/(1.0*totalPoints);
		

		/* Find the minimum in the valley region */
		int min1 = (int)Math.floor(little_mean+0.5);
		int min2 = min1 - 1;
		for (int i = (int)Math.floor(little_mean+0.5); i < localMean; i++) {
			if (histogram[i] < histogram[min1])
				min1 = i;
		}

		int max1 = (int) Math.floor(little_mean+0.5);
		for (int i = (int) Math.floor(little_mean+0.5); i > start; i--) {
			if (histogram[i] > histogram[max1])
				max1 = i;
		}

		for (int i = (int) Math.floor(little_mean+0.5); i > max1; i--) {
			if (histogram[i] < histogram[min2])
				min2 = i;
		}

		if (histogram[min2] < histogram[min1]){
			min1 = min2;
		}
		    
		threshold = min1; 
		System.out.println(threshold);
		return threshold;
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
