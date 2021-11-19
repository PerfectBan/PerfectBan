package de.perfectban.database.repository;

import de.perfectban.database.entity.Mute;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

public class MuteRepository
{
    private final EntityManager entityManager;

    public MuteRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Mute getMute(int id) {
        return entityManager.find(Mute.class, id);
    }

    public List<Mute> getMutes(UUID uuid) {
        return entityManager
                .createQuery("SELECT m FROM Mute m WHERE m.uuid = :uuid AND m.active = :active ORDER BY m.until DESC", Mute.class)
                .setParameter("uuid", uuid.toString())
                .setParameter("active", true)
                .getResultList();
    }

    public Mute createMute(UUID uuid, String reason, String matched, Timestamp until, boolean lifetime, boolean automatic) {
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        Mute mute = new Mute();

        mute.setUuid(uuid.toString());
        mute.setReason(reason);
        mute.setMatched(matched);
        mute.setUntil(until);
        mute.setLifetime(lifetime);
        mute.setAutomatic(automatic);

        entityManager.persist(mute);

        transaction.commit();

        return mute;
    }

    public boolean deleteMute(int id) {
        Mute mute = getMute(id);

        if (mute == null) {
            return false;
        }

        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();
        mute.setActive(false);
        transaction.commit();
        return true;
    }
}
