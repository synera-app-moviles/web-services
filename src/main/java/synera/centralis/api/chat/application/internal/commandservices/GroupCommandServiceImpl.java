package synera.centralis.api.chat.application.internal.commandservices;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import synera.centralis.api.chat.domain.model.aggregates.Group;
import synera.centralis.api.chat.domain.model.commands.*;
import synera.centralis.api.chat.domain.services.GroupCommandService;
import synera.centralis.api.chat.infrastructure.persistence.jpa.repositories.GroupRepository;
import synera.centralis.api.chat.infrastructure.persistence.jpa.repositories.MessageRepository;

import java.util.Optional;
import java.util.UUID;

/**
 * Implementation of GroupCommandService.
 * Handles all group-related command operations with business logic and validation.
 */
@Slf4j
@Service
public class GroupCommandServiceImpl implements GroupCommandService {

    private final GroupRepository groupRepository;
    private final MessageRepository messageRepository;

    public GroupCommandServiceImpl(GroupRepository groupRepository, MessageRepository messageRepository) {
        this.groupRepository = groupRepository;
        this.messageRepository = messageRepository;
    }

    @Override
    @Transactional
    public Optional<Group> handle(CreateGroupCommand command) {
        try {
            log.info("Creating new group with name: {}", command.name());
            
            var group = new Group(
                    command.name(),
                    command.description(),
                    command.imageUrl(),
                    command.visibility(),
                    command.memberIds(),
                    command.createdBy()
            );

            var savedGroup = groupRepository.save(group);
            log.info("Successfully created group with ID: {}", savedGroup.getId());
            
            return Optional.of(savedGroup);
            
        } catch (Exception e) {
            log.error("Error creating group: {}", e.getMessage(), e);
            return Optional.empty();
        }
    }

    @Override
    @Transactional
    public Optional<Group> handle(UpdateGroupCommand command) {
        try {
            log.info("Updating group with ID: {}", command.groupId());
            
            var groupOptional = groupRepository.findById(command.groupId());
            if (groupOptional.isEmpty()) {
                log.warn("Group not found with ID: {}", command.groupId());
                return Optional.empty();
            }

            var group = groupOptional.get();
            group.updateGroup(command.name(), command.description(), command.imageUrl());
            
            var savedGroup = groupRepository.save(group);
            log.info("Successfully updated group with ID: {}", savedGroup.getId());
            
            return Optional.of(savedGroup);
            
        } catch (Exception e) {
            log.error("Error updating group: {}", e.getMessage(), e);
            return Optional.empty();
        }
    }

    @Override
    @Transactional
    public Optional<Group> handle(UpdateGroupVisibilityCommand command) {
        try {
            log.info("Updating visibility for group with ID: {}", command.groupId());
            
            var groupOptional = groupRepository.findById(command.groupId());
            if (groupOptional.isEmpty()) {
                log.warn("Group not found with ID: {}", command.groupId());
                return Optional.empty();
            }

            var group = groupOptional.get();
            group.updateVisibility(command.visibility());
            
            var savedGroup = groupRepository.save(group);
            log.info("Successfully updated visibility for group with ID: {}", savedGroup.getId());
            
            return Optional.of(savedGroup);
            
        } catch (Exception e) {
            log.error("Error updating group visibility: {}", e.getMessage(), e);
            return Optional.empty();
        }
    }

    @Override
    @Transactional
    public Optional<Group> handle(AddMemberToGroupCommand command) {
        try {
            log.info("Adding member {} to group {}", command.memberToAdd().userId(), command.groupId());
            
            var groupOptional = groupRepository.findById(command.groupId());
            if (groupOptional.isEmpty()) {
                log.warn("Group not found with ID: {}", command.groupId());
                return Optional.empty();
            }

            var group = groupOptional.get();
            group.addMember(command.memberToAdd());
            
            var savedGroup = groupRepository.save(group);
            log.info("Successfully added member to group with ID: {}", savedGroup.getId());
            
            return Optional.of(savedGroup);
            
        } catch (Exception e) {
            log.error("Error adding member to group: {}", e.getMessage(), e);
            return Optional.empty();
        }
    }

    @Override
    @Transactional
    public Optional<Group> handle(RemoveMemberFromGroupCommand command) {
        try {
            log.info("Removing member {} from group {}", command.memberToRemove().userId(), command.groupId());
            
            var groupOptional = groupRepository.findById(command.groupId());
            if (groupOptional.isEmpty()) {
                log.warn("Group not found with ID: {}", command.groupId());
                return Optional.empty();
            }

            var group = groupOptional.get();
            group.removeMember(command.memberToRemove());
            
            var savedGroup = groupRepository.save(group);
            log.info("Successfully removed member from group with ID: {}", savedGroup.getId());
            
            return Optional.of(savedGroup);
            
        } catch (Exception e) {
            log.error("Error removing member from group: {}", e.getMessage(), e);
            return Optional.empty();
        }
    }

    @Override
    @Transactional
    public Optional<UUID> handle(DeleteGroupCommand command) {
        try {
            log.info("Deleting group with ID: {}", command.groupId());
            
            if (!groupRepository.existsById(command.groupId())) {
                log.warn("Group not found with ID: {}", command.groupId());
                return Optional.empty();
            }

            // Delete all messages in the group first
            messageRepository.deleteByGroupId(command.groupId());
            
            // Delete the group
            groupRepository.deleteById(command.groupId());
            
            log.info("Successfully deleted group with ID: {}", command.groupId());
            return Optional.of(command.groupId());
            
        } catch (Exception e) {
            log.error("Error deleting group: {}", e.getMessage(), e);
            return Optional.empty();
        }
    }
}