//package com.g.play.gplay.socket;
//
//import org.springframework.stereotype.Service;
//
//import java.net.DatagramPacket;
//import java.net.DatagramSocket;
//import java.net.InetAddress;
//import java.net.SocketException;
//
//@Service
//public class Server extends Thread {
//
//    private DatagramSocket socket;
//    private boolean running;
//    private byte[] buf = new byte[256];
//
//    public Server() throws SocketException {
//        socket = new DatagramSocket(4445);
//    }
//
//    public void run() {
//        running = true;
//
//        while (running) {
//            DatagramPacket packet = new DatagramPacket(buf, buf.length);
//
//            try {
//                socket.receive(packet);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//
//            InetAddress address = packet.getAddress();
//            int port = packet.getPort();
//            packet = new DatagramPacket(buf, buf.length, address, port);
//            String received = new String(packet.getData(), 0, packet.getLength());
//
//            if (received.equals("end")) {
//                running = false;
//                continue;
//            }
//
//            try {
//                socket.send(packet);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//        }
//        socket.close();
//    }
//}
