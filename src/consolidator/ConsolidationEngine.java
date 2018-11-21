/**
 * new solution for consolidation
 */
package consolidator;

import java.lang.Math;

import featureExtraction.Morphology;
import models.Image;
import utility.ImageToolkit;

/**
 * @author Ian
 * Feb. 2017
 */
public class ConsolidationEngine {
	
	//Perform Erosion/Dilation on binary image before outputting
    //To change the number of times Erosion or Dilation runs, simply change the constants
    //located at the top of this file.

    private final int DILATION_NUM = 1;
    private final int EROSION_NUM = 1;
	
	public ConsolidationEngine() {}
	
	public ConsolidationEngine(double pos_lmt, double neg_lmt, int depth) {
		this.positive_range_lmt = pos_lmt;
		this.negative_range_lmt = neg_lmt;
		this.cur_depth = depth;
	}

	private int max_recursive_depth = 1;
	private int min_kernel_size = 1;
	private int cur_depth = 0;
	private double positive_range_lmt = -1;
	private double negative_range_lmt = -1;
	
	public int[][] noRecursion(int[][] img) {
		Morphology morph = new Morphology();
		int[][] noRecursion = ImageToolkit.deepCopy(img);
		noRecursion = consolidation(noRecursion, 0, noRecursion.length, 0, 0);
		
		for(int i = 0; i < EROSION_NUM; i++){
			noRecursion = morph.Erosion(noRecursion);
		}
		for(int i = 0; i < DILATION_NUM; i++){
			noRecursion = morph.Dilation(noRecursion);
		}
		
		return noRecursion;
	}
	
	public int[][] recursion(int[][] img) {
		Morphology morph = new Morphology();
		int[][] recursion = ImageToolkit.deepCopy(img);
		recursion = consolidation(recursion, 0, recursion.length, 1, 1);
		
		for(int i = 0; i < EROSION_NUM; i++){
			recursion = morph.Erosion(recursion);
		}
		for(int i = 0; i < DILATION_NUM; i++){
			recursion = morph.Dilation(recursion);
		}
		
		return recursion;
	}
	
	public int[][] mixedConsolidation(int[][] img) {
		Morphology morph = new Morphology();
		int[][] noRecursion = recursion(img);
		int[][] recursion = noRecursion(img);

		int[][] mixed = ImageToolkit.imageOR(noRecursion, recursion);
		
		for(int i = 0; i < EROSION_NUM; i++){
			mixed = morph.Erosion(mixed);
		}
		for(int i = 0; i < DILATION_NUM; i++){
			mixed = morph.Dilation(mixed);
		}
		
		return mixed;
	}
	
	/**
	 * @author Ian
	 * consolidator interface
	 * Feb. 2017
	 * @param img
	 * @param offset
	 * @param range
	 * @param recursiveDepthLmt: max recursive depth
	 * @param kernelSizeLmt: min kernel size
	 * @return
	 */
	private int[][] consolidation(int[][] img, int offset, int range, int recursiveDepthLmt, int kernelSizeLmt) {
		this.max_recursive_depth = recursiveDepthLmt;
		this.min_kernel_size = kernelSizeLmt;
		// idea 1: add neighbor lines into calculation
		
//		double[] sub = LineDetectors.lineDetector_median(offset,range,0,img);
		
		double[] sub = LineDetectors.lineDetector(offset,range,0,img); //NAP algorithm
		
//		System.out.print("offset:");
//		System.out.print(offset);
//		System.out.print(" range:");
//		System.out.println(range);
		
		// idea 2: add neighbor lines * gaussian distribution into calculation
//		double[] sub = LineDetectors.lineDetector_Gaussian(offset,range, 5, 0.5, img);
		
		//common line height realizer
		if(this.positive_range_lmt == -1 || positive_range_lmt == -1) {
			
//			double tlh = LineHeightDetectors.averageLineHeightDetector(sub, 1);
//			double blh = LineHeightDetectors.averageLineHeightDetector(sub, -1);
			
			double tlh = LineHeightDetectors.medianLineHeightDetector(sub, 1);
			double blh = LineHeightDetectors.medianLineHeightDetector(sub, -1);
			
//			double tlh = LineHeightDetectors.mostCommonLineHeightDetector(sub, 1);
//			double blh = LineHeightDetectors.mostCommonLineHeightDetector(sub, -1);
			
			
			this.positive_range_lmt = tlh;
			this.negative_range_lmt = blh;
			System.out.print("tlh:");
			System.out.print(tlh);
			System.out.print(" blh:");
			System.out.println(blh);
			//System.out.println(this.range_lmt);
		}
//		System.out.print("pos_lmt:");
//		System.out.print(positive_range_lmt);
//		System.out.print(" neg_lmt:");
//		System.out.println(negative_range_lmt);

		int cur_positive_range = 0;
		int cur_negative_range = 0;
		for(int i = 0; i < range; i++) {
			if (sub[i] >= 0) {
		        cur_positive_range = cur_positive_range+1;
			} else {
		        cur_negative_range = cur_negative_range+1;
			}
			if(i + 1 >= range) {
				if(cur_positive_range > 0) {
					if(cur_positive_range > this.min_kernel_size) {
						if(cur_positive_range > positive_range_lmt && cur_depth < this.max_recursive_depth) {
							cur_depth++;
							ConsolidationEngine engin = new ConsolidationEngine(this.positive_range_lmt, this.negative_range_lmt, this.cur_depth);
							img = engin.consolidation(img, offset + i - cur_positive_range + 1, cur_positive_range, this.max_recursive_depth, this.min_kernel_size);
							cur_depth--;
						} else {
							img = solidate(offset + i - cur_positive_range + 1, cur_positive_range, img);
						}
					} else {
						cur_negative_range = cur_negative_range + cur_positive_range;
		                cur_positive_range = 0;
					}
					int bg = i - cur_negative_range - cur_positive_range + 1;
					int ed = i - cur_positive_range;
					if (bg != ed && ed - bg > negative_range_lmt) {
						
						//old approach: try to find valley/peak first
//						double[] tmp_sub = new double[ed - bg + 1];
//						for(int counter = 0; counter < tmp_sub.length; counter++) {
//							tmp_sub[counter] = sub[bg + counter];
//						}
//						boolean haslow = HistToolkit.hasPeak(tmp_sub);
//						if (haslow && cur_depth <= MAX_RECURSIVE_DEPTH) {
//							cur_depth++;
//							ConsolidationEngine engine = new ConsolidationEngine(this.positive_range_lmt, this.negative_range_lmt);
//							img = engine.consolidation(img, offset + bg, cur_negative_range);
//						}
						if (cur_depth < this.max_recursive_depth) {
							cur_depth++;
							ConsolidationEngine engine = new ConsolidationEngine(this.positive_range_lmt, this.negative_range_lmt, this.cur_depth);
							img = engine.consolidation(img, offset + bg, cur_negative_range, this.max_recursive_depth, this.min_kernel_size);
							cur_depth--;
						}
					}
					cur_positive_range = 0;
		            cur_negative_range = 0;
				}
				continue;
			}
			if (sub[i + 1] < 0 && cur_positive_range > 0) {
				if(cur_positive_range > this.min_kernel_size) {
					if(cur_positive_range > positive_range_lmt && cur_depth < this.max_recursive_depth) {
						cur_depth++;
						ConsolidationEngine engine = new ConsolidationEngine(this.positive_range_lmt, this.negative_range_lmt, this.cur_depth);
						img = engine.consolidation(img, offset + i - cur_positive_range + 1, cur_positive_range, this.max_recursive_depth, this.min_kernel_size);
						cur_depth--;
					} else {
						img = solidate(offset + i - cur_positive_range + 1, cur_positive_range, img);
					}
				} else {
					cur_negative_range = cur_negative_range + cur_positive_range;
	                cur_positive_range = 0;
				}
				int bg = i - cur_negative_range - cur_positive_range + 1;
				int ed = i - cur_positive_range;
				if (bg != ed && ed - bg > negative_range_lmt) {
					
					//old approach: try to find valley/peak first
//					double[] tmp_sub = new double[ed - bg + 1];
//					for(int counter = 0; counter < tmp_sub.length; counter++) {
//						tmp_sub[counter] = sub[bg + counter];
//					}
//					boolean haslow = HistToolkit.hasPeak(tmp_sub);
//					if (haslow && cur_depth <= MAX_RECURSIVE_DEPTH) {
//						cur_depth++;
//						ConsolidationEngine engine = new ConsolidationEngine(this.positive_range_lmt, this.negative_range_lmt);
//						img = engine.consolidation(img, offset + bg, cur_negative_range);
//					}
					
					if (cur_depth < this.max_recursive_depth) {
						cur_depth++;
						ConsolidationEngine engine = new ConsolidationEngine(this.positive_range_lmt, this.negative_range_lmt, this.cur_depth);
						img = engine.consolidation(img, offset + bg, cur_negative_range, this.max_recursive_depth, this.min_kernel_size);
						cur_depth--;
					}
				}
				cur_positive_range = 0;
	            cur_negative_range = 0;
			}
		}
		
		return img;
	}

	/**
	 * @author Ian
	 * FillRow_Kernel()
	 * textual line smearer
	 * Feb. 2017
	 */
	private static int[][] solidate(int x, int range, int[][] img) {
		int h, w;
		h = img.length;
		if (h > 0) {
			w = img[0].length;
		} else {
			return img;
		}
		double line_Threshold = 0;
		line_Threshold = LineThresholdFinders.lineThresholdFinder_average(x, range, img);

//		line_Threshold = LineThresholdFinders.lineThresholdFinder_median(x, range, img);
		
//		line_Threshold = LineThresholdFinders.lineThresholdFinder_top1Common(x, range, img);

//		System.out.println("RET: " + line_Threshold);

		int mid = (int) Math.floor(w/2.0);
		for(int v = 0; v < mid; v++) {
			int sum = 0;
			boolean flag = false;
			if (x + range > h || v + range > w) {
				continue;
			}
			for (int i = x; i < x + range; i++) {
				for (int j = v; j < v + range; j++) {
					if (img[i][j] == 0) {
						sum++;
					}
					if (line_Threshold <= sum / Math.pow(range, 2)) {
						flag = true;
						break;
					}
				}
				if (flag) {
					break;
				}
			}
			if (flag) {
				for (int i = x; i < x + range; i++) {
					for (int j = v; j < v + range; j++) {
						img[i][j] = 0;
					}
				}
			}
		}
		for(int v = w-1; v >= mid; v--) {
			int sum = 0;
			boolean flag = false;
			if(x + range > h || v - range < 1) {
				continue;
			}
			for(int i = x; i < x + range; i++) {
				for(int j = v; j >= v - range; j--) {
					if(img[i][j] == 0) {
						sum++;
					}
					if(line_Threshold <= sum / Math.pow(range, 2)) {
						flag = true;
						break;
					}
				}
				if (flag) {
					break;
				}
			}
			if (flag) 
			{
				for(int i = x; i < x + range; i++) {
					for(int j = v; j >= v - range; j--) {
						img[i][j] = 0;
					}
				}
			}
		}
		return img;
	}
}
