package OtherClasses;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

// import java.io.InputStream;
// import java.util.Arrays;

public class CSVReader {
    // Method to read external price file. 
    public static ArrayList<Float> ReadCSV(String fileName){
        // 
        // 
        File file = new File(fileName);
        Scanner inputStream;  // allocating inputStream
        
        // Float[] prices; 
        ArrayList<Float> array = new ArrayList<Float>();

        try{
            // getting file stream
            inputStream = new Scanner(file);

            // looping at each file
            int line_counter = 0; 
            int num_lines_to_skip = 2;
            System.out.println("Skipping " + Integer.toString(num_lines_to_skip) + " lines.");

            while(inputStream.hasNext()){
                String line = inputStream.nextLine(); 
                line_counter++;
                
                // Skipping firsts rows
                if (line_counter < (num_lines_to_skip+1)){
                    // System.out.println("Skipping line" + line_counter);
                    continue;
                };

                // Adding the value to array
                // System.out.println(line);
                array.add(Float.parseFloat(line.trim()));
                
                // Breaking for convinience. 
                // if (line_counter > 5){
                //     System.out.println("Stopping by break");
                //     break;
                // }
            }
            // the data is read in array 
            System.out.println("Historic data file readed.");
            inputStream.close();

        } catch (FileNotFoundException e){
            e.printStackTrace();
        }
        
        return array;
    }
    
}
