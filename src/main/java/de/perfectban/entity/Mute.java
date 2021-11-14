package de.perfectban.entity;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "mute")
public class Mute
{
    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;

    @Column(name = "uuid")
    private String uuid;

    @Column(name = "reason")
    private String reason;

    @Column(name = "match")
    private String match;

    @Column(name = "until")
    private Date until;

    @Column(name = "lifetime", columnDefinition = "TINYINT")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean lifetime;

    @Column(name = "automatic", columnDefinition = "TINYINT")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean automatic;

    @Column(name = "active", columnDefinition = "TINYINT")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean active;

    public Mute() {}

    public int getId() {
        return id;
    }

    public Mute setId(int id) {
        this.id = id;
        return this;
    }

    public String getUuid() {
        return uuid;
    }

    public Mute setUuid(String uuid) {
        this.uuid = uuid;
        return this;
    }

    public String getReason() {
        return reason;
    }

    public Mute setReason(String reason) {
        this.reason = reason;
        return this;
    }

    public String getMatch() {
        return match;
    }

    public Mute setMatch(String match) {
        this.match = match;
        return this;
    }

    public Date getUntil() {
        return until;
    }

    public Mute setUntil(Date until) {
        this.until = until;
        return this;
    }

    public boolean isLifetime() {
        return lifetime;
    }

    public Mute setLifetime(boolean lifetime) {
        this.lifetime = lifetime;
        return this;
    }

    public boolean isAutomatic() {
        return automatic;
    }

    public Mute setAutomatic(boolean automatic) {
        this.automatic = automatic;
        return this;
    }

    public boolean isActive() {
        return active;
    }

    public Mute setActive(boolean active) {
        this.active = active;
        return this;
    }
}
