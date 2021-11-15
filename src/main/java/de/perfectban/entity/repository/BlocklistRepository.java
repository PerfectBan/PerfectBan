package de.perfectban.entity.repository;

import de.perfectban.entity.Blocklist;

import javax.persistence.EntityManager;
import java.util.List;

public class BlocklistRepository
{
    private final EntityManager entityManager;

    public BlocklistRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Blocklist getEntry(int id) {
        return entityManager.find(Blocklist.class, id);
    }

    public List<Blocklist> getBlocklist() {
        return entityManager
                .createQuery("SELECT bl FROM Blocklist bl", Blocklist.class)
                .getResultList();
    }

    public Blocklist createEntry(String match, String type) {
        Blocklist blocklist = new Blocklist();

        // todo: check if type is valid

        blocklist.setMatch(match);
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
