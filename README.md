<h1 align="center" id="title">Java Card Game</h1>

<p id="description">This is a 2-player game. Initially five cards were drawn at random from a deck of cards and delivered to the players. Also three cards will be placed on the desk. One player's cards are face up while the other player's cards are back up. Player 1 begins and switches out part of his cards in the first round. The other player can view his hand but Player 1 is unable to see Player 2's card. Player 2 then modifies a few of his cards. Lastly the desk card faces will be displayed and the winner of this round will be determined. In the next round player 2 begins the game. On the right side of the game panel the active player's hand point is shown. The player with his cards face up can redraw 0 to 5 of his cards from the deck by checking the box next to the cards and pressing the redrawn button. The new Hand's point will be displayed if the player switches some cards. The player performs this action just once. Then it is the opposing player's turn to select some of his or her cards to redrew. After that it's the turn of the other players to choose which cards to redraw. The winner of this set of games will be decided once the second player has finished and the three cards on the desk reveal their faces. The point of each player's hand is then calculated once more taking into account both the player's five cards and the three cards on the desk. Until one of the players wins three sets the game will continue. The players' turns should be switched in the following round. How do we compute a player's hand? Each player's five cards and the three cards on the desk will be considered. Each card has a value: Ace=14 King=13 Queen=12 Jack=11 and the others have the same value as their numbers. The point of a player is the summation of all of his cards' values and the 3 cards on the desk with the following additional points: 1- If there is a Pair of cards their points will be doubled 2- If a "Three of a Kind" exists their points will be tripled. 3-if there is "Four of a Kind" their points will be multiplied by 4. For example the point of following cards 9♣ 9♠ 9♦ 9♥ J♥ K♥ J♥ 8♣ is (9+9+9+9)*4+2*(11+11)+13 + 8 4- If there is a Straight of 5 cards or more Their points will be multiplied by 5. For Example the point of following cards 7♣ 6♠ 5♠ 4♥ 3♥ 9♦ 9♥ is (3+4+5+6+7)*5 +2(9+9) 5- If there are 5 Cards or more with the same suit Their points will be multiplied by 6</p>

<h2>Project Screenshots:</h2>

<img src="https://snipboard.io/c5Zidr.jpg" alt="project-screenshot" width="1000" height="1000/">

  
  
<h2>💻 Built with</h2>

Technologies used in the project:

*   Java
