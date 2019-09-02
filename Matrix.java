import java.util.*;
import java.io.*;


public class Matrix{
    
    private double[][] elements;
    private int row;
    private int col;

    public void set_row(int row){this.row = row;}
    public void set_col(int col){this.col = col;}
    public void set_elements(double[][] elements){this.elements = elements;}

    public int get_row(){return this.row;}
    public int get_col(){return this.col;}
    public double[][] get_elements(){return this.element;}

    public Matrix(){};

    public Matrix(int row, int col, double[][] elements){
        this.row = row;
        this.col = col;
        this.elements = elements;
    }

    public String toString(){
        String elements_string;

        for(int j=0; j<this.row; j++){
            for(int k=0; k<this.col; k++){
                if(j!=0 || k!= 0) elements_string = elements_string +  " " + Double.toString(this.elements[j][k]);

                //initial string format [row] [col] [elements row by row]
                else elements_string = Int.toString(row) + " " + Int.toString(col) + " " + Double.toString(this.elements[j][k]);
            }
        }

        return elements_string;
    }

    public static Matrix create_matrix(){
        int row;
        int col;
        Scanner scan = new Scanner(System.in);

        row = scan.nextInt();
        col = scan.nextInt();

        double[][] elements = new double[row][col];

        for(int j=0; jrow; j++){
            for(int k=0; k<col; k++){
                elements[j][k] = scan.nextDouble();
            }
        }
        scan.next();
        
        return Matrix(row,col,elements);
    }
    
}