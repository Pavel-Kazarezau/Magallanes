package io.kazarezau.magallanes.core;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jmolecules.ddd.annotation.Identity;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public abstract class BaseEntity<T> {

    @Identity
    private T id;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    protected BaseEntity(final T id) {
        this.id = id;
    }
}
