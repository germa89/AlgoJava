// ALGOJAVA
// 


// defaults imports
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

// My own imports 
import OtherClasses.CSVReader;
import OtherClasses.DecisionMaker;
import OtherClasses.MovingAverage;
import OtherClasses.Portfolio;

public class App {
    // Main function
    public static void main(String[] args) {
        System.out.println("\n*** WELCOME TO ALGOJAVA ***\n");
        System.out.println("\nThis code performs buy/selling on the stock whose price history is given in the csv file attached. " +
        "The algorithm will buy shares when:" + 
        " - The MA_short is lower than MA_long.\n" +
        "The algorithm will sell shares when:\n" + 
        " - The MA_short is higher than MA_long.\n" +
        " - The Stop loss get triggered." + 
        " - The profit objective is reached." + 
        "\n\nMore information on the README attached. ");
        
        // Setting currency format
        Locale locale = new Locale("es", "ES");      
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale); 


        String fileName = "stock_history.csv";
        ArrayList<Float> prices = CSVReader.ReadCSV(fileName);

        // A simple trading algorithm.
        // We are going to buy stock everytime that the 50 moving average goes below the 200 moving average. 

        // configuration
        int ma_long_ = 200; // long moving average
        int ma_short_ = 50; // short moving average

        float total_investment = 100000f;

        // clases configuration
        Portfolio portfolio = new Portfolio(total_investment);
        DecisionMaker decisionMaker = new DecisionMaker(portfolio);

        decisionMaker.maximum_loss_percentage = 40.0f; // (in percentage)
        decisionMaker.maximum_allocation_per_trade_percentage = 10.0f; // (in percentage) 
        decisionMaker.profit_objective = 200.0f; // (in percentage) Realise profit when it this percentage. 


        System.out.println("***\nStarting loop\n" +
        "To calculate the moving averages, this algorithm discard the first %ma_long_% data points. "+
        "And then we assume that the next data points are happening on real time, hence we will use"+
        "the previous moving averages previously calculated to decide whether we are buying or selling.\n***"
        );
        for (int itime=0+ma_long_; (prices.size()-1)!=itime; itime++){
            // Updating
            float price = prices.get(itime+1); 
            portfolio.update(price, itime);

            // Calculate moving averages at given time. 
            float ma_long = MovingAverage.Calculate(prices, ma_long_, itime);
            float ma_short = MovingAverage.Calculate(prices, ma_short_, itime);

            // Updating decisionMaker 
            decisionMaker.portfolio = portfolio;
            decisionMaker.moving_average_long = ma_long;
            decisionMaker.moving_average_short = ma_short;
            
            // Implement limits 
            if (decisionMaker.stop_loss()){
                // We have too much losses, let's sell everything.
                portfolio.sell_off(price); 
                continue;                
            }

            if (decisionMaker.profit_objective()){
                portfolio.sell(portfolio.position_shares, price);
                continue; 
            }

            // Strategy 
            if (decisionMaker.good_to_buy()){
                // it is a good moment to buy. 
                int number_of_shares =  decisionMaker.decide_position_to_buy(price);
                portfolio.buy(number_of_shares, price);

            } else if (decisionMaker.good_to_sell()) {
                // it is a good moment to sell!
                int number_of_shares =  decisionMaker.decide_position_to_sell();
                portfolio.sell(number_of_shares, price);

            } else {
                // System.out.println("Decided to: HOLD");
            }
                  

            
            // System.out.println(ma_long);
            // System.out.println(ma_short);
            
            // if (itime > 220){
            //     break;
            // }
        };
        
        
        System.out.println("\n\nSummary:");
        System.out.println("--------");

        System.out.println("Final value of portfolio: " + currencyFormatter.format(portfolio.value));
        System.out.println("Number of shares in portfolio: " + Integer.toString(portfolio.position_shares));
        System.out.println("Value of shares in portfolio: " + currencyFormatter.format(portfolio.position_value)); 
        System.out.println("Profit portfolio: " + currencyFormatter.format(portfolio.value - total_investment));
        
        
		NumberFormat defaultFormat = NumberFormat.getPercentInstance();
		defaultFormat.setMinimumFractionDigits(2);
        float percentage_profit = (portfolio.value - total_investment)/total_investment;
        System.out.println("Profit portfolio (%): " + defaultFormat.format(percentage_profit)); 

        System.out.println("\n*** END OF ALGOJAVA ***\n");
        System.out.println("Designed and coded by German Martinez Ayuso.");
        System.out.println("Available freely in Github - https://www.github.com/germa89\n\n***");

    }
}
