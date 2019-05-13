package com.g.play.gplay.controller;

import com.g.play.gplay.model.Song;
import com.g.play.gplay.service.MusicService;
import com.mongodb.client.gridfs.model.GridFSFile;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * List of endpoints related to music
 */
@RestController
@RequestMapping("/music")
public class MusicController {

    @Autowired
    private MusicService musicService;

    /**
     * Endpoint to save a new song into the database
     * This is not the end point to add a song to a specific user's playlist,
     * it is to be able to register a song to the general application database for users to search
     *
     * @param file The audio file of the song
     * @return The response entity with appropriate message and Http status
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseEntity<String> addMusic(@RequestParam("file") MultipartFile file) {
        try {
            musicService.saveSong(file);
            return new ResponseEntity<>("Successfully added audio file", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error while adding audio file, exception: " + e.toString(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Endpoint to search for a song in the general application
     *
     * @param keyword the keyword to search by
     * @return the list of songs that resulted from the search
     */
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public ResponseEntity<List<Song>> getMusic(@RequestParam @NotNull String keyword) {
        try {
            List<Song> songs = musicService.getSongs(keyword);
            return ResponseEntity.ok()
                    .body(songs);
        } catch (Exception e) {
            return new ResponseEntity(new ArrayList(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Endpoint to play a song once clicked on.
     *
     * @param id the id of the song to play, this ID is actually linked to an fs.file object ID
     * @return the input stream of the song
     */
    @RequestMapping(value = "/play", method = RequestMethod.GET, produces = {MediaType.APPLICATION_OCTET_STREAM_VALUE})
    public ResponseEntity<InputStreamResource> playSong(@RequestParam @NotNull ObjectId id) {
        try {
            GridFSFile song = musicService.getSong(id);

            return ResponseEntity.ok()
                    .contentLength(song.getLength())
                    .body(musicService.playSong(song));
        } catch (Exception e) {
            return new ResponseEntity(ResponseEntity.badRequest(), HttpStatus.BAD_REQUEST);
        }
    }
}
