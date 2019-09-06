import java.util.*;
import java.io.*;

public class HMM3{
    
    private static Scanner sc = new Scanner(System.in);
    public static void main(String[] args){
       
        /*
         *  Matrix where row = current state, col = next state (timestep), elements = joint probability of state entering next state (row -> col).
         */
        Matrix transition_matrix = create_matrix();

        /*
         *  Matrix where row = current state, col = observation, elements = joint probability of state and observation.
         */
        Matrix emission_matrix = create_matrix();

        /*
         *  Matrix where row size = 1, col = states, elements = probability of entering initial state (col).
         */
        Matrix initial_state_matrix = create_matrix();

        /*
         *  Array of observations where array[0] is the first observation, array[n-1] is the n-th observation
         */
        int[] observation_sequence = create_observation_sequence();

        /*
         *  Matrix where row = alpha at time(row), col = states, elements = probability of entering state (col) given sequence of previous observations.
         */
        ArrayList<Matrix> alpha_matrix = new ArrayList<Matrix>();

        /*
         * Array of double that represent the scale factor used. Where scale factor is scaling from the previous timestep.
         */
        double[] scale_factor = new double[observation_sequence.length];

        /*
         *  Matrix where row = beta at time(row), col = states, elements = probability of observing future observations at current timestep(row) and state(col).
         */
        ArrayList<Matrix> beta_matrix = new ArrayList<Matrix>();

        alpha_pass(alpha_matrix, observation_sequence, transition_matrix, emission_matrix, initial_state_matrix, scale_factor);
        beta_pass(beta_matrix, observation_sequence, transition_matrix, emission_matrix, initial_state_matrix, scale_factor);

        Matrix final_alpha = alpha_matrix.get(alpha_matrix.size()-1);
        double total = 0;

        for(int i=0; i<final_alpha.get_elements()[0].length; i++){
            total += final_alpha.get_elements()[0][i];
        }
        for(int i=0; i<scale_factor.length; i++){
            total *= scale_factor[i];
        }

        System.out.println(total);
    }

    public static Matrix create_matrix(){
        int row;
        int col;

        row = sc.nextInt();
        col = sc.nextInt();
        double[][] elements = new double[row][col];

        for(int j=0; j<row; j++){
            for(int k=0; k<col; k++){
                elements[j][k] = sc.nextDouble();
            }
        }
        Matrix new_matrix = new Matrix(row,col,elements);
        return new_matrix;
    }

    public static int[] create_observation_sequence(){
        int[] observation_sequence;
        int length = sc.nextInt();

        observation_sequence = new int[length];
        for(int i=0; i<length; i++){
            int result = sc.nextInt();
            observation_sequence[i] = result;
        }

        return observation_sequence;
    }

    /*
     * Function used to initialise forward algorithm
     */
    public static void alpha_pass(ArrayList<Matrix> alpha_matrix, int[] observation_sequence, Matrix transition_matrix, Matrix emission_matrix, Matrix initial_state_matrix, double[] scale_factor){
        
        Matrix next_state = new Matrix();
        Matrix current_state = new Matrix();
        double current_scale = 0;
        
        while(alpha_matrix.size() != observation_sequence.length){

            //calculate alpha(1)
            // alpha(1) = pi (written below) *P(x|0)
            if(alpha_matrix.size() == 0) next_state = initial_state_matrix;//.multiply(transition_matrix);
            // alpha(t) = alpha(t-1)*B (written below) *P(x|0)
            else next_state = current_state.multiply(transition_matrix);

            // multiplication of P(x|0)
            next_state = alpha_multiply_observation(next_state, emission_matrix, observation_sequence[alpha_matrix.size()]);

            // Obtaining summation of alpha(t) to get scale factor
            current_scale = 0;
            for(int i = 0; i<next_state.get_col(); i++){
                current_scale += next_state.get_elements()[0][i];
            }

            // Scaling by scale factor
            for(int i = 0; i<next_state.get_col(); i++){
                next_state.get_elements()[0][i]/=current_scale;
            }

            // Storing alpha and scale factor
            scale_factor[alpha_matrix.size()] = current_scale;
            alpha_matrix.add(next_state);

            // alpha is passed down and saved
            current_state = next_state;
        }
    }

    /*
     * Function used to multiply given state distribution probablility and emission distribution of states, similar to dot product of emissions.
     */

    public static Matrix alpha_multiply_observation(Matrix state_probability, Matrix emission_matrix, int observation){

        double[][] alpha = new double[1][emission_matrix.get_row()];
        for(int i=0; i<emission_matrix.get_row(); i++){
            for(int j=0; j<emission_matrix.get_col(); j++){
                if(observation == j){
                    alpha[0][i] = Math.exp(Math.log(state_probability.get_elements()[0][i]) + Math.log(emission_matrix.get_elements()[i][j]));
                } 
            }
        }
        return  new Matrix(alpha.length,alpha[0].length,alpha);
    }

    public static void beta_pass(ArrayList<Matrix> beta_matrix, int[] observation_sequence, Matrix transition_matrix, Matrix emission_matrix, Matrix initial_state_matrix, double[] scale_factor){

        //initializing beta(T), current is t, next is t+1
        Matrix current_beta;
        Matrix next_beta;

        double[][] final_beta = new double[1][emission_matrix.get_row()];
        for(int i = 0; i<final_beta[0].length; i++){
            final_beta[0][i] = scale_factor[observation_sequence.length-1];
        }
        current_beta = new Matrix(final_beta.length, final_beta[0].length, final_beta);

        //initializing arraylist of beta matrices
        for(int i =0; i<observation_sequence.length; i++){
            beta_matrix.add(new Matrix());
        }

        beta_matrix.set(observation_sequence.length-1, current_beta);

        for(int i = observation_sequence.length-2; i>= 0; i--){
            next_beta = current_beta;
            current_beta = next_beta.multiply(transition_matrix);
            current_beta = alpha_multiply_observation(current_beta, emission_matrix, observation_sequence[i+1]);
            current_beta = current_beta * scale_factor[i];
            beta_matrix.set(i, current_beta);
        }

    }
}