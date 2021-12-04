package NeuralNetwork.Components;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;

/**
 * Layer represents a layer at the neural network.
 * Each layer owns n neurons.
 *
 * @author Luca Jung
 * @version 1.0
 */
public class Layer {
    String layerName;



    private ArrayList<Neuron> networkLayer = new ArrayList<>();

    /**
     * Layer instantiater (for JSON)
     */
    public Layer() {
    }

    /**
     * Generates a layer
     * @param numberOfNeurons the number of neurons in the layer
     * @param layerName the name of layer (for JSON)
     */
    public Layer(int numberOfNeurons, String layerName){
        this.layerName = layerName;
        for (int i = 0; i < numberOfNeurons; i++) {
            networkLayer.add(new Neuron());
        }
    }

    /**
     * returns a neuron from the list
     * @param index the index of the neuron
     * @return the neuron at the index
     */
    public Neuron getNeuron(int index){
        if(index <= networkLayer.size() && index >= 0){
            return networkLayer.get(index);
        }
        else{
            return null;
        }
    }

    /**
     * gets the number of neurons in layer
     * @return the size of the layer
     */
    public int getNumberOfNeurons(){
        return networkLayer.size();
    }

    /**
     * placeholder (for JSON)
     * @param n ignored
     */
    public void setNumberOfNeurons(int n){
        //do nothing
    }

    /**
     * puts in Network Layer
     * @param networkLayer the layer we are setting this layer to
     */
    public void setNetworkLayer(ArrayList<Neuron> networkLayer) {
        this.networkLayer = networkLayer;
    }

    /**
     * get the Layers Neuron List
     * @return the List passed through by value
     */
    public ArrayList<Neuron> getNetworkLayer() {
        return networkLayer;
    }

    /**
     * exports the Layer as JSON String
     * @return the JSON String
     */
    public String toString(){
        String out = "";
        ObjectMapper mapper = new ObjectMapper();
        try {
            System.out.println(mapper.writeValueAsString(this));
            out += mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return out;
    }

}
