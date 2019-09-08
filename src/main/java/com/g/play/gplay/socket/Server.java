package com.g.play.gplay.socket;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;

/**
 * Server using the observer design pattern used to
 * register and notify clients based on certain events
 */

@Service
public class Server {

    // List of streamers to send the bytes to
    private List<Client> clients;
    private DatagramSocket ds;
    private InetAddress ip;

    /**
     * Default Constructor
     *
     * @throws SocketException      Exceptions creating socket related exceptions
     * @throws UnknownHostException Localhost related exceptions
     */
    public Server() throws SocketException, UnknownHostException {
        ds = new DatagramSocket();
        ip = InetAddress.getLocalHost();
        clients = new ArrayList<>();
    }

    /**
     * Add a client to the list of observers to notify
     *
     * @param port the port to assign to the client
     * @return the newly created client
     */
    public Client registerClient(int port) {
        Client client = new Client().withPort(port);
        clients.add(client);
        return client;
    }

    /**
     * Remove a client to be opted out from the notify list
     *
     * @param client the client to remove
     * @return the newly removed client
     */
    public Client removeClient(Client client) {
        clients.remove(client);
        return client;
    }

    /**
     * Notify the observers and execute their update functions
     */
    public void notifyObservers() throws IOException {
        // Step 1 : Create a socket to send data
        DatagramPacket dpSend;
        byte[] send;

        while (true) {
            try {
                for (Client client : clients) {

                    // convert the String input into the byte array.
                    send = ("Some data" + client.port).getBytes();

                    // Step 2 : Create the datagramPacket for sending the data.
                    dpSend = new DatagramPacket(send, send.length, ip, client.port);

                    // Step 3 : send the data in byte buffer.
                    ds.send(dpSend);
                }
            } catch (ConcurrentModificationException ignored) {
            }
        }
    }
}