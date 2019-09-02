import java.util.*;
import java.io.*;
public class HMM0{
    
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

        // System.out.println("Starting multiplication...");
        Matrix next_state = initial_state_matrix.multiply(transition_matrix);
        Matrix results = next_state.multiply(emission_matrix);
        // System.out.println("Multiplication done.. Printing results now");
        System.out.println(results);

    }
    
    public static Matrix create_matrix(){
        int row;
        int col;


        // System.out.println("inside");
        while(!sc.hasNextInt());
        // System.out.println("outside");
        // String input = sc.nextLine();
        // System.out.println(input);

        row = sc.nextInt();


        while(!sc.hasNextInt());
        col = sc.nextInt();

        // System.out.println("It's passed the integers!");
        double[][] elements = new double[row][col];

        for(int j=0; j<row; j++){
            for(int k=0; k<col; k++){
                while(!sc.hasNextDouble());
                elements[j][k] = sc.nextDouble();
            }
        }
        // sc.next();
        Matrix new_matrix = new Matrix(row,col,elements);
        return new_matrix;
    }
}