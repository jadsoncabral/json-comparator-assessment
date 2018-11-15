package br.jad.comparator.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * ComparatorRecord Entity for JPA Repository
 * @see ComparatorRepository
 */
@Entity
@Table( name = "COMPARATOR_RECORD" )
public class ComparatorRecord {

    @EmbeddedId
    private ComparatorIdentity id;

    @NotNull
    private String content;

    @NotNull
    @JsonFormat( pattern = "yyyy-MM-dd HH:mm:ss" )
    private LocalDateTime date;

    public ComparatorRecord() { }

    public ComparatorRecord(ComparatorIdentity id, String content, LocalDateTime date) {
        this.id = id;
        this.content = content;
        this.date = date;
    }

    public ComparatorIdentity getId() {
        return id;
    }

    public void setId(ComparatorIdentity id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
