package ch12;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MultiClientServer implements Runnable {
    public static final String serverIP = "127.0.0.1";
    public static final int serverPort = 1680;
    public static final int maxClientNum = 100;

    static List<Socket> clients = Collections.synchronizedList(new ArrayList<Socket>());

    int clientNo;
    Socket socket;

    public MultiClientServer(Socket ss, int no) {
        socket = ss;
        clientNo = no;
    }

    public static void main(String args[]) {
        try {
            ServerSocket serverSocket = new ServerSocket(serverPort);
            System.out.printf("Server ready at %s:%d.\n", serverIP, serverPort);

            Thread handleServerUserInput = new Thread(new Runnable() {
                @Override
                public void run() {
                    BufferedReader sin = new BufferedReader(
                            new InputStreamReader(System.in));

                    String serverMessage = null;
                    try {
                        while (true) {
                            System.out.print("SInfo:");
                            serverMessage = sin.readLine();
                            if (serverMessage.equals("l")) {
                                System.out.printf("There are %d clients:\n", clients.size());
                                synchronized (clients) {
                                    for (Socket cl : clients) {
                                        System.out.println(cl);
                                    }
                                }
                                continue;
                            }
                            synchronized (clients) {
                                for (Socket cl : clients) {
                                    PrintWriter out = new PrintWriter(cl.getOutputStream());
                                    out.println(serverMessage);
                                    out.flush();
                                    System.out.printf("Sending message %s to Client %s ok.\n", serverMessage, cl);
                                }
                            }
                            if (serverMessage.equals("88")) {

                                //close client socket
                                synchronized (clients) {
                                    for (Socket cl : clients) {
                                        cl.close();
                                    }
                                }
                                break;
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            serverSocket.close();
                            System.out.println("Server is closed ok.");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            handleServerUserInput.start();

            while (clients.size() < maxClientNum) {
                Socket clientSocket = serverSocket.accept();
                clients.add(clientSocket);
                System.out.printf("\nClient %s no. %d is connected.\n", clientSocket, clients.size() - 1);
                System.out.print("\nSInfo:");
                Thread t = new Thread(new MultiClientServer(clientSocket, clients.size() - 1));
                t.start();
            }
        } catch (Exception e) {
//            e.printStackTrace();
//            System.out.println("Error:" + e);
        }

    }

    public void run() {
        try {
            BufferedReader socketIn = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));

            String s;
            while (!(s = socketIn.readLine()).equals("88")) {
                System.out.println("# Received message from Client No." + clientNo + ": " + s);
                System.out.print("SInfo:");
            }
            System.out.println("The connection to Client No." +
                    clientNo + " is closing... ... ");

            socketIn.close();
            socket.close();

        } catch (Exception e) {
//            e.printStackTrace();
//            System.out.println("Error:" + e);
        } finally {
            clients.remove(socket);
            System.out.printf("Client %d is closed successfully.\n", clientNo);
            System.out.printf("SInfo:");
        }

    }
}
