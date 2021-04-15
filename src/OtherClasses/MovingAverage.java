package OtherClasses;

import java.util.ArrayList;

public class MovingAverage {
    // calculate the backward moving average
    // 

    public static Float Calculate(ArrayList <Float> Array, int window, int index ){
        // Function to calculate the backward moving average in an array given an index.
        // Example: 
        // moving_average = MovingAverage(array[index-window,index])
        // 
        float average = 0.0F;

        float sum = 0.0f;
        int count = 0;

        for (int i=index; (index-window)<i ; i--){
            sum = sum + Array.get(i);
            count++;
        }

        average = sum/count; 

        return average;
    }
    
}
