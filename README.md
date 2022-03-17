Snake And Ladder Game Implementation

In this document, I will briefly try to explain the design of the solution that I have implemented. 

The solution contains following domain elements:
1.	Game: As we are modelling a game.
2.	Board: As we are modelling board games.
3.	Cell: Every board in a board game will consists of cells. Cell types will depend on the current game that we are modelling 
4.	Player: As every game will need players to play the game.
5.	Move: A move encapsulates the details regarding a single turn played by the player.
6.	Token: Every game will have some Tokens, which can be moved on the board to track the player progress

Game Class Hierarchy: 

<img width="204" alt="image" src="https://user-images.githubusercontent.com/3645355/158749124-cdf0ee1d-ed26-411e-a692-682de58d23d6.png">

 
1.	As shown in the diagram, Game is the topmost interface which declares the lifecycle events for a game.
2.	BoardGame is an abstract class, that implements Game. This class controls the overall flow of the game execution. It implements a couple of Template methods to handle the flow. Any Game can extend this call and add its own behaviour for the abstract methods. 
3.	SLGame is an implementation of the BoardGame that adds the behaviour for the Snake and Ladder game.
4.	SLGameBuilder is the builder for the SLGame class.

A Game is constructed using a GamesFactory:

<img width="261" alt="image" src="https://user-images.githubusercontent.com/3645355/158749168-b1a8aebe-56d3-42de-bf55-abc54a4896ac.png">

 
A Game class has following elements: Following are the Has-A relationships for the Game

1.	A game has a Board
2.	A game has Players
3.	A game has a GameState
4.	A game has GameTrackers to track the game progress

Board

<img width="181" alt="image" src="https://user-images.githubusercontent.com/3645355/158749191-1ce8535d-da01-4592-b46c-6ce3ae96331f.png">
<img width="253" alt="image" src="https://user-images.githubusercontent.com/3645355/158749208-27fb6a1b-453f-44f9-b3fe-71cebf13511a.png">


   
A Board is made up of Cells. It has a size and a dimension. Creation of Board is managed through SLBoardFactory. 

1.	BoardCell has (Has-A) multiple Cells

Cell: A Cell is the smallest unit on the Board. Following are the different implementations of the Cell as per the requirement of the solution

<img width="184" alt="image" src="https://user-images.githubusercontent.com/3645355/158749243-8830b28f-4da7-47b4-a6ce-38148d0a6076.png">

 
a.	SLBoardCell: This is a normal cell on the board with a cell number. It maintains the list of Tokens present on the Cell. Whenever a token arrives at SLBoardCell, the token gets accepted and added to the list of tokens present on the Cell.
b.	LadderCell: A ladder cell decorates the SLBoardCell. It consists of two internal SLBoardCells, viz,
a.	ladderStartCell
b.	ladderEndCell
c.	Whenever a token arrives on the LadderCell (which decorates ladderStartCell and ladderEndCell), ladderStartCell initially accepts is and then internally, it moves the respective token UP to the ladderEndCell!
c.	SnakeCell: A snake cell is just like ladder cell but with different functionality. Instead of moving up, it moves DOWN from the snakeStartCell to the snakeEndCell
d.	SLFinalCell: This is a variation of SLBoardCell. Whenever a token arrives at this cell, it indicates the end of the game!

Here is the signature for the acceptToken method of the Cell:

Move acceptToken(Token token) throws GameException;


It indicates that, whenever a Token is accepted by the Cell, a Move is performed on the Board. The Cell determines the type of the Move performed and returns an Instance of the Move!

MoveType:

public enum SLMoveType {
    NORMAL_ADVANCE, LADDER_ADVANCE, SNAKE_DESCEND, UNLUCKY_MOVE, ADVANCE_LUCKY_MOVE, QUIT_GAME
}

A move for Snake and Ladder Game captures following information:

<img width="166" alt="image" src="https://user-images.githubusercontent.com/3645355/158749341-ce069bec-525d-41c4-995b-efdb510ccdd1.png">
<img width="166" alt="image" src="https://user-images.githubusercontent.com/3645355/158749351-ba1e0a36-a326-49a9-87b2-64f054f03a6b.png">

   
SLMovesFactory generates the required types of Moves

Following are the different types of Cells required for the game:

<img width="147" alt="image" src="https://user-images.githubusercontent.com/3645355/158749375-fa3596bd-a00f-4e5c-adb1-6bb2b53aca5f.png">
<img width="141" alt="image" src="https://user-images.githubusercontent.com/3645355/158749383-f0b59695-d5d6-4ce6-a995-c369e32074ed.png">
<img width="146" alt="image" src="https://user-images.githubusercontent.com/3645355/158749389-8ec50e86-e555-498b-a3ca-ad98ffa989e5.png">


Note: A Board has one-to-many relatationship between the Cell. Whenever a board for the game is built, its Cells are maintained in the List<? extends Cell> boardCells. 
So, while building the Board, diffent types of Cells are added to the list. This is a variation of the Command Pattern, wherein, the behaviour of the acceptToken method depends on the type of the Cell(or the Command) in the list.

Player

<img width="157" alt="image" src="https://user-images.githubusercontent.com/3645355/158749408-4d61e9fb-cb4f-43f5-a29d-4408494def14.png">

 
A Player is an integral part of the Game. Game maintains (Has-A) a list of Players. A Player Has-A Token. All the fields on the Player are there to capture various statistics of moves that are performed.

Token
 

<img width="157" alt="image" src="https://user-images.githubusercontent.com/3645355/158749418-60bcea5f-6d53-4550-93e5-cc877f8fb6d6.png">



Dice
A SLGame has an instance of a Dice that used to roll by the Player during the turn. Currently, on roll, it returns a random number between 1 to 6.

GameState

<img width="204" alt="image" src="https://user-images.githubusercontent.com/3645355/158749452-9166509b-594a-43af-9dc3-3ae76a3ab718.png">

 
Transition between different game states for SLGame can be roughly be visualized as follows:

<img width="295" alt="image" src="https://user-images.githubusercontent.com/3645355/158749470-9f95c581-a7dc-47cc-8a83-69ff4e996812.png">

 
GameTracker

GameTracker are objects to whom some work can be delegated upon the state change of the Game. Implemented an Observer Pattern. In this, the game trackers can be attached to the Game while before the game is started or they can also be added during the execution of the game. 
Hooks have been provided in the BoardGame to update the status of the game.
For SLGame, the attached GameTracker is invoked after the Game state is GameCompleted as follows:

/**
 * <p>Generates the game analytics. Processes every {@link GameTracker}
 * object associated with the game</p>
 */
protected void generateGameAnalytics() {
    for (GameTracker gameTracker : getGameTrackers()) {
        gameTracker.trackGameProgress(this);
    }
}

Other hooks can be utilized for processing the GameTracker!

<img width="171" alt="image" src="https://user-images.githubusercontent.com/3645355/158749543-383ce024-c80b-4dcd-9b49-4edb4d290f4f.png">

 
A GameTracker can be associated with multiple Games. Once the trackGameProgress(Game game); is invoked on GameTracker, it creates an instance of GameStatisticsData and analyses the statistics recored on the Game instance



1.	GameSimulator executes the simulation of the Game. 
2.	By default, the simulation is executed once. 
3.	It can be configured using the command line arguments
4.	By default, the board generated is as per the example given in the problem statement

Execution Steps:

1.	./gradlew clean build
2.	java -jar build/libs/snakeandladder.jar 10


Following is a part of snapshot for 10 runs:

2022-03-16T22:07:17,317 DEBUG main games.domain.game.SLGame:119 - Performed move by user: Player 1 , Move Type: NORMAL_ADVANCE, Move from Position 2 and Move to Position: 7, Roll on the dice 5
2022-03-16T22:07:17,317 DEBUG main games.domain.game.SLGame:119 - Performed move by user: Player 2 , Move Type: NORMAL_ADVANCE, Move from Position 3 and Move to Position: 6, Roll on the dice 3
2022-03-16T22:07:17,317 DEBUG main games.domain.game.SLGame:119 - Performed move by user: Player 3 , Move Type: NORMAL_ADVANCE, Move from Position 8 and Move to Position: 10, Roll on the dice 2
2022-03-16T22:07:17,317 DEBUG main games.domain.game.SLGame:119 - Performed move by user: Player 4 , Move Type: NORMAL_ADVANCE, Move from Position 25 and Move to Position: 29, Roll on the dice 4
2022-03-16T22:07:17,317 DEBUG main games.domain.game.SLGame:119 - Performed move by user: Player 1 , Move Type: NORMAL_ADVANCE, Move from Position 7 and Move to Position: 12, Roll on the dice 5
2022-03-16T22:07:17,317 DEBUG main games.domain.game.SLGame:119 - Performed move by user: Player 2 , Move Type: NORMAL_ADVANCE, Move from Position 6 and Move to Position: 9, Roll on the dice 3
2022-03-16T22:07:17,317 DEBUG main games.domain.game.SLGame:119 - Performed move by user: Player 3 , Move Type: NORMAL_ADVANCE, Move from Position 10 and Move to Position: 16, Roll on the dice 6
2022-03-16T22:07:17,318 DEBUG main games.domain.game.SLGame:140 - Got a 6 on the Dice! Player Player 3 will play another turn! 
2022-03-16T22:07:17,318 DEBUG main games.domain.game.SLGame:119 - Performed move by user: Player 3 , Move Type: NORMAL_ADVANCE, Move from Position 16 and Move to Position: 17, Roll on the dice 1
2022-03-16T22:07:17,318 DEBUG main games.domain.game.SLGame:119 - Performed move by user: Player 4 , Move Type: NORMAL_ADVANCE, Move from Position 29 and Move to Position: 35, Roll on the dice 6
2022-03-16T22:07:17,318 DEBUG main games.domain.game.SLGame:140 - Got a 6 on the Dice! Player Player 4 will play another turn! 
2022-03-16T22:07:17,318 DEBUG main games.domain.game.SLGame:119 - Performed move by user: Player 4 , Move Type: NORMAL_ADVANCE, Move from Position 35 and Move to Position: 41, Roll on the dice 6
2022-03-16T22:07:17,318 DEBUG main games.domain.game.SLGame:140 - Got a 6 on the Dice! Player Player 4 will play another turn! 















Sample statistics: This is printed for every game:

2022-03-16T22:07:17,337  INFO main board.games.statistics.SLGameStatsTracker:55 - ########################################
2022-03-16T22:07:17,337  INFO main board.games.statistics.SLGameStatsTracker:56 - Game Completed :: Printing the Game Statistics...
2022-03-16T22:07:17,338  INFO main board.games.statistics.SLGameStatsTracker:57 - Game Won by :: Player 4
2022-03-16T22:07:17,338  INFO main board.games.statistics.SLGameStatsTracker:58 - Total turns played in Game (all players): 101
2022-03-16T22:07:17,338  INFO main board.games.statistics.SLGameStatsTracker:60 - Total turns involving a Ladder: 8
2022-03-16T22:07:17,338  INFO main board.games.statistics.SLGameStatsTracker:61 - Total tiles climbed by ladder in the game: 185
2022-03-16T22:07:17,338  INFO main board.games.statistics.SLGameStatsTracker:62 - Longest climb in a single game: 33
2022-03-16T22:07:17,340  INFO main board.games.statistics.SLGameStatsTracker:63 - Minimum climb in a single game: 18
2022-03-16T22:07:17,342  INFO main board.games.statistics.SLGameStatsTracker:64 - Average climb in a single game (Total Tiles Climbed / Total Ladder Advance Moves): 23.0
2022-03-16T22:07:17,342  INFO main board.games.statistics.SLGameStatsTracker:67 - Total turns involving a Snake: 8
2022-03-16T22:07:17,343  INFO main board.games.statistics.SLGameStatsTracker:68 - Total tiles descended by snake in the game: 258
2022-03-16T22:07:17,343  INFO main board.games.statistics.SLGameStatsTracker:69 - Steepest descend in a single game: 58
2022-03-16T22:07:17,343  INFO main board.games.statistics.SLGameStatsTracker:70 - Minimum descend in a single game: 22
2022-03-16T22:07:17,343  INFO main board.games.statistics.SLGameStatsTracker:71 - Average descend in a single game (Total Tiles Descended / Total Snake Descend Moves): 32.0
2022-03-16T22:07:17,343  INFO main board.games.statistics.SLGameStatsTracker:74 - Total lucky rolls in the game: 19
2022-03-16T22:07:17,343  INFO main board.games.statistics.SLGameStatsTracker:75 - Total unlucky rolls in the game: 9
2022-03-16T22:07:17,343  INFO main board.games.statistics.SLGameStatsTracker:76 - Tiles covered in longest turn of the game: 16
2022-03-16T22:07:17,343  INFO main board.games.statistics.SLGameStatsTracker:77 - Total Rolls with 6 in the game: 17
2022-03-16T22:07:17,343  INFO main board.games.statistics.SLGameStatsTracker:80 - Printing Winner Statistics:
2022-03-16T22:07:17,343  INFO main board.games.statistics.SLGameStatsTracker:81 - Total turns played by winner: 28
2022-03-16T22:07:17,343  INFO main board.games.statistics.SLGameStatsTracker:82 - Number of times 6 rolled by winner on dice: 7
2022-03-16T22:07:17,343  INFO main board.games.statistics.SLGameStatsTracker:83 - Maximum 
2022-03-16T22:07:17,344  INFO main board.games.statistics.SLGameStatsTracker:84 - Total lucky turns by winner: 9
2022-03-16T22:07:17,344  INFO main board.games.statistics.SLGameStatsTracker:85 - Total Ladder moves by winner: 4
2022-03-16T22:07:17,344  INFO main board.games.statistics.SLGameStatsTracker:86 - Total unlucky moves by winner:3
2022-03-16T22:07:17,344  INFO main board.games.statistics.SLGameStatsTracker:87 - Total snake descend moves by winner:2
2022-03-16T22:07:17,344  INFO main board.games.statistics.SLGameStatsTracker:90 - ########################################


Following statistics is printed after completing simulation for all the games:

2022-03-16T22:07:17,423  INFO main board.games.statistics.SLGameStatsTracker:103 - -----------------------------------------
2022-03-16T22:07:17,424  INFO main board.games.statistics.SLGameStatsTracker:104 - Total games played in the simulation: 10
2022-03-16T22:07:17,424  INFO main board.games.statistics.SLGameStatsTracker:105 - Maximum turns played in the game: 140
2022-03-16T22:07:17,424  INFO main board.games.statistics.SLGameStatsTracker:106 - Minimum turns played in the game: 62
2022-03-16T22:07:17,424  INFO main board.games.statistics.SLGameStatsTracker:107 - Average turns required to end the game: 106.0
2022-03-16T22:07:17,424  INFO main board.games.statistics.SLGameStatsTracker:108 - -----------------------------------------




