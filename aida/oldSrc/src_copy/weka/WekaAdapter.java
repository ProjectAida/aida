package weka;

import global.Constants;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

import weka.classifiers.Evaluation;
import weka.classifiers.evaluation.Prediction;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.classifiers.trees.J48;
import weka.core.Instances;

/**
 * Class that is responsible for running the tests and training on the ANN and generating results. 
 * This class acts as the classifier for the project which uses the arrtibutes generated by featureExtraction 
 * and compiled by AnalysisFileWriter.
 * @author mdatla
 *
 */
public class WekaAdapter implements Prediction{

	private Instances trainSet;
	private Instances testSet;
	private String trainSetPath;
	private String testSetPath;

	public WekaAdapter(String trainSetPath){
		this.trainSetPath=trainSetPath;
	}
	public WekaAdapter(String trainSetPath, String TestSetPath){
		this.trainSetPath=trainSetPath;
		this.testSetPath=TestSetPath;

	}

	public void readDataset() throws Exception{

		trainSet = new Instances(new FileReader(this.trainSetPath));
		trainSet.setClassIndex(trainSet.numAttributes()-1);

		testSet = new Instances(new FileReader(this.testSetPath));
		testSet.setClassIndex(testSet.numAttributes()-1);

	}

	public void runDecisionTree() throws Exception{
		Instances test,train;
		trainSet.randomize(new Random(123));

		trainSet.deleteAttributeAt(0);

		for(int i=0;i<10;i++){

			double correct = 0;

			test = trainSet.testCV(10,i);
			train = trainSet.trainCV(10, i);

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

			tree.buildClassifier(train);

			System.out.println(tree);

			double total = test.numInstances();

			System.out.println(correct);
			System.out.println(total);


			double testCorrect = 0,trainCorrect =0;
			double testTotal = test.numInstances();
			double trainTotal =  train.numInstances();

			for(int k=0; k<test.numInstances();k++){
				if (test.instance(k).classValue() == tree.classifyInstance(test.instance(k))) {
					correct++;
				}
			}
			System.out.println("Accuracy of the Decision Tree is: " + (correct / total)*100 +"%");
			System.out.println("==================================================================================");
		}
	}

	/**
	 * This methods uses the *.arff files as a dataset and trains the ANN and then uses the same ANN to test the images in the testing dataset.
	 * Training time is set to 1000, and the results are compiled into a *.csv file. 
	 * @throws Exception
	 */
	public void runNeuralNetwork() throws Exception{
		Instances test,train,testCopy,trainCopy;
		Instances test2 = null;

		int iterations =1000;

		System.out.println("Initializing");
		trainSet.randomize(new Random(123));

		Instances trainSetCopy = new Instances(trainSet, 0, trainSet.numInstances());
		//trainSet.deleteAttributeAt(0);
		Instances testSetCopy = new Instances(testSet, 0, testSet.numInstances());
		testSet.deleteAttributeAt(0);

		System.out.println("Creating Files");
		File snippetOut = new File(Constants.data,"TrueSnippets.txt");
		File pageOut = new File(Constants.data,"TruePages.txt");
		BufferedWriter snippetStream = new BufferedWriter(new FileWriter(snippetOut, false));
		BufferedWriter pageStream = new BufferedWriter(new FileWriter(pageOut, false));
		StringBuilder sbSnippet = new StringBuilder();
		StringBuilder sbPage = new StringBuilder();

		MultilayerPerceptron network = new MultilayerPerceptron();
		network.setTrainingTime(iterations);

		train = trainSet;
		test = testSet;
		trainCopy = trainSetCopy;
		testCopy = testSetCopy;

		System.out.println("Building Classifier");
		network.buildClassifier(train);

		
		ArrayList<String> parentList = new ArrayList<String>();
		
		System.out.println("Classifier Runnning");
		for (int k = 0; k < test.numInstances(); k++) {
		    String result = null;
		    if(network.classifyInstance(test.instance(k))==1.0){
			String[] snippetName= testCopy.instance(k).toString(0).split("_");
			String parentName = snippetName[0]+"_"+snippetName[1]+"_"+snippetName[2]+"_"+snippetName[3]+".jpg";
			if(!parentList.contains(parentName)){
			parentList.add(parentName);
		    }
			sbSnippet.append(testCopy.instance(k).toString(0)+"\n");
		    }
		    
		}

		System.out.println("Printing Snippet List");
                    snippetStream.append(sbSnippet.toString());
                    snippetStream.flush();
                    snippetStream.close();

                    for(String s: parentList){
                        sbPage.append(s+"\n");
                    }
                    System.out.println("Printing Page List");
                    pageStream.append(sbPage.toString());
                    pageStream.flush();
                    pageStream.close();

                    System.out.println("End");




		//Evaluation classes is used to calculate accuracies, and other useful machine learning statistics.
		
//		Evaluation eval = new Evaluation(train);
//		eval.evaluateModel(network, test);
//		System.out.println("Accuracy: "+((1-eval.errorRate())*100));
//		System.out.println("Precision: "+eval.precision(1)*100);
//		System.out.println("Recall: "+eval.recall(1)*100);
//		System.out.println("True Negative Rate: "+eval.trueNegativeRate(1)*100);
//		System.out.println("False Positive Rate: "+eval.falsePositiveRate(1)*100);
//		System.out.println("False Negative Rate: "+eval.falseNegativeRate(1)*100);
//		System.out.println("True Positives: "+eval.numTruePositives(1));
//		System.out.println("True Negatives: "+eval.numTrueNegatives(1));
//		System.out.println("False Positives: "+eval.numFalsePositives(1));
//		System.out.println("False Negatives: "+eval.numFalseNegatives(1));


		
		// FinalResultGenerator is imported to generate results for the parent newspapers and its statistics.
		
//		FinalResultGenerator rg = new FinalResultGenerator();
//		rg.generateResults();




	}
	@Override
	public double actual() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public double predicted() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public double weight() {
		// TODO Auto-generated method stub
		return 0;
	}

}

