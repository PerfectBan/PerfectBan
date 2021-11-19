package de.perfectban.database.repository;

import de.perfectban.database.entity.Ban;
import de.perfectban.database.entity.BanChange;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.sql.Timestamp;
import java.util.UUID;

public class BanChangeRepository
{
    private final EntityManager entityManager;

    public BanChangeRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public BanChange createChange(Ban ban, String reason, Timestamp until, Boolean lifetime, UUID moderator, String action) {
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        BanChange banChange = new BanChange();

        banChange.setBan(ban);
        banChange.setModerator(moderator == null ? null : moderator.toString());
        banChange.setAction(action);

        banChange.setReasonFrom(ban.getReason());
        banChange.setReasonTo(reason);

        banChange.setUntilFrom(ban.getUntil());
        banChange.setUntilTo(until);

        banChange.setLifetimeFrom(ban.isLifetime());
        banChange.setLifetimeTo(lifetime);

        entityManager.persist(banChange);

        transaction.commit();

        return banChange;
    }
}
