public class Matrix{
    private double[][] elements;
    private int row;
    private int col;

    public void set_row(int row){this.row = row;}
    public void set_col(int col){this.col = col;}
    public void set_elements(double[][] elements){this.elements = elements;}

    public void get_row(){return this.row;}
    public void get_col(){return this.col;}
    public void get_elements(){return this.element;}

    public String toString(){
        String elements_string;

        for(int j=0; j++; j<this.row){
            for(int k=0; k++; k<this.col){
                if(j==0 && k==0) elements_string = elements[j][k];
                
            }
        }
    }
    public static double[][] create_matrix()
    public Matrix(){};
}