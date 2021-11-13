package de.perfectban.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Ban")
public class Ban
{
    @Id
    @GeneratedValue
    @Column(name="id")
    private int id;

    @Column(name="token")
    private String token;

    @Column(name="uuid")
    private String uuid;

    @Column(name="reason")
    private String reason;

    @Column(name="until")
    private Date until;

    @Column(name="lifetime")
    private boolean lifetime;

    @Column(name="automatic")
    private boolean automatic;

    public int getId() {
        return id;
    }

    public Ban setId(int id) {
        this.id = id;
        return this;
    }

    public String getToken() {
        return token;
    }

    public Ban setToken(String token) {
        this.token = token;
        return this;
    }

    public String getUuid() {
        return uuid;
    }

    public Ban setUuid(String uuid) {
        this.uuid = uuid;
        return this;
    }

    public String getReason() {
        return reason;
    }

    public Ban setReason(String reason) {
        this.reason = reason;
        return this;
    }

    public Date getUntil() {
        return until;
    }

    public Ban setUntil(Date until) {
        this.until = until;
        return this;
    }

    public boolean isLifetime() {
        return lifetime;
    }

    public Ban setLifetime(boolean lifetime) {
        this.lifetime = lifetime;
        return this;
    }

    public boolean isAutomatic() {
        return automatic;
    }

    public Ban setAutomatic(boolean automatic) {
        this.automatic = automatic;
        return this;
    }
}
