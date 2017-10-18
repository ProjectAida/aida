package execute;

import weka.WekaAdapter;
import global.Constants;
/**
 * Class responsible for running the training from the pointed to file. This is solely a main class that uses the WekaAdapter class
 * to use the weka ANN.
 * First argument is the training set, the second argument is the testing set.
 * @author maanas
 *
 */
public class RunTraining {

	public static void main(String args[]){
		
		
		//Arguments to wekaAdapter: WekaAdapter(training_datasetPath, testing_datasetPath)
		// First argument is the dataset that will be used to create the classifier. and second argumnt is used for testing
		WekaAdapter wa = new WekaAdapter(Constants.analysisFileDirectory+"TrainList.arff",Constants.analysisFileDirectory+"TestList.arff");
		try {
			System.out.println("Reading Dataset...");
			wa.readDataset();
			System.out.println("Running Neural Network...");
			wa.runNeuralNetwork();
			//wa.runDecisionTree();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}


