package edu.java.clients;

import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import edu.java.ScrapperApplication;
import edu.java.clients.interfaces.UpdateLinkService;
import edu.java.scrapper.IntegrationTest;
import java.util.ArrayList;
import java.util.List;
import org.example.dto.request.SendUpdateRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {ScrapperApplication.class})
@WireMockTest
@DirtiesContext
public class BotTest extends IntegrationTest {
    @Autowired
    private UpdateLinkService getBotClient;

    @Autowired
    GitHubClient gitHubClient;
    @RegisterExtension
    static WireMockExtension wireMockExtension = WireMockExtension.newInstance()
        .options(wireMockConfig().dynamicPort().dynamicPort()).build();

    @DynamicPropertySource
    public static void setUpMockBaseUrl(DynamicPropertyRegistry registry) {
        registry.add("app.base-url-bot", wireMockExtension::baseUrl);
    }

    @DynamicPropertySource
    public static void setType(DynamicPropertyRegistry registry) {
        registry.add("app.use-queue", () -> "false");
    }

    @Test
    public void updates() {
        List<Long> list = new ArrayList<>();
        list.addFirst(2L);
        var request = new SendUpdateRequest(1L, "https://api.github.com", "лол", list);
        wireMockExtension.stubFor(post(urlEqualTo("/updates"))
            .willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-Type", "application/json")
            ));

        assertDoesNotThrow(() -> getBotClient.updates(request));
    }
}
