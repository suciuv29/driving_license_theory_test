package UI;

import Model.Chestionar;
import Model.Intrebare;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.control.TextArea;
import javafx.scene.control.CheckBox;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


/**
 * clasa Controller
 * Timer + situatia de pornire a chestionarului
 */
public class Controller {
    boolean chestionarActiv = false;
    int nIntreb = 1;
    int raspCorecte = 0;
    int raspGresite = 0;
    int ramase = 26;
    Scene activeScene;
    boolean processed = false;
    int secondsLeft = 60 * 30;
    Timer timer;
    String addZero(int n) {
        if (n <= 9) return "0";
        return "";
    }
    class RunTask extends TimerTask {
        public void run() {
            secondsLeft -= 1;
            Platform.runLater(new Runnable() {
                public void run() {
                    ((Label) activeScene.lookup("#timelabel")).setText(addZero(secondsLeft / 60) + (secondsLeft / 60) + ":" + addZero(secondsLeft / 60) + (secondsLeft % 60));
                }
            });
            if (secondsLeft == 0) {
                timer.cancel();
                Platform.runLater(new Runnable() {
                    public void run() {
                        Final();
                    }
                });
            }
        }
    }
    /**
     * metoda care creaaza chestionarul la inceput
     * @param actionEvent
     */
    public void creeazaChestionar(ActionEvent actionEvent) {
        DataTransfer.getService().createChestionar();
        Scene scene = ((Node) actionEvent.getSource()).getScene();
        ((Label)scene.lookup("#chestionarActiv")).setText("Exista chestionar activ!");
        chestionarActiv = true;
    }

    /**
     * incare o intrebare din lista
     * @param scene
     * @param i
     */
    public void incarcaIntrebare(Scene scene, int i) {
        Chestionar c = DataTransfer.getService().getChestionar();
        Intrebare actual = c.searchIntrebari(i);
        String intr = actual.getIntrebare();
        var rasp = actual.getRaspunsuri();
        for (int j=0;j<rasp.size();j++)
            if (rasp.get(j).charAt(0) == '*')
                rasp.set(j, rasp.get(j).substring(1));
        String r1 = rasp.get(0);
        String r2 = rasp.get(1);
        String r3 = rasp.get(2);
        String r4 = rasp.get(3);
        ((TextArea) scene.lookup("#intrebare")).setText(intr);
        ((CheckBox) scene.lookup("#r1")).setText(r1);
        ((CheckBox) scene.lookup("#r2")).setText(r2);
        ((CheckBox) scene.lookup("#r3")).setText(r3);
        ((CheckBox) scene.lookup("#r4")).setText(r4);
    }

    /**
     * verifica daca raspunsul intrebarii e corect
     * @param scene
     * @param i
     * @return
     */
    public boolean checkAnswer(Scene scene, int i) {
        Chestionar c = DataTransfer.getService().getChestionar();
        Intrebare actual = c.searchIntrebari(i);
        List<String> raspCorecte = actual.getRaspunsuriCorecte();
        List<CheckBox> list = new LinkedList<CheckBox>();
        list.add(((CheckBox) scene.lookup("#r1")));
        list.add(((CheckBox) scene.lookup("#r2")));
        list.add(((CheckBox) scene.lookup("#r3")));
        list.add(((CheckBox) scene.lookup("#r4")));
        int Count = 0;
        int Sem = 1;
        for (String s : raspCorecte) System.out.println(s);
        for (CheckBox check : list) {
            if (check.isSelected()) {
                System.out.println("-*" + check.getText());
                Count++;
                if (!raspCorecte.contains("*" + check.getText())) {
                    Sem = 0;
                    break;
                }
            }
        }
        if (Sem == 1 && raspCorecte.size() == Count)
            return true;
        return false;
    }

    /**
     * rezultatul final al chestionarului
     */
    public void Final() {
        new Alert(Alert.AlertType.INFORMATION, "Raspunsuri corecte: " + raspCorecte + "\n" + "Raspunsuri gresite: " + raspGresite).showAndWait();
        ((Stage) activeScene.getWindow()).close();
    }

    /**
     * porneste chestionarul
     * @param actionEvent
     * @throws IOException
     */
    public void startQuiz(ActionEvent actionEvent) throws IOException {
        if (chestionarActiv == true) {
            Parent root = FXMLLoader.load(getClass().getResource("questions.fxml"));
            Stage stage = new Stage();
            stage.setTitle("My app");
            stage.setScene(new Scene(root));
            stage.show();
            nIntreb = 1;
            raspGresite = 0;
            raspCorecte = 0;
            secondsLeft = 30 * 60;
            ramase = 26;
            processed = false;
            timer = new Timer();
            timer.schedule(new RunTask(), 1000, 1000);
            activeScene = stage.getScene();
            incarcaIntrebare(stage.getScene(), 1);
        } else {
            new Alert(Alert.AlertType.ERROR, "Nu exista chestionar activ!").showAndWait();
        }
    }

    /**
     * afiseaza daca raspunsul dat pe fiecare intrebare e corect sau gresit
     * @param actionEvent
     */
    public void trimiteRaspuns(ActionEvent actionEvent) {
        if (processed) return;
        activeScene = ((Node) actionEvent.getSource()).getScene();
        if (checkAnswer(activeScene, nIntreb)) {
            new Alert(Alert.AlertType.INFORMATION, "Raspuns corect!").show();
            raspCorecte++;
            ((Label) activeScene.lookup("#ccount")).setText(Integer.toString(raspCorecte));
        }
        else {
            if (raspGresite < 4)
            new Alert(Alert.AlertType.INFORMATION, "Raspuns gresit!").show();
            raspGresite++;
            ((Label) activeScene.lookup("#gcount")).setText(Integer.toString(raspGresite));
        }
        if (raspGresite == 5 || ramase == 0)
            Final();
        processed = true;
    }

    /**
     * face next la intrebarea urmatoare
     * @param actionEvent
     */
    public void urmatoareaIntrebare(ActionEvent actionEvent) {
        processed = false;
        nIntreb++;
        ramase--;
        ((Label) activeScene.lookup("#tcount")).setText(Integer.toString(ramase));
        activeScene = ((Node) actionEvent.getSource()).getScene();
        incarcaIntrebare(activeScene, nIntreb);
    }
}
