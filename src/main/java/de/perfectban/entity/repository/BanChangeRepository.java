package de.perfectban.entity.repository;

import de.perfectban.entity.Ban;
import de.perfectban.entity.BanChange;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.sql.Timestamp;

public class BanChangeRepository
{
    private final EntityManager entityManager;

    public BanChangeRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public BanChange createChange(Ban ban, String reason, Timestamp until, Boolean lifetime, String moderator) {
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        BanChange banChange = new BanChange();

        banChange.setBan(ban);
        banChange.setModerator(moderator);

        banChange.setReasonFrom(ban.getReason());
        banChange.setReasonTo(reason);
        banChange.setReasonChanged(reason != null && !reason.equals(ban.getReason()));

        banChange.setUntilFrom(ban.getUntil());
        banChange.setUntilTo(until);
        banChange.setUntilChanged(until != null && until.getTime() != ban.getUntil().getTime());

        banChange.setLifetimeFrom(ban.isLifetime());
        banChange.setLifetimeTo(lifetime);
        banChange.setLifetimeChanged(lifetime != null && lifetime != ban.isLifetime());

        entityManager.persist(banChange);
        entityManager.flush();

        transaction.commit();

        return banChange;
    }
}
