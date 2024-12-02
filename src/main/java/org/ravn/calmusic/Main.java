package org.ravn.calmusic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        logger.info("Application started");

        try {
            // Some logic here
            logger.debug("This is a debug message");

            // Simulating an error
            //throw new Exception("Something went wrong!");

        } catch (Exception e) {
            logger.error("An error occurred: ", e);
        }

        logger.info("Application finished");
    }
}