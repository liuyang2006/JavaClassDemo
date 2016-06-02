package ch12;

import java.io.*;
import java.net.Socket;

public class MyClient {
    public static final String serverIP = MultiClientServer.serverIP;
    public static final int serverPort = MultiClientServer.serverPort;

    static Integer clientID;

    public static void main(String args[]) {
        try {
            final Socket socket = new Socket(serverIP, serverPort);

            System.out.printf("Client connected to server %s:%d ok.\n", serverIP, serverPort);
            Thread handleUserInput = new Thread(new Runnable() {
                @Override
                public void run() {
                    PrintWriter out = null;
                    try {
                        out = new PrintWriter(socket.getOutputStream());
                        BufferedReader sin = new BufferedReader(
                                new InputStreamReader(System.in));

                        String s = "";
                        do {
                            s = sin.readLine();
                            out.println(s);
                            out.flush();
                            System.out.printf("Send %s to server %s ok.\n", s, socket);
                            System.out.printf("Info[%d]:", clientID);
                        } while (!s.equals("88"));
                        System.out.println("The connection is closed.");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            });

            handleUserInput.start();


            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            Object obj = ois.readObject();

            if (obj instanceof Integer) {
                clientID = (Integer) obj;
                System.out.printf("clientID = %s\n", clientID);
                System.out.printf("Info[%d]:", clientID);
            } else {
                throw new IllegalStateException("Receiving non-client obj.");
            }

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            while (true) {
                String serverInfo = in.readLine();
                if (serverInfo == null)
                    break;
                if (serverInfo.equals("88")) {
                    System.out.println("Receiving server is end.");
                    break;
                }
                System.out.println("\n@ Server response:  " + serverInfo);
                System.out.printf("Info[%d]:", clientID);
            }
            in.close();
            socket.close();

            handleUserInput.stop();
//            System.out.println("Interrupt");

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error" + e);
        } finally {
            System.out.println("Client is over.");
            System.exit(0);
        }

    }
}

