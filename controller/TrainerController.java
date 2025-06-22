package controller;

import model.Trainer;
import model.TrainerManagement;
import view.TrainerView;

public class TrainerController {

    private Trainer model;
    private TrainerManagement service;
    private TrainerView view;
    private TrainerController controller;

    public TrainerController() {
        this.service = new TrainerManagement();
    }
}
