package org.example.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import org.jetbrains.annotations.NotNull;

public record ApiErrorResponse(
    @Schema(description = "Описание ошибки", example = "Server Error")
    @NotNull
    String description,
    @Schema(description = "Код ошибки", example = "500")
    String code,
    @Schema(description = "Класс ошибки", example = "Exception")
    String exceptionName,
    @Schema(description = "Сообщение ошибки", example = "Неправильный тип поля")
    String exceptionMessage,
    @Schema(description = "Трассировка стека", example = "[org.springframework.web.method.annotation."
        + "HandlerMethodValidationException: 400 BAD_REQUEST]")
    String[] stackTrace
) {
}
