package controller;

import datastorage.CaregiverDAO;
import datastorage.DAOFactory;
import datastorage.TreatmentDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Caregiver;
import model.Patient;
import model.Treatment;
import utils.DateConverter;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class NewTreatmentController {
    @FXML
    private Label lblSurname;
    @FXML
    private Label lblFirstname;
    @FXML
    private TextField txtBegin;
    @FXML
    private TextField txtEnd;
    @FXML
    private TextField txtDescription;
    @FXML
    private TextArea taRemarks;
    @FXML
    private DatePicker datepicker;
    @FXML
    private ComboBox<String> comboBoxCaregiver;

    private AllTreatmentController controller;
    private Patient patient;
    private Stage stage;

    private final ObservableList<String> myComboBoxCaregiverData =
            FXCollections.observableArrayList();
    private final ObservableList<Long> myComboBoxCaregiverDataLong =
            FXCollections.observableArrayList();


    public void initialize(AllTreatmentController controller, Stage stage, Patient patient) {
        this.controller= controller;
        this.patient = patient;
        this.stage = stage;

        comboBoxCaregiver.setItems(myComboBoxCaregiverData);
        comboBoxCaregiver.getSelectionModel().select(1);

        showPatientData();
        createComboBoxCaregiverData();
    }

    private void showPatientData(){
        this.lblFirstname.setText(patient.getFirstName());
        this.lblSurname.setText(patient.getSurname());
    }

    /**
     * handles a add-click-event. Creates a treatment and calls the create method in the {@link TreatmentDAO}
     */
    @FXML
    public void handleAdd(){
        LocalDate date = this.datepicker.getValue();
        LocalTime begin = DateConverter.convertStringToLocalTime(txtBegin.getText());
        LocalTime end = DateConverter.convertStringToLocalTime(txtEnd.getText());
        String description = txtDescription.getText();
        String remarks = taRemarks.getText();
        long cid = myComboBoxCaregiverDataLong.get(this.comboBoxCaregiver.getSelectionModel().getSelectedIndex());
        Treatment treatment = new Treatment(patient.getPid(), date,
                begin, end, description, remarks,false, cid);
        createTreatment(treatment);
        controller.readAllAndShowInTableView();
        stage.close();
    }

    /**
     * creates Treatment
     * @param treatment calls the create method {@link TreatmentDAO}
     */
    private void createTreatment(Treatment treatment) {
        TreatmentDAO dao = DAOFactory.getDAOFactory().createTreatmentDAO();
        try {
            dao.create(treatment);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * handles a cancel-click-event. Closes the Combobox
     */
    @FXML
    public void handleCancel(){
        stage.close();
    }

    private void createComboBoxCaregiverData(){
        CaregiverDAO dao = DAOFactory.getDAOFactory().createCaregiverDAO();
        try {
            ArrayList<Caregiver> caregiverList = (ArrayList<Caregiver>) dao.readAll();
            for (Caregiver caregiver: caregiverList) {
                this.myComboBoxCaregiverData.add(caregiver.getSurname());
                this.myComboBoxCaregiverDataLong.add(caregiver.getCid());
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
}