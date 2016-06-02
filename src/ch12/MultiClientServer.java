package ch12;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class Client implements Serializable {
    public Socket clientSocket;
    public Integer clientID;

    public Client(Socket soc, Integer id) {
        clientSocket = soc;
        clientID = id;
    }

    @Override
    public String toString() {
        return "Client{" +
                "clientSocket=" + clientSocket +
                ", clientID=" + clientID +
                '}';
    }
}

public class MultiClientServer implements Runnable {
    public static final String serverIP = "127.0.0.1";
    public static final int serverPort = 1680;
    public static final int maxClientNum = 100;

    static List<Client> clients = Collections.synchronizedList(new ArrayList<Client>());

    //    int clientNo;
//    Socket socket;
    Client client;

    public MultiClientServer(Client client) {
        this.client = client;
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
                                    for (Client cl : clients) {
                                        System.out.println(cl);
                                    }
                                }
                                continue;
                            }
                            synchronized (clients) {
                                for (Client cl : clients) {
                                    PrintWriter out = new PrintWriter(cl.clientSocket.getOutputStream());
                                    out.println(serverMessage);
                                    out.flush();
                                    System.out.printf("Sending message %s to Client %s ok.\n", serverMessage, cl);
                                }
                            }
                            if (serverMessage.equals("88")) {

                                //close client socket
                                synchronized (clients) {
                                    for (Client cl : clients) {
                                        cl.clientSocket.close();
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

            int currentID = 0;
            while (clients.size() < maxClientNum) {
                Socket clientSocket = serverSocket.accept();
                Client client = new Client(clientSocket, currentID++);
                clients.add(client);
                System.out.printf("\nClient %s no. %d is connected.\n", clientSocket, clients.size() - 1);
                System.out.print("\nSInfo:");
                Thread t = new Thread(new MultiClientServer(client));
                t.start();
            }
        } catch (Exception e) {
//            e.printStackTrace();
//            System.out.println("Error:" + e);
        }

    }

    public void run() {
        try {

            ObjectOutputStream oos = new ObjectOutputStream(client.clientSocket.getOutputStream());
            oos.writeObject(client.clientID);
            oos.flush();

            BufferedReader socketIn = new BufferedReader(
                    new InputStreamReader(client.clientSocket.getInputStream()));

            String s;
            while (!(s = socketIn.readLine()).equals("88")) {
                System.out.println("# Received message from Client No." + client.clientID + ": " + s);
                System.out.print("SInfo:");
            }
            System.out.println("The connection to Client No." +
                    client.clientID + " is closing... ... ");

            socketIn.close();
            client.clientSocket.close();

        } catch (Exception e) {
//            e.printStackTrace();
//            System.out.println("Error:" + e);
        } finally {
            clients.remove(client);
            System.out.printf("Client %d is closed successfully.\n", client.clientID);
            System.out.printf("SInfo:");
        }

    }
}
