package org.ravn.calmusic.dao;

import org.ravn.calmusic.config.TransactionManager;
import org.ravn.calmusic.model.Album;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class AlbumDaoImpl implements AlbumDao {

    private final Logger logger = Logger.getLogger(AlbumDaoImpl.class.getName());

    private final Connection connection;

    public AlbumDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void save(Album album) {
        // Save the album to the database
        String sql = "INSERT INTO albums (album_name, artist_id) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, album.getTitle());
            statement.setInt(2, album.getArtistId());
            statement.executeUpdate();

            // Get the auto-generated album ID
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                album.setAlbumId(resultSet.getInt(1));
            }
        } catch (SQLException e) {
            logger.severe("Error saving album: " + e.getMessage());
        }
    }

    @Override
    public List<Album> findAll() {
        String sql = "SELECT * FROM albums LIMIT 10";
        List<Album> albums = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                // Process the result set
                Album album = new Album();
                album.setAlbumId(resultSet.getInt("album_id"));
                album.setTitle(resultSet.getString("album_name"));
                album.setArtistId(resultSet.getInt("artist_id"));
                albums.add(album);
            }
        } catch (SQLException e) {
            logger.severe("Error fetching albums: " + e.getMessage());
        }

        return albums;
    }
}
