package org.ravn.calmusic.dao;

import org.ravn.calmusic.model.Album;

import java.util.List;

public interface AlbumDao {

    void save(Album album);

    List<Album> findAll();
}
