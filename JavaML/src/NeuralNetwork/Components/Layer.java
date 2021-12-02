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

    public Layer() {
    }

    public Layer(int numberOfNeurons, String layerName){
        this.layerName = layerName;
        for (int i = 0; i < numberOfNeurons; i++) {
            networkLayer.add(new Neuron());
        }
    }

    public Neuron getNeuron(int index){
        if(index <= networkLayer.size() && index >= 0){
            return networkLayer.get(index);
        }
        else{
            return null;
        }
    }

    public int getNumberOfNeurons(){
        return networkLayer.size();
    }

    public void setNumberOfNeurons(int n){
        //do nothing
    }


    public void setNetworkLayer(ArrayList<Neuron> networkLayer) {
        this.networkLayer = networkLayer;
    }

    public ArrayList<Neuron> getNetworkLayer() {
        return networkLayer;
    }


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
