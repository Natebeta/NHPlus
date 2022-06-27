package controller;

import datastorage.DAOFactory;
import datastorage.PatientDAO;
import datastorage.TreatmentDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Patient;
import model.Treatment;
import datastorage.DAOFactory;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * The <code>AllTreatmentController</code> contains the entire logic of the all treatment view. It determines which data is displayed and how to react to events.
 */
public class AllTreatmentController {
    @FXML
    private TableView<Treatment> tableView;
    @FXML
    private TableColumn<Treatment, Integer> colID;
    @FXML
    private TableColumn<Treatment, Integer> colPid;
    @FXML
    private TableColumn<Treatment, String> colDate;
    @FXML
    private TableColumn<Treatment, String> colBegin;
    @FXML
    private TableColumn<Treatment, String> colEnd;
    @FXML
    private TableColumn<Treatment, String> colDescription;
    @FXML
    private TableColumn<Treatment, String> colCaregiver;
    @FXML
    private ComboBox<String> comboBoxPatient;
    @FXML
    public Button btnLock;

    private final ObservableList<Treatment> tableviewContent =
            FXCollections.observableArrayList();
    private TreatmentDAO dao;

    private final ObservableList<String> myComboBoxPatientData =
            FXCollections.observableArrayList();
    private ArrayList<Patient> patientList;

    /**
     * Initializes the corresponding fields. Is called as soon as the corresponding FXML file is to be displayed.
     */
    public void initialize() {
        readAllAndShowInTableView();
        comboBoxPatient.setItems(myComboBoxPatientData);
        comboBoxPatient.getSelectionModel().select(0);


        this.colID.setCellValueFactory(new PropertyValueFactory<>("tid"));
        this.colPid.setCellValueFactory(new PropertyValueFactory<>("PidSurname"));
        this.colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        this.colBegin.setCellValueFactory(new PropertyValueFactory<>("begin"));
        this.colEnd.setCellValueFactory(new PropertyValueFactory<>("end"));
        this.colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        this.colCaregiver.setCellValueFactory(new PropertyValueFactory<>("CidSurname"));
        this.tableView.setItems(this.tableviewContent);
        createComboBoxData();
    }

    /**
     * calls readAll in {@link TreatmentDAO} and shows treatments in the table
     */
    public void readAllAndShowInTableView() {
        this.tableviewContent.clear();
        comboBoxPatient.getSelectionModel().select(0);
        this.dao = DAOFactory.getDAOFactory().createTreatmentDAO();
        List<Treatment> allTreatments;
        try {
            allTreatments = dao.readAll();
            tableviewContent.clear();
            this.tableviewContent.addAll(allTreatments);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createComboBoxData(){
        PatientDAO dao = DAOFactory.getDAOFactory().createPatientDAO();
        try {
            patientList = (ArrayList<Patient>) dao.readAll();
            this.myComboBoxPatientData.add("Alle");
            for (Patient patient: patientList) {
                this.myComboBoxPatientData.add(patient.getSurname());
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    @FXML
    public void handleComboBox(){
        String p = this.comboBoxPatient.getSelectionModel().getSelectedItem();
        this.tableviewContent.clear();
        this.dao = DAOFactory.getDAOFactory().createTreatmentDAO();
        List<Treatment> allTreatments;
        if(p.equals("Alle")){
            try {
                allTreatments= this.dao.readAll();
                this.tableviewContent.addAll(allTreatments);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        Patient patient = searchInList(p);
        if(patient !=null){
            try {
                allTreatments = dao.readTreatmentsByPid(patient.getPid());
                this.tableviewContent.addAll(allTreatments);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private Patient searchInList(String surname){
        for (Patient patient : this.patientList) {
            if (patient.getSurname().equals(surname)) {
                return patient;
            }
        }
        return null;
    }

    /**
     * handles a lock-click-event. Locks a caregiver and calls the update lock status method in the {@link TreatmentDAO}
     */
    @FXML
    public void handleLock(){
        int index = this.tableView.getSelectionModel().getSelectedIndex();
        Treatment t = this.tableviewContent.remove(index);
        TreatmentDAO dao = DAOFactory.getDAOFactory().createTreatmentDAO();
        try {
            dao.updateLockStatus(t.getTid());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * handles a add-click-event. Creates a treatment and calls the create method in the {@link TreatmentDAO}
     */
    @FXML
    public void handleNewTreatment() {
        try{
            String p = this.comboBoxPatient.getSelectionModel().getSelectedItem();
            Patient patient = searchInList(p);
            newTreatmentWindow(patient);
        }
        catch(NullPointerException e){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText("Patient für die Behandlung fehlt!");
            alert.setContentText("Wählen Sie über die Combobox einen Patienten aus!");
            alert.showAndWait();
        }
    }

    @FXML
    public void handleMouseClick(){
        tableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2 && (tableView.getSelectionModel().getSelectedItem() != null)) {
                int index = this.tableView.getSelectionModel().getSelectedIndex();
                Treatment treatment = this.tableviewContent.get(index);

                treatmentWindow(treatment);
            }
        });
    }

    public void newTreatmentWindow(Patient patient){
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("/NewTreatmentView.fxml"));
            AnchorPane pane = loader.load();
            Scene scene = new Scene(pane);
            //da die primaryStage noch im Hintergrund bleiben soll
            Stage stage = new Stage();

            NewTreatmentController controller = loader.getController();
            controller.initialize(this, stage, patient);

            stage.setScene(scene);
            stage.setResizable(false);
            stage.showAndWait();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void treatmentWindow(Treatment treatment){
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("/TreatmentView.fxml"));
            AnchorPane pane = loader.load();
            Scene scene = new Scene(pane);
            Stage stage = new Stage();
            TreatmentController controller = loader.getController();
            controller.initializeController(this, stage, treatment);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}