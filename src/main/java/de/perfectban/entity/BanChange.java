package de.perfectban.entity;

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

    @Column(name = "reason_changed", columnDefinition = "boolean")
    private boolean reasonChanged;

    @Column(name = "until_from", columnDefinition = "timestamp null")
    private Timestamp untilFrom;

    @Column(name = "until_to", columnDefinition = "timestamp null")
    private Timestamp untilTo;

    @Column(name = "until_changed", columnDefinition = "boolean")
    private boolean untilChanged;

    @Column(name = "lifetime_from", columnDefinition = "boolean")
    private Boolean lifetimeFrom;

    @Column(name = "lifetime_to", columnDefinition = "boolean")
    private Boolean lifetimeTo;

    @Column(name = "lifetime_changed", columnDefinition = "boolean")
    private boolean lifetimeChanged;

    @Column(name = "moderator", columnDefinition = "varchar(64) not null")
    private String moderator;

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

    public boolean isReasonChanged() {
        return reasonChanged;
    }

    public BanChange setReasonChanged(boolean reasonChanged) {
        this.reasonChanged = reasonChanged;
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

    public boolean isUntilChanged() {
        return untilChanged;
    }

    public BanChange setUntilChanged(boolean untilChanged) {
        this.untilChanged = untilChanged;
        return this;
    }

    public Boolean isLifetimeFrom() {
        return lifetimeFrom;
    }

    public BanChange setLifetimeFrom(Boolean lifetimeFrom) {
        this.lifetimeFrom = lifetimeFrom;
        return this;
    }

    public Boolean isLifetimeTo() {
        return lifetimeTo;
    }

    public BanChange setLifetimeTo(Boolean lifetimeTo) {
        this.lifetimeTo = lifetimeTo;
        return this;
    }

    public boolean isLifetimeChanged() {
        return lifetimeChanged;
    }

    public BanChange setLifetimeChanged(boolean lifetimeChanged) {
        this.lifetimeChanged = lifetimeChanged;
        return this;
    }

    public String getModerator() {
        return moderator;
    }

    public BanChange setModerator(String moderator) {
        this.moderator = moderator;
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
