package br.jad.comparator.domain;

import br.jad.comparator.commons.ComparatorType;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Composite primary key for ComparatorRecord
 * @see ComparatorRecord
 */
@Embeddable
public class ComparatorIdentity implements Serializable {

    @NotNull
    private Long comparatorId;

    @Enumerated(EnumType.STRING)
    @NotNull
    private ComparatorType type;

    public ComparatorIdentity() { }

    public ComparatorIdentity(Long comparatorId, ComparatorType type) {
        this.comparatorId = comparatorId;
        this.type = type;
    }

    public Long getComparatorId() {
        return comparatorId;
    }

    public void setComparatorId(Long comparatorId) {
        this.comparatorId = comparatorId;
    }

    public ComparatorType getType() {
        return type;
    }

    public void setType(ComparatorType type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ComparatorIdentity that = (ComparatorIdentity) o;

        return !(comparatorId != null ? !comparatorId.equals(that.comparatorId) : that.comparatorId != null) && !(type != null ? !type.equals(that.type) : that.type != null);

    }

    @Override
    public int hashCode() {
        int result = comparatorId != null ? comparatorId.hashCode() : 0;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }
}
