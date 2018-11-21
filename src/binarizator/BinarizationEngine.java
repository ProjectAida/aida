package binarizator;

public class BinarizationEngine {
	/** 
	 * old binarization method used in D-Lib report.
	 * @param mean
	 * @param histogram
	 * @return
	 */
	public static int dlibBinMethod(double mean, long[] histogram){
		int threshold = 0;
		
		//**********start of old solution**********
		int localMean = (int)mean;
		//System.out.println(localMean);
		    
		int start = 0;
		long sum = 0;
		long totalPoints = 0;
		for (int i = start; i < localMean; i++) {
			long points = histogram[i];
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
		//**********end of old solution**********
		
		return threshold;
	}
	/**
	 * new solution for binarization (Otsu's method 1975)
	 * @param rdf
	 * @return
	 */
	public static int otsuMethod(double[] rdf) {
		int threshold = 0;
		//**********start of new solution**********
		// author: Yi Liu, Date: Jan.13 2017
		// reference: 
		// Otsu, Nobuyuki. "A threshold selection method from gray-level histograms." Automatica 11, no. 285-296 (1975): 23-27.
		// 
		//**********improvement of new solution**********
		// author: Yi Liu, Date: Feb. 3 2017
		// reference:
		// Fan, Jiu-Lun, and Bo Lei. "A modified valley-emphasis method for automatic thresholding." 
			//Pattern Recognition Letters 33, no. 6 (2012): 703-708.
		//
		// Optimal Threshold Approaching
		double ut = 0;
		for (int i = 0; i < rdf.length; i++) {
			ut += i * rdf[i];
		}
		double w0 = 0;
		double uk = 0;
		
		int tmp_threshold_1 = 0;
		int tmp_threshold_2 = 0;
		double max_diff = 0;
		for (int i = 0; i < rdf.length; i++) {
			w0 += rdf[i];
			if(w0 == 0) {
				continue;
			}
			double w1 = 1 - w0;
			if(w1 == 0) {
				break;
			}
			double u0 = uk / w0;
			uk += i * rdf[i];
			double u1 = (ut - uk) / w1;
			//********improvement********
//			double wndRangeSum = this.rdf[i];
//			for(int j = 1; j <= THRESHING_WND_OFFSET; j++) {
//				if(i-j >= 0) {
//					wndRangeSum += this.rdf[i-j]*Math.exp((j*j)/(2*(THRESHING_SIGMA*THRESHING_SIGMA)));
//					//wndRangeSum += this.rdf[i-j];
//				}
//				if(i+j < this.rdf.length) {
//					wndRangeSum += this.rdf[i+j]*Math.exp((j*j)/(2*(THRESHING_SIGMA*THRESHING_SIGMA)));
//					//wndRangeSum += this.rdf[i+j];
//				}
//			}
//			//double W_t_sigma = 1 - wndRangeSum;
//			double tmp = wndRangeSum * w0 * w1 * ((u1 - u0) * (u1 - u0));
			//********end of improvement********
			double tmp = w0 * w1 * ((u1 - u0) * (u1 - u0));
			if (tmp >= max_diff) {
				tmp_threshold_1 = i;
				if(tmp > max_diff) {
					tmp_threshold_2 = i;
				}
				max_diff = tmp;
			}
		}
		threshold = (int)(((double)tmp_threshold_1 + tmp_threshold_2) / 2.0);
		System.out.println("new" + threshold);
		//**********end of new solution**********
		return threshold;
	}
	/**
	 * Signal-wise binarization method (based on k-mean bleed-through removal)
	 * @param histogram (histogram of imput image)
	 * @return
	 */
	public static int threeMeanBinMethod(long[] histogram) {
		int threshold = 0;
		
		int nLvl = histogram.length;
		long [][] cluster1 = new long[2][nLvl];
		long [][] cluster2 = new long[2][nLvl];
		long [][] cluster3 = new long[2][nLvl];
		double cent1 = 0;
		double cent2 = 0;
		double cent3 = 0;

		long [][] tmpcluster1 = new long[2][nLvl];
		long [][] tmpcluster2 = new long[2][nLvl];
		long [][] tmpcluster3 = new long[2][nLvl];
		//initial with the centroids are evenly distributed to reach convergence faster
		int stepLen = (int)((double)nLvl / (double)(3+1));
		double tmpcent1 = stepLen;
		double tmpcent2 = stepLen * 2;
		double tmpcent3 = stepLen * 3;

		for(int i=0; i<2; i++){
			for(int j=0; j<nLvl; j++) {
				cluster1[i][j] = -1;
				cluster2[i][j] = -1;
				cluster3[i][j] = -1;
				tmpcluster1[i][j] = -1;
				tmpcluster2[i][j] = -1;
				tmpcluster3[i][j] = -1;
			}
		}

		boolean Cent1notEqual = true;
		boolean Cent2notEqual = true;
		boolean Cent3notEqual = true;

		while(Cent1notEqual && Cent2notEqual && Cent3notEqual) {
		    cent1 = tmpcent1;
		    cent2 = tmpcent2;
		    cent3 = tmpcent3;
		    
		    for(int i=0; i<nLvl; i++) {
		    	double dist1 = Math.abs(i-cent1);
		    	double dist2 = Math.abs(i-cent2);
		    	double dist3 = Math.abs(i-cent3);
		    	if(dist1 <= dist2 && dist1 <= dist3) {
		    		tmpcluster1[0][i] = i;
		            tmpcluster1[1][i] = histogram[i];
		    	} else if (dist2 <= dist1 && dist2 <= dist3) {
		    		tmpcluster2[0][i] = i;
		            tmpcluster2[1][i] = histogram[i];
		    	} else if (dist3 <= dist1 && dist3 <= dist2) {
		    		tmpcluster3[0][i] = i;
		            tmpcluster3[1][i] = histogram[i];
		    	}
		    }
		    double sum1 = 0;
		    double sum2 = 0;
		    double sum3 = 0;
		    double pts1 = 0;
		    double pts2 = 0;
		    double pts3 = 0;
		    for(int i=0; i<nLvl; i++) {
		    	if(tmpcluster1[0][i] != -1) {
		    		sum1 = sum1 + (tmpcluster1[0][i] * tmpcluster1[1][i]);
		            pts1 = pts1 + tmpcluster1[1][i];
		    	}
		    	if(tmpcluster2[0][i] != -1) {
		    		sum2 = sum2 + (tmpcluster2[0][i] * tmpcluster2[1][i]);
			        pts2 = pts2 + tmpcluster2[1][i];
		    	}
		    	if(tmpcluster3[0][i] != -1) {
		    		sum3 = sum3 + (tmpcluster3[0][i] * tmpcluster3[1][i]);
			        pts3 = pts3 + tmpcluster3[1][i];
		    	}
		    }

		    if(pts1 == 0) {
		        tmpcent1 = 0;
		    } else {
		    	tmpcent1 = sum1 / pts1;
		    }
		    if(pts2 == 0) {
		        tmpcent2 = 0;
		    } else {
		    	tmpcent2 = sum2 / pts2;
		    }
		    if(pts3 == 0) {
		        tmpcent3 = 0;
		    } else {
		    	tmpcent3 = sum3 / pts3;
		    }
		    
		    for (int i=0; i<2; i++) {
		    	for(int j=0; j<nLvl; j++) {
		    		cluster1[i][j] = tmpcluster1[i][j];
		    		cluster2[i][j] = tmpcluster2[i][j];
		    		cluster3[i][j] = tmpcluster3[i][j];
		    	}
		    }
		    
		    for(int i=0; i<2; i++){
				for(int j=0; j<nLvl; j++) {
					tmpcluster1[i][j] = -1;
					tmpcluster2[i][j] = -1;
					tmpcluster3[i][j] = -1;
				}
			}
		    
		    Cent1notEqual = cent1 != tmpcent1;
		    Cent2notEqual = cent2 != tmpcent2;
		    Cent3notEqual = cent3 != tmpcent3;
		}
		for (int i=0; i<nLvl; i++) {
			if(cluster2[0][i] != -1) {
				threshold = (int)cluster2[0][i];
				break;
			}
		}
		return threshold;
	}
}
