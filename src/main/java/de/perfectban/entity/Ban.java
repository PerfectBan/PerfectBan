package de.perfectban.entity;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;

@Entity @Table(name = "ban")
public class Ban
{
    @Id @GeneratedValue
    @Column(name="id")
    private int id;

    @Column(name="uuid")
    private String uuid;

    @Column(name="reason")
    private String reason;

    @Column(name="until")
    private Date until;

    @Column(name="lifetime",columnDefinition="TINYINT")
    @Type(type="org.hibernate.type.NumericBooleanType")
    private boolean lifetime;

    @Column(name="automatic",columnDefinition="TINYINT")
    @Type(type="org.hibernate.type.NumericBooleanType")
    private boolean automatic;

    @Column(name="active",columnDefinition="TINYINT")
    @Type(type="org.hibernate.type.NumericBooleanType")
    private boolean active;

    public Ban() {}

    public int getId() {
        return id;
    }

        public Ban setId(int id) {
            this.id = id;
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

    public boolean isActive() {
        return active;
    }

    public Ban setActive(boolean active) {
        this.active = active;
        return this;
    }
}
