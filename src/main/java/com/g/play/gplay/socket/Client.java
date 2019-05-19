//package com.g.play.gplay.socket;
//
//import org.springframework.stereotype.Service;
//
//import java.io.IOException;
//import java.net.DatagramPacket;
//import java.net.DatagramSocket;
//import java.net.InetAddress;
//
//@Service
//public class Client {
//
//    private DatagramSocket socket;
//    private InetAddress address;
//
//    private byte[] buf;
//
//    public Client() throws IOException {
//        socket = new DatagramSocket();
//        address = InetAddress.getByName("localhost");
//    }
//
//    public String sendEcho(String msg) {
//        buf = msg.getBytes();
//        DatagramPacket packet = new DatagramPacket(buf, buf.length, address, 4445);
//        try {
//            socket.send(packet);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        packet = new DatagramPacket(buf, buf.length);
//
//        try {
//            socket.receive(packet);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return new String(packet.getData(), 0, packet.getLength());
//    }
//}
