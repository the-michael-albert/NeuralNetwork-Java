package NeuralNetwork.Components;

import NeuralNetwork.Exceptions.NeuralNetworkException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * The Neuron is the smallest unit at the neural network.
 * It saves all connections to the next layes neurons,
 * its value and its error (backpropagation)
 *
 * @author Luca Jung
 * @version 3.0
 */
public class Neuron {
    String name;


    private ArrayList<Double> connections = new ArrayList<>();
    private int activationFunction = 0;
    private Double value = null;
    private Double error = null;


    /**
     * sets the value of the neuron
     * @param value the value of the neuron
     */
    public void setValue(Double value){
        this.value = value;
    }

    /**
     * get Neuron Value
     * @return the Neurons output
     */
    public Double getValue(){
        return this.value;
    }

    /**
     * puts a connection in the connections array
     * @param value the value we are placing as a weight
     */
    public void addConnection(Double value){
        connections.add(value);
    }

    /**
     * overwrites a connection
     * @param index the index of the weight we want to overwrite
     * @param value the new weight
     */
    public void setConnection(int index, Double value){
        connections.set(index, value);
    }

    /**
     * the new activation function for this method
     * @param activationFunction the Activation function constant from the NeuralNetworkMathFunctions class
     */
    public void setActivationFunction(int activationFunction){
        this.activationFunction = activationFunction;
    }

    /**
     * get the activation function key
     * @return the Activation Function Constant
     */
    public int getActivationFunction(){
        return this.activationFunction;
    }

    /**
     * set the error value for the Neuron
     * @param error the error double
     */
    public void setError(Double error){
        this.error = error;
    }

    /**
     * get the error value for this neuron
     * @return the error as a double
     */
    public Double getError() {
        return this.error;
    }

    /**
     * gets a double from the connection weight array
     * @param index the location in the connection array
     * @return the weight
     */
    public Double getConnection(int index){
        if(index >= 0 && index < connections.size()){
            return connections.get(index);
        }
        else{
            return null;
        }
    }

    /**
     * get how many connections there are
     * @return the number of connections in the weight list
     */
    public int getNumberOfConnections(){
        return connections.size();
    }

    /**
     * set number of connections (for JSON)
     * @param n the number of connections
     */
    public void setNumberOfConnections(int n){
        //do nothing
    }

    /**
     * gets the name of the neuron
     * @return Neuron's name String
     */
    public String getName() {
        return name;
    }

    /**
     * sets the name of the Neuron
     * @param name the neuron name
     */
    public void setName(String name) {
        this.name = name;
    }

    //EXPORTING METHODS

    /**
     * get the Connections array (weights)
     * @return Arraylist of Connections and Weights
     */
    public ArrayList<Double> getConnections(){
        return connections;
    }

    /**
     * gets Hashcode (for JSON)
     * @return the Hashcode calculated based on instance variables
     */
    public int getHashCode(){
        return hashCode();
    }

    /**
     * sets a hashcode (for JSON)
     * @param hashcode ignored
     */
    public void setHashCode(int hashcode) {
        //do nothing
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Neuron)) return false;

        Neuron neuron = (Neuron) o;

        if (activationFunction != neuron.activationFunction) return false;
        if (!name.equals(neuron.name)) return false;
        if (!connections.equals(neuron.connections)) return false;
        if (!value.equals(neuron.value)) return false;
        return error.equals(neuron.error);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + connections.hashCode();
        result = 31 * result + activationFunction;
        result = 31 * result + value.hashCode();
        result = 31 * result + error.hashCode();
        return result;
    }

    @Override
    public String toString() {
        ObjectMapper mapper = new ObjectMapper();

        try {
            return mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * generates a neuron from a string
     * @param s the JSON String
     * @return a neuron Object
     * @throws JsonProcessingException Just in case the neuron does not import correctly
     */
    public static Neuron fromString(String s) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Neuron n = mapper.readValue(s, Neuron.class );
        System.out.println(n.hashCode());
        return n;
    }


    //    //format(:NAME,ACTIVATION,VALUE,ERROR;CONNECTIONS[,];HASHCODE)
//    public String toString(){
//        String out = getName()
//                + "," + activationFunction
//                + "," + value + ","
//                + error + ";";
//
//        for (Double d: connections) {
//            out += d + ",";
//        }
//        if (connections.size() == 0){
//            out += ",";
//        }
//        return out.substring(0, out.length() - 1) + ";" + hashCode();
//    }
//
//    //format(:NAME,ACTIVATION,VALUE,ERROR;CONNECTIONS[,];HASHCODE)
//
//    public static Neuron fromString(String s) throws NeuralNetworkException {
//        Neuron out = new Neuron();
//        String[] segments = s.split(";");
//        System.out.println(Arrays.toString(segments));
//
//        //populating connections
//        ArrayList<Double> connections= new ArrayList<>();
//        for (String doubleStrings: segments[1].split(",")) {
//            connections.add(Double.valueOf(doubleStrings));
//        }
//        out.connections = connections;
//
//        String[] identifiers = segments[0].split(",");
//
//        out.name = identifiers[0];
//        out.activationFunction = Integer.parseInt(identifiers[1]);
//        out.value = Double.parseDouble(identifiers[2]);
//        out.error = Double.parseDouble(identifiers[3]);
//
//        if (out.hashCode() == Integer.parseInt(segments[2])){
//            return out;
//        }else {
//            throw new NeuralNetworkException("FAILURE TO IMPORT NEURON [HASHCODE]: " + Integer.parseInt(segments[2]) + " != " + out.hashCode());
//        }
//    }

}
