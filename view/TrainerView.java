package view;

import model.Pokemon;
import model.Trainer;

public class TrainerView {

    public void viewTrainer(Trainer trainer)
    {
        System.out.println("-- Trainer Profile --");
        System.out.println("["+"Trainer ID #" + trainer.getTrainerID() +"]");
        System.out.println("Name:" + trainer.getTrainerName());

        // Display Active Pokemon Lineup
        System.out.println("Active Pokemon Lineup: " );
        Pokemon[] activePokemon = trainer.getActivePokemon();
        if (activePokemon == null || activePokemon.length == 0) { System.out.println("No active Pokemon"); }
        else {
            for (Pokemon p: activePokemon)
            {
                //ystem.out.println()
            }
        }
        // Display Stored Pokemon
        System.out.println("Pokemon in Storage: " );
		  
        //GIVING ME ERRORS (METHOD DNE), JUST UNCOMMENT LATER
		  
		  /*
		  Pokemon[] storedPokemon = trainer.getStoredPokemon();
        if (storedPokemon == null || storedPokemon.length == 0) { System.out.println("No stored Pokemon"); }
        else {
            for (Pokemon p: storedPokemon)
            {
                //ystem.out.println()
            }
        }
        System.out.println("====================");
		  */
    }

    public void viewAllTrainers(Trainer trainer[])
    {
        System.out.println("-- Trainer Profiles --");
        System.out.println("=========================");
        if (trainer == null || trainer.length == 0) { System.out.println("No active Pokemon"); }
        else {
            for (Trainer t : trainer)
            {
                viewTrainer(t);
            }
        }
    }
}
