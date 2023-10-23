package javaBasicsTest.basicsTest.src.task4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Consumer implements Runnable {

    private Socket consumer;
    private BufferedReader in;

    private boolean done;


    @Override
    public void run() {
        try {
            consumer = new Socket("127.0.0.1", 9999);
            in = new BufferedReader(new InputStreamReader(consumer.getInputStream()));
            System.out.println("Consumer start. Now you can receive messages.");
            String message;
            while ((message = in.readLine()) != null) {
                System.out.println("Message received: " + message);
            }
        } catch (IOException e) {
            shutdown();
        }
    }

    public void shutdown() {
        done = true;
        try {
            in.close();
            if (!consumer.isClosed()) {
                consumer.close();
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
                        inReader.close();
                        shutdown();
                    } else {
                    }
                }
            } catch (IOException e) {
                shutdown();
            }
        }

    }

    public static void main(String[] args) {
        Consumer consumer = new Consumer();
        consumer.run();
    }
}