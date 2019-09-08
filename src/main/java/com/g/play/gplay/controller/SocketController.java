package com.g.play.gplay.controller;

import com.g.play.gplay.socket.Client;
import com.g.play.gplay.socket.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller to define all of the end points required to host a server room
 */
@RestController
@RequestMapping("/socket")
public class SocketController {

    private final Server server;

    @Autowired
    public SocketController(Server server) {
        this.server = server;
    }

    @RequestMapping(value = "/host", method = RequestMethod.POST)
    public ResponseEntity<String> host() {
        try {
            server.notifyObservers();
            return new ResponseEntity<>("Done Hosting!", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Could not start session! Ex: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/listen", method = RequestMethod.POST)
    public ResponseEntity<String> join(@RequestBody Integer port) {
        try {
            Client client = server.registerClient(port);
            client.listen();
            return new ResponseEntity<>("Done Joining: ", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Could not initiate client! Ex: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
