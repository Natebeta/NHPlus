package model;

/**
 * User for login
 */
public class User extends Person {
    private long uid;
    private String username;
    private String password;
    private String authorization;
    private boolean locked;

    /**
     * constructs a patient from the given params.
     * @param firstName
     * @param surname
     * @param username
     * @param password
     * @param authorization
     * @param locked
     */
    public User(String firstName, String surname, String username, String password, String authorization, boolean locked) {
        super(firstName, surname);
        this.username = username;
        this.password = password;
        this.authorization = authorization;
        this.locked = locked;
    }

    /**
     * constructs a patient from the given params.
     * @param id
     * @param firstName
     * @param surname
     * @param username
     * @param password
     * @param authorization
     * @param locked
     */
    public User(long id, String firstName, String surname, String username, String password, String authorization, boolean locked) {
        super(firstName, surname);
        this.uid = id;
        this.username = username;
        this.password = password;
        this.authorization = authorization;
        this.locked = locked;
    }

    /**
     * @return uid user id
     */
    public long getUid() {
        return uid;
    }

    /**
     * @param uid set new user id
     */
    public void setUid(long uid) {
        this.uid = uid;
    }

    /**
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username set new username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password set new password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return authorization
     */
    public String getAuthorization() {
        return authorization;
    }

    /**
     * @param authorization set new authorization
     */
    public void setAuthorization(String authorization) {
        this.authorization = authorization;
    }

    /**
     * @return locked status
     */
    public boolean isLocked() {
        return locked;
    }

    /**
     * @param locked set locked status
     */
    public void setLocked(boolean locked) {
        this.locked = locked;
    }
}
