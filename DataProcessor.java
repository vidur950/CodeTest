package com.company;
import javax.xml.crypto.Data;
import java.io.*;
import java.lang.*;
import java.util.*;
import java.util.concurrent.atomic.*;
import java.util.concurrent.*;

// This class is responsible for performing the testing of data read from test file or entered by the user
// The user can load the test data from a test file. This test file should be specified as the second command line argument
// when executing this program
// If the user does not enter any second command line argument then the program will prompt the user to enter the pair
// of cities from the console for testing

class DataProcessor
{
    DataLoader dataLoader;
    Set notToVisitCities;
    AtomicBoolean destinationFound;
    String source;
    String destination;

    public DataProcessor(String filePath)
    {
        dataLoader = new DataLoader(filePath);

    }

    public void ProcessResultsFromTestFile(String path) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(path)))) {
            String temp = br.readLine();
            while (temp != null && !temp.isEmpty()) {
                ProcessResults(temp);
                System.out.println( "For " + temp + " : " + (destinationFound.get()?  "GO":"CANNOT GO"));
                temp = br.readLine();

            }
        }
        catch (IOException e)
        {
            System.out.println("Could not load test file of cities, error message: " + e.getMessage());
            System.out.println("Please restart and try again");
        }
    }

    // this method will process user requests
    // the requests can come from a test file or from the console or over the network
    // if requests are to be processed over the network like http then another method will be added here
    public void ProcessUserRequest(String testFile)
    {
        if (!testFile.isEmpty())
        {
            ProcessResultsFromTestFile(testFile);
            return;
        }

        BufferedReader brreader = new BufferedReader(new InputStreamReader(System.in));
        String dataEntered;
        while (true) {
            try {
                System.out.println("Enter Y to continue ... ");
                dataEntered = brreader.readLine();
                if (dataEntered.isEmpty() || !dataEntered.toUpperCase().equals("Y")) {
                    System.out.println("Bye ...");
                    break;
                }
                System.out.println("Enter pair of cities separated by commas ... ");
                dataEntered = brreader.readLine();
                ProcessResults(dataEntered);
                System.out.println( "For " + dataEntered + " : " + (destinationFound.get()?  "GO":"CANNOT GO"));

            } catch (IOException e) {
                System.out.println("Some error, please try again ... " + e.getMessage());
            }
        }
    }

    private void GetResult(String city)
    {
        HashSet<String> directCities = dataLoader.GetConnectedCities(city);

        for (var connectionCity: directCities)
            if (destinationFound.get()) {
                break;
            }
            else if (connectionCity.equals(destination))
            {
                destinationFound.set(true);
                break;
            }
            else if (notToVisitCities.contains(connectionCity))
            {
                continue;
            }
            else if (connectionCity.equals(source))
            {
                notToVisitCities.add(source);
                continue;
            }
            else {
                notToVisitCities.add(connectionCity);
                GetResult(connectionCity);
            }

    }

    public void ProcessResults (String dataEntered)
    {
        destinationFound = new AtomicBoolean();
        destinationFound.set(false);
        if (dataEntered.isEmpty())
            return;

        String[] vals = dataEntered.trim().split(",");
        if (vals == null || vals.length != 2)
            return ;

        source = vals[0].trim().toUpperCase();
        destination = vals[1].trim().toUpperCase();
        if (source.isEmpty() || destination.isEmpty())
            return ;
        if (!dataLoader.CityLoaded(source) || !dataLoader.CityLoaded(destination))
            return ;
        if (source.equals(destination)) {
            destinationFound.set(true);
            return;
        }

        ConcurrentHashMap citiesMap = new ConcurrentHashMap();
        notToVisitCities = citiesMap.newKeySet();

        GetResult (source);
    }
}
