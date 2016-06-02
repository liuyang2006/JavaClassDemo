package ch12;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class MyServer {
    public static void main(String args[]) {
        try {
            ServerSocket server = new ServerSocket(1680);
            Socket socket = server.accept();

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream());

            BufferedReader sin = new BufferedReader(
                    new InputStreamReader(System.in));

            String s;
            while (!(s = in.readLine()).equals("bye")) {
                System.out.println("# Received from Client:  " + s);
                out.println(sin.readLine());
                out.flush();
            }
            System.out.println("The connection is closing... ... ");

            in.close();
            out.close();
            socket.close();
            server.close();

        } catch (Exception e) {
            System.out.println("Error:" + e);
        }
    }
}
