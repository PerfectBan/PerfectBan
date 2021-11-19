package de.perfectban.database.repository;

import com.sun.istack.Nullable;
import de.perfectban.database.entity.Ban;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.sql.Timestamp;
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

    public Ban createBan(UUID uuid, String reason, Timestamp until, boolean lifetime, boolean automatic, UUID moderator) {
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
        ban.setModerator(moderator == null ? null : moderator.toString());

        entityManager.persist(ban);

        transaction.commit();
        entityManager.close();

        return ban;
    }

    public void editBan(int id, String reason, Timestamp until, Boolean lifetime, UUID moderator) {
        Ban ban = getBan(id);

        if (ban == null) {
            return;
        }

        BanChangeRepository banChangeRepository = new BanChangeRepository(entityManager);
        banChangeRepository.createChange(ban, reason, until, lifetime, (moderator == null ? null : moderator.toString()), "edit");

        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        if (reason != null) {
            ban.setReason(reason);
        }

        if (lifetime != null) {
            ban.setLifetime(lifetime);
        }

        if (Boolean.FALSE.equals(lifetime) && until != null) {
            ban.setUntil(until);
        }

        transaction.commit();
        entityManager.close();
    }

    public void deleteBan(int id, @Nullable UUID moderator, String reason) {
        Ban ban = getBan(id);

        if (ban == null) {
            return;
        }

        if (moderator != null) {
            BanChangeRepository banChangeRepository = new BanChangeRepository(entityManager);
            banChangeRepository.createChange(ban, reason, null, null, (moderator == null ? null : moderator.toString()), "delete");
        }

        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();
        ban.setActive(false);
        transaction.commit();
        entityManager.close();
    }
}
