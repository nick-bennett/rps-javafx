package edu.cnm.deepdive;

import edu.cnm.deepdive.controller.RpsController;
import java.io.IOException;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class Main extends Application {

  private static final String LAYOUT_RESOURCE = "rps.fxml";
  private static final String RESOURCE_BUNDLE =
      Main.class.getPackageName().replace('.', '/') + "/strings";
  private static final String WINDOW_TITLE_KEY = "window_title";
  private static final String ICON_RESOURCE = "icon.png";

  private RpsController controller;

  public static void main(String[] args) {
    launch();
  }

  @Override
  public void start(Stage stage) throws IOException {
    ResourceBundle bundle = ResourceBundle.getBundle(RESOURCE_BUNDLE);
    FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(LAYOUT_RESOURCE), bundle);
    Parent root = fxmlLoader.load();
    controller = fxmlLoader.getController();
    Scene scene = new Scene(root);
    stage.setTitle(bundle.getString(WINDOW_TITLE_KEY));
    stage.getIcons().add(new Image(Main.class.getResourceAsStream(ICON_RESOURCE)));
    stage.setResizable(true);
    stage.setScene(scene);
    stage.sizeToScene();
    stage.show();
    Bounds bounds = root.getLayoutBounds();
    stage.setMinWidth(root.minWidth(-1) + stage.getWidth() - bounds.getWidth());
    stage.setMinHeight(root.minHeight(-1) + stage.getHeight() - bounds.getHeight());
    stage.setMaxWidth(stage.getWidth());
    stage.setMaxHeight(stage.getHeight());
  }

  @Override
  public void stop() throws Exception {
    controller.stop();
    super.stop();
  }

}