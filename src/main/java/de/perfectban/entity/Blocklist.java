package de.perfectban.entity;

import javax.persistence.*;

@Entity
@Table(name = "blocklist")
public class Blocklist
{
    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;

    @Column(name = "match")
    private String match;

    @Column(name = "type")
    private String type;

    public Blocklist() {}

    public int getId() {
        return id;
    }

    public Blocklist setId(int id) {
        this.id = id;
        return this;
    }

    public String getMatch() {
        return match;
    }

    public Blocklist setMatch(String match) {
        this.match = match;
        return this;
    }

    public String getType() {
        return type;
    }

    public Blocklist setType(String type) {
        this.type = type;
        return this;
    }
}
