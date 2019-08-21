package pl.coderslab.charity.entity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.util.Objects;
import java.util.UUID;

@MappedSuperclass
public abstract class UUIDgenerator {

    @Column(unique = true)
    private String uuid = UUID.randomUUID().toString();

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Override
    public final int hashCode() {
        return Objects.hashCode(uuid);
    }

    @Override
    public final boolean equals(Object obj) {

        if (!(obj instanceof UUIDgenerator)) {
            return false;
        }
        UUIDgenerator that = (UUIDgenerator) obj;
        return Objects.equals(this.uuid, that.uuid);
    }
}