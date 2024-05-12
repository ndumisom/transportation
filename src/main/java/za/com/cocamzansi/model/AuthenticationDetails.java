package za.com.cocamzansi.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AuthenticationDetails {
    private String id;

    private String token;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
