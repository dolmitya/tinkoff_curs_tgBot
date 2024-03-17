package edu.java.dao.implementations;

import edu.java.dao.implementations.JdbcLinkDao;
import edu.java.dto.jdbc.LinkDto;
import edu.java.errors.DuplicateLinkException;
import edu.java.scrapper.IntegrationTest;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.OffsetDateTime;
import java.util.List;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.Assert.assertThrows;



class JdbcLinkDaoTest extends IntegrationTest {
    @Autowired
    private JdbcLinkDao jdbcLinkDao;

    @Test
    @Transactional
    @Rollback
    void add() throws URISyntaxException {
        OffsetDateTime data = OffsetDateTime.parse("2022-01-01T10:10:00+00:00");
        LinkDto linkDto=new LinkDto(new URI("https://github.com/"),data,data);
        jdbcLinkDao.add(linkDto);
        assertThat(jdbcLinkDao.findAll().size()).isEqualTo(1);
        var exception = assertThrows(DuplicateLinkException.class, () -> jdbcLinkDao.add(linkDto));
        assertThat(exception.getMessage()).isEqualTo("Введена существующая ссылка!");
    }

    @Test
    @Transactional
    @Rollback
    void remove() throws URISyntaxException {
        OffsetDateTime data = OffsetDateTime.parse("2022-01-01T10:30:00+00:00");
        LinkDto linkDto=new LinkDto(new URI("https://github.com/"),data,data);
        jdbcLinkDao.add(linkDto);

        assertThat(jdbcLinkDao.findAll().size()).isEqualTo(1);
        jdbcLinkDao.remove(jdbcLinkDao.findAll().getFirst().getLinkId());
        assertThat(jdbcLinkDao.findAll().size()).isEqualTo(0);
    }

    @Test
    @Transactional
    @Rollback
    void findAll() throws URISyntaxException {

        OffsetDateTime data = OffsetDateTime.parse("2022-01-01T10:30:00+00:00");
        LinkDto linkDto1=new LinkDto(2L,new URI("https://github.com/"),data,data);
        data = OffsetDateTime.parse("2021-01-01T10:30:00+00:00");
        LinkDto linkDto2=new LinkDto(3L,new URI("https://stackoverflow.com/"),data,data);
        jdbcLinkDao.add(linkDto1);
        var x=jdbcLinkDao.findAll().getFirst().getLinkId();
        jdbcLinkDao.add(linkDto2);
        List<LinkDto> linkDtoList=List.of(linkDto1,linkDto2);
        assertThat(jdbcLinkDao.findAll()).isEqualTo(linkDtoList);
    }
}
