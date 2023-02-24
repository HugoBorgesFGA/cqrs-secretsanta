package br.tech.hugobp.cqrs.postgres.commandstore;

import br.tech.hugobp.cqrs.postgres.utils.NonRepeatableEntity;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
class CommandEntity extends NonRepeatableEntity {
    private LocalDateTime createdAt;
    private String name;
    private int version;
    private String commandId;
    private String entityId;
    private String data;
    private String error;
}
