package models;

import javax.persistence.*;

@Entity
@Table(name = "userexpprogress")
public class JediBotUser {
    @Id
    private long chat_id;
    @Column(name = "exp_quantity")
    private long experience;
    @Column(name = "first_name")
    private String firstName;

    public JediBotUser(long chat_id, long experience,String firstName) {
        this.chat_id = chat_id;
        this.experience = experience;
        this.firstName = firstName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setUserName(String userName) {
        this.firstName = userName;
    }

    public JediBotUser() {
    }

    public long getChat_id() {
        return chat_id;
    }

    public void setChatId(long chatId) {
        this.chat_id = chatId;
    }

    public long getExperience() {
        return experience;
    }

    public void setExperience(long experience) {
        this.experience = experience;
    }
}
