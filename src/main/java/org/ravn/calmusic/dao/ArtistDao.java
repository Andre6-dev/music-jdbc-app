package org.ravn.calmusic.dao;

import org.ravn.calmusic.model.Artist;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ArtistDao {

    private final Logger logger = LoggerFactory.getLogger(ArtistDao.class);

    private final Connection connection;

    public ArtistDao(Connection connection) {
        this.connection = connection;
    }

    public void save(Artist artist) {
        // Save the artist to the database
        String sql = "INSERT INTO artists (artist_name) VALUES (?)";
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, artist.getName());
            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating artist failed, no rows affected.");
            }

            // Get the auto-generated artist ID
            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    artist.setArtistId(resultSet.getInt(1));
                } else {
                    throw new SQLException("Creating artist failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            logger.error("Error saving artist: ", e);
        }
    }

    public Artist findByName(String name) {
        // find the artist by name using regular expression
        String sql = "SELECT * FROM artists WHERE artist_name REGEXP ?";
        Artist artist = new Artist();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                // Process the result set
                artist.setArtistId(resultSet.getInt("artist_id"));
                artist.setName(resultSet.getString("artist_name"));
            }
        } catch (SQLException e) {
            logger.error("Error fetching artist: ", e);
        }

        return artist;
    }

    // Get artists using pagination
    public List<Artist> findAll(int limit, int offset) {
        String sql = "SELECT * FROM artists LIMIT ? OFFSET ?";
        List<Artist> artists = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, limit);
            statement.setInt(2, offset);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                // Process the result set
                Artist artist = new Artist();
                artist.setArtistId(resultSet.getInt("artist_id"));
                artist.setName(resultSet.getString("artist_name"));
                artists.add(artist);
            }
        } catch (SQLException e) {
            logger.error("Error fetching artists: ", e);
        }

        return artists;
    }



}
