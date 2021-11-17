package de.perfectban.database.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "ban_change")
public class BanChange
{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Ban ban;

    @Column(name = "reason_from", columnDefinition = "text not null")
    private String reasonFrom;

    @Column(name = "reason_to", columnDefinition = "text null")
    private String reasonTo;

    @Column(name = "until_from", columnDefinition = "timestamp null")
    private Timestamp untilFrom;

    @Column(name = "until_to", columnDefinition = "timestamp null")
    private Timestamp untilTo;

    @Column(name = "lifetime_from", columnDefinition = "boolean")
    private Boolean lifetimeFrom;

    @Column(name = "lifetime_to", columnDefinition = "boolean")
    private Boolean lifetimeTo;

    @Column(name = "moderator", columnDefinition = "varchar(64) null")
    private String moderator;

    @Column(name = "action", columnDefinition = "varchar(16) null")
    private String action;

    @Column(name = "created", columnDefinition = "timestamp default CURRENT_TIMESTAMP()")
    private Timestamp created;

    public BanChange() {}

    public int getId() {
        return id;
    }

    public BanChange setId(int id) {
        this.id = id;
        return this;
    }

    public Ban getBan() {
        return ban;
    }

    public BanChange setBan(Ban ban) {
        this.ban = ban;
        return this;
    }

    public String getReasonFrom() {
        return reasonFrom;
    }

    public BanChange setReasonFrom(String reasonFrom) {
        this.reasonFrom = reasonFrom;
        return this;
    }

    public String getReasonTo() {
        return reasonTo;
    }

    public BanChange setReasonTo(String reasonTo) {
        this.reasonTo = reasonTo;
        return this;
    }

    public Timestamp getUntilFrom() {
        return untilFrom;
    }

    public BanChange setUntilFrom(Timestamp untilFrom) {
        this.untilFrom = untilFrom;
        return this;
    }

    public Timestamp getUntilTo() {
        return untilTo;
    }

    public BanChange setUntilTo(Timestamp untilTo) {
        this.untilTo = untilTo;
        return this;
    }

    public Boolean getLifetimeFrom() {
        return lifetimeFrom;
    }

    public BanChange setLifetimeFrom(Boolean lifetimeFrom) {
        this.lifetimeFrom = lifetimeFrom;
        return this;
    }

    public Boolean getLifetimeTo() {
        return lifetimeTo;
    }

    public BanChange setLifetimeTo(Boolean lifetimeTo) {
        this.lifetimeTo = lifetimeTo;
        return this;
    }

    public String getModerator() {
        return moderator;
    }

    public BanChange setModerator(String moderator) {
        this.moderator = moderator;
        return this;
    }

    public String getAction() {
        return action;
    }

    public BanChange setAction(String action) {
        this.action = action;
        return this;
    }

    public Timestamp getCreated() {
        return created;
    }

    public BanChange setCreated(Timestamp created) {
        this.created = created;
        return this;
    }
}
