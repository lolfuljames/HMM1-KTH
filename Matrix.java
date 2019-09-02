import java.util.*;
import java.io.*;
import java.lang.*;


public class Matrix{
    private static Scanner scan = new Scanner(System.in);
    private double[][] elements;
    private int row;
    private int col;

    public void set_row(int row){this.row = row;}
    public void set_col(int col){this.col = col;}
    public void set_elements(double[][] elements){this.elements = elements;}

    public int get_row(){return this.row;}
    public int get_col(){return this.col;}
    public double[][] get_elements(){return this.elements;}

    public Matrix(){};

    public Matrix(int row, int col, double[][] elements){
        this.row = row;
        this.col = col;
        this.elements = elements;
    }

    public String toString(){
        String elements_string = "";

        for(int j=0; j<this.row; j++){
            for(int k=0; k<this.col; k++){
                if(j!=0 || k!= 0) elements_string = elements_string +  " " + Double.toString(this.elements[j][k]);

                //initial string format [row] [col] [elements row by row]
                else elements_string = Integer.toString(row) + " " + Integer.toString(col) + " " + Double.toString(this.elements[j][k]);
            }
        }

        return elements_string;
    }

    public Matrix multiply(Matrix B){
        int result_row = this.row;
        int result_col = B.col;


        //Sanity check for matrix dimensions
        if(this.col != B.row) {
            System.out.printf("Error! Incorrect dimensions for matrix multiplication for matrix A dimension %dx%d and matrix B dimension %dx%d", this.row, this.col, B.row, B.col);
            return new Matrix();
        }

        //Copying over elements data
        double[][] A_elements = this.get_elements();
        double[][] result_elements = new double[result_row][result_col];
        double[][] B_elements = B.get_elements();

        //Actual multiplication
        for(int i=0; i<result_row; i++){
            for(int j=0; j<result_col; j++){
                for(int k=0; k<this.col; k++){

                    //Using natural log (ln) to perform multiplication
                    //Log(x*y) = Log(x) + Log(y)
                    double result = Math.log(A_elements[i][k])+Math.log(B_elements[k][j]); 
                    result = Math.exp(result);
                    result_elements[i][j] += result;
                }
            }
        }

        return new Matrix(result_row,result_col,result_elements);
    }

    public static Matrix create_matrix(){
        int row;
        int col;


        // System.out.println("inside");
        while(!scan.hasNextInt());
        // System.out.println("outside");
        // String input = scan.nextLine();
        // System.out.println(input);

        row = scan.nextInt();


        while(!scan.hasNextInt());
        col = scan.nextInt();

        // System.out.println("It's passed the integers!");
        double[][] elements = new double[row][col];

        for(int j=0; j<row; j++){
            for(int k=0; k<col; k++){
                while(!scan.hasNextDouble());
                elements[j][k] = scan.nextDouble();
            }
        }
        // scan.next();
        Matrix new_matrix = new Matrix(row,col,elements);
        return new_matrix;
    }
    
}