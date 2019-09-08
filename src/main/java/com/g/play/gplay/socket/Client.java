package com.g.play.gplay.socket;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * Java program to illustrate Client side
 * Implementation using DatagramSocket
 */

@Service
public class Client {

    // Port of the client to send and receive data
    protected int port;

    /**
     * Builder pattern for the client to set a port
     *
     * @param port the port to listen to
     * @return the current client object
     */
    public Client withPort(int port) {
        this.port = port;
        return this;
    }

    public void listen() throws IOException {
        // Step 1:Create the socket object for
        // carrying the data.
        DatagramSocket ds = new DatagramSocket(port);

        byte receive[] = new byte[65535];

        while (true) {
            // Step 2 : Create the datagramPacket for sending
            // the data.
            DatagramPacket dpReceive = new DatagramPacket(receive, receive.length);

            // Step 3 : invoke the receive call to actually receive the data.
            ds.receive(dpReceive);

            System.out.println(data(receive));

            // Clear the buffer after every message.
            receive = new byte[65535];
        }
    }

    // A utility method to convert the byte array
    // data into a string representation.
    public static StringBuilder data(byte[] a) {
        if (a == null)
            return null;
        StringBuilder ret = new StringBuilder();
        int i = 0;
        while (a[i] != 0) {
            ret.append((char) a[i]);
            i++;
        }
        return ret;
    }
}
