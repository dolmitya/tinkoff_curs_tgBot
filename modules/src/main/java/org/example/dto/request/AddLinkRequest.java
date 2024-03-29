package org.example.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import java.net.URI;
import org.jetbrains.annotations.NotNull;

public record AddLinkRequest(
    @Schema(description = "Ссылка", example = "https://api.github.com/dolmitya")
    @NotNull
    URI link
) {
}
