package controller;

import datastorage.PatientDAO;
import datastorage.TreatmentDAO;
import datastorage.UserDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import model.Patient;
import model.User;
import utils.DateConverter;
import datastorage.DAOFactory;
import utils.PasswordUtils;

import javax.crypto.SecretKey;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;


/**
 * The <code>AllPatientController</code> contains the entire logic of the patient view. It determines which data is displayed and how to react to events.
 */
public class AllUserController {
    @FXML
    private TableView<User> tableView;
    @FXML
    private TableColumn<User, Integer> colID;
    @FXML
    private TableColumn<User, String> colFirstName;
    @FXML
    private TableColumn<User, String> colSurname;
    @FXML
    private TableColumn<User, String> colUsername;
    @FXML
    private TableColumn<User, String> colPassword;
    @FXML
    private TableColumn<User, String> colAuthorization;
    @FXML
    private TableColumn<User, Boolean> colLocked;

    @FXML
    private Label emptyField;

    @FXML
    Button btnDelete;
    @FXML
    Button btnAdd;
    @FXML
    TextField txtSurname;
    @FXML
    TextField txtFirstname;
    @FXML
    TextField txtUsername;
    @FXML
    TextField txtPassword;
    @FXML
    private ComboBox<String> comboBoxUserRole;

    private ObservableList<User> tableviewContent = FXCollections.observableArrayList();
    private UserDAO dao;

    private final ObservableList<String> comboBoxUserData = FXCollections.observableArrayList("Admin", "User");
    private final ObservableList<Boolean> comboBoxUserLocked = FXCollections.observableArrayList(Boolean.TRUE, Boolean.FALSE);

    /**
     * Initializes the corresponding fields. Is called as soon as the corresponding FXML file is to be displayed.
     */
    public void initialize() {
        readAllAndShowInTableView();

        this.colID.setCellValueFactory(new PropertyValueFactory<User, Integer>("uid"));

        //CellValuefactory to display data in TableView
        this.colFirstName.setCellValueFactory(new PropertyValueFactory<User, String>("firstName"));
        //CellFactory writes in table cell
        this.colFirstName.setCellFactory(TextFieldTableCell.forTableColumn());

        this.colSurname.setCellValueFactory(new PropertyValueFactory<User, String>("surname"));
        this.colSurname.setCellFactory(TextFieldTableCell.forTableColumn());

        this.colUsername.setCellValueFactory(new PropertyValueFactory<User, String>("username"));
        this.colUsername.setCellFactory(TextFieldTableCell.forTableColumn());

        this.colPassword.setCellValueFactory(new PropertyValueFactory<User, String>("password"));

        this.colAuthorization.setCellValueFactory(new PropertyValueFactory<User, String>("authorization"));
        this.colAuthorization.setCellFactory(ComboBoxTableCell.forTableColumn(comboBoxUserData));

        this.colLocked.setCellValueFactory(new PropertyValueFactory<User, Boolean>("locked"));
        this.colLocked.setCellFactory(ComboBoxTableCell.forTableColumn(comboBoxUserLocked));

        //Display data
        this.tableView.setItems(this.tableviewContent);

        comboBoxUserRole.setItems(comboBoxUserData);
        comboBoxUserRole.getSelectionModel().selectLast();
    }

    /**
     * handles new firstname value
     * @param event event including the value that a user entered into the cell
     */
    @FXML
    public void handleOnEditFirstname(TableColumn.CellEditEvent<User, String> event){
        event.getRowValue().setFirstName(event.getNewValue());
        doUpdate(event);
    }

    /**
     * handles new surname value
     * @param event event including the value that a user entered into the cell
     */
    @FXML
    public void handleOnEditSurname(TableColumn.CellEditEvent<User, String> event){
        event.getRowValue().setSurname(event.getNewValue());
        doUpdate(event);
    }

    /**
     * handles new username value
     * @param event event including the value that a user entered into the cell
     */
    @FXML
    public void handleOnEditUsername(TableColumn.CellEditEvent<User, String> event){
        event.getRowValue().setUsername(event.getNewValue());
        doUpdate(event);
    }

    /**
     * handles new password value
     * @param event event including the value that a user entered into the cell
     */
    @FXML
    public void handleOnEditPassword(TableColumn.CellEditEvent<User, String> event){
        event.getRowValue().setPassword(event.getNewValue());
        doUpdate(event);
    }

    /**
     * handles new authorization value
     * @param event event including the value that a user entered into the cell
     */
    @FXML
    public void handleOnEditAuthorization(TableColumn.CellEditEvent<User, String> event){
        event.getRowValue().setAuthorization(event.getNewValue());
        doUpdate(event);
    }

    /**
     * handles new locked status
     * @param event event including the value that is set
     */
    @FXML
    public void handleOnEditLocked(TableColumn.CellEditEvent<User, Boolean> event) {
        event.getRowValue().setLocked(event.getNewValue());
        try {
            dao.update(event.getRowValue());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * updates a user by calling the update-Method in the {@link UserDAO}
     * @param t row to be updated by the user
     */
    private void doUpdate(TableColumn.CellEditEvent<User, String> t) {
        try {
            dao.update(t.getRowValue());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * calls readAll in {@link UserDAO} and shows users in the table
     */
    private void readAllAndShowInTableView() {
        this.tableviewContent.clear();
        this.dao = DAOFactory.getDAOFactory().createUserDAO();
        List<User> allUser;
        try {
            allUser = dao.readAll();
            this.tableviewContent.addAll(allUser);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * handles a delete-click-event. Calls the delete methods in the {@link UserDAO}
     */
    @FXML
    public void handleDeleteRow() {
        User selectedItem = this.tableView.getSelectionModel().getSelectedItem();
        try {
            dao.deleteById(selectedItem.getUid());
            this.tableView.getItems().remove(selectedItem);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * handles a add-click-event. Creates a user and calls the create method in the {@link UserDAO}
     */
    @FXML
    public void handleAdd() {
        String surname = this.txtSurname.getText();
        String firstname = this.txtFirstname.getText();
        String username = this.txtUsername.getText();
        String password = this.txtPassword.getText();
        String authorization = comboBoxUserRole.getSelectionModel().getSelectedItem();

        String encryptedPassword = passwordEncryption(password);

        if(surname.isEmpty() || firstname.isEmpty() || username.isEmpty() || password.isEmpty()) {
            emptyField.setText("Du hast nicht alle Felder ausgefüllt!");
        } else {
            try {
                User u = new User(firstname, surname, username, encryptedPassword, authorization, false);
                dao.create(u);
                emptyField.setText("");
            } catch (SQLException e) {
                e.printStackTrace();
            }
            readAllAndShowInTableView();
            clearTextfields();
        }
    }

    /**
     * Encrypts the given password string by the method of {@link PasswordUtils} and convert it back to string
     * @param password as a string
     */
    private String passwordEncryption(String password) {
        SecretKey secretKey = PasswordUtils.getKeyPassword(password);
        assert null != secretKey;
        return PasswordUtils.convertSecretKeyToString(secretKey);
    }

    /**
     * removes content from all textfields
     */
    private void clearTextfields() {
        this.txtFirstname.clear();
        this.txtSurname.clear();
        this.txtUsername.clear();
        this.txtPassword.clear();
        this.comboBoxUserRole.getSelectionModel().clearSelection();
    }
}