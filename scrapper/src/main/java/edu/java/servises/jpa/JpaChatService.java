package edu.java.servises.jpa;

import edu.java.domain.implementations.jpa.JpaChatRepository;
import edu.java.domain.implementations.jpa.JpaLinkRepository;
import edu.java.entities.ChatEntity;
import edu.java.errors.ChatAlreadyExistsException;
import edu.java.errors.ChatNotExistsException;
import edu.java.servises.interfaces.TgChatService;
import java.time.OffsetDateTime;
import lombok.RequiredArgsConstructor;
import org.example.dto.response.StateResponse;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
public class JpaChatService implements TgChatService {
    private final JpaChatRepository jpaChatRepository;
    private final JpaLinkRepository jpaLinkRepository;

    @Override
    public void register(long tgChatId, String username) {

        if (jpaChatRepository.existsById(tgChatId)) {
            throw new ChatAlreadyExistsException("Чат уже создан!");
        }
        jpaChatRepository.saveAndFlush(new ChatEntity(tgChatId, OffsetDateTime.now(), username, "NONE"));
    }

    @Override
    public void unregister(long tgChatId) {
        if (!jpaChatRepository.existsById(tgChatId)) {
            throw new ChatNotExistsException("Чат не был создал ранее!");
        }
        var links = jpaChatRepository.findById(tgChatId).orElseThrow().getLinks();
        jpaChatRepository.deleteById(tgChatId);
        jpaChatRepository.flush();
        for (var link : links) {

            if (link.getChats().isEmpty()) {
                jpaLinkRepository.deleteById(link.getLinkId());
            }
        }
        jpaLinkRepository.flush();
    }

    @Override
    public void setState(long tgChatId, String state) {
        jpaChatRepository.findById(tgChatId).orElseThrow().setState(state);
        jpaChatRepository.flush();
    }

    @Override
    public Boolean isRegister(long tgChatId) {
        return jpaChatRepository.existsById(tgChatId);
    }

    @Override
    public StateResponse getState(long tgChatId) {
        return new StateResponse(jpaChatRepository.findById(tgChatId).orElseThrow().getState());
    }
}
