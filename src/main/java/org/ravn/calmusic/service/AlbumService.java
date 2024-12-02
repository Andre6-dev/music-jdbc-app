package org.ravn.calmusic.service;

import org.ravn.calmusic.config.TransactionManager;
import org.ravn.calmusic.dao.AlbumDao;
import org.ravn.calmusic.model.Album;

import java.util.List;

public class AlbumService {

    private final TransactionManager transactionManager;
    private final AlbumDao albumDao;

    public AlbumService(TransactionManager transactionManager, AlbumDao albumDao) {
        this.transactionManager = transactionManager;
        this.albumDao = albumDao;
    }

    public void saveAlbum(Album album) {
        albumDao.save(album);
    }

    public List<Album> getAllAlbums() {
        return albumDao.findAll();
    }

    public void commit() {
        try {
            transactionManager.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void rollback() {
        try {
            transactionManager.rollback();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
