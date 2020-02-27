package com.company;
import javax.xml.crypto.Data;
import java.io.*;
import java.lang.*;
import java.util.*;
import java.util.concurrent.atomic.*;
import java.util.concurrent.*;

// This class loads the list of cities from a text file at a specific location on the user's computer
// and user can specify the location of the file as the first command line argument when executing the program

class DataLoader {

    HashMap<String, HashSet<String>> directCitiesConnections;

    private void LoadData(String path) throws IOException {

        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(path)))) {

            String temp = br.readLine();

            while (temp != null && !temp.isEmpty()) {
                String[] vals = temp.trim().split(",");
                if (vals == null || vals.length != 2)
                    continue;
                String firstCity = vals[0].trim().toUpperCase();
                String secondCity = vals[1].trim().toUpperCase();

                if (firstCity.isEmpty() || secondCity.isEmpty())
                    continue;

                HashSet<String> _dataEntered = directCitiesConnections.get(firstCity);
                if (_dataEntered == null || _dataEntered.isEmpty())
                    directCitiesConnections.put(firstCity, new HashSet<String>());
                _dataEntered = directCitiesConnections.get(firstCity);
                _dataEntered.add(secondCity);

                _dataEntered = directCitiesConnections.get(secondCity);
                if (_dataEntered == null || _dataEntered.isEmpty())
                    directCitiesConnections.put(secondCity, new HashSet<String>());
                _dataEntered = directCitiesConnections.get(secondCity);
                _dataEntered.add(firstCity);

                temp = br.readLine();
            }

        }

    }

    public int CitiesLoadedCount()
    {
        return directCitiesConnections.keySet().size();
    }

    public DataLoader(String citiesListFile) {
        directCitiesConnections = new HashMap<>();
        try {
            LoadData(citiesListFile);
        }
        catch (IOException e)
        {
            System.out.println("Could not load list of cities, error message: " + e.getMessage());
            System.out.println("Please restart and try again");
        }


    }

    public HashSet<String> GetConnectedCities (String source) {
        if (source.isEmpty() || directCitiesConnections.isEmpty() || !directCitiesConnections.containsKey(source.toUpperCase()))
            return new HashSet<String>();
        return directCitiesConnections.get(source.toUpperCase());
    }

    public boolean CityLoaded(String city)
    {
        return directCitiesConnections.containsKey(city.toUpperCase());
    }
}
