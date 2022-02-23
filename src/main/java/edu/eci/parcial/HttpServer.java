package edu.eci.parcial;

import java.net.*;
import java.io.*;
import java.util.Locale;

public class HttpServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(getPort());
        } catch (IOException e) {
            System.err.println("Could not listen on port: 35000.");
            System.exit(1);
        }
        boolean running = true;
        while(running) {
            Socket clientSocket = null;
            try {
                System.out.println("Listo para recibir ...");
                clientSocket = serverSocket.accept();
            } catch (IOException e) {
                System.err.println("Accept failed.");
                System.exit(1);
            }

            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            clientSocket.getInputStream()));
            String inputLine, outputLine = "";
            boolean primeraLinea = true;
            String file = "";
            while ((inputLine = in.readLine()) != null) {
                System.out.println("Received: " + inputLine);
                if(primeraLinea){
                    file = inputLine.split(" ")[1];
                    System.out.println(file);
                    primeraLinea = false;
                }
                if (!in.ready()) {
                    break;
                }
            }
            if (file.startsWith("/clima/")){
                outputLine = "HTTP/1.1 200 OK\r\n"
                        + "Content-Type text/html\r\n"
                        + "\r\n"
                        + "<!DOCTYPE html>"
                        + "<html>"
                        + "<head>"
                        + "<meta charset=\"UTF-8\">"
                        + "<title>Title of the document</title>\n"
                        + "</head>"
                        + "<body>"
                        + "Prueba1"
                        + "</body>"
                        + "</html>";
            }else if(file.startsWith("/consulta/")){
                outputLine = "HTTP/1.1 200 OK\r\n"
                        + "Content-Type text/html\r\n"
                        + "\r\n"
                        + "<!DOCTYPE html>"
                        + "<html>"
                        + "<head>"
                        + "<meta charset=\"UTF-8\">"
                        + "<title>Title of the document</title>\n"
                        + "</head>"
                        + "<body>"
                        + "prueba2"
                        + "</body>"
                        + "</html>";}
            else{
                outputLine = "HTTP/1.1 200 OK\r\n"
                        + "Content-Type text/html\r\n"
                        + "\r\n"
                        + "<!DOCTYPE html>"
                        + "<html>"
                        + "<head>"
                        + "<meta charset=\"UTF-8\">"
                        + "<title>Title of the document</title>\n"
                        + "</head>"
                        + "<body>"
                        + "prueba2"
                        + "</body>"
                        + "</html>";}


            out.println(outputLine);


            out.close();
            in.close();
            clientSocket.close();
        }
        serverSocket.close();
    }

    static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 4567; //returns default port if heroku-port isn't set

    }
}
