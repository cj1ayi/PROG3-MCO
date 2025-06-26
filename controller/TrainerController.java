package controller;

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
        prompt("== ADD TRAINER ==");
        String name = promptString("Enter trainer name: ");
        String birthdate = promptString("Enter trainer birthdate: ");
        String sex = promptString("Enter trainer sex (Female/Male): ");
        String hometown = promptString("Enter trainer hometown: ");
        String description = promptString("Enter trainer description: ");

        Trainer trainer = new Trainer(name, birthdate, sex, hometown, description);
        trainerMgmt.addTrainer(trainer);

        prompt("Trainer profile created! Trainer ID: " + trainer.getTrainerID());

    }
}
