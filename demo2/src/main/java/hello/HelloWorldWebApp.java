package hello;

import static spark.Spark.*;

public class HelloWorldWebApp {
    public static void main(String[] args) {
        get("/hello", (req, res) -> "Hello World");
    }
}