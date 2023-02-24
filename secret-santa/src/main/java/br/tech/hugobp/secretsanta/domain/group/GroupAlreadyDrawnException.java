package br.tech.hugobp.secretsanta.domain.group;

import br.tech.hugobp.cqrs.command.exceptions.BusinessException;

public class GroupAlreadyDrawnException extends BusinessException {

    public GroupAlreadyDrawnException(Group group) {
        super(String.format("The group %s was already drawn", group.getId()));
    }
}
