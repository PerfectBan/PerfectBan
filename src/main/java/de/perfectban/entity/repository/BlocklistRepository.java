package de.perfectban.entity.repository;

import de.perfectban.entity.Blocklist;
import de.perfectban.exceptions.InvalidTypeException;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BlocklistRepository
{
    public final static String TYPE_MATCH = "match";
    public final static String TYPE_CONTAINS = "contains";

    private final EntityManager entityManager;
    private final ArrayList<String> types;

    public BlocklistRepository(EntityManager entityManager) {
        this.entityManager = entityManager;

        this.types = new ArrayList<>();
        this.types.add(TYPE_MATCH);
        this.types.add(TYPE_CONTAINS);
    }

    public Blocklist getEntry(int id) {
        return entityManager.find(Blocklist.class, id);
    }

    public List<Blocklist> getBlocklist() {
        return entityManager
                .createQuery("SELECT bl FROM Blocklist bl", Blocklist.class)
                .getResultList();
    }

    public Blocklist createEntry(String match, String type) throws InvalidTypeException {
        Blocklist blocklist = new Blocklist();

        if (!types.contains(type)) {
            throw new InvalidTypeException();
        }

        blocklist.setMatched(match);
        blocklist.setType(type);

        entityManager.persist(blocklist);
        entityManager.flush();

        return blocklist;
    }

    public boolean deleteEntry(int id) {
        Blocklist blocklist = getEntry(id);

        if (blocklist == null) {
            return false;
        }

        entityManager.remove(blocklist);
        return true;
    }
}
