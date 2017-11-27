import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

/**
 * Natasya Virgichalia
 * Date: 11/26/2017
 * Question 16.23
 */
public class imageWithAudio extends Application{

        @Override
        public void start(Stage primaryStage) throws Exception {
            AnimationBox pane = new AnimationBox();
            primaryStage.setScene(new Scene(pane, 500, 500));
            primaryStage.setTitle("Image Animation with Audio");
            primaryStage.show();
        }

        private class AnimationBox extends BorderPane {

            Button start = new Button("Start Animation");
            String url;
            String prefix;
            String directory = "/image/";
            String imageName = ".png";
            long speed;
            int numImages;
            int image = 1;
            Timeline timeline = null;

            StackPane center = new StackPane();
            AnimationBox() {
                setCenter(center);
                GridPane bottom = new GridPane();
                bottom.setPadding(new Insets(5));
                bottom.setHgap(5);
                Label lblInfo = new Label("Enter information for animation");
                bottom.add(lblInfo, 0, 0);
                HBox top = new HBox(start);
                top.setAlignment(Pos.CENTER_RIGHT);
                setTop(top);

                TextField textNumImage = new TextField();
                textNumImage.setPrefColumnCount(30);
                Label labelNumImage = new Label("Number of Images");
                bottom.add(labelNumImage, 0, 3);
                bottom.add(textNumImage, 1, 3);

                TextField imagePrefix = new TextField();
                imagePrefix.setPrefColumnCount(30);
                Label labelPrefix = new Label("Image file prefix");
                bottom.add(labelPrefix, 0, 2);
                bottom.add(imagePrefix, 1, 2);

                TextField imageSpeed = new TextField();
                imageSpeed.setPrefColumnCount(30);
                Label labelSpeed = new Label("Animation Speed");
                bottom.add(labelSpeed, 0, 1);
                bottom.add(imageSpeed, 1, 1);

                TextField audioFile = new TextField();
                audioFile.setPrefColumnCount(30);
                Label labelAudio = new Label("Audio file URL");
                bottom.add(labelAudio, 0, 4);
                bottom.add(audioFile, 1, 4);
                setBottom(bottom);

                start.setOnAction(e -> {
                    speed = Long.parseLong(imageSpeed.getText().trim());
                    prefix = imagePrefix.getText().trim();
                    numImages = Integer.parseInt(textNumImage.getText());
                    url = audioFile.getText();
                    initTimeline();
                });
            }
            private void initTimeline(){
                if (timeline != null) {
                    timeline.stop();
                    timeline = null;
                    image = 1;
                }
                timeline = new Timeline(
                        new KeyFrame(Duration.millis(speed), e-> nextImage()));
                MediaPlayer mp = new MediaPlayer(new Media(url));
                mp.play();
                mp.setCycleCount(MediaPlayer.INDEFINITE);
                timeline.setOnFinished(event -> mp.stop());
                timeline.setCycleCount(numImages);
                timeline.play();
            }
            private void nextImage(){
                center.getChildren().clear();
                center.getChildren().add(
                        new ImageView(new Image(directory + prefix + image++ + imageName)));
            }
        }
        public static void main(String[] args) {
            Application.launch(args);
        }
    }
