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

            String json = getClimaCiudadAPI("London");
            if (file.startsWith("/clima/")){
                outputLine = "HTTP/1.1 200 OK\r\n"
                        + "Content-Type text/html\r\n"
                        + "\r\n"
                        + "<!DOCTYPE html>"
                        + "<html>"
                        + "<head>"
                        + "<meta charset=\"UTF-8\">"
                        + "<title>Title of the document</title>\n"
                        + "<script src='index.js'> </script>"
                        + "</head>"
                        + "<body>"
                        + "Prueba1"
                        + "<input type=\"text\" id = \"ingresado\"placeholder=\"Ingrece la cantidad'\">"
                        + "<button type='button' onclick='index.connection( $('#name').val(),$('#ingresado').val())'>Calcular!</button>"

                        + "<br><br>"
                        + "</body>"
                        + "</html>";
            }else if(file.startsWith("/consulta")){
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
                        +"<p>" +json+"</p>"
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
    public static String getClimaCiudadAPI(String lugar)  throws IOException {

        String USER_AGENT = "Mozilla/5.0";
        String GET_URL = "http://api.openweathermap.org/data/2.5/weather?q="+lugar+"&appid=6e72a28cc3ae5b0869e48a94997f0fd3";
        URL url = new URL(GET_URL);
        HttpURLConnection solicitud = (HttpURLConnection) url.openConnection();
        solicitud.setRequestMethod("GET");
        solicitud.setRequestProperty("User-Agent", USER_AGENT);
        int responseCode = solicitud.getResponseCode();
        System.out.println("GET Response Code :: " + responseCode);

        if (responseCode == HttpURLConnection.HTTP_OK) { // success
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    solicitud.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            System.out.println(response.toString());
            return response.toString();
        } else {
            return null;
        }
    }

    static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 4567; //returns default port if heroku-port isn't set

    }
}
