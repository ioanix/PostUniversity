package ro.ubb.exmp.config;

public class Configuration {

    private String url;
    private String user;
    private String password;

    public Configuration() {
    }

    public Configuration(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "url = " + url + '\n' +
                "user = " + user + '\n' +
                "password = " + password + '\n';
    }
}
