package de.perfectban.entity.repository;

import de.perfectban.entity.Mute;

import javax.persistence.EntityManager;
import java.util.Date;
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

    public Mute createMute(UUID uuid, String reason, String match, Date until, boolean lifetime, boolean automatic) {
        Mute mute = new Mute();

        mute.setUuid(uuid.toString());
        mute.setReason(reason);
        mute.setMatch(match);
        mute.setUntil(until);
        mute.setLifetime(lifetime);
        mute.setAutomatic(automatic);

        entityManager.persist(mute);

        return mute;
    }

    // todo: soft delete?
    public boolean deleteMute(int id) {
        Mute mute = getMute(id);

        if (mute == null) {
            return false;
        }

        entityManager.remove(mute);
        return true;
    }
}
