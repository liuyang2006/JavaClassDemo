package ch12;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class MyServer {

    public static final int serverPort = MultiClientServer.serverPort;

    public static void main(String args[]) {
        try {
            ServerSocket server = new ServerSocket(serverPort);
            System.out.printf("Server is ready at port %d\n", serverPort);
            Socket socket = server.accept();

            BufferedReader socketIn = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));

            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(new Integer(0));
            oos.flush();

            PrintWriter out = new PrintWriter(socket.getOutputStream());

            BufferedReader standartInput = new BufferedReader(
                    new InputStreamReader(System.in));

            String s;
            while (!(s = socketIn.readLine()).equals("88")) {
                System.out.println("# Received from Client:  " + s);
                String str = standartInput.readLine();
                out.println(str);
                out.flush();
                if (str.equals("88"))
                    break;
                else
                    System.out.printf("Send %s to client.\n", str);
            }

            socketIn.close();
            out.close();
            socket.close();
            server.close();

        } catch (Exception e) {
//            System.out.println("Error:" + e);
//            e.printStackTrace();
        } finally {
            System.out.println("The server connection is closed.");
        }
    }
}
