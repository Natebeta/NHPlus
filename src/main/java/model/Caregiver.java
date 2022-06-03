package model;

public class Caregiver extends Person{
    private long cid;
    private String phoneNumber;
    private Boolean lockStatus;
    /**
     * constructs a caregiver from the given params.
     * @param firstName
     * @param surname
     * @param phoneNumber
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
     */
    public Caregiver(long cid, String firstName, String surname, String phoneNumber, boolean lockStatus) {
        super(firstName, surname);
        this.cid = cid;
        this.phoneNumber = phoneNumber;
        this.lockStatus = lockStatus;
    }

    public Boolean getLockStatus() {
        return lockStatus;
    }

    public void setLockStatus(boolean lockStatus) {
        this.lockStatus = lockStatus;
    }

    public long getCid() {
        return cid;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

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
