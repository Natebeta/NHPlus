package controller;

import datastorage.CaregiverDAO;
import datastorage.DAOFactory;
import datastorage.PatientDAO;
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
import java.util.ArrayList;

/**
 * The <code>TreatmentController</code> contains the entire logic of the treatments.
 */
public class TreatmentController {
    @FXML
    private Label lblPatientName;
    @FXML
    private Label lblCarelevel;
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
    private Stage stage;
    private Patient patient;
    private Treatment treatment;
    private ArrayList<Caregiver
            > caregiverList = new ArrayList();

    private final ObservableList<String> myComboBoxCaregiverData =
            FXCollections.observableArrayList();

    /**
     * Initializes the controller fields.
     */
    public void initializeController(AllTreatmentController controller, Stage stage, Treatment treatment) {
        this.stage = stage;
        this.controller= controller;
        PatientDAO pDao = DAOFactory.getDAOFactory().createPatientDAO();
        try {
            this.patient = pDao.read((int) treatment.getPid());
            this.treatment = treatment;
            comboBoxCaregiver.setItems(myComboBoxCaregiverData);
            showData();
            createComboBoxCaregiverData();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * shows a treatment by calling the ReadAll-Method in the {@link TreatmentDAO}
     */
    private void showData() throws SQLException {
        this.lblPatientName.setText(patient.getSurname()+", "+patient.getFirstName());
        this.lblCarelevel.setText(patient.getCareLevel());
        LocalDate date = DateConverter.convertStringToLocalDate(treatment.getDate());
        this.datepicker.setValue(date);
        this.txtBegin.setText(this.treatment.getBegin());
        this.txtEnd.setText(this.treatment.getEnd());
        this.txtDescription.setText(this.treatment.getDescription());
        this.taRemarks.setText(this.treatment.getRemarks());
        this.comboBoxCaregiver.getSelectionModel().select(this.treatment.getCaregiver().getSurname());
    }

    /**
     *
     * updates a treatment by calling the update-Method in the {@link TreatmentDAO}
     */
    @FXML
    public void handleChange(){
        this.treatment.setDate(this.datepicker.getValue().toString());
        this.treatment.setBegin(txtBegin.getText());
        this.treatment.setEnd(txtEnd.getText());
        this.treatment.setDescription(txtDescription.getText());
        this.treatment.setRemarks(taRemarks.getText());
        this.treatment.setCaregiver(caregiverList.get(this.comboBoxCaregiver.getSelectionModel().getSelectedIndex()));
        doUpdate();
        controller.readAllAndShowInTableView();
        stage.close();
    }

    /**
     *
     * updates a treatment by calling the update-Method in the {@link TreatmentDAO}
     */
    private void doUpdate(){
        TreatmentDAO dao = DAOFactory.getDAOFactory().createTreatmentDAO();
        try {
            dao.update(treatment);
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

    /**
     * handles a combobox-click-event. Opens a combobox filled with informations from the Treatment
     */
    private void createComboBoxCaregiverData(){
        CaregiverDAO dao = DAOFactory.getDAOFactory().createCaregiverDAO();
        try {
            ArrayList<Caregiver> caregiverList = (ArrayList<Caregiver>) dao.readAll();
            for (Caregiver caregiver: caregiverList) {
                this.myComboBoxCaregiverData.add(caregiver.getSurname());
                this.caregiverList.add(caregiver);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
}

