/**
 * Useful small function
 */
package utility;

/**
 * @author Ian
 * Feb. 2017
 */
public class HistToolkit {
	public static boolean hasPeak(double[] tmp_sub) {
		//fixed window size 3
		int length = tmp_sub.length;
		for (int i = 0; i < length; i++) {
			if ((i + 2) >= length) {
				return false;
			}
			if (tmp_sub[i + 1] > tmp_sub[i] && tmp_sub[i + 1] > tmp_sub[i + 2]) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean isHistEqual(double[] histA, double[] histB) {
		if(histA.length != histB.length) {
			return false;
		}
		else {
			for (int i = 0; i < histA.length; i++) {
				if(histA[i] != histB[i]) {
					return false;
				}
			}
			return true;
		}
	}
	
	public static long[] computeHist(int[][] img, int maxIntensity) {
		long[] hist = new long[maxIntensity];
		assert img.length > 0;
		int height = img.length;
		int width = img[0].length;
		for (int i = 0; i < height; i++){
			for(int j= 0; j < width; j++){
				int value = img[i][j];
				if((value >= 0)&&(value < maxIntensity)){
					hist[value]++;
				}
			}
		}
		return hist;
	}
	
	public static double[] computeRDF(long[] hist) {
		int histLen = hist.length;
		double[] rdf = new double[histLen];
		double size = 0;
		for(int i=0; i<histLen; i++) {
			size += hist[i];
		}
		for(int i = 0; i < histLen; i++) {
			rdf[i] = hist[i] / size;
		}
		return rdf;
	}
	
	public static double[] computeCDF(double[] rdf) {
		int histLen = rdf.length;
		double[] cdf = new double[histLen];
		for(int i=0; i<histLen; i++) {
			cdf[i] = 0;
		}
		for(int i=0; i<histLen; i++) {
			for(int j=0; j<=i; j++) {
				cdf[i] += rdf[j];
			}
		}
		return cdf;
	}
}
