package garvit.spidy.daanpaatra;

/**
 * Created by garvit on 25/05/18.
 */

public class DonationItemPojo {

    private String Name;
    private String Contact;
    private String Description;
    private String ImageUrl;

    public DonationItemPojo(String name, String contact, String description, String imageUrl) {
        Name = name;
        Contact = contact;
        Description = description;
        ImageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return "DonationItemPojo{" +
                "Name='" + Name + '\'' +
                ", Contact='" + Contact + '\'' +
                ", Description='" + Description + '\'' +
                ", ImageUrl='" + ImageUrl + '\'' +
                '}';
    }

    public DonationItemPojo() {
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getContact() {
        return Contact;
    }

    public void setContact(String contact) {
        Contact = contact;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }



}
