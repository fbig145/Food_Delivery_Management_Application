package business;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import data.DatabaseConnection;

import java.sql.Connection;
import java.sql.Statement;


public class RegisterController {

    @FXML
    private Button cancelButton;
    @FXML
    private Label registrationMessageLabel;
    @FXML
    private PasswordField passwordTextField;
    @FXML
    private PasswordField confirmTextField;
    @FXML
    private Label confirmPasswordLabel;
    @FXML
    private TextField firstnameTextField;
    @FXML
    private TextField lastnameTextField;
    @FXML
    private TextField emailTextField;
    @FXML
    private TextField birthdateTextField;
    @FXML
    private TextField cityTextField;
    @FXML
    private TextField addressTextField;
    @FXML
    private TextField usernameTextField;

    public void cancelButtonOnAction(ActionEvent event){
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
       // Platform.exit();
    }

    public void registerButtonOnAction(ActionEvent event) {
      //  registrationMessageLabel.setText("Registration completed successfully");
        if(passwordTextField.getText().equals(confirmTextField.getText())){
            confirmPasswordLabel.setText("");
           // registrationMessageLabel.setText("Registration completed successfully");
            registerUser();
        }else{
            confirmPasswordLabel.setText("Password does not match");
        }
    }

    public void registerUser(){
       DatabaseConnection connectNow = new DatabaseConnection();
       Connection connectDB = connectNow.getConnection();

       String firstname = firstnameTextField.getText();
       String lastname = lastnameTextField.getText();
       String email = emailTextField.getText();
       String birthdate = birthdateTextField.getText();
       String city = cityTextField.getText();
       String address = addressTextField.getText();
       String username = usernameTextField.getText();
       String password = passwordTextField.getText();

       String insertFields = "insert into clients(first_name, last_name, email, birth_date, city, address, username, password) values('";
       String insertValues = firstname + "','" + lastname + "','" + email + "','" + birthdate + "','" + city + "','" + address + "','" + username + "','" + password + "')";
       String insertToRegister = insertFields + insertValues;

       try{
           Statement statement = connectDB.createStatement();
           statement.executeUpdate(insertToRegister);
           registrationMessageLabel.setText("Registration completed successfully");

       }catch(Exception e){
           e.getCause();
           e.printStackTrace();
       }

    }


}
