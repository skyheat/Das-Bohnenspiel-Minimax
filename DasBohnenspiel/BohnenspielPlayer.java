/**
 * Implements an intelligent player for Bohnenspiel. 
 */
import java.util.*;

public class BohnenspielPlayer
{
    // what's my name?
    private final String name;
    // what colour do I have?
    private final Farbe farbe;
    // used to index the board
    private final int turn;

    /**
     * Constructs a Bohnenspiel player.
     */
    public BohnenspielPlayer(Farbe f)
    {
        name = "22507198";
        farbe = f;
        turn = farbe.ordinal();
    }
    
    /**
     * Returns the player's name.
     */
    public String getName()
    {
        return name;
    }
    
    /**
     * Returns the player's colour.
     */
    public Farbe getFarbe()
    {
        return farbe;
    }
    
    /**
     * Returns a legal move in game, i.e. a number h in [1, 6]. 
     * h must denote a non-empty house on this player's side of the board. 
     * You can assume that at least one legal move is available. 
     * DO NOT RETURN AN ILLEGAL MOVE - that's an automatic loss of game. 
     */
    public int chooseMove(Bohnenspiel game)
    {
        // COMPLETE THIS 
        // Placeholder simply plays a random move
    	int move = 0;
    	int value = Integer.MIN_VALUE;
    	int depth = 3;
    	
    	for(int i = 0; i < Bohnenspiel.numberofhouses; i++)	{
    		Bohnenspiel next = game.copyGame(getFarbe());
    		try	{
    			next.move(i+1);;
    		} catch(Exception e)	{
    			continue;
    		}
    		//depth = 3, depth = 4, depth = 5
    		int bestScore = minimax(next, depth, Integer.MIN_VALUE, Integer.MAX_VALUE, Farbe.flip(getFarbe()));
    		if(bestScore > value)	{
    			move = i;
    			value = bestScore;
    		}
    	}
    	return move + 1; //should be 1-6 not 0-5
    	/*
        while (true)
        {
            int h = (int) (Math.random() * Bohnenspiel.numberofhouses);
            if (game.getBoard()[h + turn * Bohnenspiel.numberofhouses] > 0) return h + 1;
        }*/
    }
    
    //Alpha Beta MiniMax
    private int minimax(Bohnenspiel game, int depth, int alpha, int beta, Farbe player)	{
    	
    	if(depth == 0 || game.isOver())	{
    		return evaluate(game);
    	}
    	
    	int value;
    	
    	if(player == getFarbe())	{
    		value = Integer.MIN_VALUE;
    		for(int i =0; i < Bohnenspiel.numberofhouses; i++)	{
    			Bohnenspiel next = game.copyGame(player);
    			try	{
    				next.move(i+1);;
    			} catch (Exception e)	{
    				continue;
    			}
    			value = Math.max(value, minimax(next, depth-1, alpha, beta, Farbe.flip(player)));
    			alpha = Math.max(alpha, value);
    			if(beta <= alpha)	{
    				break;
    			}
    		}
    		return value;
    	}
    	else	{
    		value = Integer.MAX_VALUE;
    		for(int i = 0; i < Bohnenspiel.numberofhouses; i++)	{
    			Bohnenspiel next = game.copyGame(player);
    			try	{
    				next.move(i+1);
    			} catch(Exception e)	{
    				continue;
    			}
    			value = Math.min(value, minimax(next, depth-1, alpha, beta, Farbe.flip(player)));
    			beta = Math.min(beta, value);
    			if(beta <= alpha)	{
    				break;
    			}
    		}
    		return value;
    	}
    }
    
    //Simple Evaluate that takes into account player win conditions and difference between player and opponent
    private int evaluate(Bohnenspiel game)	{
    	int[] stores = game.getStores();
    	int playerStore; int oppStore;
    	
    	if(Farbe.WEISSE == getFarbe())	{
    		playerStore = stores[0];
    		oppStore = stores[1];
    	}
    	else	{
    		playerStore = stores[1];
    		oppStore = stores[0];
    	}
    	
    	if(playerStore > 36)	{
    		return Integer.MAX_VALUE;
    	}
    	else if(oppStore > 36)	{
    		return Integer.MIN_VALUE;
    	}
    	else	{
    		return playerStore - oppStore;
    	}
    }
}