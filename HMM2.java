import java.util.*;
import java.io.*;

public class HMM2{
    
    private static Scanner sc = new Scanner(System.in);
    public static void main(String[] args){
       
        /*
         *  Matrix where row = current state, col = next state (timestep), elements = joint probability of state entering next state (row -> col)
         */
        Matrix transition_matrix = create_matrix();

        /*
         *  Matrix where row = current state, col = observation, elements = joint probability of state and observation
         */
        Matrix emission_matrix = create_matrix();

        /*
         *  Matrix where row size = 1, col = states, elements = probability of entering initial state (col)
         */
        Matrix initial_state_matrix = create_matrix();

        /*
         *  Array of observations where array[0] is the first observation, array[n-1] is the n-th observation
         */
        int[] observation_sequence = create_observation_sequence();

        /*
         *  Matrix where row = alpha at time(row), col = states, elements = probability of entering state (col) given sequence of previous observations
         */
        ArrayList<Matrix> delta_matrix = new ArrayList<Matrix>();

        /*
         *  Matrix where row = alpha at time(row), col = states, element is storing the most possible previous state.
         * 
         *  Eg. At row = 1, element stores most possible state of timestep = 1 given the state (col) in timestep 2.
         */
        ArrayList<Matrix> prev_state_index = new ArrayList<Matrix>();

        /*
         * Array of integers that represent the optimal/most possible state sequence, array[0] being the initial state sequence
         */
        int[] optimal_state_sequence = new int[observation_sequence.length];

        delta_pass(delta_matrix, prev_state_index, observation_sequence, emission_matrix, transition_matrix, initial_state_matrix, observation_sequence.length);

        // for(int i = 0; i<delta_matrix.size(); i++){
        //     System.out.printf("At timestep t = %d\n", i);
        //     System.out.println(delta_matrix.get(i));
        //     if(i==0) continue;
        //     System.out.println(prev_state_index.get(i-1));
        // }

        // System.out.println("^^^^^^^^^^^^^^^^OUTSIDE");

        double[] final_delta = delta_matrix.get(delta_matrix.size()-1).get_elements()[0];
        double highest_prob = 0;


        //get the final state that has the highest probability
        for(int state=0; state<transition_matrix.get_row(); state++){
            if(final_delta[state]>highest_prob) {
                highest_prob = final_delta[state];
                optimal_state_sequence[observation_sequence.length-1] = state;
                // System.out.println(optimal_state_sequence[observation_sequence.length-1]);
                // System.out.println(observation_sequence.length-1);
                // System.out.println("hello");
            } 
        }
        
        //backtrack to get the rest of the sequence
        for(int i=prev_state_index.size()-1; i>=0; i--){
            // System.out.println(i);
            optimal_state_sequence[i] = (int)prev_state_index.get(i).get_elements()[0][optimal_state_sequence[i+1]];
        }

        for(int i=0; i<optimal_state_sequence.length; i++){
            System.out.print(optimal_state_sequence[i]);
            System.out.print(" ");
        }
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
    public static void delta_pass(ArrayList<Matrix> delta_matrix, ArrayList<Matrix> prev_state_index, int[] observation_sequence, Matrix emission_matrix, Matrix transition_matrix, Matrix initial_state_matrix, int timestep){
        
        Matrix next_state = new Matrix();
        Matrix current_state = new Matrix();
        
        while(delta_matrix.size() != timestep){

            //calculate alpha(1)
            // alpha(1) = pi (written below) *P(x|0)
            
            if(delta_matrix.size() == 0) next_state = initial_state_matrix;//.multiply(transition_matrix);
            // alpha(t) = alpha(t-1)*B (written below) *P(x|0)
            else next_state = current_state.multiply_max(transition_matrix, prev_state_index);

            // multiplication of P(x|0)
            next_state = alpha_multiply_observation(next_state, emission_matrix, observation_sequence[delta_matrix.size()]);
            delta_matrix.add(next_state);

            // alpha is passed down and saved
            current_state = next_state;

        }

        // for(int i = 0; i<delta_matrix.size(); i++){
        //     System.out.println(delta_matrix.get(i));
        //     if(i==0) continue;
        //     System.out.println(prev_state_index.get(i-1));
        // }

        // System.out.println("^^^^^^^^^^^^^^^^INSIDE");
    }

    /*
     * Function used to multiply given state distribution probablility and emission distribution of states
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
}