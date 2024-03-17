package edu.java.client;

import edu.java.dto.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

@RestController public class StackoverflowClient {
    @Autowired @Qualifier("getStackOverflowClient") private WebClient client;

    @GetMapping("/questions/{ids}") public Question getQuestion(@PathVariable long ids) {
        return client.get().uri("/questions/{ids}?site=stackoverflow", ids).retrieve().onStatus(
            HttpStatusCode::is4xxClientError,
            error -> Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Link is not valid"))
        ).onStatus(
            HttpStatusCode::is5xxServerError,
            error -> Mono.error(new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error"))
        ).bodyToMono(Question.class).block();
    }
}
