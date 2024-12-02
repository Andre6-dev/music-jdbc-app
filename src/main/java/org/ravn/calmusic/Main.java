package org.ravn.calmusic;

import org.ravn.calmusic.config.TransactionManager;
import org.ravn.calmusic.dao.AlbumDao;
import org.ravn.calmusic.dao.AlbumDaoImpl;
import org.ravn.calmusic.model.Album;
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

    public static void main(String[] args) {
        logger.info("Application started");

        try {
            transactionManager = new TransactionManager();
            albumDao = new AlbumDaoImpl(transactionManager.getConnection());

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
                case 4:
                    listAlbums();
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

    private static void listAlbums() {
        List<Album> albums = albumDao.findAll();
        System.out.println("\n=== List of Albums ===");
        for (Album album : albums) {
            System.out.println("ID: " + album.getAlbumId() + ", Title: " + album.getTitle() + ", Artist ID: " + album.getArtistId());
        }
        System.out.println("=========================\n");
    }

    private static void addAlbum() {
        System.out.print("Enter the album title: ");
        String title = scanner.nextLine();
        System.out.print("Enter the artist ID: ");
        int artistId = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        Album album = new Album();
        album.setTitle(title);
        album.setArtistId(artistId);

        albumDao.save(album);
        System.out.println("Album saved successfully!");
    }

    private static void printMenuHeader() {
        System.out.println("\n===============================================");
        System.out.println("             Music Library Menu");
        System.out.println("===============================================");
        System.out.println("1. Add Album");
        System.out.println("2. Add Artist");
        System.out.println("3. Add Song");
        System.out.println("4. List Albums");
        System.out.println("5. List Artists");
        System.out.println("6. List Songs");
        System.out.println("7. Exit");
        System.out.println("===============================================");
    }

}