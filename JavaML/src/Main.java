import NeuralNetwork.Components.Layer;
import NeuralNetwork.Components.Neuron;
import NeuralNetwork.NeuralNetwork;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import piclab.Picture;
import sun.security.mscapi.CPublicKey;

import java.io.File;
import java.util.Arrays;

public class Main {

    //x is at pos [1]
    //o is at pos [0]

    public static void main(String[] args) throws Exception {
        String input = "{\"neuralNetwork\":[{\"networkLayer\":[{\"name\":\"0-0\",\"connections\":[-0.3048484690059022,0.6495142508973343,1.7310964554815944,1.732042809009672],\"activationFunction\":0,\"value\":0.3,\"error\":-7.291469157499494E-6,\"hashCode\":-1356730510,\"numberOfConnections\":4},{\"name\":\"0-1\",\"connections\":[-0.700210495277092,0.10889201143684159,1.5246316039453125,0.9105229355183354],\"activationFunction\":0,\"value\":0.2,\"error\":-7.259043403199466E-6,\"hashCode\":-2008681385,\"numberOfConnections\":4}],\"numberOfNeurons\":2},{\"networkLayer\":[{\"name\":\"1-0\",\"connections\":[-1.567667934650443],\"activationFunction\":0,\"value\":0.48620979623687177,\"error\":1.1132940251546723E-5,\"hashCode\":1093179258,\"numberOfConnections\":1},{\"name\":\"1-1\",\"connections\":[-0.026789351175589823],\"activationFunction\":0,\"value\":0.596993734482707,\"error\":1.8097496885024414E-7,\"hashCode\":707610822,\"numberOfConnections\":1},{\"name\":\"1-2\",\"connections\":[1.4741784535553606],\"activationFunction\":0,\"value\":0.7311720261219921,\"error\":-9.383246451312828E-6,\"hashCode\":-206392367,\"numberOfConnections\":1},{\"name\":\"1-3\",\"connections\":[1.2082790522946154],\"activationFunction\":0,\"value\":0.7064157432110348,\"error\":-7.717692257762645E-6,\"hashCode\":692738260,\"numberOfConnections\":1}],\"numberOfNeurons\":4},{\"networkLayer\":[{\"name\":\"2-0\",\"connections\":[],\"activationFunction\":2,\"value\":0.5143938853502897,\"error\":-3.002680719721429E-5,\"hashCode\":175419737,\"numberOfConnections\":0}],\"numberOfNeurons\":1}],\"neuralNetworkBias\":[0.17632183176198046,-0.6388229242580403],\"mathFunctions\":{\"SIGMOID_FUNCTION\":0,\"RELU_FUNCTION\":1,\"LEAKYRELU_FUNCTION\":2},\"connected\":true,\"networkLayerSize\":3}";
        NeuralNetwork n = NeuralNetwork.fromString(input);
        System.out.println(Arrays.toString(n.predict(new Double[]{0.2, 0.2})));
    }

}
