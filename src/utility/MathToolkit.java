/**
 * Math equation solver
 */
package utility;

import java.lang.Math;

/**
 * @author Ian
 * Feb. 2017
 */
public class MathToolkit {
	public static double GaussianFunc(int u, double sigma, int x) {
		double ret = Math.exp(-((x - u)*(x - u))/(2*sigma*sigma));
		return ret;
	}
}
