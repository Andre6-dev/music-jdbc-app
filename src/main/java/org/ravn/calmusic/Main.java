package org.ravn.calmusic;

import org.ravn.calmusic.config.TransactionManager;
import org.ravn.calmusic.dao.AlbumDaoImpl;
import org.ravn.calmusic.dao.ArtistDao;
import org.ravn.calmusic.model.Album;
import org.ravn.calmusic.model.Artist;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    private static final Scanner scanner = new Scanner(System.in);
    private static TransactionManager transactionManager;
    private static AlbumDaoImpl albumDao;
    private static ArtistDao artistDao;

    public static void main(String[] args) {
        logger.info("Application started");

        try {
            transactionManager = new TransactionManager();
            albumDao = new AlbumDaoImpl(transactionManager.getConnection());
            artistDao = new ArtistDao(transactionManager.getConnection());
            showMenu();
        } catch (Exception e) {
            logger.error("An error occurred: ", e);
        }

        logger.info("Application finished");
    }

    private static void showMenu() {
        boolean exit = false;
        while (!exit) {
            printMenuHeader();
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    addAlbum();
                    break;
                case 2:
                    addArtist();
                    break;
                case 3:
                    // addSong();
                    break;
                case 4:
                    listAlbums();
                    break;
                case 5:
                    listArtists();
                    break;
                case 6:
                    listArtistsByName();
                    break;
                case 7:
                    exit = true;
                    System.out.println("\nThank you for using the application. Goodbye!");
                    break;
                default:
                    System.out.println("\nInvalid option, please try again.");
            }
        }
    }

    private static void listArtistsByName() {
        System.out.print("Enter the artist name: ");
        String name = scanner.nextLine();

        Artist artist = artistDao.findByName(name);
        if (artist != null) {
            System.out.println("\n=== Artist Details ===");
            System.out.println("ID: " + artist.getArtistId() + ", Name: " + artist.getName());
            System.out.println("=======================\n");
        } else {
            System.out.println("Artist not found!");
        }
    }

    private static void addArtist() {
        try {
            System.out.print("Enter the artist name: ");
            String name = scanner.nextLine();

            Artist artist = new Artist();
            artist.setName(name);

            artistDao.save(artist);
            transactionManager.commit();
            System.out.println("Artist saved successfully!");
        } catch (SQLException e) {
            logger.error("An error occurred: ", e);
            try {
                transactionManager.rollback();
            } catch (SQLException ex) {
                logger.error("An error occurred: ", ex);
            }
        }
    }

    private static void listArtists() {
        System.out.print("Enter the page number: ");
        int page = scanner.nextInt();
        System.out.print("Enter the page size: ");
        int size = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        List<Artist> artists = artistDao.findAll(size, (page - 1) * size);
        System.out.println("\n=== List of Artists ===");
        for (Artist artist : artists) {
            System.out.println("ID: " + artist.getArtistId() + ", Name: " + artist.getName());
        }
        System.out.println("=========================\n");
    }

    private static void listAlbums() {
        List<Album> albums = albumDao.findAll();
        System.out.println("\n=== List of Albums ===");
        for (Album album : albums) {
            System.out.println("ID: " + album.getAlbumId() + ", Title: " + album.getTitle() + ", Artist ID: " + album.getArtistId());
        }
        System.out.println("=========================\n");
    }

    private static void addAlbum() {
        try {
            System.out.print("Enter the album title: ");
            String title = scanner.nextLine();
            System.out.print("Enter the artist ID: ");
            int artistId = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            Album album = new Album();
            album.setTitle(title);
            album.setArtistId(artistId);

            albumDao.save(album);
            transactionManager.commit();
            System.out.println("Album saved successfully!");
        } catch (SQLException e) {
            logger.error("An error occurred: ", e);
            try {
                transactionManager.rollback();
            } catch (SQLException ex) {
                logger.error("An error occurred: ", ex);
            }
        }
    }

    private static void printMenuHeader() {
        System.out.println("\n===============================================");
        System.out.println("             Music Library Menu");
        System.out.println("===============================================");
        System.out.println("1. Add Album");
        System.out.println("2. Add Artist");
        System.out.println("3. Add Song");
        System.out.println("4. List Albums");
        System.out.println("5. List Artists by Pagination");
        System.out.println("6. List Artists by Name");
        System.out.println("7. Exit");
        System.out.println("===============================================");
    }

}