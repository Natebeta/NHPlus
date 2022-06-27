package model;

public class User extends Person {
    private long uid;
    private String username;
    private String password;
    private String authorization;
    private boolean locked;

    public User(String firstName, String surname, String username, String password, String authorization, boolean locked) {
        super(firstName, surname);
        this.username = username;
        this.password = password;
        this.authorization = authorization;
        this.locked = locked;
    }

    public User(long id, String firstName, String surname, String username, String password, String authorization, boolean locked) {
        super(firstName, surname);
        this.uid = id;
        this.username = username;
        this.password = password;
        this.authorization = authorization;
        this.locked = locked;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAuthorization() {
        return authorization;
    }

    public void setAuthorization(String authorization) {
        this.authorization = authorization;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }
}
