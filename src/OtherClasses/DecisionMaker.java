package OtherClasses;

public class DecisionMaker {

    public float moving_average_long = 0.0f;
    public float moving_average_short = 0.0f;
    public float current_price = 0.0f;
    public float maximum_loss_percentage = 10.0f; // in percentage
    public float profit_objective = 20.0f; // in percentage.
    public float maximum_allocation_per_trade_percentage = 5.0f;
    public float maximum_allocation_position_percentage = 30.0f; 
    public float factor_reducing_position_while_selling = 0.5f; // we will sell half of the position when the price increase. 
    public int days_wait_to_buy = 5;
    public int days_wait_to_sell = 2; 

    private float excess_ma_application = 0.0f; // this reduce or increase MA_long to give some tolerance. Not necessary. 
    
    public Portfolio portfolio; 
    
    // constructor
    public DecisionMaker(Portfolio portfolio){
        this.portfolio = portfolio;
    }

    public void decide(){
        // Take decisions...

    }

    public boolean stop_loss(){
        boolean stop_loss;

        float gains = (this.current_price - this.portfolio.average_share_value)*this.portfolio.position_shares;
        float max_loss = -1*(this.portfolio.value*this.maximum_loss_percentage/100);
        // if ((this.portfolio.profit/this.portfolio.invested) < (-1*this.maximum_loss_percentage/100)){
        // if (this.portfolio.position_value < this.portfolio.invested*this.maximum_loss_percentage/100){
        
        if (gains < max_loss){
            stop_loss = true;
            
            // System.out.println(max_loss);
            // System.out.println(gains);
            // System.out.println("Stop loss activated!");
        } else {
            stop_loss = false; 
        }
        
        return stop_loss;
    }

    public boolean profit_objective(){
        boolean profit;
        // we have reached the profit objetive. We sell. 
        float gains = (this.current_price/this.portfolio.average_share_value);

        if (gains > (this.profit_objective/100)){
            profit = true;
            System.out.println("Profit objective reached!");
        } else {
            profit = false; 
        }
        return profit;
    }

    public boolean good_to_buy(){
        boolean decision; 

        if (
            (this.moving_average_short < this.moving_average_long*(1-this.excess_ma_application)) &&
            (this.portfolio.position_value < this.portfolio.value*this.maximum_allocation_position_percentage/100) &&
            (this.portfolio.days_from_last_trade > this.days_wait_to_buy)
            ){
            // price has decreased a lot, let's buy!
            decision = true; // buy
            
            // System.out.println("Conditions to buy reached.");
        } else{
            decision = false;
        }
        return decision; 
    }

    public boolean good_to_sell(){
        boolean decision; 

        if (this.moving_average_short > this.moving_average_long*(1+this.excess_ma_application) &&
            (this.portfolio.days_from_last_trade > this.days_wait_to_sell)
            ){
            // price has decreased a lot, let's buy!
            decision = true; // sell
            // System.out.println("Conditions to sell reached.");
        } else{
            decision = false;
        }
        return decision; 
    }

    public int decide_position_to_buy(float price){
        // calculate number of shares to buy.
        float number_shares_ = (this.maximum_allocation_per_trade_percentage/100)*this.portfolio.value/price;
        int number_shares = (int) Math.floor(number_shares_);
        return number_shares;
    }

    public int decide_position_to_sell(){
        // calculate number of shares to sell.
        int current_position = this.portfolio.position_shares;
        int number_shares = (int) Math.ceil(current_position*this.factor_reducing_position_while_selling);
        return number_shares;
    }
}




