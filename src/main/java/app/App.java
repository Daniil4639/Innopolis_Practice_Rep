package app;

public class App {

    public static void main(String[] args) {
        ListTester.start("resources/list_res/list_input.txt",
                "resources/list_res/list_output.txt");

        SetTester.start("resources/set_res/set_input.txt",
                "resources/set_res/set_output.txt");
    }
}
