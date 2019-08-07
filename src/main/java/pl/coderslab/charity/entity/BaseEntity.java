package pl.coderslab.charity.entity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.util.Objects;
import java.util.UUID;

@MappedSuperclass
public abstract class BaseEntity {

    @Column(unique = true)
    private String uuid = UUID.randomUUID().toString();

    public String getUuid() {
        return uuid;
    }

    @Override
    public final int hashCode() {
        return Objects.hashCode(uuid);
    }

    @Override
    public final boolean equals(Object obj) {

        if (!(obj instanceof BaseEntity)) {
            return false;
        }
        BaseEntity that = (BaseEntity) obj;
        return Objects.equals(this.uuid, that.uuid);
    }
}