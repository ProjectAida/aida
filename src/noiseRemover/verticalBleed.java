package noiseRemover;

import java.math.*;

public class verticalBleed {
	
	//not a general idea
	//not in use
	
	public static int[][] remove(int[][] img) {
		int h = img.length;
		int w = 0;
		if(h>0){
			w = img[0].length;
		} else {
			return null;
		}
		double[] vert_img = new double[w];
		for (int j = 0; j < w; j++) {
			vert_img[j] = 0;
		}
		for (int j = 0; j < w; j++) {
			for (int i = 0; i < h; i++) {
				if(img[i][j] == 0) {
					vert_img[j]++;
				}
			}
		}
		//*********differential function of vertical NTP*********//
		double sum = 0;
		double[] dif = new double[w-1];
		for(int j = 0; j < w-1; j++) {
			dif[j] = vert_img[j+1] - vert_img[j];
			dif[j] = Math.pow(dif[j], 4);
			sum += dif[j];
		}
		double mean = sum / w;
		for (int j = 0; j < w-1; j++) {
			if(dif[j] > mean) {
				for(int i = 0; i < h; i++) {
					img[i][j+1] = 255;
				}
			}
		}
		
		//*********vertical NTP*********//
//		double sum = 0;
//		for (int j = 0; j < w; j++) {
//			vert_img[j] = Math.pow(vert_img[j], 4);
//			sum += vert_img[j];
//		}
//		
//		double mean = sum / w;
//		
//		for (int j = 0; j < w; j++) {
//			if(vert_img[j] > mean) {
//				for(int i = 0; i < h; i++) {
//					img[i][j] = 255;
//				}
//			}
//		}
		
		return img;
	}
}
