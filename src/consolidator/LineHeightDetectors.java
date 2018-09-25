package consolidator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Iterator;

public class LineHeightDetectors {
	/** 
	 * Author: Ian
	 * @param sub: NAP or partial NAP
	 * @param direct: 1. >0 --> find black line height
	 * 				  2. otherwise --> find white line height
	 * @return most common line height (in number of row)
	 */
	 static double mostCommonLineHeightDetector(double[] sub, int direct) {
		if (direct > 0) {
			Hashtable<Integer, Integer> lineState = new Hashtable<Integer, Integer>();
			int counter = 0;
			for(int i = 0; i < sub.length; i++) {
				if(sub[i] > 0) {
					counter++;
				}
				if(counter > 0) {
					if(i+1 < sub.length) {
						if(sub[i+1] <= 0) {
							if(null == lineState.get(counter)) {
								lineState.put(counter, 1);
							}
							else {
								Integer tmp = lineState.get(counter) + 1;
								lineState.put(counter, tmp);
							}
							counter = 0;
						}
					}
					else {
						if(null == lineState.get(counter)) {
							lineState.put(counter, 1);
						}
						else {
							Integer tmp = lineState.get(counter) + 1;
							lineState.put(counter, tmp);
						}
						counter = 0;
					}
				}
			}
			int[][] top3 = {{0,0}};
			Iterator<Integer> it = lineState.keySet().iterator();
			while(it.hasNext()) {
				int key = it.next();
				int val = lineState.get(key);
				if(val > top3[0][1]) {
					top3[0][1] = val;
					top3[0][0] = key;
				}
			}
			double maxsum = top3[0][0];
			return maxsum;
		}
		else {
			Hashtable<Integer, Integer> lineState = new Hashtable<Integer, Integer>();
			int counter = 0;
			for(int i = 0; i < sub.length; i++) {
				if(sub[i] <= 0) {
					counter++;
				}
				if(counter > 0) {
					if(i+1 < sub.length) {
						if(sub[i+1] > 0) {
							if(null == lineState.get(counter)) {
								lineState.put(counter, 1);
							}
							else {
								Integer tmp = lineState.get(counter) + 1;
								lineState.put(counter, tmp);
							}
							counter = 0;
						}
					}
					else {
						if(null == lineState.get(counter)) {
							lineState.put(counter, 1);
						}
						else {
							Integer tmp = lineState.get(counter) + 1;
							lineState.put(counter, tmp);
						}
						counter = 0;
					}
				}
			}
			int[][] top3 = {{0,0},{0,0},{0,0}};
			Iterator<Integer> it = lineState.keySet().iterator();
			while(it.hasNext()) {
				int key = it.next();
				int val = lineState.get(key);
				if(val > top3[0][1]) {
					top3[0][1] = val;
					top3[0][0] = key;
				}
			}
			double maxsum = top3[0][0];
			return maxsum;
		}
	}
	
	/** 
	 * Author: Ian
	 * @param sub: NAP or partial NAP
	 * @param direct: 1. >0 --> find black line height
	 * 				  2. otherwise --> find white line height
	 * @return average of top3 most common line height (in number of row)
	 */
	 static double most3CommonLineHeightDetector(double[] sub, int direct) {
		if (direct > 0) {
			Hashtable<Integer, Integer> lineState = new Hashtable<Integer, Integer>();
			int counter = 0;
			for(int i = 0; i < sub.length; i++) {
				if(sub[i] > 0) {
					counter++;
				}
				if(counter > 0) {
					if(i+1 < sub.length) {
						if(sub[i+1] <= 0) {
							if(null == lineState.get(counter)) {
								lineState.put(counter, 1);
							}
							else {
								Integer tmp = lineState.get(counter) + 1;
								lineState.put(counter, tmp);
							}
							counter = 0;
						}
					}
					else {
						if(null == lineState.get(counter)) {
							lineState.put(counter, 1);
						}
						else {
							Integer tmp = lineState.get(counter) + 1;
							lineState.put(counter, tmp);
						}
						counter = 0;
					}
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
			return (double)maxsum / (double)3;
		}
		else {
			Hashtable<Integer, Integer> lineState = new Hashtable<Integer, Integer>();
			int counter = 0;
			for(int i = 0; i < sub.length; i++) {
				if(sub[i] <= 0) {
					counter++;
				}
				if(counter > 0) {
					if(i+1 < sub.length) {
						if(sub[i+1] > 0) {
							if(null == lineState.get(counter)) {
								lineState.put(counter, 1);
							}
							else {
								Integer tmp = lineState.get(counter) + 1;
								lineState.put(counter, tmp);
							}
							counter = 0;
						}
					}
					else {
						if(null == lineState.get(counter)) {
							lineState.put(counter, 1);
						}
						else {
							Integer tmp = lineState.get(counter) + 1;
							lineState.put(counter, tmp);
						}
						counter = 0;
					}
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
			return (double)maxsum / (double)3;
		}
	}
	
	/** 
	 * Author: Ian
	 * @param sub: NAP or partial NAP
	 * @param direct: 1. >0 --> find black line height
	 * 				  2. otherwise --> find white line height
	 * @return max line height (in number of row)
	 */
	 static int maxLineHeightDetector(double[] sub, int direct) {
		if(direct > 0) {
			Hashtable<Integer, Integer> lineState = new Hashtable<Integer, Integer>();
			int counter = 0;
			int lineCounter = 0;
			for(int i = 0; i < sub.length; i++) {
				if(sub[i] > 0) {
					counter++;
				}
				if(counter > 0) {
					if(i+1 < sub.length) {
						if(sub[i+1] <= 0) {
							lineCounter++;
							lineState.put(lineCounter, counter);
							counter = 0;
						}
					}
					else {
						lineCounter++;
						lineState.put(lineCounter, counter);
						counter = 0;
					}
				}
			}
			int[][] max = {{0,0}};
			Iterator<Integer> it = lineState.keySet().iterator();
			while(it.hasNext()) {
				int key = it.next();
				int val = lineState.get(key);
				if(val > max[0][1]) {
					max[0][1] = val;
					max[0][0] = key;
				}
			}
			return max[0][1];
		}
		else {
			Hashtable<Integer, Integer> lineState = new Hashtable<Integer, Integer>();
			int counter = 0;
			int lineCounter = 0;
			for(int i = 0; i < sub.length; i++) {
				if(sub[i] <= 0) {
					counter++;
				}
				if(counter > 0) {
					if(i+1 < sub.length) {
						if(sub[i+1] > 0) {
							lineCounter++;
							lineState.put(lineCounter, counter);
							counter = 0;
						}
					}
					else {
						lineCounter++;
						lineState.put(lineCounter, counter);
						counter = 0;
					}
				}
			}
			int[][] max = {{0,0}};
			Iterator<Integer> it = lineState.keySet().iterator();
			while(it.hasNext()) {
				int key = it.next();
				int val = lineState.get(key);
				if(val > max[0][1]) {
					max[0][1] = val;
					max[0][0] = key;
				}
			}
			return max[0][1];
		}
	}
	
	/** 
	 * Author: Ian
	 * @param sub: NAP or partial NAP
	 * @param direct: 1. >0 --> find black line height
	 * 				  2. otherwise --> find white line height
	 * @return max line height (in number of row)
	 */
	 static double medianLineHeightDetector(double[] sub, int direct) {
		if(direct > 0) {
			Hashtable<Integer, Integer> lineState = new Hashtable<Integer, Integer>();
			int counter = 0;
			int lineCounter = 0;
			for(int i = 0; i < sub.length; i++) {
				if(sub[i] > 0) {
					counter++;
				}
				if(counter > 0) {
					if(i+1 < sub.length) {
						if(sub[i+1] <= 0) {
							lineCounter++;
							lineState.put(lineCounter, counter);
							counter = 0;
						}
					}
					else {
						lineCounter++;
						lineState.put(lineCounter, counter);
						counter = 0;
					}
				}
			}
			ArrayList<Integer> lineHeights = new ArrayList<Integer>(lineState.values());
			Collections.sort(lineHeights);
			
			double mediant = 0;
			if(lineHeights.size() % 2 == 0) {
				int tmpA = lineHeights.get((lineHeights.size() / 2) - 1);
				int tmpB = lineHeights.get(lineHeights.size() / 2);
				mediant = ((double)tmpA + (double)tmpB) / 2;
			} else {
				mediant = (double)lineHeights.get((int)Math.floor(lineHeights.size() / 2));
			}
			
			return mediant;
		}
		else {
			Hashtable<Integer, Integer> lineState = new Hashtable<Integer, Integer>();
			int counter = 0;
			int lineCounter = 0;
			for(int i = 0; i < sub.length; i++) {
				if(sub[i] <= 0) {
					counter++;
				}
				if(counter > 0) {
					if(i+1 < sub.length) {
						if(sub[i+1] > 0) {
							lineCounter++;
							lineState.put(lineCounter, counter);
							counter = 0;
						}
					}
					else {
						lineCounter++;
						lineState.put(lineCounter, counter);
						counter = 0;
					}
				}
			}
			ArrayList<Integer> lineHeights = new ArrayList<Integer>(lineState.values());
			Collections.sort(lineHeights);
			
			double mediant = 0;
			if(lineHeights.size() % 2 == 0) {
				int tmpA = lineHeights.get((lineHeights.size() / 2) - 1);
				int tmpB = lineHeights.get(lineHeights.size() / 2);
				mediant = ((double)tmpA + (double)tmpB) / 2;
			} else {
				mediant = (double)lineHeights.get((int)Math.floor(lineHeights.size() / 2));
			}
			
			return mediant;
		}
	}
	
	/** 
	 * Author: Ian
	 * @param sub: NAP or partial NAP
	 * @param direct: 1. >0 --> find black line height
	 * 				  2. otherwise --> find white line height
	 * @return average line height (in number of row)
	 */
	 static double averageLineHeightDetector(double[] sub, int direct) {
		if(direct > 0) {
			int sum = 0;
			int counter = 0;
			int lineCounter = 0;
			for(int i = 0; i < sub.length; i++) {
				if(sub[i] > 0) {
					counter++;
				}
				if(counter > 0) {
					if(i+1 < sub.length) {
						if(sub[i+1] <= 0) {
							lineCounter++;
							sum += counter;
							counter = 0;
						}
					}
					else {
						lineCounter++;
						sum += counter;
						counter = 0;
					}
				}
			}
			double avg = ((double) sum) / ((double) lineCounter);
			return avg;
		}
		else {
			int sum = 0;
			int counter = 0;
			int lineCounter = 0;
			for(int i = 0; i < sub.length; i++) {
				if(sub[i] <= 0) {
					counter++;
				}
				if(counter > 0) {
					if(i+1 < sub.length) {
						if(sub[i+1] > 0) {
							lineCounter++;
							sum += counter;
							counter = 0;
						}
					}
					else {
						lineCounter++;
						sum += counter;
						counter = 0;
					}
				}
			}
			double avg = ((double) sum) / ((double) lineCounter);
			return avg;
		}
	}
}
