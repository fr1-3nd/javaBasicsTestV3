package javaBasicsTest.basicsTest.src.task4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Publisher implements Runnable {

    private Socket publisher;
    private BufferedReader in;
    private PrintWriter out;
    private boolean done;


    @Override
    public void run() {
        try {
            publisher = new Socket("127.0.0.1", 9999);
            in = new BufferedReader(new InputStreamReader(System.in));
            out = new PrintWriter(publisher.getOutputStream(), true);
            System.out.println("Publisher start. Now, you can send messages.");
            while (!done) {
                String input;
                input = in.readLine();
                if ("/quit".equals(input)) {
                    out.println(input);
                    in.close();
                    shutdown();
                } else {
                    out.println(input);
                }
            }
        } catch (IOException e) {
            shutdown();
        }
    }

    public void shutdown() {
        done = true;
        try {
            in.close();
            out.close();
            if (!publisher.isClosed()) {
                publisher.close();
            }
        } catch (IOException e) {
            //ignore
        }
    }

    class InputHandler implements Runnable {
        @Override
        public void run() {
            try {
                BufferedReader inReader = new BufferedReader(new InputStreamReader(System.in));
                while (!done) {
                    String message = inReader.readLine();
                    if (message.equals("/quit")) {
                        out.println(message);
                        inReader.close();
                        shutdown();
                    } else {
                        out.println(message);
                    }
                }
            } catch (IOException e) {
                shutdown();
            }
        }

    }

    public static void main(String[] args) {
        Publisher publisher = new Publisher();
        publisher.run();
    }
}