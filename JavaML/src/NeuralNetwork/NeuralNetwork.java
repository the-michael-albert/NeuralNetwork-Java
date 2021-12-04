package NeuralNetwork;

import NeuralNetwork.Components.Layer;
import NeuralNetwork.Exceptions.NeuralNetworkException;
import NeuralNetwork.Tools.NeuralNetworkMathFunctions;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;


/**
 * Basic Neural Network with
 * integrated backpropagation
 *
 * @author Luca Jung
 * @version 3.2
 */
public class NeuralNetwork{

    private ArrayList<Layer> neuralNetwork = new ArrayList<>();
    private ArrayList<Double> neuralNetworkBias = new ArrayList<>();
    private NeuralNetworkMathFunctions mathFunctions = new NeuralNetworkMathFunctions();
    private boolean isConnected = false;

    /**
     * Constructor for creating a neural network for filling in (JSON)
     */
    public NeuralNetwork(){}

    /**
     * creates a neural network with
     * @param networkSize the array formatted as {INPUT, MIDDLE ... , OUTPUT}
     */
    public NeuralNetwork(int[] networkSize){
        for (int i = 0; i < networkSize.length; i++) {
            //add network layer
            neuralNetwork.add(new Layer(networkSize[i], "" + i));

            //add connection
            if(i > 0){
                neuralNetworkBias.add(null);
                for (int neuron = 0; neuron < neuralNetwork.get(i - 1).getNumberOfNeurons(); neuron++) {
                    for (int nextLayerNumberOfNeurons = 0; nextLayerNumberOfNeurons < neuralNetwork.get(i).getNumberOfNeurons(); nextLayerNumberOfNeurons++) {
                        neuralNetwork.get(i - 1).getNeuron(neuron).addConnection(null);
                    }
                }
            }
        }
        for (int neuron = 0; neuron < neuralNetwork.get(neuralNetwork.size() - 1).getNumberOfNeurons(); neuron++) {
            this.setActivationFunction(neuralNetwork.size() - 1, neuron, mathFunctions.LEAKYRELU_FUNCTION);
        }
    }

    /**
     * Connects neurons randomly using random values between min and max
     * @param min min weight for a connection
     * @param max max weight for a connection
     */
    public void connectNeuralNetworkRandomly(Double min, Double max) {
        for (int i = 1; i < neuralNetwork.size(); i++) {
            neuralNetworkBias.set(i - 1, mathFunctions.getRandomDouble(min,max));
            for (int neuron = 0; neuron < neuralNetwork.get(i - 1).getNumberOfNeurons(); neuron++) {
                for (int nextLayerNumberOfNeurons = 0; nextLayerNumberOfNeurons < neuralNetwork.get(i).getNumberOfNeurons(); nextLayerNumberOfNeurons++) {
                    Double randomDouble = mathFunctions.getRandomDouble(min,max);
                    neuralNetwork.get(i - 1).getNeuron(neuron).setConnection(nextLayerNumberOfNeurons, randomDouble);
                }
            }
        }
        this.setConnected(true);
    }

    /**
     * calls the connection method using preset values
     */
    public void connectNeuralNetworkRandomly() {
        this.connectNeuralNetworkRandomly(-0.8,0.8);
    }

    /**
     * Predict an output for after training
     * @param input the input that we are trying to predict
     * @return an output prediction that matches the training set
     * @throws Exception thrown if we have an error in the Neural Network
     */
    public Double[] predict(Double[] input) throws Exception {
        if(input.length == neuralNetwork.get(0).getNumberOfNeurons()){
            if(this.isNetworkReady()){
                //set input
                for (int inputNeuron = 0; inputNeuron < neuralNetwork.get(0).getNumberOfNeurons(); inputNeuron++) {
                    neuralNetwork.get(0).getNeuron(inputNeuron).setValue(input[inputNeuron]);
                }

                //calculate
                for (int layer = 1; layer < neuralNetwork.size(); layer++) {
                    for (int currentNeuron = 0; currentNeuron < neuralNetwork.get(layer).getNumberOfNeurons(); currentNeuron++) {
                        Double newValue = neuralNetworkBias.get(layer - 1);
                        for (int previousNeuron = 0; previousNeuron < neuralNetwork.get(layer - 1).getNumberOfNeurons(); previousNeuron++) {
                            newValue += neuralNetwork.get(layer - 1).getNeuron(previousNeuron).getConnection(currentNeuron) * neuralNetwork.get(layer - 1).getNeuron(previousNeuron).getValue();
                        }
                        newValue = mathFunctions.getValue(neuralNetwork.get(layer).getNeuron(currentNeuron).getActivationFunction(), newValue, false);
                        neuralNetwork.get(layer).getNeuron(currentNeuron).setValue(newValue);
                    }
                }

                Double[] output = new Double[neuralNetwork.get(neuralNetwork.size() - 1).getNumberOfNeurons()];
                for (int i = 0; i < output.length; i++) {
                    output[i] = neuralNetwork.get(neuralNetwork.size() - 1).getNeuron(i).getValue();
                }

                return output;

            }
            else{
                throw new NeuralNetworkException("Neural Network is not ready to use");
            }
        }
        else{
            throw new NeuralNetworkException("Unexpected input size");
        }
    }

    /**
     * trains the neural network off of an input and output
     * @param input the input array we want to train it off of
     * @param target the output array we would like it to generate
     * @param learningRate the multiplier for the weight correction delta
     * @throws Exception Any Neural Network exceptions that arise
     */
    public void trainNeuralNetwork(Double[] input, Double[] target, Double learningRate) throws Exception {
        predict(input);
        calculateError(target);
        addDeltaToWeights(learningRate);
    }

    /**
     * calculates error based on output values and the expected output
     * @param target the expected output values
     */
    private void calculateError(Double[] target){
        for (int layer = neuralNetwork.size() - 1; layer >= 0; layer--) {
            if (layer == neuralNetwork.size() - 1){
                for (int neuron = 0; neuron < neuralNetwork.get(layer).getNumberOfNeurons(); neuron++) {
                    neuralNetwork.get(layer).getNeuron(neuron).setError(
                            mathFunctions.getValue(
                                    neuralNetwork.get(layer).getNeuron(neuron).getActivationFunction(),
                                    neuralNetwork.get(layer).getNeuron(neuron).getValue(),
                                    true
                            ) * (neuralNetwork.get(layer).getNeuron(neuron).getValue() - target[neuron])
                    );
                }
            }
            else {
                for (int neuron = 0; neuron < neuralNetwork.get(layer).getNumberOfNeurons(); neuron++) {
                    Double sum = 0.0;
                    for (int nextLayerNeuron = 0; nextLayerNeuron < neuralNetwork.get(layer + 1).getNumberOfNeurons(); nextLayerNeuron++) {
                        sum += neuralNetwork.get(layer + 1).getNeuron(nextLayerNeuron).getError() * neuralNetwork.get(layer).getNeuron(neuron).getConnection(nextLayerNeuron);
                    }
                    neuralNetwork.get(layer).getNeuron(neuron).setError(
                            mathFunctions.getValue(
                                    neuralNetwork.get(layer).getNeuron(neuron).getActivationFunction(),
                                    neuralNetwork.get(layer).getNeuron(neuron).getValue(),
                                    true
                            ) * sum
                    );
                }
            }
        }
    }

    /**
     * adds a delta to the connections
     * @param learningRate learning (weight delta multiplier)
     */
    private void addDeltaToWeights(double learningRate){
        for (int layer = 1; layer < neuralNetwork.size(); layer++) {
            for (int neuron = 0; neuron < neuralNetwork.get(layer).getNumberOfNeurons(); neuron++) {
                for (int previousLayerNeuron = 0; previousLayerNeuron < neuralNetwork.get(layer - 1).getNumberOfNeurons(); previousLayerNeuron++) {
                    Double delta = -learningRate * neuralNetwork.get(layer - 1).getNeuron(previousLayerNeuron).getValue() * neuralNetwork.get(layer).getNeuron(neuron).getError();
                    neuralNetwork.get(layer - 1).getNeuron(previousLayerNeuron).setConnection(neuron, neuralNetwork.get(layer - 1).getNeuron(previousLayerNeuron).getConnection(neuron) + delta);
                }
            }
        }
    }

    /**
     * is the network connected?
     * @param state connected or not
     */
    private void setConnected(boolean state){
        this.isConnected = state;
    }

    /**
     * checks if network is ready to learn
     * @return if neurons are connected and size is bigger than 1
     */
    private boolean isNetworkReady(){
        if(isConnected && neuralNetwork.size() > 1){
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * get size of network Layers array
     * @return the size of network Layers array
     */
    public int getNetworkLayerSize(){
        return neuralNetwork.size();
    }

    /**
     * placeholder for JSON Generation
     * @param n ignored
     */
    public void setNetworkLayerSize(int n){
        //do nothing
    }

    /**
     * gets size of a layer
     * @param layer the layer index in network
     * @return size of the layer
     */
    public int getNumberOfNeurons(int layer){
        if(layer >= 0 && layer < getNetworkLayerSize()){
            return neuralNetwork.get(layer).getNumberOfNeurons();
        }
        else {
            return 0;
        }
    }

    /**
     * calculated how many neurons a neuron is connected to
     * @param layer the layer index in neuronLayers
     * @param neuron the neuron index in Layer
     * @return the number of connections the neuron posesses
     */
    public int getNumberOfConnections(int layer, int neuron){
        if(layer >= 0 && layer < getNetworkLayerSize()){
            if(neuron >= 0 && neuron < getNumberOfNeurons(layer)){
                return neuralNetwork.get(layer).getNeuron(neuron).getNumberOfConnections();
            }
        }
        return 0;
    }

    /**
     * Sets activation function for a neuron
     * @param layer the layer the neuron is in
     * @param neuron the neurons position in layer
     * @param function the function Constant from NeuralNetworkMathFunction
     */
    public void setActivationFunction(int layer, int neuron, int function){
        neuralNetwork.get(layer).getNeuron(neuron).setActivationFunction(function);
    }

    /**
     * Sets the connection weight for a specific index
     * @param layer the layer index in neural network
     * @param neuron the neurons index
     * @param connection the index of the neuron's connection
     * @param connectionValue the weight of the connection
     */
    public void setConnection(int layer, int neuron, int connection, double connectionValue){
        neuralNetwork.get(layer).getNeuron(neuron).setConnection(connection, connectionValue);
    }

    /**
     * returns the connection weight for a neuron
     * @param layer the layer we are targeting
     * @param neuron the neuron we are targeting
     * @param connection the connection index of the neuron
     * @return the connection weight as a double
     */
    public Double getConnection(int layer, int neuron, int connection){
        return neuralNetwork.get(layer).getNeuron(neuron).getConnection(connection);
    }

    /**
     * returns the neural network arrayList (for JSON)
     * @return the Arraylist passed through ref.
     */
    public ArrayList<Layer> getNeuralNetwork() {
        return neuralNetwork;
    }

    /**
     * sets the neural network (for JSON)
     * @param neuralNetwork the network we are setting it to
     */
    public void setNeuralNetwork(ArrayList<Layer> neuralNetwork) {
        this.neuralNetwork = neuralNetwork;
    }

    /**
     * get the biases for the neural network
     * @return the arraylist of biases
     */
    public ArrayList<Double> getNeuralNetworkBias() {
        return neuralNetworkBias;
    }

    /**
     * placeholder for JSON Generation
     * @param neuralNetworkBias ignored
     */
    public void setNeuralNetworkBias(ArrayList<Double> neuralNetworkBias) {
        this.neuralNetworkBias = neuralNetworkBias;
    }

    /**
     * get the Math Function we are using
     * @return a copy of the math function
     */
    public NeuralNetworkMathFunctions getMathFunctions() {
        return mathFunctions;
    }

    /**
     * sets the Neural Network Math Functions (for JSON)
     * @param mathFunctions the Neural Network Math Functions we are setting it to
     */
    public void setMathFunctions(NeuralNetworkMathFunctions mathFunctions) {
        this.mathFunctions = mathFunctions;
    }

    /**
     * is the network connected
     * @return  whether the network is connected or not
     */
    public boolean isConnected() {
        return isConnected;
    }

    /**
     * exports the network to a JSON String
     * @return a String in Json format with all of the instance variable
     */
    public String toString(){
        for (int i = 0; i < neuralNetwork.size(); i++) {
            for (int j = 0; j < neuralNetwork.get(i).getNumberOfNeurons(); j++) {
                neuralNetwork.get(i).getNeuron(j).setName(i + "-" + j);
            }
        }

        String out  = "";
        ObjectMapper mapper = new ObjectMapper();
        try {
            out += mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }


        return out;
    }

    /**
     * Generated a NeuralNetwork Object from a JSON String
     * @param s the JSON String
     * @return the Neural Network Object
     * @throws JsonProcessingException Error in case the JSON String is incorrectly Formatted.
     */
    public static NeuralNetwork fromString(String s) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(s, NeuralNetwork.class);
    }



}