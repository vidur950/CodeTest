# CodeTest
Code test files for Master Card role
All code files and required test files have been uploaded
cites.txt -- contains a sample list of cities
testcities.txt -- contains cities against which the program is to be tested

the program will load a list of pairs of cities from the cities.txt file , and this is the master file containing the main pairs of cities.
the location of the file should be specified as the first command line argument when running the program
the user can optionally specify a second command line argument for the location of a test file contaning test data. 
for now, the testcities.txt file can be used as file containing test data to be tested

To run the program at least one argument to load the list of cities is needed else the program will not be able to process any results
for successful testing all data in both files should be entered as a pair of cities per line
like:
city,city
city,city
city,city
----- and so on
for a pair of cities to test that was entered, if there is at least one connection from one city to another, the program prints "GO"
else it prints "CANNOT GO"

there are 3 code files also:
Main.java
DataLoader.java
DataProcessor.java

DataProcessor.java : 
This class is responsible for performing the testing of data read from test file or entered by the user
The user can load the test data from a test file. This test file should be specified as the second command line argument
when executing this program
If the user does not enter any second command line argument then the program will prompt the user to enter the pair
of cities from the console for testing

DataLoader.java:
This class loads the list of cities from a text file at a specific location on the user's computer
and user can specify the location of the file as the first command line argument when executing the program

All results for every pair of test cities entered will appear on the console.
