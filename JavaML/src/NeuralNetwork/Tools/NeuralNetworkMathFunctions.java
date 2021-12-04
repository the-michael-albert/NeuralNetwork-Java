package NeuralNetwork.Tools;

/**
 * NeuralNetworkMathFunctions are used by the neural network
 * in order to store activation functions and their derivatives.
 *
 * @author Luca Jung
 * @version 1.0
 */
public class NeuralNetworkMathFunctions {

    public final int SIGMOID_FUNCTION = 0;
    public final int RELU_FUNCTION = 1;
    public final int LEAKYRELU_FUNCTION = 2;

    /**
     * calculates the output of a NN function
     * @param function the NN function
     * @param x the value to squish
     * @param derivative whether we are trying to calculate a
     * @return the value of the function
     */
    public Double getValue(int function, Double x, boolean derivative){
        switch (function){
            case SIGMOID_FUNCTION:
                if(derivative){
                    return this.sigmoidDerivativeFunction(x);
                }
                else {
                    return this.sigmoidFunction(x);
                }
            case RELU_FUNCTION:
                if(derivative){
                    return this.reLUDerivativeFunction(x);
                }
                else {
                    return this.reLUFunction(x);
                }
            case LEAKYRELU_FUNCTION:
                if(derivative){
                    return this.leakyReLUDerivativeFunction(x);
                }
                else {
                    return this.leakyReLUFunction(x);
                }
        }
        return null;
    }

    /**
     * Sigmoid function (squishing)
     * @param x the input value to squish
     * @return the output of the function (0 to 1)
     */
    private Double sigmoidFunction(Double x){
        return 1 / (1 + Math.exp(-x));
    }

    /**
     * derivates the squishing function
     * @param x the value we are deriving
     * @return the original input to a sigmoid
     */
    private Double sigmoidDerivativeFunction(Double x){
        return sigmoidFunction(x) * (1 - sigmoidFunction(x));
    }

    /**
     * use the RELU function
     * @param x the input value
     * @return either 0 or x, whichever is greater
     */
    private Double reLUFunction(Double x){
        if(x > 0){
            return x;
        }
        else {
            return 0.0;
        }
    }

    /**
     * derivative of the relu function
     * @param x the input
     * @return either 1 or 0
     */
    private Double reLUDerivativeFunction(Double x){
        if(x > 0){
            return 1.0;
        }
        else {
            return 0.0;
        }
    }

    /**
     * Leaky ReLU Function [if x is less than 0, x/100: else x]
     * @param x the input
     * @return [if x is less than 0, x/100: else x]
     */
    private Double leakyReLUFunction(Double x){
        if(x > 0){
            return x;
        }
        else {
            return 0.01 * x;
        }
    }

    /**
     * derivative of the LeakyReLU Function
     * @param x the input
     * @return 1 if x > 0 or 0.01
     */
    private Double leakyReLUDerivativeFunction(Double x){
        if(x > 0){
            return 1.0;
        }
        else {
            return 0.01;
        }
    }

    /**
     * generates a random double
     * @param min minimum possible
     * @param max max possible
     * @return random double between min and max
     */
    public Double getRandomDouble(Double min, Double max){
        return min + Math.random() * (max - min);
    }

}