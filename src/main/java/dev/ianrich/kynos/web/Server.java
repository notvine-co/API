package dev.ianrich.kynos.web;

import dev.ianrich.kynos.controller.DefaultController;
import dev.ianrich.kynos.web.construct.Request;
import dev.ianrich.kynos.web.construct.Response;
import dev.ianrich.kynos.web.construct.Route;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private int port = 8080;

    private boolean isStop = false;

    private Thread serverThread;

    private ServerSocket socket;

    private Map<String, Route> routes = new HashMap<>();

    private Server() {

    }

    public static Server create() {
        return new Server();
    }

    public void start() {
        this.start(this.port);
    }

    public synchronized void start(int port) {
        long start = System.currentTimeMillis();

        this.port = port;

        try {
            if (socket == null) {
                socket = new ServerSocket(this.port);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (serverThread == null) {
            serverThread = new Thread(() -> init());
            serverThread.start();
        }

        System.out.println("Server started! (" + (System.currentTimeMillis() - start) + "ms)");
    }

    public void stop() {
        try {
            this.isStop = true;
            this.socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void init() {
        try {
            ExecutorService fixedThreadPool = Executors.newFixedThreadPool(2);
            while (!isStop) {
                Socket clientSocket = socket.accept();
                HttpRequestHandler request = new HttpRequestHandler(clientSocket);
                fixedThreadPool.execute(request);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Server get(String path, Route route, Boolean identifier) {
        routes.put("GET:" + path + (identifier ? "/{identifier}" : ""), route);
        System.out.println("Created GET route for \"" + path + "\"");
        return this;
    }

    public Server post(String path, Route route) {
        routes.put("POST:" + path, route);
        System.out.println("Created POST route for \"" + path + "\"");
        return this;
    }

    public Server put(String path, Route route) {
        routes.put("PUT:" + path, route);
        System.out.println("Created PUT route for \"" + path + "\"");
        return this;
    }

    public Server delete(String path, Route route) {
        routes.put("DELETE:" + path, route);
        System.out.println("Created DELETE route for \"" + path + "\"");
        return this;
    }

    public Server patch(String path, Route route) {
        routes.put("PATCH:" + path, route);
        System.out.println("Created PATCH route for \"" + path + "\"");
        return this;
    }

    class HttpRequestHandler implements Runnable {

        private Socket socket;

        public HttpRequestHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                Request request = new Request(socket);
                Response response = new Response(socket);

                String routeKey = String.format("%s:%s", request.getMethod(), request.getUri().getPath());
                Route route = routes.getOrDefault(routeKey, DefaultController.getPage());
                route.handle(request, response);
                socket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
