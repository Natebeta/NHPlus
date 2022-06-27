package model;

/**
 * Caregivers working in a NURSING home and treat patients.
 */
public class Caregiver extends Person{
    private long cid;
    private String phoneNumber;
    private Boolean lockStatus;

    /**
     * constructs a caregiver from the given params.
     * @param firstName
     * @param surname
     * @param phoneNumber
     * @param lockStatus
     */
    public Caregiver(String firstName, String surname, String phoneNumber, boolean lockStatus) {
        super(firstName, surname);
        this.phoneNumber = phoneNumber;
        this.lockStatus = lockStatus;
    }

    /**
     * constructs a caregiver from the given params.
     * @param cid
     * @param firstName
     * @param surname
     * @param phoneNumber
     * @param lockStatus
     */
    public Caregiver(long cid, String firstName, String surname, String phoneNumber, boolean lockStatus) {
        super(firstName, surname);
        this.cid = cid;
        this.phoneNumber = phoneNumber;
        this.lockStatus = lockStatus;
    }

    /**
     *
     * @return lockStatus
     */
    public Boolean getLockStatus() {
        return lockStatus;
    }

    /**
     *
     * sets lockStatus by default false and via button to true
     */
    public void setLockStatus(boolean lockStatus) {
        this.lockStatus = lockStatus;
    }

    /**
     *
     * @return caregiver id
     */
    public long getCid() {
        return cid;
    }

    /**
     *
     * @return phonenumber
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     *
     * @param phoneNumber new phonenumber
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     *
     * @return string-representation of the caregiver
     */
    public String toString() {
        return "Caregiver" + "\nMNID: " + this.cid +
                "\nFirstname: " + this.getFirstName() +
                "\nSurname: " + this.getSurname() +
                "\nPhonenumber: " + this.phoneNumber +
                "\n";
    }
}
