package OtherClasses;

public class Portfolio {
    /*
    This is a portfolio class. But it is more like an allocation because it does not accept multiple types of holdings. 
    Only one type share is allowed.
    */
    public Float position_value = 0.0f;
    public Integer position_shares = 0;
    public Float invested = 0.0f;
    public Float cash = 0.0f; 
    public Float profit = 0.0f;
    public float value = 0.0f; 
    public float average_share_value = 0.0f;
    public int day=0; 
    
    public int days_from_last_trade = 100;

    public Portfolio(float cash){
        this.cash = cash;
        this.value = cash;
        this.day = 0; 
;
        System.out.println(this.days_from_last_trade);
    }
    
    public void update(float price, int day){
        this.position_value = this.position_shares*price;
        this.profit = this.position_value - this.invested;
        this.value = this.position_value + this.cash; 
        this.day = day;
        this.days_from_last_trade = this.days_from_last_trade + 1;
        // System.out.println("Updating portfolio: The new position value is " + this.position_value.toString());
    }

    public void buy(Integer number_of_shares, Float price){
        this.position_shares = this.position_shares + number_of_shares;
        this.invested = this.invested + number_of_shares*price;
        this.cash = this.cash - number_of_shares*price;
        this.average_share_value = calculate_new_average_share_value(number_of_shares, price);

        System.out.println("Day " + Integer.toString(this.day) + ". Added "+ number_of_shares.toString() + " new shares at " + price.toString() + " $ to the portofolio");
        this.days_from_last_trade = 0;
    }

    private float calculate_new_average_share_value(int number_of_shares_to_add, float price){
        //
        float new_asv = (this.average_share_value*this.position_shares + number_of_shares_to_add*price)/(this.position_shares + number_of_shares_to_add);
        return new_asv;
    }

    public void sell(Integer number_of_shares, Float price){
        if (this.position_shares >= number_of_shares && number_of_shares>0){
            this.position_shares = this.position_shares - number_of_shares;
            this.invested = this.invested - number_of_shares*price;
            this.cash = this.cash + number_of_shares*price; 

            System.out.println("Day " + Integer.toString(this.day) + ". You reduced "+ number_of_shares.toString() + " shares by selling at " + price.toString() + " $ to the portofolio");
        
        } else if (this.position_shares < number_of_shares){
            System.out.println("You cannot short a stock. We are not Citadel.");
        }

        this.days_from_last_trade = 0;
    }

    public boolean has_opened_positions(){
        if (this.position_shares > 0){
            return true;
        } else {
            return false;
        }
    }

    public void sell_off(float price){
        System.out.println("Day " + Integer.toString(this.day) + ". STOP LOSS. Selling all."); 
        this.sell(this.position_shares, price);
    }
}
