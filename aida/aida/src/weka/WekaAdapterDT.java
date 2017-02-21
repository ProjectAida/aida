package weka;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

import weka.classifiers.trees.J48;
import weka.core.Instances;

public class WekaAdapterDT {

	private Instances dataset;
	private String datasetPath;

	public WekaAdapterDT(String datasetPath){
		this.datasetPath=datasetPath;
	}

	public void runDecisionTree() throws Exception{
		Instances test,train,testCopy,trainCopy;
		dataset.randomize(new Random(123));
		
		Instances datasetCopy = new Instances(dataset, 0, dataset.numInstances());
		dataset.deleteAttributeAt(0);
		
		for(int i=1;i<=10;i++){
			test = dataset.testCV(10,i);
			train = dataset.trainCV(10, i);
			testCopy = datasetCopy.testCV(10, i);
			trainCopy = datasetCopy.trainCV(10, i);
			
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
			
			double correct = 0;
			double total = test.numInstances();
			
			for(int j=0; j<test.numInstances();j++){
				if (test.instance(j).classValue() == tree.classifyInstance(test.instance(j))) {
					correct++;
				}
			}
			
			System.out.println("Accuracy of the Decision Tree is: " + correct / total);
			
		}
	}
}
