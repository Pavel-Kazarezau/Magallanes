package io.kazarezau.magallanes.core;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.jmolecules.ddd.annotation.Identity;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public abstract class BaseEntity<T> {

    @Identity
    @EqualsAndHashCode.Exclude
    private T id;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    protected BaseEntity(final T id) {
        this.id = id;
    }
}
