package sdufighters;

import java.io.IOException;
import java.io.*;
import java.net.*;
import javafx.animation.*;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.*;
import javafx.scene.input.*;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MainMenu extends Application {

    private Image iconpic;
    private Image backgroundpic;
    private Image logopic;
    private Image oneplayerpic;
    private Image twoplayersoffpic;
    private Image twoplayersonpic;
    private Image trainingpic;
    private Image optionspic;
    private Image exitpic;
    private Image backpic;
    private Image fightpic;

    private final int widthb = 1000;
    private final int heightb = 100;
    private final int x = 0;
    private int y1 = 0;
    private int y2 = 0;
    private int y3 = 0;
    private int y4 = 0;
    private int y5 = 0;
    private int y6 = 0;
    private int y7 = 0;
    private int y8 = 0;
    private int chosen = 0;
    private String mode = "";

    private long startLoading, endLoading, loadingDelta;
    private double elapsedTime;

    private Thread load;
    private MortalKombat game;
    private boolean loadDone = false, serverStatus = true, isReceiving = false;
    private static boolean isGame = false;

    private Socket socket = null;
    private DataInputStream in;
    private DataOutputStream out;

    @Override
    public void start(Stage primaryStage) throws IOException {
        //connect
        try {
            int port = 4444;
            String ip = "0.0.0.0";
            socket = new Socket(ip, port);
        } catch (ConnectException ex) {
            System.out.println("Server is off");
            serverStatus = false;
        }
        //initialize streams
        Pane root = new Pane();
        Pane arenasroot = new Pane();
        Pane optionsroot = new Pane();
        Pane loadingroot = new Pane();
        Options opt = new Options();
        optionsroot.getChildren().addAll(opt);
        optionsroot.setDisable(true);
        optionsroot.setOpacity(0);
        optionsroot.toBack();
        Arena arenas = new Arena();
        arenasroot.getChildren().addAll(arenas);
        arenasroot.setDisable(true);
        arenasroot.setOpacity(0);
        arenasroot.toBack();
        Loading loading = new Loading();
        loadingroot.getChildren().addAll(loading);
        loadingroot.setDisable(true);
        loadingroot.setOpacity(0);
        loadingroot.toBack();
        Pane main = new Pane();
        main.toFront();
        Scene scene = new Scene(root, 1000, 700);
        ImageView background = new ImageView(backgroundpic);
        ImageView logo = new ImageView(logopic);
        ImageView oneplayer = new ImageView(oneplayerpic);
        ImageView twoplayersoff = new ImageView(twoplayersoffpic);
        ImageView twoplayerson = new ImageView(twoplayersonpic);
        ImageView training = new ImageView(trainingpic);
        ImageView options = new ImageView(optionspic);
        ImageView exit = new ImageView(exitpic);
        ImageView back = new ImageView(backpic);
        ImageView fight = new ImageView(fightpic);
        oneplayer.setViewport(new Rectangle2D(x, y1, widthb, heightb));
        twoplayersoff.setViewport(new Rectangle2D(x, y2, widthb, heightb));
        twoplayerson.setViewport(new Rectangle2D(x, y3, widthb, heightb));
        training.setViewport(new Rectangle2D(x, y4, widthb, heightb));
        options.setViewport(new Rectangle2D(x, y5, widthb, heightb));
        exit.setViewport(new Rectangle2D(x, y6, widthb, heightb));
        back.setViewport(new Rectangle2D(x, y7, 200, heightb));
        fight.setViewport(new Rectangle2D(x, y8, 230, heightb));
        logo.setFitHeight(300);
        logo.setFitWidth(472);
        oneplayer.setFitHeight(50);
        oneplayer.setFitWidth(500);
        twoplayersoff.setFitHeight(50);
        twoplayersoff.setFitWidth(500);
        twoplayerson.setFitHeight(50);
        twoplayerson.setFitWidth(500);
        training.setFitHeight(50);
        training.setFitWidth(500);
        options.setFitHeight(50);
        options.setFitWidth(500);
        exit.setFitHeight(50);
        exit.setFitWidth(500);
        back.setFitHeight(50);
        back.setFitWidth(100);
        fight.setFitHeight(50);
        fight.setFitWidth(115);
        logo.setTranslateX(264);
        logo.setTranslateY(25);
        oneplayer.setTranslateX(250);
        oneplayer.setTranslateY(350);
        twoplayersoff.setTranslateX(250);
        twoplayersoff.setTranslateY(400);
        twoplayerson.setTranslateX(250);
        twoplayerson.setTranslateY(450);
        if (!serverStatus) {
            twoplayerson.setDisable(true);
            twoplayerson.setOpacity(0.5);
        }
        training.setTranslateX(250);
        training.setTranslateY(500);
        options.setTranslateX(250);
        options.setTranslateY(550);
        exit.setTranslateX(250);
        exit.setTranslateY(600);
        back.setTranslateX(75);
        back.setTranslateY(600);
        fight.setTranslateX(810);
        fight.setTranslateY(600);
        back.setVisible(false);
        back.setDisable(true);
        fight.setVisible(false);
        fight.setDisable(true);
        background.toBack();
        main.getChildren().addAll(logo, oneplayer, twoplayersoff, twoplayerson, training, options, exit);
        root.getChildren().addAll(background, main, arenasroot, loadingroot, optionsroot, back, fight);

        final Animation animation1 = new Transition() {
            {
                setCycleDuration(Duration.millis(1000));
            }

            @Override
            protected void interpolate(double frac) {
                if (y1 <= 300)
                    y1 += 100;
                oneplayer.setViewport(new Rectangle2D(x, y1, widthb, heightb));
            }

        };
        final Animation animation2 = new Transition() {
            {
                setCycleDuration(Duration.millis(1000));
            }

            @Override
            protected void interpolate(double frac) {
                if (y2 <= 300)
                    y2 += 100;
                twoplayersoff.setViewport(new Rectangle2D(x, y2, widthb, heightb));
            }

        };
        final Animation animation3 = new Transition() {
            {
                setCycleDuration(Duration.millis(1000));
            }

            @Override
            protected void interpolate(double frac) {
                if (y3 <= 300)
                    y3 += 100;
                twoplayerson.setViewport(new Rectangle2D(x, y3, widthb, heightb));
            }

        };
        final Animation animation4 = new Transition() {
            {
                setCycleDuration(Duration.millis(1000));
            }

            @Override
            protected void interpolate(double frac) {
                if (y4 <= 300)
                    y4 += 100;
                training.setViewport(new Rectangle2D(x, y4, widthb, heightb));
            }

        };
        final Animation animation5 = new Transition() {
            {
                setCycleDuration(Duration.millis(1000));
            }

            @Override
            protected void interpolate(double frac) {
                if (y5 <= 300)
                    y5 += 100;
                options.setViewport(new Rectangle2D(x, y5, widthb, heightb));
            }

        };
        final Animation animation6 = new Transition() {
            {
                setCycleDuration(Duration.millis(1000));
            }

            @Override
            protected void interpolate(double frac) {
                if (y6 <= 300)
                    y6 += 100;
                exit.setViewport(new Rectangle2D(x, y6, widthb, heightb));
            }

        };
        final Animation animation7 = new Transition() {
            {
                setCycleDuration(Duration.millis(1000));
            }

            @Override
            protected void interpolate(double frac) {
                if (y7 <= 300)
                    y7 += 100;
                back.setViewport(new Rectangle2D(x, y7, 200, heightb));
            }

        };
        final Animation animation8 = new Transition() {
            {
                setCycleDuration(Duration.millis(1000));
            }

            @Override
            protected void interpolate(double frac) {
                if (y8 <= 300)
                    y8 += 100;
                fight.setViewport(new Rectangle2D(x, y8, 230, heightb));
            }

        };

        oneplayer.setOnMouseEntered((MouseEvent event) -> {

            animation1.cycleCountProperty().set(1);
            animation1.play();

        });
        twoplayersoff.setOnMouseEntered((MouseEvent event) -> {

            animation2.cycleCountProperty().set(1);
            animation2.play();

        });
        twoplayerson.setOnMouseEntered((MouseEvent event) -> {

            animation3.cycleCountProperty().set(1);
            animation3.play();

        });
        training.setOnMouseEntered((MouseEvent event) -> {

            animation4.cycleCountProperty().set(1);
            animation4.play();

        });
        options.setOnMouseEntered((MouseEvent event) -> {

            animation5.cycleCountProperty().set(1);
            animation5.play();

        });
        exit.setOnMouseEntered((MouseEvent event) -> {

            animation6.cycleCountProperty().set(1);
            animation6.play();

        });
        back.setOnMouseEntered((MouseEvent event) -> {

            animation7.cycleCountProperty().set(1);
            animation7.play();

        });
        fight.setOnMouseEntered((MouseEvent event) -> {

            animation8.cycleCountProperty().set(1);
            animation8.play();

        });
        oneplayer.setOnMouseExited((MouseEvent event) -> {

            animation1.stop();
            y1 = 0;
            oneplayer.setViewport(new Rectangle2D(x, y1, widthb, heightb));

        });
        twoplayersoff.setOnMouseExited((MouseEvent event) -> {

            animation2.stop();
            y2 = 0;
            twoplayersoff.setViewport(new Rectangle2D(x, y2, widthb, heightb));

        });
        twoplayerson.setOnMouseExited((MouseEvent event) -> {

            animation3.stop();
            y3 = 0;
            twoplayerson.setViewport(new Rectangle2D(x, y3, widthb, heightb));

        });
        training.setOnMouseExited((MouseEvent event) -> {

            animation4.stop();
            y4 = 0;
            training.setViewport(new Rectangle2D(x, y4, widthb, heightb));

        });
        options.setOnMouseExited((MouseEvent event) -> {

            animation5.stop();
            y5 = 0;
            options.setViewport(new Rectangle2D(x, y5, widthb, heightb));

        });
        exit.setOnMouseExited((MouseEvent event) -> {

            animation6.stop();
            y6 = 0;
            exit.setViewport(new Rectangle2D(x, y6, widthb, heightb));

        });
        back.setOnMouseExited((MouseEvent event) -> {

            animation7.stop();
            y7 = 0;
            back.setViewport(new Rectangle2D(x, y7, 200, heightb));

        });
        fight.setOnMouseExited((MouseEvent event) -> {

            animation8.stop();
            y8 = 0;
            fight.setViewport(new Rectangle2D(x, y8, 230, heightb));

        });

        oneplayer.setOnMouseClicked((MouseEvent event) -> {

            mode = "oneplayer";
            animation1.stop();
            y1 = 0;
            oneplayer.setViewport(new Rectangle2D(x, y1, widthb, heightb));
            main.setDisable(true);
            FadeTransition ft = new FadeTransition(Duration.millis(1000), main);
            ft.setFromValue(1.0);
            ft.setToValue(0);
            ft.setCycleCount(1);
            ft.play();
            ft.setOnFinished(event1 -> {
                main.toBack();
                arenasroot.toFront();
                FadeTransition ft1 = new FadeTransition(Duration.millis(1000), arenasroot);
                ft1.setFromValue(0);
                ft1.setToValue(1.0);
                ft1.setCycleCount(1);
                ft1.play();
                fight.setVisible(true);
                fight.setDisable(false);
                arenasroot.setDisable(false);
                back.setVisible(true);
                back.setDisable(false);
            });

        });
        twoplayersoff.setOnMouseClicked((MouseEvent event) -> {

            mode = "twoplayersoff";
            animation2.stop();
            y2 = 0;
            twoplayersoff.setViewport(new Rectangle2D(x, y2, widthb, heightb));
            main.setDisable(true);
            FadeTransition ft = new FadeTransition(Duration.millis(1000), main);
            ft.setFromValue(1.0);
            ft.setToValue(0);
            ft.setCycleCount(1);
            ft.play();
            ft.setOnFinished(event1 -> {
                FadeTransition ft1 = new FadeTransition(Duration.millis(1000), arenasroot);
                ft1.setFromValue(0);
                ft1.setToValue(1.0);
                ft1.setCycleCount(1);
                ft1.play();
                fight.setVisible(true);
                fight.setDisable(false);
                arenasroot.setDisable(false);
                back.setVisible(true);
                back.setDisable(false);
            });

        });
        twoplayerson.setOnMouseClicked((MouseEvent event) -> {
            loading.progress.setVisible(false);
            mode = "twoplayerson";
            animation3.stop();
            y3 = 0;
            twoplayerson.setViewport(new Rectangle2D(x, y3, widthb, heightb));
            isGame = true;
            game = new MortalKombat();
            game.arena = new Image(MortalKombat.class.getResource("/levels/arenaon.jpg").toString());
            game.ariv.setImage(game.arena);
            game.sfxv = Float.parseFloat(opt.sfxvp.getText()) / 100;
            game.musicv = Float.parseFloat(opt.musicvp.getText()) / 100;
            try {

                in = new DataInputStream(socket.getInputStream());
                out = new DataOutputStream(socket.getOutputStream());
                isReceiving = true;
                //start a thread which will start listening for messages
                new ReceiveMessage(in).start();
                // send the name to the server!
                if (ReceiveNumberOfPlayers() == 0) {
                    game.name = "player1";
                    out.writeUTF(game.name);
                    System.out.println("&" + game.name);
                } else {
                    game.name = "player2";
                    out.writeUTF(game.name);
                    System.out.println("&" + game.name);
                }
            } catch (IOException ex) {
                if (!socket.isClosed()) {
                    try {
                        socket.close();
                    } catch (IOException ex1) {
                    }
                }
            }
            game.setDisable(false);
            game.setOpacity(0);
            game.setTranslateX(0);
            loadingroot.setOpacity(0);
            FadeTransition ft = new FadeTransition(Duration.millis(1000), main);
            ft.setFromValue(1.0);
            ft.setToValue(0);
            ft.setCycleCount(1);
            ft.play();
            ft.setOnFinished(event1 -> {
                loadingroot.toFront();
                FadeTransition ft1 = new FadeTransition(Duration.millis(1000), loadingroot);
                ft1.setFromValue(0);
                ft1.setToValue(1.0);
                ft1.setCycleCount(1);
                ft1.play();
                ft1.setOnFinished(event2 -> {
                    load = new Thread(() -> {
                        final Timeline timeline = new Timeline();
                        timeline.setCycleCount(-1);
                        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(1000), new KeyValue(loading.logo.rotateProperty(), 360)));
                        timeline.play();
                        try {
                            if (game.name.equals("player2")) {
                                out.writeUTF("start 2");
                                game.isStartOnline = true;
                                game.isGameOn = true;
                                System.out.println("start");
                            } else {
                                while (!game.isStartOnline) {
                                    System.out.println("...");
                                }
                                System.out.println("start");
                            }
                        } catch (IOException ex) {
                        }
                        loadingroot.setOpacity(0);
                        FadeTransition ft2 = new FadeTransition(Duration.millis(1000), game);
                        ft2.setFromValue(0);
                        ft2.setToValue(1.0);
                        ft2.setCycleCount(1);
                        ft2.play();
                        timeline.stop();
                    });
                    load.start();
                });
            });

            root.getChildren().add(game);
            game.toFront();
        });
        training.setOnMouseClicked((MouseEvent event) -> {

            mode = "training";
            animation4.stop();
            y4 = 0;
            training.setViewport(new Rectangle2D(x, y4, widthb, heightb));
            main.setDisable(true);
            FadeTransition ft = new FadeTransition(Duration.millis(1000), main);
            ft.setFromValue(1.0);
            ft.setToValue(0);
            ft.setCycleCount(1);
            ft.play();
            ft.setOnFinished(event1 -> {
                FadeTransition ft1 = new FadeTransition(Duration.millis(1000), arenasroot);
                ft1.setFromValue(0);
                ft1.setToValue(1.0);
                ft1.setCycleCount(1);
                ft1.play();
                fight.setVisible(true);
                fight.setDisable(false);
                arenasroot.setDisable(false);
                back.setVisible(true);
                back.setDisable(false);
            });

        });
        options.setOnMouseClicked((MouseEvent event) -> {

            animation5.stop();
            y5 = 0;
            options.setViewport(new Rectangle2D(x, y5, widthb, heightb));
            main.setDisable(true);
            FadeTransition ft = new FadeTransition(Duration.millis(1000), main);
            ft.setFromValue(1.0);
            ft.setToValue(0);
            ft.setCycleCount(1);
            ft.play();
            ft.setOnFinished(event1 -> {
                FadeTransition ft1 = new FadeTransition(Duration.millis(1000), optionsroot);
                ft1.setFromValue(0);
                ft1.setToValue(1.0);
                ft1.setCycleCount(1);
                ft1.play();
                optionsroot.setDisable(false);
                back.setVisible(true);
                back.setDisable(false);
            });

        });
        exit.setOnMouseClicked((MouseEvent event) -> {

            animation6.stop();
            primaryStage.close();
            /*FadeTransition ft = new FadeTransition(Duration.millis(1000), main);
            ft.setFromValue(1.0);
            ft.setToValue(0);
            ft.setCycleCount(1);
            ft.play();*/

        });
        back.setOnMouseClicked((MouseEvent event) -> {

            animation7.stop();
            y7 = 0;
            back.setViewport(new Rectangle2D(x, y7, 200, heightb));
            back.setVisible(false);
            back.setDisable(true);
            fight.setDisable(true);
            fight.setVisible(false);
            FadeTransition ft = new FadeTransition(Duration.millis(1000));
            if (optionsroot.getOpacity() > 0) {
                ft.setNode(optionsroot);
            } else if (arenasroot.getOpacity() > 0) {
                ft.setNode(arenasroot);
            }
            ft.setFromValue(1.0);
            ft.setToValue(0);
            ft.setCycleCount(1);
            ft.play();
            ft.setOnFinished(event1 -> {
                main.toFront();
                FadeTransition ft1 = new FadeTransition(Duration.millis(1000), main);
                ft1.setFromValue(0);
                ft1.setToValue(1.0);
                ft1.setCycleCount(1);
                ft1.play();
                if (!optionsroot.isDisabled()) {
                    optionsroot.setDisable(true);
                } else if (!arenasroot.isDisabled()) {
                    arenasroot.setDisable(true);
                }
                main.setDisable(false);
            });

        });
        fight.setOnMouseClicked((MouseEvent event) -> {
            fight.setViewport(new Rectangle2D(x, y8, 230, heightb));
            fight.setDisable(true);
            fight.setVisible(false);
            back.setDisable(true);
            back.setVisible(false);
            chosen = arenas.chosen;
            isGame = true;
            arenasroot.setDisable(true);
            game = new MortalKombat();
            switch (mode) {
                case "oneplayer":
                    game.isBotIn = true;
                    game.start2.setVisible(true);
                    break;
                case "twoplayersoff":
                    game.start2.setVisible(false);
                    break;
                case "training":
                    game.isTraining = true;
                    game.health2.setVisible(false);
                    game.com.setVisible(false);
                    game.player2.setOpacity(0);
                    game.player2.setVisible(false);
                    break;
            }
            switch (chosen) {
                case 0:
                    game.arena = new Image(MortalKombat.class.getResource("/levels/arena1.jpg").toString());
                    break;
                case 1:
                    game.arena = new Image(MortalKombat.class.getResource("/levels/arena2.jpg").toString());
                    break;
                case 2:
                    game.arena = new Image(MortalKombat.class.getResource("/levels/arena3.jpg").toString());
                    break;
                case 3:
                    game.arena = new Image(MortalKombat.class.getResource("/levels/arena4.jpg").toString());
                    break;
                case 4:
                    game.arena = new Image(MortalKombat.class.getResource("/levels/arena5.jpg").toString());
                    break;
            }
            game.ariv.setImage(game.arena);
            game.sfxv = Float.parseFloat(opt.sfxvp.getText()) / 100;
            game.musicv = Float.parseFloat(opt.musicvp.getText()) / 100;
            game.setDisable(false);
            game.setOpacity(0);
            game.setTranslateX(0);
            loadingroot.setOpacity(0);
            animation8.stop();
            y1 = 0;
            fight.setViewport(new Rectangle2D(x, y8, 230, heightb));
            main.toBack();
            FadeTransition ft = new FadeTransition(Duration.millis(1000), arenasroot);
            ft.setFromValue(1.0);
            ft.setToValue(0);
            ft.setCycleCount(1);
            ft.play();
            ft.setOnFinished(event1 -> {
                loadingroot.toFront();
                FadeTransition ft1 = new FadeTransition(Duration.millis(1000), loadingroot);
                ft1.setFromValue(0);
                ft1.setToValue(1.0);
                ft1.setCycleCount(1);
                ft1.play();
                ft1.setOnFinished(event2 -> {
                    final Timeline timeline = new Timeline();
                    timeline.setCycleCount(-1);
                    timeline.getKeyFrames().add(new KeyFrame(Duration.millis(1000),
                            new KeyValue(loading.logo.rotateProperty(), 360)));
                    timeline.play();
                    startLoading = System.currentTimeMillis();
                    load = new Thread(() -> {
                        while (!loadDone) {
                            endLoading = System.currentTimeMillis();
                            loadingDelta = endLoading - startLoading;
                            elapsedTime = loadingDelta / 1000.0;
                            if (elapsedTime >= 3.0) {
                                loadDone = true;
                                break;
                            } else if (elapsedTime % 1.0 == 0.0) {
                                loading.progress.setProgress(loading.progress.getProgress() + 0.33);
                                if (loading.progress.getProgress() > 0.9) {
                                    loading.progress.setProgress(1.0);
                                }
                            }
                        }
                        loadingroot.setOpacity(0);
                        game.isGameOn = true;
                        FadeTransition ft2 = new FadeTransition(Duration.millis(1000), game);
                        ft2.setFromValue(0);
                        ft2.setToValue(1.0);
                        ft2.setCycleCount(1);
                        ft2.play();
                        timeline.stop();
                    });
                    load.start();
                });
            });
            root.getChildren().add(game);
            game.toFront();
        });

        scene.setOnKeyPressed(event -> {
            KeyCode k = event.getCode();
            if (isGame && game.isStart && !game.isEnd && game.elapsedSeconds < 5) {
                switch (k) {
                    case W:
                        if (!game.isJumping1 && !game.isJumpingP1 && game.isOnGround1 && !game.isPunching1 && !game.isKicking1 && !game.isDead1 && (!game.isOnline || game.name.equals("player1"))) {
                            if (game.isOnline) {
                                try {
                                    out.writeUTF("up");
                                    System.out.println("up");
                                } catch (IOException ex) {
                                }
                            }
                            if (!game.isMovingR1 && !game.isMovingL1) {
                                game.y = 420;
                                game.x = 0;
                                game.isJumping1 = true;
                            } else {
                                game.y = 560;
                                if (game.isMovingR1) {
                                    if (game.isOnline) {
                                        try {
                                            out.writeUTF("upR");
                                            System.out.println("upR");
                                        } catch (IOException ex) {
                                        }
                                    }
                                    game.isJumpingR1 = true;
                                    if (!game.p1r) {
                                        game.x = 864;
                                    } else {
                                        game.x = 0;
                                    }
                                } else {
                                    if (game.isOnline) {
                                        try {
                                            out.writeUTF("upL");
                                            System.out.println("upL");
                                        } catch (IOException ex) {
                                        }
                                    }
                                    game.isJumpingL1 = true;
                                    if (!game.p1r) {
                                        game.x = 0;
                                    } else {
                                        game.x = 864;
                                    }
                                }
                                game.isJumpingP1 = true;
                            }
                            game.isSat1 = false;
                            game.isSitting1 = false;
                            game.isUp1 = true;
                            game.isOnGround1 = false;
                            AudioClip sound = new AudioClip(game.jumppath);
                            sound.setVolume(Integer.parseInt(opt.sfxvp.getText()));
                            sound.setCycleCount(1);
                            sound.play();
                        }
                        break;
                    case A:
                        if (!game.isSitting1 && !game.isSat1 && game.isOnGround1 && !game.isPunching1 && !game.isKicking1 && !game.isDead1 && (!game.isOnline || game.name.equals("player1"))) {
                            if (game.isOnline) {
                                try {
                                    out.writeUTF("left");
                                    System.out.println("left");
                                } catch (IOException ex) {
                                }
                            }
                            game.y = 140;
                            if (game.p1r) {
                                if (!game.isMovingL1) {
                                    game.x = 768;
                                }
                            } else {
                                if (!game.isMovingL1) {
                                    game.x = 0;
                                }
                            }
                            game.isMovingL1 = true;
                            game.isMovingR1 = false;
                        }
                        break;
                    case S:
                        if (!game.isSitting1 && !game.isSat1 && !game.isJumping1 && !game.isPunching1 && !game.isKicking1 && !game.isJumpingP1 && game.isOnGround1 && !game.isDead1 && (!game.isOnline || game.name.equals("player1"))) {
                            if (game.isOnline) {
                                try {
                                    out.writeUTF("down");
                                    System.out.println("down");
                                } catch (IOException ex) {
                                }
                            }
                            game.y = 280;
                            game.x = 0;
                            game.isSitting1 = true;
                        }
                        break;
                    case D:
                        if (!game.isSitting1 && !game.isSat1 && game.isOnGround1 && !game.isPunching1 && !game.isKicking1 && !game.isDead1 && (!game.isOnline || game.name.equals("player1"))) {
                            if (game.isOnline) {
                                try {
                                    out.writeUTF("right");
                                    System.out.println("right");
                                } catch (IOException ex) {
                                }
                            }
                            game.y = 140;
                            if (game.p1r) {
                                if (!game.isMovingR1) {
                                    game.x = 0;
                                }
                            } else {
                                if (!game.isMovingR1) {
                                    game.x = 768;
                                }
                            }
                            game.isMovingR1 = true;
                            game.isMovingL1 = false;
                        }
                        break;
                    case G:
                        if (!game.isPunching1 && !game.isKicking1 && game.isOnGround1 && !game.isUp1 && !game.isDown1 && !game.isJumping1 && !game.isJumpingP1 && !game.hit2 && !game.isDead1 && (!game.isOnline || game.name.equals("player1"))) {
                            game.isMovingL1 = false;
                            game.isMovingR1 = false;
                            game.isSat1 = false;
                            game.isSitting1 = false;
                            game.isPunching1 = true;
                            game.count = 1;
                            game.y = 700;
                            game.x = 96;
                            AudioClip sound = new AudioClip(game.punchpath);
                            sound.setVolume(Integer.parseInt(opt.sfxvp.getText()));
                            sound.setCycleCount(1);
                            sound.play();
                        } else if (game.isPunching1 && game.count == 1 && !game.hit2 && (!game.isOnline || game.name.equals("player1"))) {
                            game.y = 700;
                            game.count = 2;
                            AudioClip sound = new AudioClip(game.punchpath);
                            sound.setVolume(Integer.parseInt(opt.sfxvp.getText()));
                            sound.setCycleCount(1);
                            sound.play();
                        }
                        if (game.count == 1) {
                            if (game.isOnline) {
                                try {
                                    out.writeUTF("punch 1");
                                    System.out.println("punch 1");
                                } catch (IOException ex) {
                                }
                            }
                        } else if (game.count == 2) {
                            if (game.isOnline) {
                                try {
                                    out.writeUTF("punch 2");
                                    System.out.println("punch 2");
                                } catch (IOException ex) {
                                }
                            }
                        }
                        break;
                    case T:
                        if (!game.isPunching1 && !game.isKicking1 && game.isOnGround1 && !game.isUp1 && !game.isDown1 && !game.isJumping1 && !game.isJumpingP1 && !game.hit2 && !game.isDead1 && (!game.isOnline || game.name.equals("player1"))) {
                            if (game.isOnline) {
                                try {
                                    out.writeUTF("kick");
                                    System.out.println("kick");
                                } catch (IOException ex) {
                                }
                            }
                            game.isMovingL1 = false;
                            game.isMovingR1 = false;
                            game.isSat1 = false;
                            game.isSitting1 = false;
                            game.isKicking1 = true;
                            game.x = 0;
                            AudioClip sound = new AudioClip(game.kickpath);
                            sound.setVolume(Integer.parseInt(opt.sfxvp.getText()));
                            sound.setCycleCount(1);
                            sound.play();
                        }
                        break;
                    case UP:
                        if (!mode.equals("training") && !game.isJumping2 && !game.isJumpingP2 && game.isOnGround2 && !game.isPunching2 && !game.isKicking2 && !game.isDead2 && !game.isBotIn && (!game.isOnline || game.name.equals("player2"))) {
                            if (game.isOnline) {
                                try {
                                    out.writeUTF("up");
                                    System.out.println("up");
                                } catch (IOException ex) {
                                }
                            }
                            if (!game.isMovingR2 && !game.isMovingL2) {
                                game.y2 = 420;
                                game.x2 = 0;
                                game.isJumping2 = true;
                            } else {
                                game.y2 = 560;
                                if (game.isMovingR2) {
                                    game.isJumpingR2 = true;
                                    try {
                                        out.writeUTF("upR");
                                    } catch (IOException ex) {
                                    }
                                    if (game.p1r) {
                                        game.x2 = 864;
                                    } else {
                                        game.x2 = 0;
                                    }
                                } else {
                                    game.isJumpingL2 = true;
                                    if (game.isOnline) {
                                        try {
                                            out.writeUTF("upL");
                                        } catch (IOException ex) {
                                        }
                                    }
                                    if (game.p1r) {
                                        game.x2 = 0;
                                    } else {
                                        game.x2 = 864;
                                    }
                                }
                                game.isJumpingP2 = true;
                            }
                            game.isSat2 = false;
                            game.isSitting2 = false;
                            game.isUp2 = true;
                            game.isOnGround2 = false;
                            AudioClip sound = new AudioClip(game.jumppath);
                            sound.setVolume(Integer.parseInt(opt.sfxvp.getText()));
                            sound.setCycleCount(1);
                            sound.play();
                        }
                        break;
                    case LEFT:
                        if (!mode.equals("training") && !game.isSitting2 && !game.isSat2 && !game.isPunching2 && !game.isKicking2 && game.isOnGround2 && !game.isDead2 && !game.isBotIn && (!game.isOnline || game.name.equals("player2"))) {
                            if (game.isOnline) {
                                try {
                                    out.writeUTF("left");
                                    System.out.println("left");
                                } catch (IOException ex) {
                                }
                            }
                            game.y2 = 140;
                            if (game.p1r) {
                                if (!game.isMovingL2) {
                                    game.x2 = 768;
                                }
                            } else {
                                if (!game.isMovingL2) {
                                    game.x2 = 0;
                                }
                            }
                            game.isMovingL2 = true;
                            game.isMovingR2 = false;
                        }
                        break;
                    case DOWN:
                        if (!mode.equals("training") && !game.isSitting2 && !game.isSat2 && !game.isJumping2 && !game.isJumpingP2 && game.isOnGround2 && !game.isPunching2 && !game.isKicking2 && !game.isDead2 && !game.isBotIn && (!game.isOnline || game.name.equals("player2"))) {
                            if (game.isOnline) {
                                try {
                                    out.writeUTF("down");
                                    System.out.println("down");
                                } catch (IOException ex) {
                                }
                            }
                            game.y2 = 280;
                            game.x2 = 0;
                            game.isSitting2 = true;
                        }
                        break;
                    case RIGHT:
                        if (!mode.equals("training") && !game.isSitting2 && !game.isSat2 && game.isOnGround2 && !game.isDead2 && !game.isPunching2 && !game.isKicking2 && !game.isBotIn && (!game.isOnline || game.name.equals("player2"))) {
                            if (game.isOnline) {
                                try {
                                    out.writeUTF("right");
                                    System.out.println("right");
                                } catch (IOException ex) {
                                }
                            }
                            game.y2 = 140;
                            if (game.p1r) {
                                if (!game.isMovingR2) {
                                    game.x2 = 0;
                                }
                            } else {
                                if (!game.isMovingR2) {
                                    game.x2 = 768;
                                }
                            }
                            game.isMovingR2 = true;
                            game.isMovingL2 = false;
                        }
                        break;
                    case K:
                        if (!mode.equals("training") && !game.isPunching2 && !game.isKicking2 && game.isOnGround2 && !game.isUp2 && !game.isDown2 && !game.isJumping2 && !game.isJumpingP2 && !game.hit1 && !game.isDead2 && (!game.isOnline || game.name.equals("player2"))) {
                            game.isMovingL2 = false;
                            game.isMovingR2 = false;
                            game.isSat2 = false;
                            game.isSitting2 = false;
                            game.isPunching2 = true;
                            game.count2 = 1;
                            game.y2 = 700;
                            game.x2 = 96;
                            AudioClip sound = new AudioClip(game.punchpath);
                            sound.setVolume(Integer.parseInt(opt.sfxvp.getText()));
                            sound.setCycleCount(1);
                            sound.play();
                        } else if (!mode.equals("training") && game.isPunching2 && game.count2 == 1 && !game.hit1 && (!game.isOnline || game.name.equals("player2"))) {
                            game.y2 = 700;
                            game.count2 = 2;
                            AudioClip sound = new AudioClip(game.punchpath);
                            sound.setVolume(Integer.parseInt(opt.sfxvp.getText()));
                            sound.setCycleCount(1);
                            sound.play();
                        }
                        if (!mode.equals("training") && game.count2 == 1) {
                            if (game.isOnline) {
                                try {
                                    out.writeUTF("punch 1");
                                    System.out.println("punch 1");
                                } catch (IOException ex) {
                                }
                            }
                        } else if (!mode.equals("training") && game.count2 == 2) {
                            if (game.isOnline) {
                                try {
                                    out.writeUTF("punch 2");
                                    System.out.println("punch 2");
                                } catch (IOException ex) {
                                }
                            }
                        }
                        break;
                    case I:
                        if (!mode.equals("training") && !game.isPunching2 && !game.isKicking2 && game.isOnGround2 && !game.isUp2 && !game.isDown2 && !game.isJumping2 && !game.isJumpingP2 && !game.hit1 && !game.isDead2 && (!game.isOnline || game.name.equals("player2"))) {
                            if (game.isOnline) {
                                try {
                                    out.writeUTF("kick");
                                    System.out.println("kick");
                                } catch (IOException ex) {
                                }
                            }
                            game.isMovingL2 = false;
                            game.isMovingR2 = false;
                            game.isSat2 = false;
                            game.isSitting2 = false;
                            game.isKicking2 = true;
                            game.x2 = 0;
                            AudioClip sound = new AudioClip(game.kickpath);
                            sound.setVolume(Integer.parseInt(opt.sfxvp.getText()));
                            sound.setCycleCount(1);
                            sound.play();
                        }
                        break;
                    case ENTER:
                        if (game.isBotIn) {
                            game.img2.setTranslateY(370);
                            game.isBotIn = false;
                            game.isMovingL2 = false;
                            game.isMovingR2 = false;
                            game.isOnGround2 = true;
                            game.isSitting2 = false;
                            game.isSat2 = false;
                            game.isJumping2 = false;
                            game.isJumpingP2 = false;
                            game.isJumpingR2 = false;
                            game.isJumpingL2 = false;
                            game.isUp2 = false;
                            game.isDown2 = false;
                            game.isPunching2 = false;
                            game.isKicking2 = false;
                            game.start2.setVisible(false);
                            game.com.setVisible(false);
                            game.player2.setVisible(true);
                        }
                        break;

                }
            }
        });

        scene.setOnKeyReleased(event -> {
            KeyCode k = event.getCode();
            if (isGame) {
                switch (k) {
                    case D:
                        if (game.isOnline && game.name.equals("player1") && !game.isJumpingP1) {
                            try {
                                out.writeUTF("rightStop " + game.img1.getTranslateX() + " " + game.img1.getTranslateY());
                                System.out.println("rightStop " + game.img1.getTranslateX() + " " + game.img1.getTranslateY());
                            } catch (IOException ex) {
                            }
                        }
                        if (game.isMovingR1 && game.isOnGround1) {
                            game.isMovingR1 = false;
                            game.x = 0;
                            game.y = 0;
                        }
                        break;
                    case A:
                        if (game.isOnline && game.name.equals("player1") && !game.isJumpingP1) {
                            try {
                                out.writeUTF("leftStop " + game.img1.getTranslateX() + " " + game.img1.getTranslateY());
                                System.out.println("leftStop " + game.img1.getTranslateX() + " " + game.img1.getTranslateY());
                            } catch (IOException ex) {
                            }
                        }
                        if (game.isMovingL1 && game.isOnGround1) {
                            game.isMovingL1 = false;
                            game.x = 0;
                            game.y = 0;
                        }
                        break;
                    case S:
                        if (game.isOnline && game.name.equals("player1")) {
                            try {
                                out.writeUTF("downStop " + game.img1.getTranslateX() + " " + game.img1.getTranslateY());
                                System.out.println("downStop " + game.img1.getTranslateX() + " " + game.img1.getTranslateY());
                            } catch (IOException ex) {
                            }
                        }
                        if (game.isSitting1) {
                            game.isSitting1 = false;
                        }
                        break;
                    case RIGHT:
                        if (game.isOnline && game.name.equals("player2")) {
                            try {
                                out.writeUTF("rightStop " + game.img2.getTranslateX() + " " + game.img2.getTranslateY());
                                System.out.println("rightStop " + game.img2.getTranslateX() + " " + game.img2.getTranslateY());
                            } catch (IOException ex) {
                            }
                        }
                        if (game.isMovingR2 && game.isOnGround2) {
                            game.isMovingR2 = false;
                            game.x2 = 0;
                            game.y2 = 0;
                        }
                        break;
                    case LEFT:
                        if (game.isOnline && game.name.equals("player2")) {
                            try {
                                out.writeUTF("leftStop " + game.img2.getTranslateX() + " " + game.img2.getTranslateY());
                                System.out.println("leftStop " + game.img2.getTranslateX() + " " + game.img2.getTranslateY());
                            } catch (IOException ex) {
                            }
                        }
                        if (game.isMovingL2 && game.isOnGround2) {
                            game.isMovingL2 = false;
                            game.x2 = 0;
                            game.y2 = 0;
                        }
                        break;
                    case DOWN:
                        if (game.isOnline && game.name.equals("player2")) {
                            try {
                                out.writeUTF("downStop " + game.img2.getTranslateX() + " " + game.img2.getTranslateY());
                                System.out.println("downStop " + game.img2.getTranslateX() + " " + game.img2.getTranslateY());
                            } catch (IOException ex) {
                            }
                        }
                        if (game.isSitting2) {
                            game.isSitting2 = false;
                        }
                        break;
                    case ESCAPE:
                        if ((game.isGameOn && game.isEnd) || mode.equals("training")) {
                            mode = "";
                            game.isGameOn = false;
                            FadeTransition ft = new FadeTransition(Duration.millis(1000), game);
                            ft.setFromValue(1.0);
                            ft.setToValue(0);
                            ft.setCycleCount(1);
                            ft.play();
                            ft.setOnFinished(event1 -> {
                                isGame = false;
                                game.threadDone = true;
                                root.getChildren().remove(game);
                                FadeTransition ft1 = new FadeTransition(Duration.millis(1000), main);
                                main.toFront();
                                ft1.setFromValue(0);
                                ft1.setToValue(1.0);
                                ft1.setCycleCount(1);
                                ft1.play();
                                main.setDisable(false);
                            });
                        }
                        break;
                }
            }
        });

        primaryStage.getIcons().add(iconpic);
        primaryStage.setTitle("SDU FIGHTERS");
        primaryStage.setScene(scene);
        primaryStage.isAlwaysOnTop();
        primaryStage.setOnCloseRequest(value -> {
            try {
                game.shutdown();
                loadDone = true;
                isReceiving = false;
                in.close();
                out.close();
                socket.close();
            } catch (NullPointerException | IOException ex) {
            }
        });
        primaryStage.setMinHeight(745);
        primaryStage.setMinWidth(1015);
        primaryStage.setMaxHeight(745);
        primaryStage.setMaxWidth(1015);
        primaryStage.show();
    }

    public static void main(String[] args) throws IOException {
        launch(args);
    }

    private int ReceiveNumberOfPlayers() throws IOException {
        int countOfP = 0;
        DataInputStream in1 = new DataInputStream(socket.getInputStream());
        String count;
        try {
            count = in1.readUTF();
            countOfP = Integer.parseInt(count);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return countOfP;
    }

    public class ReceiveMessage extends Thread {

        final DataInputStream in;

        ReceiveMessage(DataInputStream in) {
            this.in = in;
        }

        public void run() {
            String message;
            while (isReceiving) {
                try {
                    message = in.readUTF();
                    String[] mess = message.split(" ");
                    switch (mess[0]) {
                        case "start":
                            game.isStartOnline = true;
                            game.isGameOn = true;
                            break;
                        case "up":
                            game.jump();
                            break;
                        case "down":
                            game.sit();
                            break;
                        case "left":
                            game.moveL();
                            break;
                        case "right":
                            game.moveR();
                            break;
                        case "downStop":
                            game.stand(mess[1], mess[2]);
                            break;
                        case "leftStop":
                            game.stopL(mess[1], mess[2]);
                            break;
                        case "rightStop":
                            game.stopR(mess[1], mess[2]);
                            break;
                        case "punch":
                            game.punch(mess[1]);
                            break;
                        case "kick":
                            game.kick();
                            break;
                        case "upL":
                            if (game.name.equals("player1")) {
                                game.isMovingL2 = true;
                                game.jump();
                            } else {
                                game.isMovingL1 = true;
                                game.jump();
                            }
                            break;
                        case "upR":
                            if (game.name.equals("player1")) {
                                game.isMovingR2 = true;
                                game.jump();
                            } else {
                                game.isMovingR1 = true;
                                game.jump();
                            }
                            break;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    public MainMenu() {
        initializeImages();
    }

    private void initializeImages() {
        iconpic = new Image(getClass().getResource("/icon.png").toString());
        backgroundpic = new Image(getClass().getResource("/background.jpg").toString());
        logopic = new Image(getClass().getResource("/ui/logo.png").toString());
        oneplayerpic = new Image(getClass().getResource("/ui/oneplayer.png").toString());
        twoplayersoffpic = new Image(getClass().getResource("/ui/twoplayersoff.png").toString());
        twoplayersonpic = new Image(getClass().getResource("/ui/twoplayerson.png").toString());
        trainingpic = new Image(getClass().getResource("/ui/training.png").toString());
        optionspic = new Image(getClass().getResource("/ui/options.png").toString());
        exitpic = new Image(getClass().getResource("/ui/exit.png").toString());
        backpic = new Image(getClass().getResource("/ui/back.png").toString());
        fightpic = new Image(getClass().getResource("/ui/fight.png").toString());
    }
}
