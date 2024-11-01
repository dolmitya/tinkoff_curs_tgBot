package edu.java.bot.client;

import org.example.dto.request.AddLinkRequest;
import org.example.dto.response.LinkResponse;
import org.example.dto.response.ListLinksResponse;
import org.example.dto.response.StateResponse;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

@SuppressWarnings("MultipleStringLiterals")
public class ScrapperClient {
    private final WebClient webClient;

    public ScrapperClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public void createChat(Long chat, String username) {
        webClient.post().uri("/tg-chat/{id}", chat).accept(MediaType.APPLICATION_JSON)
            .body(Mono.just(username), String.class).retrieve().onStatus(
                HttpStatusCode::is4xxClientError,
                error -> Mono.error(new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Link is not valid"
                ))
            ).onStatus(
                HttpStatusCode::is5xxServerError,
                error -> Mono.error(new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error"
                ))
            ).bodyToMono(Void.class).block();
    }

    public void deleteChat(Long chat) {
        webClient.delete().uri("/tg-chat/{id}", chat).accept(MediaType.APPLICATION_JSON)
            .retrieve().onStatus(
                HttpStatusCode::is4xxClientError,
                error -> Mono.error(new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Link is not valid"
                ))
            ).onStatus(
                HttpStatusCode::is5xxServerError,
                error -> Mono.error(new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error"
                ))
            ).bodyToMono(Void.class).block();
    }

    public ListLinksResponse getLinks(Long chat) {
        return webClient.get().uri("/links", chat).accept(MediaType.APPLICATION_JSON)
            .header("Tg-Chat-Id", chat.toString()).retrieve().onStatus(
                HttpStatusCode::is4xxClientError,
                error -> Mono.error(new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Link is not valid"
                ))
            ).onStatus(
                HttpStatusCode::is5xxServerError,
                error -> Mono.error(new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error"
                ))
            ).bodyToMono(ListLinksResponse.class).block();
    }

    public LinkResponse setLink(Long chat, AddLinkRequest addLinkRequest) {
        return webClient.post().uri("/links").accept(MediaType.APPLICATION_JSON)
            .header("Tg-Chat-Id", chat.toString()).body(Mono.just(addLinkRequest), AddLinkRequest.class)
            .retrieve().onStatus(
                HttpStatusCode::is4xxClientError,
                error -> Mono.error(new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Link is not valid"
                ))
            ).onStatus(
                HttpStatusCode::is5xxServerError,
                error -> Mono.error(new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error"
                ))
            ).bodyToMono(LinkResponse.class).block();
    }

    public LinkResponse deleteLink(Long chat, AddLinkRequest addLinkRequest) {
        return webClient.method(HttpMethod.DELETE).uri("/links").accept(MediaType.APPLICATION_JSON)
            .header("Tg-Chat-Id", chat.toString()).body(Mono.just(addLinkRequest), AddLinkRequest.class)
            .retrieve().onStatus(
                HttpStatusCode::is4xxClientError,
                error -> Mono.error(new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Link is not valid"
                ))
            ).onStatus(
                HttpStatusCode::is5xxServerError,
                error -> Mono.error(new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error"
                ))
            ).bodyToMono(LinkResponse.class).block();
    }

    public void setState(Long chat, String state) {
        webClient.post().uri("/tg-chat/state/{id}/{state}", chat, state).accept(MediaType.APPLICATION_JSON)
            .retrieve().onStatus(
                HttpStatusCode::is4xxClientError,
                error -> Mono.error(new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Link is not valid"
                ))
            ).onStatus(
                HttpStatusCode::is5xxServerError,
                error -> Mono.error(new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error"
                ))
            ).bodyToMono(Void.class).block();
    }

    public StateResponse getState(Long chat) {
        return webClient.get().uri("/tg-chat/state/{id}", chat).accept(MediaType.APPLICATION_JSON)
            .retrieve().onStatus(
                HttpStatusCode::is4xxClientError,
                error -> Mono.error(new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Link is not valid"
                ))
            ).onStatus(
                HttpStatusCode::is5xxServerError,
                error -> Mono.error(new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error"
                ))
            ).bodyToMono(StateResponse.class).block();
    }

    public Boolean isRegister(Long chat) {
        return webClient.get().uri("/tg-chat/check-reg/{id}", chat).accept(MediaType.APPLICATION_JSON).retrieve()
            .onStatus(
                HttpStatusCode::is5xxServerError,
                error -> Mono.error(new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error"
                ))
            ).bodyToMono(Boolean.class).block();
    }
}
