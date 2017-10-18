package consolidator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Iterator;

public class LineThresholdFinders {
	/**
	 * Author: Ian
	 * @param x: offset (start row #)
	 * @param range: block height
	 * @param img: snippet
	 * @return average pct_r
	 */
	 static double lineThresholdFinder_average(int x, int range, int[][] img) {
		if(img.length == 0) {
			return -1;
		}
		int w = img[0].length;
		double line_Threshold;
		int counter = 0;
		double sum_precentage = 0;
		for(int v = 0; v <= w - range; v++) {
			int sum = 0;
			for (int i = x; i < x + range; i++) {
				for (int j = v; j < v + range; j++) {
					if (img[i][j] == 0) {
						sum++;
					}
				}
			}
			sum_precentage += (double)sum / Math.pow(range, 2);
			counter++;
		}
		line_Threshold = sum_precentage / counter;
//		System.out.print("line threshold: ");
//		System.out.println(line_Threshold);
		return line_Threshold;
	}
	/**
	 * Author: Ian
	 * @param x: offset (start row #)
	 * @param range: block height
	 * @param img: snippet
	 * @return mediant pct_r
	 */
	 static double lineThresholdFinder_median(int x, int range, int[][] img) {
		if(img.length == 0) {
			return -1;
		}
		ArrayList<Integer> lineState = new ArrayList<Integer>();
		int w = img[0].length;
		for(int v = 0; v <= w - range; v++) {
			int sum = 0;
			for (int i = x; i < x + range; i++) {
				for (int j = v; j < v + range; j++) {
					if (img[i][j] == 0) {
						sum++;
					}
				}
			}
			lineState.add(sum);
		}
		Collections.sort(lineState);
		
		double mediant = 0;
		if(lineState.size() % 2 == 0) {
			int tmpA = lineState.get((lineState.size() / 2) - 1);
			int tmpB = lineState.get(lineState.size() / 2);
			mediant = ((double)tmpA + (double)tmpB) / 2;
		} else {
			mediant = (double)lineState.get((int)Math.floor(lineState.size() / 2));
		}
		
		double lineThreshold = mediant / Math.pow(range, 2);
		
		return lineThreshold;
	}
	/**
	 * Author: Ian
	 * @param x: offset (start row #)
	 * @param range: block height
	 * @param img: snippet
	 * @return top3 common average pct_r
	 */
	 static double lineThresholdFinder_top1Common(int x, int range, int[][] img) {
		if(img.length == 0) {
			return -1;
		}
		Hashtable<Integer, Integer> lineState = new Hashtable<Integer, Integer>();
		int w = img[0].length;
		double line_Threshold;
		for(int v = 0; v <= w - range; v++) {
			int sum = 0;
			for (int i = x; i < x + range; i++) {
				for (int j = v; j < v + range; j++) {
					if (img[i][j] == 0) {
						sum++;
					}
				}
			}
			if(null == lineState.get(sum)) {
				lineState.put(sum, 1);
			}
			else {
				Integer tmp = lineState.get(sum) + 1;
				lineState.put(sum, tmp);
			}
		}
		int[][] top1 = {{0,0}};
		Iterator<Integer> it = lineState.keySet().iterator();
		while(it.hasNext()) {
			int key = it.next();
			int val = lineState.get(key);
			if(val > top1[0][1]) {
				top1[0][1] = val;
				top1[0][0] = key;
			}
		}
		int maxsum = top1[0][0];
		line_Threshold = (double)maxsum / Math.pow(range, 2);
//		System.out.print("line threshold: ");
//		System.out.println(line_Threshold);
		return line_Threshold;
	}
	/**
	 * Author: Ian
	 * @param x: offset (start row #)
	 * @param range: block height
	 * @param img: snippet
	 * @return top3 common average pct_r
	 */
	 static double lineThresholdFinder_top3CommonAverage(int x, int range, int[][] img) {
		if(img.length == 0) {
			return -1;
		}
		Hashtable<Integer, Integer> lineState = new Hashtable<Integer, Integer>();
		int w = img[0].length;
		double line_Threshold;
		for(int v = 0; v <= w - range; v++) {
			int sum = 0;
			for (int i = x; i < x + range; i++) {
				for (int j = v; j < v + range; j++) {
					if (img[i][j] == 0) {
						sum++;
					}
				}
			}
			if(null == lineState.get(sum)) {
				lineState.put(sum, 1);
			}
			else {
				Integer tmp = lineState.get(sum) + 1;
				lineState.put(sum, tmp);
			}
		}
		int[][] top3 = {{0,0},{0,0},{0,0}};
		Iterator<Integer> it = lineState.keySet().iterator();
		while(it.hasNext()) {
			int key = it.next();
			int val = lineState.get(key);
			if(val > top3[2][1]) {
				top3[2][1] = val;
				top3[2][0] = key;
				if(val > top3[1][1]) {
					top3[2][1] = top3[1][1];
					top3[2][0] = top3[1][0];
					top3[1][1] = val;
					top3[1][0] = key;
					if(val > top3[0][1]) {
						top3[1][1] = top3[0][1];
						top3[1][0] = top3[0][0];
						top3[0][1] = val;
						top3[0][0] = key;
					}
				}
			}
		}
		int maxsum = top3[0][0] + top3[1][0] + top3[2][0];
		line_Threshold = ((double)maxsum / (double)3) / (double)(range*range);
//		System.out.print("line threshold: ");
//		System.out.println(line_Threshold);
		return line_Threshold;
	}
}
