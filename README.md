# AlgoJava
Simple trading algorithm in Java.  
By German Martinez-Ayuso  
Email: germanmartinezayuso@gmail.com  

## Notes

- The historical prices are obtained from an CSV file (attached) that you can edit as you wish. 

- It will use the first price records to calculate the first moving averages. This data will not be used anymore. Therefore, the algorithm will start to work on the prices after the n-day period where n is the long moving average. 

## Workflow
The algorithm follow the next steps:
- Read the historical prices csv file. 
- Initialize the portfolio and decisionmaker.
- Go to the day 1
    - If the short moving average (`MA_short`) is lower than (`MA_long`) the algorithm buys blocks of shares according to the next conditions:
        - The total value of the position cannot be higher than `DecisionMaker.maximum_allocation_position_percentage`. 
        - Each trade cannot be bigger than `DecisionMaker.maximum_allocation_per_trade_percentage`.
        - It has passed at least `DecisionMaker.days_wait_to_buy` days between purchases.

    - If the `MA_short` is higher than `MA_long` the algorithm will start to sell half (`DecisionMaker.factor_reducing_position_while_selling`) of the remaining position until all of the shares are sold. It won't sell until `DecisionMaker.days_wait_to_sell` days have passed.

    - If the losses are higher than `DecisionMaker.maximum_loss_percentage`, the algorithm will trigger a stop loss and will sell everything.

    - If the position profit reaches the `DecisionMaker.profit_objective`, the algorithm will sell all the shares.

    - Repeat until reached the end of the prices given.

- Print summary of portfolio.

## Folder Structure

The workspace contains two folders by default, where:

- `src`: the folder to maintain sources
- `lib`: the folder to maintain dependencies


## Next steps (ToDo)
- [ ] Command line inputs to modify setup and configuration.
- [ ] Plot graphs in files. 
- [ ] Consider other types of metrics.
- [ ] Include the test data (csv file) inside the source code.
## Dependency Management

The `JAVA DEPENDENCIES` view allows you to manage your dependencies. More details can be found [here](https://github.com/microsoft/vscode-java-pack/blob/master/release-notes/v0.9.0.md#work-with-jar-files-directly).
