package za.com.cocamzansi.model;

public class EmailModel {
    String emailAddressTo;

    String emailAddressFrom;

    String emailTitle;

    String emailSubject;

    String emailBody;

    public String getEmailAddressTo() {
        return this.emailAddressTo;
    }

    public void setEmailAddressTo(String emailAddressTo) {
        this.emailAddressTo = emailAddressTo;
    }

    public String getEmailAddressFrom() {
        return this.emailAddressFrom;
    }

    public void setEmailAddressFrom(String emailAddressFrom) {
        this.emailAddressFrom = emailAddressFrom;
    }

    public String getEmailTitle() {
        return this.emailTitle;
    }

    public void setEmailTitle(String emailTitle) {
        this.emailTitle = emailTitle;
    }

    public String getEmailSubject() {
        return this.emailSubject;
    }

    public void setEmailSubject(String emailSubject) {
        this.emailSubject = emailSubject;
    }

    public String getEmailBody() {
        return this.emailBody;
    }

    public void setEmailBody(String emailBody) {
        this.emailBody = emailBody;
    }
}
