package edu.cnm.deepdive;

import edu.cnm.deepdive.controller.RpsController;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class Main extends Application {

  private static final String LAYOUT_RESOURCE = "layouts/rps.fxml";
  private static final String RESOURCE_BUNDLE = "bundles/strings";
  private static final String WINDOW_TITLE_KEY = "window_title";
  private static final String ICON_RESOURCE = "images/icon.png";

  private RpsController controller;

  @Override
  public void start(Stage stage) throws IOException {
    ClassLoader classLoader = getClass().getClassLoader();
    ResourceBundle bundle = ResourceBundle.getBundle(RESOURCE_BUNDLE);
    FXMLLoader fxmlLoader = new FXMLLoader(classLoader.getResource(LAYOUT_RESOURCE), bundle);
    Parent root = fxmlLoader.load();
    controller = fxmlLoader.getController();
    Scene scene = new Scene(root);
    stage.setTitle(bundle.getString(WINDOW_TITLE_KEY));
    stage.getIcons().add(new Image(classLoader.getResourceAsStream(ICON_RESOURCE)));
    stage.setResizable(true);
    stage.setScene(scene);
    stage.sizeToScene();
    stage.show();
    Bounds bounds = root.getLayoutBounds();
    stage.setMinWidth(root.minWidth(-1) + stage.getWidth() - bounds.getWidth());
    stage.setMinHeight(root.minHeight(-1) + stage.getHeight() - bounds.getHeight());
  }

  public static void main(String[] args) {
    launch();
  }

  @Override
  public void stop() throws Exception {
//    controller.stop();
    super.stop();
  }

}