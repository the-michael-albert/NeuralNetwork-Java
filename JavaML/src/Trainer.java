import NeuralNetwork.NeuralNetwork;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

/**
 * @author Michael Albert
 * @course CSS 143 B Prof. Kalmin
 * @school UW Bothell
 */
public class Trainer {
    final int inputLength;
    final int outputLength;

    public ArrayList<double[][]> trainingSets = new ArrayList<>();

    public Trainer(int inputLength, int outputLength) {
        this.inputLength = inputLength;
        this.outputLength = outputLength;

    }

    public void exportTo(File f){
        try {
            PrintWriter writer = new PrintWriter(f);

            for (double[][] set: trainingSets) {
                String line = "";
                for (int i = 0; i < set[0].length; i++) {
                    line += set[0][i] + ",";
                }
                line = line.substring(0, line.length() - 1) + ":";
                for (int i = 0; i < set[1].length; i++) {
                    line += set[1][i] + ",";
                }
                line = line.substring(0, line.length() - 1);
                writer.write(line + "\n");
            }
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public boolean importFrom(File f){
        try {
            Scanner s = new Scanner(f);
            while (s.hasNextLine()){
                String line = s.nextLine();
                double[][] ioArray = lineToTraining(line);
                trainingSets.add(ioArray);
            }

            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    private double[][] lineToTraining(String s){
        String s1 = s.split(":")[0];
        String s2 = s.split(":")[1];
        String[] strArr1 = s1.split(",");
        String[] strArr2 = s2.split(",");

        double[] input = new double[strArr1.length];
        double[] output = new double[strArr2.length];

        for (int i = 0; i < strArr1.length; i++) {
            input[i] = Double.parseDouble(strArr1[i]);
        }

        for (int i = 0; i < strArr2.length; i++) {
            output[i] = Double.parseDouble(strArr2[i]);
        }

        return new double[][]{input, output};
    }

    public boolean addSet(double[] input, double[] output){
        if (input.length != inputLength || output.length != outputLength){
            return false;
        }
        this.trainingSets.add(new double[][]{input, output});
        return true;
    }

//    public NeuralNetwork trainedNetwork(String trainerName){
//        for (int i = 0; i < inputLength.; i++) {
//
//        }
//    }




}
