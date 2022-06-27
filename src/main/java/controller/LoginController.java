package controller;

import datastorage.DAOFactory;
import datastorage.UserDAO;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import model.User;
import utils.PasswordUtils;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public class LoginController {

    @FXML
    Button btnLogin;
    @FXML
    TextField txtUsername;
    @FXML
    PasswordField txtPassword;
    @FXML
    private Label failedLogin;

    /**
     * Checks all user in {@link UserDAO} and checks username and password input and gives access to the appropriate view.
     * The entered password will be hashed and matched with the hashed password in the database
     * @throws SQLException provides information about database access or other errors
     * @throws IOException signals a I/O error
     */
    public void login() throws SQLException, IOException {
        Main main = new Main();
        UserDAO userDAO = DAOFactory.getDAOFactory().createUserDAO();
        List<User> allUser = userDAO.readAll();
        for(User u : allUser) {
            if(Objects.equals(u.isLocked(), false)) {
                if(txtUsername.getText().equals(u.getUsername()) && passwordEncryption(txtPassword.getText()).equals(u.getPassword())) {
                    if(Objects.equals(u.getAuthorization(), "Admin")) {
                        main.changeView("/MainAdminWindowView.fxml");
                    } else if(Objects.equals(u.getAuthorization(), "User")) {
                        main.changeView("/MainWindowView.fxml");
                    }
                } else if(txtUsername.getText().isEmpty() || txtPassword.getText().isEmpty()){
                    failedLogin.setText("Bitte Login Daten eingeben.");
                } else {
                    failedLogin.setText("Die Login Daten sind falsch.");
                }
            } else {
                failedLogin.setText("Dieser User ist gesperrt.");
            }
        }
    }

    /**
     * Encrypts the given password string by the method of {@link PasswordUtils} and convert it back to string
     * @param password as a string
     */
    private String passwordEncryption(String password) {
        SecretKey secretKey = PasswordUtils.getKeyPassword(password);
        assert secretKey != null;
        return PasswordUtils.convertSecretKeyToString(secretKey);
    }
}
