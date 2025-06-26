package controller;

import model.Trainer;
import model.TrainerManagement;
import view.TrainerView;
import static utils.InputHelper.*;
import java.util.ArrayList;

public class TrainerController {

    private Trainer model;
    private TrainerManagement trainerMgmt;
    private TrainerView view;
    private TrainerController controller;

    public TrainerController() {
        this.trainerMgmt = new TrainerManagement();
        this.view = new TrainerView();
        this.controller = new TrainerController();
    }

    public void addTrainer(){
        prompt("== ADD TRAINER ==");
        String name = promptString("Enter trainer name: ");

    }
}
