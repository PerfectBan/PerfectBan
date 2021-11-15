package de.perfectban.entity.repository;

import de.perfectban.entity.Ban;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class BanRepository
{
    private final EntityManager entityManager;

    public BanRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Ban getBan(int id) {
        return entityManager.find(Ban.class, id);
    }

    public List<Ban> getBans(UUID uuid) {
        return entityManager
                .createQuery("SELECT b FROM Ban b WHERE b.uuid = :uuid AND b.active = :active ORDER BY b.until DESC", Ban.class)
                .setParameter("uuid", uuid.toString())
                .setParameter("active", true)
                .getResultList();
    }

    public Ban createBan(UUID uuid, String reason, Timestamp until, boolean lifetime, boolean automatic) {
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        Ban ban = new Ban();

        ban.setUuid(uuid.toString());
        ban.setReason(reason);

        if (!lifetime) {
            ban.setUntil(until);
        }

        ban.setLifetime(lifetime);
        ban.setAutomatic(automatic);
        ban.setActive(true);

        entityManager.persist(ban);
        entityManager.flush();

        transaction.commit();

        return ban;
    }

    // todo: soft delete?
    public boolean deleteBan(int id) {
        Ban ban = getBan(id);

        if (ban == null) {
            return false;
        }

        entityManager.remove(ban);
        return true;
    }
}
