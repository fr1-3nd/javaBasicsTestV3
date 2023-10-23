package javaBasicsTest.basicsTest.src.task4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server implements Runnable {

    private ArrayList<ConnectionHandler> connections;
    private ServerSocket server;
    private boolean done;
    private ExecutorService pool;
    private Queue<String> messageQueue;


    public Server() {
        connections = new ArrayList<>();
        done = false;
        messageQueue = new ConcurrentLinkedQueue<>();
    }


    @Override
    public void run() {
        try {
            server = new ServerSocket(9999);
            pool = Executors.newCachedThreadPool();
            System.out.println("Starting server!");
            while (!done) {
                Socket client = server.accept();
                System.out.println("Connected from: " + client.getInetAddress().getHostAddress());
                ConnectionHandler handler = new ConnectionHandler(client);
                connections.add(handler);
                pool.execute(handler);
            }
        } catch (IOException e) {
            shutdown();
        }
    }

    private void overdueMessages(ConnectionHandler client) {
        while (!messageQueue.isEmpty()) {
            String message = messageQueue.poll();
            client.sendMessage(message);
        }
    }

    public void shutdown() {
        try {
            done = true;
            pool.shutdown();
            if (!server.isClosed()) {
                server.close();
            }
            for (ConnectionHandler ch : connections) {
                ch.shutdown();
            }
        } catch (IOException e) {
            //ignore
        }
    }

    public void broadcast(String message, ConnectionHandler sender) {
        messageQueue.offer(message);
        for (ConnectionHandler client : connections) {
            if (client != sender && client != null) {
                client.sendMessage(message);
            }
        }
    }

    class ConnectionHandler implements Runnable {
        private Socket client;
        private BufferedReader in;
        private PrintWriter out;

        public ConnectionHandler(Socket client) {
            this.client = client;
        }


        @Override
        public void run() {
            try {
                out = new PrintWriter(client.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                overdueMessages(this);
                String message;
                while ((message = in.readLine()) != null) {
                    if (message.startsWith("/quit")) {
                        broadcast("Publisher disconnected", this);
                        shutdown();
                    } else {
                        broadcast(message, this);
                    }
                }
            } catch (IOException e) {
                shutdown();
            }
        }

        public void shutdown() {
            try {
                in.close();
                out.close();
                if (!client.isClosed()) {
                    client.close();
                }
            } catch (IOException e) {
                // ignore
            }
        }

        public void sendMessage(String message) {
            out.println(message);
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.run();

    }
}