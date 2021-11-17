package de.perfectban.database.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "mute")
public class Mute
{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "uuid", columnDefinition = "varchar(64) not null")
    private String uuid;

    @Column(name = "reason", columnDefinition = "text not null")
    private String reason;

    @Column(name = "matched", columnDefinition = "text null")
    private String matched;

    @Column(name = "until", columnDefinition = "timestamp null default null")
    private Timestamp until;

    @Column(name = "lifetime", columnDefinition = "boolean default false")
    private boolean lifetime;

    @Column(name = "automatic", columnDefinition = "boolean default false")
    private boolean automatic;

    @Column(name = "active", columnDefinition = "boolean default false")
    private boolean active;

    @Column(name = "created", columnDefinition = "timestamp default CURRENT_TIMESTAMP()")
    private Timestamp created;

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

    public String getMatched() {
        return matched;
    }

    public Mute setMatched(String matched) {
        this.matched = matched;
        return this;
    }

    public Timestamp getUntil() {
        return until;
    }

    public Mute setUntil(Timestamp until) {
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

    public Timestamp getCreated() {
        return created;
    }

    public Mute setCreated(Timestamp created) {
        this.created = created;
        return this;
    }
}
