package com.g.play.gplay.service;

import com.g.play.gplay.config.FsTemplate;
import com.g.play.gplay.model.Song;
import com.mongodb.client.gridfs.GridFSFindIterable;
import com.mongodb.client.gridfs.model.GridFSFile;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

/**
 * This is the service that provides all needed user functionality
 * including parsing, filtering and sorting of the data
 */
@Service
public class MusicService {

    @Autowired
    private FsTemplate template;

    /**
     * Parse the information received from the controller and save it into the database
     * using the gridFs instance
     *
     * @param file the multipart file information about the song
     * @throws Exception if anything goes wrong with the saving process
     */
    public void saveSong(MultipartFile file) throws Exception {
        if (file.getOriginalFilename() != null)
            template.gridFsTemplate().store(file.getInputStream(), filterName(file.getOriginalFilename().toLowerCase()));
        else
            throw new InvalidParameterException("Invalid File Name");
    }

    /**
     * Remove all symboles and invalid characters from file name
     *
     * @param fileName the file name
     * @return the filtered file name
     */
    private String filterName(String fileName) {
        StringBuilder finalFileName = new StringBuilder();
        String[] subStrings = fileName.split("[^A-Za-z]");
        for (int i = 0; i < subStrings.length; i++) {
            String suffix = i == subStrings.length - 1 ? ".mp3" : " ";
            finalFileName.append(subStrings[i]).append(suffix);
        }
        return finalFileName.toString();
    }

    /**
     * Retrieve all the songs from the mongoDB
     *
     * @param keyword the keyword to search by
     * @return the List of song objects returned by the search
     * @throws Exception if anything wrong happens with the search
     */
    public List<Song> getSongs(String keyword) throws Exception {
        List<Song> songs = new ArrayList<>();
        GridFSFindIterable files = template.gridFsTemplate()
                .find(new Query().addCriteria(Criteria.where("filename").regex(keyword.trim().toLowerCase())));

        for (GridFSFile file : files) {
            Song song = new Song()
                    .withObjectId(file.getObjectId().toHexString())
                    .withName(file.getFilename());

            songs.add(song);
        }
        return songs;
    }

    /**
     * Retrieve the file related to a song from the mongoDB instance
     *
     * @param id the file ID of the song
     * @return the song object
     * @throws Exception if anything wrong happens during the transaction
     */
    public GridFSFile getSong(ObjectId id) throws Exception {
        return template.gridFsTemplate().findOne(new Query().addCriteria(Criteria.where("_id").is(id)));
    }

    /**
     * Retrieve and play the song from the inputstream output
     *
     * @param song the GridFSFile object which is related to several chunks in the mongoDB instance
     * @return the Resource containing the input stream of the song
     * @throws Exception if anything goes wrong during the transaction
     */
    public GridFsResource playSong(GridFSFile song) throws Exception {
        return template.gridFsTemplate().getResource(song);
    }
}
