package utility;

import java.util.*;

public class ImageToolkit {
	/**
	 * return image combine all black pixels either in imgA or imgB, or both
	 * imgA and imgB have to have the same size
	 * @author Ian
	 * @param imgA
	 * @param imgB
	 * @throws IllegalArgumentException
	 * @return
	 */
	public static int[][] imageOR(int[][] imgA, int[][] imgB) {
		if(imgA.length != imgB.length || imgA.length < 1 || imgB.length < 1) {
			throw new IllegalArgumentException();
		}
		if(imgA[0].length != imgB[0].length) {
			throw new IllegalArgumentException();
		}
		int height = imgA.length;
		int width = imgA[0].length;
		int[][] ret = new int[height][width];
		for(int i = 0; i < height; i++) {
			for(int j = 0; j < width; j++) {
				if(imgA[i][j] == 0 || imgB[i][j] == 0) {
					ret[i][j] = 0;
				} else {
					ret[i][j] = 255;
				}
			}
		}
		return ret;
	}
	
	public static Boolean isEqual(int[][] imgA, int[][] imgB) {
		if(imgA.length != imgB.length || imgA.length < 1 || imgB.length < 1) {
			return false;
		}
		if(imgA[0].length != imgB[0].length) {
			return false;
		}
		int height = imgA.length;
		int width = imgA[0].length;
		for(int i = 0; i < height; i++) {
			for(int j = 0; j < width; j++) {
				if(imgA[i][j] != imgB[i][j]) {
					return false;
				}
			}
		}
		return true;
	}
	
	public static int[][] deepCopy(int[][] imgA) {
		if(imgA.length < 1) {
			throw new IllegalArgumentException();
		}
		int height = imgA.length;
		int width = imgA[0].length;
		int[][] ret = new int[height][width];
		for(int i = 0; i < height; i++) {
			for(int j = 0; j < width; j++) {
				ret[i][j] = imgA[i][j];
			}
		}
		return ret;
	}
	
	public static int[][] histEqualization(int[][] img, double[] cdf) {
		Dictionary<Integer, Integer> contrastTable = new Hashtable<Integer, Integer>();
		int nLvl = cdf.length;
		for(int i=0; i<nLvl; i++) {
			int p_prime = (int)Math.floor((nLvl-1) * cdf[i]);
			contrastTable.put(i, p_prime);
		}
		assert img.length > 0;
		int height = img.length;
		int width = img[0].length;
		int[][] retImg = new int[height][width];
		for(int i=0;i<height;i++){
		    for(int j=0;j<width;j++){
		        retImg[i][j] = contrastTable.get(img[i][j]);
		    }
		}
		return retImg;
	}
	
	public static int[][] convertToBinaryImage(int[][] img, int threshold){
		assert img.length > 0;
		int height = img.length;
		int width = img[0].length;
		int[][] binImg = new int[height][width];
		for (int i = 0; i < height; i++){
			for(int j= 0; j < width; j++){
				if(img[i][j] < threshold){
					binImg[i][j] = 0;
				} else {
					binImg[i][j] = 255;
				}
			}
		}
		return binImg;
	}
}
