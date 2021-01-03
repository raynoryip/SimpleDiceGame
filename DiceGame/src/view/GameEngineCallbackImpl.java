package view;

import java.util.logging.Level;
import java.util.logging.Logger;

import model.interfaces.DicePair;
import model.interfaces.Die;
import model.interfaces.GameEngine;
import model.interfaces.Player;
import view.interfaces.GameEngineCallback;

/**
 * 
 * Skeleton example implementation of GameEngineCallback showing Java logging behaviour
 * 
 * @author Caspar Ryan
 * @see view.interfaces.GameEngineCallback
 * 
 */
public class GameEngineCallbackImpl implements GameEngineCallback
{
   private static final Logger logger = Logger.getLogger(GameEngineCallback.class.getName());

   public GameEngineCallbackImpl()
   {
      // FINE shows rolling output, INFO only shows result
      logger.setLevel(Level.FINE);
   }

   @Override
   public void playerDieUpdate(Player player, Die die, GameEngine gameEngine)
   {
      // intermediate results logged at Level.FINE
	   String dieUpdate = String.format("%s die %d rolled %s", player.getPlayerName(), 
			   die.getNumber(), die.toString());
      logger.log(Level.FINE, dieUpdate);
   }

   @Override
   public void playerResult(Player player, DicePair result, GameEngine gameEngine)
   {
      // final results logged at Level.INFO
	   String pResult = String.format("%s **RESULT**: %s", player.getPlayerName(), result.toString());
      logger.log(Level.INFO, pResult);
   }
   
   public void houseDieUpdate(Die die, GameEngine gameEngine) {
	   String hUpdate = String.format("House dice %d rolled to %s", die.getNumber(), die.toString());
	   logger.log(Level.FINE, hUpdate);
   }
   
   public void houseResult(DicePair result, GameEngine gameEngine) {
	   
	   String hResult = String.format("House **Result** : %s", result.toString());
	   logger.log(Level.INFO, hResult);
	   
	   String allResult = "FINAL PLAYER RESULTS\n";
	   for(Player player: gameEngine.getAllPlayers()) {
		   allResult += player.toString() + " , RESULT .. " + player.getResult().toString() + "\n";
	   }
	   logger.log(Level.INFO, allResult);
   }
}
