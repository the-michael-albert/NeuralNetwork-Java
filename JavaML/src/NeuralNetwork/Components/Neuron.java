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





    public void setValue(Double value){
        this.value = value;
    }

    public Double getValue(){
        return this.value;
    }

    public void addConnection(Double value){
        connections.add(value);
    }

    public void setConnection(int index, Double value){
        connections.set(index, value);
    }

    public void setActivationFunction(int activationFunction){
        this.activationFunction = activationFunction;
    }

    public int getActivationFunction(){
        return this.activationFunction;
    }

    public void setError(Double error){
        this.error = error;
    }

    public Double getError() {
        return this.error;
    }

    public Double getConnection(int index){
        if(index >= 0 && index < connections.size()){
            return connections.get(index);
        }
        else{
            return null;
        }
    }

    public int getNumberOfConnections(){
        return connections.size();
    }

    public void setNumberOfConnections(int n){
        //do nothing
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    //EXPORTING METHODS
    public ArrayList<Double> getConnections(){
        return connections;
    }


    public int getHashCode(){
        return hashCode();
    }

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
