package garvit.spidy.daanpaatra;

import android.provider.ContactsContract;

/**
 * Created by garvit on 22/03/18.
 */

public class NGO_Pojo {
    private String Name;
    private String RegdId;
    private long Contact;
    private String Address;
    private String Email;
    private String Password;
    private String type="N";



    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getRegdId() {
        return RegdId;
    }

    public void setRegdId(String regdId) {
        RegdId = regdId;
    }

    public long getContact() {
        return Contact;
    }

    public void setContact(long contact) {
        Contact = contact;
    }

    public String getAddress() {
        return Address;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

}
