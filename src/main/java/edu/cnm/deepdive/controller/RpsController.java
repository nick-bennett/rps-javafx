package edu.cnm.deepdive.controller;

import edu.cnm.deepdive.model.Arena;
import edu.cnm.deepdive.view.TerrainView;
import java.util.ResourceBundle;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;

public class RpsController {

  private static final String GENERATION_FORMAT_KEY = "generation_format";
  private static final byte NUM_BREEDS = 3;
  private static final int ARENA_SIZE = 50;
  private static final int ITERATIONS_PER_SLEEP = ARENA_SIZE * ARENA_SIZE / 50;
  private static final int SLEEP_INTERVAL = 1;

  @FXML private Button reset;
  @FXML private ToggleButton toggleRun;
  @FXML private Label generation;
  @FXML private TerrainView terrainView;
  @FXML private ResourceBundle resources;
  private Arena arena;
  private boolean running;
  private String generationFormat;
  private Updater updater;

  @FXML
  private void initialize() {
    arena = new Arena.Builder()
        .setNumBreeds(NUM_BREEDS)
        .setArenaSize(ARENA_SIZE)
        .build();
    terrainView.setArena(arena);
    updater = new Updater();
    generationFormat = resources.getString(GENERATION_FORMAT_KEY);
    reset(null);
  }

  @FXML
  private void reset(ActionEvent actionEvent) {
    arena.init();
    terrainView.update();
    updateDisplay();
  }

  @FXML
  private void toggleRun(ActionEvent actionEvent) {
    if (toggleRun.isSelected()) {
      start();
    } else {
      stop();
    }
  }

  private void start() {
    running = true;
    updateControls();
    new Runner().start();
    updater.start();
  }

  public void stop() {
    toggleRun.setDisable(true);
    running = false;
    updater.stop();
  }

  private void updateDisplay() {
    terrainView.draw();
    generation.setText(String.format(generationFormat, arena.getGeneration()));
  }

  private void updateControls() {
    if (running) {
      reset.setDisable(true);
    } else {
      reset.setDisable(false);
      if (toggleRun.isSelected()) {
        toggleRun.setSelected(false);
      }
      toggleRun.setDisable(false);
      updateDisplay();
    }
  }

  private class Runner extends Thread {

    @Override
    public void run() {
      while (running) {
        for (int i = 0; i < ITERATIONS_PER_SLEEP; i++) {
          arena.advance();
        }
        terrainView.update();
        running &= !arena.isAbsorbed();
        try {
          //noinspection BusyWait
          Thread.sleep(SLEEP_INTERVAL);
        } catch (InterruptedException ignored) {
          // Ignore the exception; get on with life.
        }
      }
      Platform.runLater(RpsController.this::updateControls);
    }

  }

  private class Updater extends AnimationTimer {

    @Override
    public void handle(long now) {
      updateDisplay();
    }

  }

}
