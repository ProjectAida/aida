package weka;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import weka.associations.Apriori;
import weka.attributeSelection.CfsSubsetEval;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.classifiers.lazy.IBk;
import weka.classifiers.trees.J48;
import weka.clusterers.SimpleKMeans;
import weka.core.Instances;

/**
 *
 * @author LD Miller
 *
 * The purpose of this class is to provide some examples for using Weka inside
 * Java programs. It provides specific examples on (1) loading an arff file
 * dataset, (2) running a C4.5 decision tree classification algorithm, (3)
 * running a neural network classification algorithm, (4) running simple k-means
 * clustering algorithm, and (5) running apriori association rule miner, and (6)
 * running a feature selection algorithm. More information on constructors, etc.
 * for these classes can be found on the weka Javadocs at
 * http://www.cs.waikato.ac.nz/ml/weka/
 */
public class JavaWekaExample {

	private Instances dataset;
	private final String datasetPath;

	// generic constructor
	public JavaWekaExample(String datasetPath) {
		this.datasetPath = datasetPath;
	}

	/**
	 * This method loads the dataset from an arff file and sets which attribute
	 * is the label.
	 *
	 * @param pPath The dataset path
	 * @throws java.lang.Exception
	 */
	public void readDataset(String pPath) throws Exception {
		// reads in the dataset from the arff file
		dataset = new Instances(new FileReader(pPath));
		dataset.deleteAttributeAt(0);

		// sets the class index to the last attribute
		dataset.setClassIndex(dataset.numAttributes() - 1);
	}

	/**
	 * This method generates the price for the wine.
	 * @param pRating The rating variable
	 * @param pAge The age variable
	 * @return 
	 */
	public double generatePrice(double pRating, double pAge) {
		double peak = pRating - 50;
		double price = pRating / 2;
		if (pAge > peak) {
			price = price * (5 - (pAge - peak));
		} else {
			price = price * (5 * ((pAge + 1) / peak));
		}
		if (price < 0) {
			price = 0;
		}
		return price;
	}

	/**
	 * This method generates the wine dataset in arff format
	 * @param pFolder The folder for the dataset
	 */
	public void generateWineDataset(String pFolder) {

		BufferedWriter writer = null;
		Random random = new Random();
		try {
			writer = new BufferedWriter(new FileWriter(pFolder + "/" + "wine.arff"));
			writer.append("@relation wine\n");
			writer.append("\n");
			writer.append("@attribute rating numeric\n");
			writer.append("@attribute age numeric\n");
			writer.append("@attribute price numeric\n");
			writer.append("\n");
			writer.append("@data\n");
			for (int index = 0; index < 200; index++) {
				double rating = random.nextDouble() * 50 + 50;
				double age = random.nextDouble() * 50;
				double price = generatePrice(rating, age);
				price *= random.nextDouble() * 0.4 + 0.8;
				writer.append(rating + "," + age + "," + price + "\n");
			}
			writer.close();
		} catch (Exception ex) {
			Logger.getLogger(JavaWekaExample.class.getName()).log(Level.SEVERE, null, ex);
			System.exit(-1);
		}
	}

	/**
	 * This method creates a C4.5 decision tree from two-thirds of the dataset
	 * and evaluates it on the remaining third.
	 *
	 * See this link for more details on the default parameters:
	 * http://http://weka.sourceforge.net/doc.dev/weka/classifiers/trees/J48.html
	 * @throws java.lang.Exception
	 */
	public void runDecisionTree() throws Exception {

		// collections for training and testing
		Instances train, test;

		// randomize the dataset
		dataset.randomize(new Random(1));

		// populate testing with 1/3 data points and training with remaining 2/3
		test = dataset.testCV(3, 1);
		train = dataset.trainCV(3, 1);

		// initialize the network
		J48 tree = new J48();
		tree.setReducedErrorPruning(true);
		tree.setConfidenceFactor((float) 0.15);

		System.out.println("Confidence factor for pessimistic pruning: " + tree.getConfidenceFactor());
		System.out.println("Number of instances per leaf: " + tree.getMinNumObj());
		System.out.println("Use reduced error pruning: " + tree.getReducedErrorPruning());
		System.out.println("Number of folds for post-pruning: " + tree.getNumFolds());
		System.out.println("Use binary splits: " + tree.getBinarySplits());
		System.out.println("Do not use subtree raising: " + tree.getSubtreeRaising());
		System.out.println("Use laplace smoothing: " + tree.getUseLaplace());

		// train the tree on the train set
		tree.buildClassifier(train);

		// output the network
		System.out.println(tree);

		// compute the prediction accuracy
		double correct = 0;
		double total = test.numInstances();

		for (int i = 0; i < test.numInstances(); i++) {
			if (test.instance(i).classValue() == tree.classifyInstance(test.instance(i))) {
				correct++;
			}
		}
		//output the accuracy
		System.out.println("Accuracy of the Decision Tree is: " + correct / total);
	}

	/**
	 * This method creates a multilayer perceptron from two-thirds of the
	 * dataset and evaluates it on the remaining third.
	 *
	 * This method uses the default set of parameters for the neural network
	 * with one internal layer and 500 training iterations. The number of nodes
	 * in the internal layer is (attributes + classes)/2 based on a heuristic in
	 * one of the original neural network papers.
	 *
	 * See this link for more details on the default parameters:
	 * http://weka.sourceforge.net/doc.dev/weka/classifiers/functions/MultilayerPerceptron.html
	 * @throws java.lang.Exception
	 */
	public void runNeuralNetwork() throws Exception {

		// collections for training and testing
		Instances train, test;

		// randomize the dataset
		dataset.randomize(new Random(1));

		// populate testing with 1/3 data points and training with remaining 2/3
		test = dataset.testCV(3, 1);
		train = dataset.trainCV(3, 1);

		// initialize the network
		MultilayerPerceptron network = new MultilayerPerceptron();

		network.setHiddenLayers("5");
		// train the network on the train set
		network.buildClassifier(train);

		// output the network
		System.out.println(network);

		// compute the prediction accuracy
		double correct = 0;
		double total = test.numInstances();

		for (int i = 0; i < test.numInstances(); i++) {
			if (test.instance(i).classValue() == network.classifyInstance(test.instance(i))) {
				correct++;
			}
		}
		//output the accuracy
		System.out.println("Accuracy of the Neural Network is: " + correct / total);
	}

	/**
	 * This method creates a k-NN from two-thirds of the dataset and evaluates
	 * it on the remaining third.
	 * @param pNeighbors The number of neighbors
	 * @throws java.lang.Exception
	 */
	public void runKNearestNeighbor(int pNeighbors) throws Exception {

		// collections for training and testing
		Instances train, test;

		// randomize the dataset
		dataset.randomize(new Random(1));

		// populate testing with 1/3 data points and training with remaining 2/3
		test = dataset.testCV(3, 1);
		train = dataset.trainCV(3, 1);

		// initialize the network
		IBk knn = new IBk();
		knn.setKNN(pNeighbors);

		// train the network on the train set
		knn.buildClassifier(train);

		// compute the prediction accuracy
		double correct = 0;
		double total = test.numInstances();

		for (int i = 0; i < test.numInstances(); i++) {
			if (test.instance(i).classValue() == knn.classifyInstance(test.instance(i))) {
				correct++;
			}
		}
		//output the accuracy
		System.out.println("Accuracy of kNN with k=" + pNeighbors + " is: " + correct / total);
	}

	/**
	 * This method partitions the entire dataset into two clusters to minimize
	 * the distoration measured using Hamming distance for nominal attributes or
	 * Euclidean distance for numeric attributes.
	 * @throws java.lang.Exception
	 */
	public void runClusteringAlgorithm() throws Exception {

		// clustering algorithms do not use label
		int labelIndex = dataset.classIndex();
		dataset.setClassIndex(-1);

		// initialize the clustering algorithm
		SimpleKMeans skmeans = new SimpleKMeans();

		// set the number of clusters
		skmeans.setNumClusters(2);

		// create the clusters
		skmeans.buildClusterer(dataset);

		// output the clusters
		System.out.println(skmeans);

		// restore the label
		dataset.setClassIndex(labelIndex);
	}

	/**
	 * This method runs the apriori association rule miner on the
	 * @throws java.lang.Exception dataset.
	 */
	public void runApriori() throws Exception {

		// intialize apriori rule miner
		Apriori apriori = new Apriori();

		// create the association rules
		apriori.buildAssociations(dataset);

		// output the rules
		System.out.println(apriori);
	}

	/**
	 * This method runs the CfsSubsetEval feature selection algorithm on the
	 * dataset. Cfs uses a genetic algorithms for search.
	 *
	 * @throws java.lang.Exception
	 */
	//    public void runFeatureSelection() throws Exception {
	//        // initialize cfs feature selection algorithm
	//        CfsSubsetEval cfs = new CfsSubsetEval();
	//
	//        // compute similarities between features using cfs
	//        cfs.setLocallyPredictive(true);
	//        cfs.buildEvaluator(dataset);
	//
	//        // create a genetic algorithm
	//        GeneticSearch ga = new GeneticSearch();
	//
	//        // search for relevant attributes using ga with cfs as fitness function
	//        int[] relevant = ga.search(cfs, dataset);
	//
	//        // output the array of relevant attributes
	//        System.out.println("Array with relevant features indices= " + weka.core.Utils.arrayToString(relevant));
	//    }

	public static void main(String args[]) {
		JavaWekaExample example = new JavaWekaExample(global.Constants.analysisFileDirectory+"monks-3.arff");
		try {
			// read the dataset example
			example.readDataset(example.datasetPath);
			//System.out.println("*****Example Dataset*****");
			//System.out.println(example.dataset);

			// run the C4.5 decision tree example
			System.out.println("*****Example Decision Tree*****");
			example.runDecisionTree();

			// run the neural network example
			/*System.out.println("*****Example Neural Network*****");
             example.runNeuralNetwork();*/
			// additional example code
			/* example.runKNearestNeighbor(1);
             example.runKNearestNeighbor(2);
             example.runKNearestNeighbor(3);
             example.runKNearestNeighbor(4);
             example.runKNearestNeighbor(5);
             example.runKNearestNeighbor(20);

             example.runClusteringAlgorithm();
             example.runApriori();
             example.runFeatureSelection();
             example.generateWineDataset("C:/Users/LD Miller/Desktop");
			 */
		} catch (Exception ex) {
			Logger.getLogger(JavaWekaExample.class.getName()).log(Level.SEVERE, null, ex);
			System.exit(-1);
		}
	}
}
