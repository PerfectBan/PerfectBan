package de.perfectban.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "blocklist")
public class Blocklist
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "matched", columnDefinition = "text not null")
    private String matched;

    @Column(name = "type", columnDefinition = "varchar(16) not null")
    private String type;

    @Column(name = "created", columnDefinition = "timestamp default CURRENT_TIMESTAMP()")
    private Timestamp created;

    public Blocklist() {}

    public int getId() {
        return id;
    }

    public Blocklist setId(int id) {
        this.id = id;
        return this;
    }

    public String getMatched() {
        return matched;
    }

    public Blocklist setMatched(String matched) {
        this.matched = matched;
        return this;
    }

    public String getType() {
        return type;
    }

    public Blocklist setType(String type) {
        this.type = type;
        return this;
    }


    public Timestamp getCreated() {
        return created;
    }

    public Blocklist setCreated(Timestamp created) {
        this.created = created;
        return this;
    }
}
