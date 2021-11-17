package de.perfectban.database.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity @Table(name = "ban")
public class Ban
{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "uuid", columnDefinition = "varchar(64) not null")
    private String uuid;

    @Column(name = "reason", columnDefinition = "text not null")
    private String reason;

    @Column(name = "until", columnDefinition = "timestamp null default null")
    private Timestamp until;

    @Column(name = "lifetime", columnDefinition = "boolean default false")
    private boolean lifetime;

    @Column(name = "automatic", columnDefinition = "boolean default false")
    private boolean automatic;

    @Column(name = "active", columnDefinition = "boolean default true")
    private boolean active;

    @Column(name = "moderator", columnDefinition = "varchar(64) null")
    private String moderator;

    @OneToMany(mappedBy = "ban", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BanChange> banChanges = new ArrayList<>();

    @Column(name = "created", columnDefinition = "timestamp default CURRENT_TIMESTAMP()")
    private Timestamp created;

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

    public Timestamp getUntil() {
            return until;
    }

    public Ban setUntil(Timestamp until) {
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

    public String getModerator() {
        return moderator;
    }

    public Ban setModerator(String moderator) {
        this.moderator = moderator;
        return this;
    }

    public List<BanChange> getBanChanges() {
        return banChanges;
    }

    public Ban setBanChanges(List<BanChange> banChanges) {
        this.banChanges = banChanges;
        return this;
    }

    public Timestamp getCreated() {
        return created;
    }

    public Ban setCreated(Timestamp created) {
        this.created = created;
        return this;
    }
}
