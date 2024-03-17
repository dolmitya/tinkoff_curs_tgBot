package edu.java.dao.implementations;

import edu.java.dao.interfaces.ChatLinkRepository;
import edu.java.dto.jdbc.ChatLinkDto;
import edu.java.dto.jdbc.LinkDto;
import edu.java.errors.LinkWasTrackedException;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.net.URI;
import java.util.List;

@Repository
@AllArgsConstructor
public class JdbcChatLinkDao implements ChatLinkRepository {

    private final JdbcTemplate jdbcTemplate;

    @Transactional
    @Override
    public void add(ChatLinkDto chatLink) {
        try {
            jdbcTemplate.update("INSERT INTO chat_link (chat_id,link_id) VALUES (?,?)",
                chatLink.getChatId(), chatLink.getLinkId()
            );
        } catch (DataAccessException exception) {
            throw new LinkWasTrackedException("Данная ссылка уже отслеживается!");
        }
    }

    @Transactional
    @Override
    public void remove(ChatLinkDto chatLink) {
        jdbcTemplate.update("DELETE FROM chat_link WHERE chat_id=? AND link_id=?",
            chatLink.getChatId(), chatLink.getLinkId()
        );
    }

    @Transactional
    @Override
    public List<ChatLinkDto> findAll() {
        return jdbcTemplate.query("SELECT * FROM chat_link", new BeanPropertyRowMapper<>(ChatLinkDto.class));
    }

    @Transactional
    @Override
    public List<ChatLinkDto> getAllTgChatsByLinkId(long linkId) {
        return jdbcTemplate.query("SELECT * FROM chat_link WHERE link_id=?", new BeanPropertyRowMapper<>(ChatLinkDto.class),linkId);
    }

    @Override
    public List<LinkDto> getAllLinkByChat(long chatId) {
        return jdbcTemplate.query("SELECT * FROM link WHERE link_id IN (SELECT link_id FROM chat_link WHERE chat_id=?)",
            new BeanPropertyRowMapper<>(LinkDto.class),chatId);
    }
}
