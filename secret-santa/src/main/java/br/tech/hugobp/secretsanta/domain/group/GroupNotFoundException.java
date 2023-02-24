package br.tech.hugobp.secretsanta.domain.group;

import br.tech.hugobp.cqrs.command.exceptions.BusinessException;

public class GroupNotFoundException extends BusinessException {

    public GroupNotFoundException(String groupId) {
        super(String.format("Could not find a group with following id: %s", groupId));
    }
}
