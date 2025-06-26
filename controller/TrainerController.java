/*package controller;

import model.Trainer;
import model.TrainerManagement;
import view.TrainerView;
import static utils.InputHelper.*;
import java.util.ArrayList;

public class TrainerController {

    private Trainer trainer;
    private TrainerManagement trainerMgmt;
    private TrainerView view;
    private TrainerController controller;

    public TrainerController() {
        trainerMgmt = new TrainerManagement();
        view = new TrainerView();
        controller = new TrainerController();
    }


    public void addTrainer(){
        view.show("== ADD TRAINER ==");
        String name = view.prompt("Enter trainer name: ");
        String birthdate = view.prompt("Enter trainer birthdate: ");
        String sex = view.prompt("Enter trainer sex (Female/Male): ");
        String hometown = view.prompt("Enter trainer hometown: ");
        String description = view.prompt("Enter trainer description: ");

        Trainer trainer = new Trainer(name, birthdate, sex, hometown, description);
        trainerMgmt.addTrainer(trainer);

        view.show("Trainer profile created! Trainer ID: " + trainer.getTrainerID());

    }
}

temporarily hashed out for future implementation (sorry i want the code to run n my head hurts ToT)
*/
