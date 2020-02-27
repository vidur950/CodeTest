package com.company;
import javax.xml.crypto.Data;
import java.io.*;
import java.lang.*;
import java.util.*;
import java.util.concurrent.atomic.*;
import java.util.concurrent.*;

// this program will load a list of pairs of cities from a text file
// the location of the file should be specified as the first command line argument when running the program
// the user can optionally specify a second command line argument for the location of test file contaning test data
// at least one argument to load the list of cities is needed else the program will not be able to process any results
// for successful testing all data in both files should be entered as a pair of cities per line
// like:
// city,city
// city,city
// city,city
// ----- and so on
// for a pair of cities to test that was entered, if there is at least one connection from one city to another, the program prints "GO"
// else it prints "CANNOT GO"

public class Main {

    public static void main(String[] args) {
        String citiesListPath = "";
        String testCitiesPath = "";

        if (args == null || args.length == 0 || args.length > 2)
        {
            System.out.println("Please restart program and enter full path for locations of files containing cities and test data as the arguments to the program");
            return;
        }
        else if (args.length == 1)
        {
            citiesListPath = args[0];
            testCitiesPath = "";
            if (!new File(citiesListPath).exists())
            {
                System.out.println("Please restart program and enter correct file names");
                return;
            }
        }
        else {
            citiesListPath = args[0];
            testCitiesPath = args[1];
            if (!new File(citiesListPath).exists() || !new File(testCitiesPath).exists()) {
                System.out.println("Please restart program and enter correct file names");
                return;
            }
        }
            var dataProcessor = new DataProcessor(citiesListPath);
            dataProcessor.ProcessUserRequest(testCitiesPath);

        System.out.println("Bye, program is ending");
        }
}
