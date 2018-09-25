package consolidator;

import java.util.ArrayList;
import java.util.Arrays;

import utility.MathToolkit;

public class LineDetectors {
	/**
	 * @author Ian
	 * row summation with neighborhood emphasis
	 * Feb. 2017
	 */
	 static double[] lineDetector(int offset, int range, int sumlvl, int[][] img) {
		double[] bar_img = new double[range];
		for(int i = 0; i < range; i++) {
			for (int j = 0; j < img[i + offset].length; j++) {
				if(img[i + offset][j] == 0) {
					bar_img[i] += 1;
				}
			}
			for (int lvl = 0; lvl < sumlvl; lvl++) {
				if(offset + i - lvl >= 0) {
					for (int j = 0; j < img[i + offset - lvl].length; j++) {
						if(img[i + offset - lvl][j] == 0) {
							bar_img[i] += 1;
						}
					}
				}
				if(offset + i + lvl < range) {
//					System.out.println(img.length);
//					System.out.println(i + offset + lvl);
//					System.out.println(i);
//					System.out.println(offset);
//					System.out.println(lvl);
					for (int j = 0; j < img[i + offset + lvl].length; j++) {
						if(img[i + offset + lvl][j] == 0) {
							bar_img[i] += 1;
						}
					}
				}
			}
		}
		
//		/**  
//		 * trim zeros and maxes
//		 **/
//		ArrayList<Double> trim = new ArrayList<Double>();
//		for(int i = 0; i < bar_img.length; i++) {
//			if((int)bar_img[i] != 0 && (int)bar_img[i] != img[0].length) {
//				trim.add(bar_img[i]);
//			}
//		}
//		
//		Double[] tmp = trim.toArray(new Double[trim.size()]);
//		if(tmp.length == 0) {
//			tmp = new Double[bar_img.length];
//			for(int i = 0; i < bar_img.length; i++) {
//				tmp[i] = bar_img[i];
//			}
//		}
		
		double[] tmp = bar_img.clone();
		
		double sum = 0;
		for(int i = 0; i < tmp.length; i++) {
			sum += tmp[i];
		}
		double bar_mean = sum / (double) tmp.length;
		for(int i = 0; i < range; i++) {
			bar_img[i] = bar_img[i] - bar_mean;
		}
		return bar_img;
	}
	/**
	 * @author Ian
	 * row summation with Gaussian weight
	 * Feb. 2017
	 */
	 static double[] lineDetector_Gaussian(int offset, int range, int sumlvl, double sigma, int[][] img) {
		double[] bar_img = new double[range];
		for(int i = 0; i < range; i++) {
			for (int j = 0; j < img[i + offset].length; j++) {
				if(img[i + offset][j] == 0) {
					bar_img[i] += 1;
				}
			}
			for (int lvl = 0; lvl < sumlvl; lvl++) {
				if(offset + i - lvl > 0) {
					double tmp_sum = 0;
					for (int j = 0; j < img[i + offset - lvl].length; j++) {
						if(img[i + offset - lvl][j] == 0) {
							tmp_sum += 1;
						}
					}
					bar_img[i] += tmp_sum * MathToolkit.GaussianFunc(lvl, sigma, i);
				}
				if(offset + i + lvl <= range) {
					double tmp_sum = 0;
					for (int j = 0; j < img[i + offset + lvl].length; j++) {
						if(img[i + offset + lvl][j] == 0) {
							tmp_sum += 1;
						}
					}
					bar_img[i] += tmp_sum * MathToolkit.GaussianFunc(lvl, sigma, i);
				}
			}
		}
		double sum = 0;
		for(int i = 0; i < range; i++) {
			sum += bar_img[i];
		}
		double bar_mean = sum / (double) range;
		for(int i = 0; i < range; i++) {
			bar_img[i] = bar_img[i] - bar_mean;
		}
		return bar_img;
	}
	/**
	 * @author Ian
	 * row summation with neighborhood emphasis
	 * Feb. 2017
	 */
	 static double[] lineDetector_median(int offset, int range, int sumlvl, int[][] img) {
		double[] bar_img = new double[range];
		for(int i = 0; i < range; i++) {
			for (int j = 0; j < img[i + offset].length; j++) {
				if(img[i + offset][j] == 0) {
					bar_img[i] += 1;
				}
			}
			for (int lvl = 0; lvl < sumlvl; lvl++) {
				if(offset + i - lvl >= 0) {
					for (int j = 0; j < img[i + offset - lvl].length; j++) {
						if(img[i + offset - lvl][j] == 0) {
							bar_img[i] += 1;
						}
					}
				}
				if(offset + i + lvl < range) {
//					System.out.println(img.length);
//					System.out.println(i + offset + lvl);
//					System.out.println(i);
//					System.out.println(offset);
//					System.out.println(lvl);
					for (int j = 0; j < img[i + offset + lvl].length; j++) {
						if(img[i + offset + lvl][j] == 0) {
							bar_img[i] += 1;
						}
					}
				}
			}
		}
		
		/**  
		 * trim zeros and maxes
		 **/
		ArrayList<Double> trim = new ArrayList<Double>();
		for(int i = 0; i < bar_img.length; i++) {
			if((int)bar_img[i] != 0 && (int)bar_img[i] != img[0].length) {
				trim.add(bar_img[i]);
			}
		}
		
		Double[] tmp = trim.toArray(new Double[trim.size()]);
		
		if(tmp.length == 0) {
			tmp = new Double[bar_img.length];
			for(int i = 0; i < bar_img.length; i++) {
				tmp[i] = bar_img[i];
			}
		}
		Arrays.sort(tmp);

		double mediant = 0;
		if(tmp.length % 2 == 0) {
			double tmpA = tmp[(tmp.length / 2) - 1];
			double tmpB = tmp[(tmp.length / 2)];;
			mediant = (tmpA + tmpB) / 2.0;
		} else {
			mediant = tmp[(int)Math.floor(tmp.length / 2)];
		}
		for(int i = 0; i < range; i++) {
			bar_img[i] = bar_img[i] - mediant;
		}
		return bar_img;
	}
}
