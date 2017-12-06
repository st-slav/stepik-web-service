package ru.mamsta.stepik.webservice.dbservice.dataset;

public class UserDataSet implements Comparable<UserDataSet> {

    private  Long id;
    private String login;
    private String password;

    public UserDataSet(Long id, String login, String password) {
        this.id = id;
        this.login = login;
        this.password = password;
    }

    public UserDataSet(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserDataSet user = (UserDataSet) o;

        return getLogin().equals(user.getLogin());
    }

    @Override
    public int hashCode() {
        return getLogin().hashCode();
    }

    @Override
    public String toString() {
        return "UserDataSet{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    @Override
    public int compareTo(UserDataSet o) {
        return getLogin().compareTo(o.getLogin());
    }
}
