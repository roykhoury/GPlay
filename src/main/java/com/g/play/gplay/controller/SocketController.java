//package com.g.play.gplay.controller;
//
//import com.g.play.gplay.socket.Client;
//import com.g.play.gplay.socket.Server;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RestController;
//
///**
// * Controller to define all of the end points required to host a server room
// */
//@RestController
//@RequestMapping("/socket")
//public class SocketController {
//
//    @Autowired
//    private Client client;
//
//    @Autowired
//    private Server server;
//
//    @RequestMapping(value = "/host", method = RequestMethod.POST)
//    public ResponseEntity<String> host() {
//        try {
//            Thread thread = new Thread(server);
//            thread.run();
//            return new ResponseEntity<>("Done Listening!", HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>("Could not start session!" + e.getMessage(), HttpStatus.BAD_REQUEST);
//        }
//    }
//
//    @RequestMapping(value = "/send", method = RequestMethod.POST)
//    public ResponseEntity<String> host(@RequestBody String payload) {
//        try {
//            return new ResponseEntity<>("Done Listening: " + client.sendEcho(payload), HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>("Could not initiate client!" + e.getMessage(), HttpStatus.BAD_REQUEST);
//        }
//    }
//}
