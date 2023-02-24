package br.tech.hugobp.secretsanta.cqrs.command.creategroup;

import br.tech.hugobp.cqrs.event.EventPublisher;
import br.tech.hugobp.secretsanta.cqrs.event.GroupCreatedEvent;
import br.tech.hugobp.secretsanta.domain.group.Group;
import br.tech.hugobp.secretsanta.domain.repositories.group.GroupRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateGroupHandlerTest {

    @Mock
    GroupRepository groupRepository;

    @Mock
    EventPublisher eventPublisher;

    @Mock
    CreateGroupCommand createGroupCommand;

    @InjectMocks
    CreateGroupHandler createGroupHandler;

    @Test
    public void test_group_creation_is_ignored_if_group_already_exists() {
        final String idOfExistentGroup = UUID.randomUUID().toString();

        when(createGroupCommand.getId()).thenReturn(idOfExistentGroup);
        when(groupRepository.findById(eq(idOfExistentGroup))).thenReturn(mock(Group.class));

        createGroupHandler.process(createGroupCommand, eventPublisher);

        verify(groupRepository, never()).upsert(any());
        verify(eventPublisher, never()).publish(any());
    }

    @Test
    public void test_group_creation_is_not_ignored_if_group_id_does_not_exist() {
        final String newGroupId = UUID.randomUUID().toString();

        when(createGroupCommand.getId()).thenReturn(newGroupId);
        when(createGroupCommand.getName()).thenReturn("Group name");

        when(groupRepository.findById(eq(newGroupId))).thenReturn(null);

        createGroupHandler.process(createGroupCommand, eventPublisher);

        final ArgumentCaptor<Group> newGroupCaptor = ArgumentCaptor.forClass(Group.class);
        verify(groupRepository, times(1)).upsert(newGroupCaptor.capture());
        final Group newGroup = newGroupCaptor.getValue();

        assertNotNull(newGroup);
        assertEquals(newGroupId, newGroup.getId());
        assertEquals("Group name", newGroup.getName());

        final ArgumentCaptor<GroupCreatedEvent> groupCreatedEventArgumentCaptor = ArgumentCaptor.forClass(GroupCreatedEvent.class);
        verify(eventPublisher, times(1)).publish(groupCreatedEventArgumentCaptor.capture());

        final GroupCreatedEvent groupCreatedEvent = groupCreatedEventArgumentCaptor.getValue();
        assertNotNull(groupCreatedEvent);
        assertEquals(createGroupCommand.getName(), groupCreatedEvent.getName());
    }
}