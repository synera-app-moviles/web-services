package synera.centralis.api.announcement.application.internal.commandservices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import synera.centralis.api.announcement.domain.model.aggregates.Announcement;
import synera.centralis.api.announcement.domain.model.commands.CreateAnnouncementCommand;
import synera.centralis.api.announcement.domain.model.commands.DeleteAnnouncementCommand;
import synera.centralis.api.announcement.domain.model.commands.UpdateAnnouncementCommand;
import synera.centralis.api.announcement.domain.services.AnnouncementCommandService;
import synera.centralis.api.announcement.infrastructure.persistence.jpa.repositories.AnnouncementRepository;
import synera.centralis.api.announcement.infrastructure.persistence.jpa.repositories.CommentRepository;

import java.util.Optional;
import java.util.UUID;

/**
 * Announcement Command Service Implementation
 * Handles command operations for announcements
 */
@Service
public class AnnouncementCommandServiceImpl implements AnnouncementCommandService {

    private final AnnouncementRepository announcementRepository;
    private final CommentRepository commentRepository;

    @Autowired
    public AnnouncementCommandServiceImpl(AnnouncementRepository announcementRepository,
                                        CommentRepository commentRepository) {
        this.announcementRepository = announcementRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    @Transactional
    public Optional<Announcement> handle(CreateAnnouncementCommand command) {
        try {
            var announcement = new Announcement(
                command.title(),
                command.description(),
                command.image(),
                command.priority(),
                command.createdBy()
            );

            var savedAnnouncement = announcementRepository.save(announcement);
            return Optional.of(savedAnnouncement);
        } catch (Exception e) {
            throw new RuntimeException("Error creating announcement: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional
    public Optional<Announcement> handle(UpdateAnnouncementCommand command) {
        return announcementRepository.findById(command.announcementId())
                .map(announcement -> {
                    try {
                        announcement.update(
                            command.title(),
                            command.description(),
                            command.image(),
                            command.priority()
                        );
                        return announcementRepository.save(announcement);
                    } catch (Exception e) {
                        throw new RuntimeException("Error updating announcement: " + e.getMessage(), e);
                    }
                });
    }

    @Override
    @Transactional
    public boolean handle(DeleteAnnouncementCommand command) {
        return announcementRepository.findById(command.announcementId())
                .map(announcement -> {
                    // Delete all comments associated with this announcement first
                    commentRepository.deleteByAnnouncementId(command.announcementId());
                    
                    // Delete the announcement
                    announcementRepository.delete(announcement);
                    return true;
                })
                .orElse(false);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(UUID announcementId) {
        return announcementRepository.existsById(announcementId);
    }
}