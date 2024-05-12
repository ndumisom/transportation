package za.com.cocamzansi.model;

public class EmailConfirmationModel {
    private Integer confirmemailId;

    private String email;

    private String confirmationToken;

    private boolean enabled;

    public Integer getConfirmemailId() {
        return this.confirmemailId;
    }

    public void setConfirmemailId(Integer confirmemailId) {
        this.confirmemailId = confirmemailId;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getConfirmationToken() {
        return this.confirmationToken;
    }

    public void setConfirmationToken(String confirmationToken) {
        this.confirmationToken = confirmationToken;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
