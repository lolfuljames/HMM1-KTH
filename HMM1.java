import java.util.*;
import java.io.*;
public class HMM1{
    
    private static Scanner sc = new Scanner(System.in);
    public static void main(String[] args){
       
        Matrix transition_matrix = create_matrix();
        // System.out.println("transition done");
        // System.out.println(transition_matrix);
        Matrix emission_matrix = create_matrix();
        // System.out.println(emission_matrix);
        // System.out.println("emission done");

        Matrix initial_state_matrix = create_matrix();
        // System.out.println(initial_state_matrix);
        // System.out.println("initial done");

        int[] observation_sequence = create_observation_sequence(); 
        // System.out.println(observation_sequence);
        // System.out.println("observation done");

        ArrayList<Matrix> alpha_matrix = new ArrayList<Matrix>();

        alpha_pass(alpha_matrix, observation_sequence, emission_matrix, transition_matrix, initial_state_matrix, observation_sequence.length);
        // System.out.println("Starting multiplication...");
        // Matrix next_state = initial_state_matrix.multiply(transition_matrix);
        // Matrix results = next_state.multiply(emission_matrix);
        // System.out.println("Multiplication done.. Printing results now");
        // System.out.println(results);

    }

    public static Matrix create_matrix(){
        int row;
        int col;

        row = sc.nextInt();
        col = sc.nextInt();

        // System.out.println("It's passed the integers!");
        double[][] elements = new double[row][col];

        for(int j=0; j<row; j++){
            for(int k=0; k<col; k++){
                elements[j][k] = sc.nextDouble();
            }
        }
        // sc.next();
        Matrix new_matrix = new Matrix(row,col,elements);
        return new_matrix;
    }

    public static int[] create_observation_sequence(){
        int[] observation_sequence;
        int length = sc.nextInt();

        // System.out.println(length);

        observation_sequence = new int[length];
        for(int i=0; i<length; i++){
            int result = sc.nextInt();
            observation_sequence[i] = result;
            // System.out.println(result);
        }

        return observation_sequence;
    }

    /*
     * Function used to initialise forward algorithm
     */
    public static void alpha_pass(ArrayList<Matrix> alpha_matrix, int[] observation_sequence, Matrix emission_matrix, Matrix transition_matrix, Matrix initial_state_matrix, int timestep){
        if(alpha_matrix.size() == 0){
           Matrix state1 = initial_state_matrix.multiply(transition_matrix);
           System.out.println(state1);
           alpha_matrix.add(alpha_multiply_observation(state1, emission_matrix, observation_sequence[0]));
        } while(alpha_matrix.size() != timestep){
            break;
        }
    }

    /*
     * Function used to multiply given state distribution probablility and emission distribution of states
     */

    public static Matrix alpha_multiply_observation(Matrix state_probability, Matrix emission_matrix, int observation){

        double[][] alpha = new double[1][emission_matrix.get_col()];
        for(int i=0; i<emission_matrix.get_row(); i++){
            for(int j=0; j<emission_matrix.get_col(); j++){
                if(observation == j) alpha[0][i] = Math.exp(Math.log(state_probability.get_elements()[0][i]) + Math.log(emission_matrix.get_elements()[i][j]));
            }
        }
        return new Matrix(alpha.length,alpha[0].length,alpha);
    }
}