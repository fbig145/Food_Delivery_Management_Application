package business;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import data.DatabaseConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class loginController {

    @FXML
    private Button cancelButton;
    @FXML
    private Button loginButton;
    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField passwordTextField;
    @FXML
    private Button registerButton;

    public void registerButtonOnAction(ActionEvent event){
        createAccountForm();
    }

    public void createAccountForm(){
        try{

            Parent root = FXMLLoader.load(getClass().getResource("../presentation/register.fxml"));
            Stage registerStage = new Stage();
            registerStage.initStyle(StageStyle.UNDECORATED);
            registerStage.setScene(new Scene(root, 520, 605));
            registerStage.show();

        }catch(Exception e){
            e.getCause();
            e.printStackTrace();
        }
    }

    public void cancelButtonOnAction(ActionEvent event){
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    public void loginButtonOnAction(ActionEvent event){
            validateLogin();
    }

    public void createAdminForm(){
        try{
            Parent root = FXMLLoader.load(getClass().getResource("../presentation/admin.fxml"));
            Stage adminStage = new Stage();
            adminStage.initStyle(StageStyle.UNDECORATED);
            adminStage.setScene(new Scene(root, 1350, 700));
            adminStage.show();
        }catch(Exception e){
            e.getCause();
            e.printStackTrace();
        }
    }

    public void createClientForm(){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../presentation/client.fxml"));
            Parent root = (Parent) loader.load();
            clientController secController = loader.getController();
            secController.transferUsername(usernameTextField.getText());

            Stage clientStage = new Stage();
            clientStage.initStyle(StageStyle.UNDECORATED);
            clientStage.setScene(new Scene(root, 1000, 600));
            clientStage.show();
        }catch(Exception e){
            e.getCause();
            e.printStackTrace();
        }
    }

    public void validateLogin(){
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String verifyLogin = "select count(1) from clients where username = '" + usernameTextField.getText() +"'and password ='" + passwordTextField.getText() + "'";
        try{
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(verifyLogin);

            while(queryResult.next()){
                if(queryResult.getInt(1) == 1){
                    createClientForm();
                }else if(usernameTextField.getText().equals("admin") && passwordTextField.getText().equals("admin")){
                    //loginMessageLabel.setText("Invalid login. Please try again");
                    createAdminForm();
                }
//                else{
//                    loginMessageLabel.setText("Invalid login. Please try again");
//                }
            }
        }catch(Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }


}
