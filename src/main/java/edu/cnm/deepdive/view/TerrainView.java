package edu.cnm.deepdive.view;

import edu.cnm.deepdive.model.Arena;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class TerrainView extends Canvas {

  private static final double MAX_HUE = 360;

  private byte[][] terrain;
  private Color[] breedColors;
  private Arena arena;
  private boolean bound;
  private WritableImage buffer;
  private PixelWriter writer;

  @Override
  public boolean isResizable() {
    if (!bound) {
      widthProperty().bind(((Pane) getParent()).widthProperty());
      heightProperty().bind(((Pane) getParent()).heightProperty());
      bound = true;
    }
    return true;
  }

  @Override
  public void resize(double width, double height) {
    super.resize(width, height);
    if (arena != null) {
      draw();
    }
  }

  public void setArena(Arena arena) {
    this.arena = arena;
    int numBreeds = arena.getNumBreeds();
    int size = arena.getArenaSize();
    terrain = new byte[size][size];
    breedColors = new Color[numBreeds];
    for (int i = 0; i < numBreeds; i++) {
      breedColors[i] = Color.hsb(i * MAX_HUE / numBreeds, 1, 0.9);
    }
    buffer = new WritableImage(size, size);
    writer = buffer.getPixelWriter();
  }

  public void draw() {
    if (buffer != null) {
      GraphicsContext context = getGraphicsContext2D();
      context.drawImage(buffer, 0, 0, terrain.length, terrain.length, 0, 0, getWidth(), getHeight());
    }
  }

  public void update() {
    arena.copyTerrain(terrain);
    for (int row = 0; row < terrain.length; row++) {
      for (int col = 0; col < terrain[row].length; col++) {
        writer.setColor(col, row, breedColors[terrain[row][col]]);
      }
    }
  }

}






