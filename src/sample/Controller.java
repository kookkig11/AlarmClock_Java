package sample;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.time.LocalDateTime;

public class Controller {
    private int day, year, hour, minute;
    private String dayWeek, month;
    private MediaPlayer mediaAlarm;
    private AlarmClock alarmClock;
    private AlarmClockManager alarmClockManager;
    private boolean isAlarm = false;
    private boolean stopAlarm = true;

    @FXML
    private Label clockLabel, currentDayLabel, clickAlarmLabel;
    @FXML
    private ComboBox addHourBox, addMinuteBox, addDayOfWeekBox;
    @FXML
    private TableView<AlarmClock> alarmTable;
    @FXML
    private TableColumn<AlarmClock, String> dayAlarmColumn, timeAlarmColumn;

    public void initialize(){
        //set clickAlarmButton invisible when isAlarm=false
        clickAlarmLabel.setVisible(false);

        //set alarm sound
        Media media = new Media(getClass().getResource("/Style/AlarmSound.mp3").toString());
        mediaAlarm = new MediaPlayer(media);
        mediaAlarm.setCycleCount(MediaPlayer.INDEFINITE);

        try{
            alarmClockManager = new AlarmClockManager();
        } catch (Exception e){
            System.out.println("File not found!!");
        }

        //show current day
        dayWeek = LocalDateTime.now().getDayOfWeek().toString();
        day = LocalDateTime.now().getDayOfMonth();
        month = LocalDateTime.now().getMonth().toString();
        year = LocalDateTime.now().getYear();
        currentDayLabel.setText(dayWeek + " " + day + "/" + month + "/" + year);
        currentDayLabel.setStyle("-fx-border-color: black; -fx-background-color: black");

        //show digital clock
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            hour = LocalDateTime.now().getHour();
            minute = LocalDateTime.now().getMinute();

            String setClockLabel;
            if (hour < 10) {
                if (minute < 10) {
                    setClockLabel = "0" + hour + ":0" + minute;
                } else {
                    setClockLabel = "0" + hour + ":" + minute;
                }
            } else {
                if (minute < 10) {
                    setClockLabel = hour + ":0" + minute;
                } else {
                    setClockLabel = hour + ":" + minute;
                }
            }
            checkClockToAlarm(dayWeek, hour, minute);

            //System.out.println(setClockLabel);
            clockLabel.setText(setClockLabel);
            clockLabel.setStyle("-fx-font-family: 'Courier New'; -fx-font-size: 64px");
        }),
                new KeyFrame(Duration.seconds(1))
        );
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();

        addHourBox.getSelectionModel().selectFirst();
        addMinuteBox.getSelectionModel().selectFirst();
        addDayOfWeekBox.getSelectionModel().selectFirst();
        //set choose day of week
        addDayOfWeekBox.getItems().addAll("MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY", "SUNDAY", "EVERYDAY");
        //set choose hour
        addHourBox.getItems().addAll("00", "01", "02", "03", "04", "05", "06", "07", "08", "09");
        for (int i = 10; i < 24; i++) {
            addHourBox.getItems().add(Integer.toString(i));
        }
        //set choose minute
        addMinuteBox.getItems().addAll("00", "01", "02", "03", "04", "05", "06", "07", "08", "09");
        for (int i = 10; i < 60; i++) {
            addMinuteBox.getItems().add(Integer.toString(i));
        }

        //set TableView
        dayAlarmColumn.setCellValueFactory(new PropertyValueFactory<>("dayOfWeekAlarm"));
        timeAlarmColumn.setCellValueFactory(new PropertyValueFactory<>("timeFormat"));
        updateSetTimeToAlarmTable();
    }

    public void updateSetTimeToAlarmTable(){
        // update time from ArrayList
        alarmTable.setItems(FXCollections.observableArrayList(alarmClockManager.getAlarmClocks()));
    }

    public void checkClockToAlarm(String day, int hour, int minute){
        if (!isAlarm) {
            if (alarmClockManager.alarmWhenCheckTrue(day, hour, minute)) {
                startAlarming();
            }
        }
    }

    public void startAlarming(){
        isAlarm = true;
        stopAlarm = false;
        clickAlarmLabel.setVisible(true);
        mediaAlarm.play();
    }

    public void stopAlarming(){
        isAlarm = false;
        stopAlarm = true;
        clickAlarmLabel.setVisible(false);
        mediaAlarm.stop();
    }

    public void setStopAlarming(boolean check){
        stopAlarm = check;
    }

    @FXML
    public void handleClickToStopAndOpenProblemPage(MouseEvent event) throws IOException {
        if (isAlarm){
            Parent root = FXMLLoader.load(getClass().getResource("Alarming.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Alarming");
            stage.setScene(new Scene(root, 250, 300));
            stage.show();
        }

        if (!stopAlarm){
            stopAlarming();
        }
    }

    @FXML
    public void handleAddButtonOnClick(ActionEvent event) {
        int addHour = Integer.parseInt(addHourBox.getValue().toString());
        int addMinute = Integer.parseInt(addMinuteBox.getValue().toString());
        String addDay = addDayOfWeekBox.getValue().toString();
        alarmClockManager.addAlarm(new AlarmClock(addHour, addMinute, addDay));
        updateSetTimeToAlarmTable();

        addHourBox.getSelectionModel().selectFirst();
        addMinuteBox.getSelectionModel().selectFirst();
        addDayOfWeekBox.getSelectionModel().selectFirst();
    }

    @FXML
    public void handleDeleteButtonOnClick(ActionEvent event){
        try {
            AlarmClock alarmClock = alarmTable.getSelectionModel().getSelectedItem();
            if (!alarmClock.equals(null)) {
                alarmClockManager.deleteAlarm(alarmClock);
                updateSetTimeToAlarmTable();
            }
        }catch (Exception e){
            System.out.println("ERROR! Can't delete alarm time");
        }
    }
}