public class Main{
    public static void main(String[] args){
        Matrix transition_matrix = Matrix.create_matrix();
        // System.out.println("transition done");
        // System.out.println(transition_matrix);

        Matrix emission_matrix = Matrix.create_matrix();
        // System.out.println(emission_matrix);
        // System.out.println("emission done");

        Matrix initial_state_matrix = Matrix.create_matrix();
        // System.out.println(initial_state_matrix);
        // System.out.println("initial done");

        // System.out.println("Starting multiplication...");
        Matrix next_state = initial_state_matrix.multiply(transition_matrix);
        Matrix results = next_state.multiply(emission_matrix);
        // System.out.println("Multiplication done.. Printing results now");
        System.out.println(results);

    }
}