# ALGOJAVA
Simple trading algorithm in Java.  
By German Martinez-Ayuso  
Email: germanmartinezayuso@gmail.com  

## Objective
This simple and little project aims to:
- Help me to learn Java programming language.
- Learn the basics of algorithm trading. 


## Workflow
The algorithm follow the next steps:
- If the short moving average (`MA_short`) is lower than (`MA_long`) the algorithm buys blocks of shares according to the next conditions:
    - The total value of the position cannot be higher than `DecisionMaker.maximum_allocation_position_percentage`. 
    - Each trade cannot be bigger than `DecisionMaker.maximum_allocation_per_trade_percentage`.
    - It has passed at least `DecisionMaker.days_wait_to_buy` days between purchases.

- If the `MA_short` is higher than `MA_long` the algorithm will start to sell half (`DecisionMaker.factor_reducing_position_while_selling`) of the remaining position until all of the shares are sold. It won't sell until `DecisionMaker.days_wait_to_sell` days have passed.

- If the losses are higher than `DecisionMaker.maximum_loss_percentage`, the algorithm will trigger a stop loss and will sell everything.

- If the position profit reaches the `DecisionMaker.profit_objective`, the algorithm will sell all the shares.


## Folder Structure

The workspace contains two folders by default, where:

- `src`: the folder to maintain sources
- `lib`: the folder to maintain dependencies

## Dependency Management

The `JAVA DEPENDENCIES` view allows you to manage your dependencies. More details can be found [here](https://github.com/microsoft/vscode-java-pack/blob/master/release-notes/v0.9.0.md#work-with-jar-files-directly).
