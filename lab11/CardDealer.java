
public class CardDealer {
    public static void main(String[]args)
    {
    	DeckOfCards playDeck = new DeckOfCards();
       	System.out.println("fresh, unshuffled deck");
    	System.out.println(playDeck.toString());
    	playDeck.shuffle();
       	System.out.println("\n\nshuffled deck");
    	System.out.println(playDeck.toString());
    	
    	for (;playDeck.numCardsRemaining() > 0;)
    	{
    		System.out.println("\nDrawing cards...");
    		Card player1 = playDeck.draw();
    		Card player2 = playDeck.draw();
    		System.out.println("Player 1: " + player1);
    		System.out.println("Player 2: " + player2);
    		if (player1.compareTo(player2) > 0)
    		{
        		System.out.println("Player 1 wins!");
    		}
    		else if (player2.compareTo(player1) > 0)
    		{
        		System.out.println("Player 2 wins!");
    		}
    		else
    		{
        		System.out.println("Everyone wins!");
    		}
    		
    	}
    	playDeck.restoreDeck();
       	System.out.println("\n\nrestored deck");
    	System.out.println(playDeck.toString());
    }
}
