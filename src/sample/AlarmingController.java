package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class AlarmingController {
    private String problem;
    private ProblemManager problemManager;

    @FXML
    private Label problemLabel;
    @FXML
    private TextField answerTextField;

    public void initialize(){
        problemManager = new ProblemManager();

        //set problem
        problemManager.createProblem();
        problem = problemManager.getProblem();
        problemLabel.setText(problem);

    }

    public void showAlert(String text){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(text);
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    @FXML
    public void handleCheckButtonOnClick(ActionEvent event){
        int answer = Integer.parseInt(answerTextField.getText());
        problemManager.checkCompareAnswer(answer);

        if (!problemManager.getCheckAnswer()){
            showAlert("Wrong Answer!!");
        }
        else{
            showAlert("Correct Answer!!");

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("AlarmClock.fxml"));
                Controller controller = (Controller) loader.getController();
                controller.setStopAlarming(true);
            }catch (Exception e){

            }
        }
    }
}
