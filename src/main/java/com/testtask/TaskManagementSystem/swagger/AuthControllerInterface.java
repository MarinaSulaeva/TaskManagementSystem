package com.testtask.TaskManagementSystem.swagger;

import com.testtask.TaskManagementSystem.DTO.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@Tag(name = "auth", description = "Регистрация, получение и обновление токена")
public interface AuthControllerInterface {
    @Operation(summary = "Создание нового пользователя",
            description = "В тело необходимо передать username (логин), password (паспорт) и роль.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Успешное создание пользователя"),
            @ApiResponse(responseCode = "400", description = "Валидация не пройдена",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    ResponseEntity<?> createNewUser(@RequestBody @Valid Register register);
    @Operation(summary = "Получение токена доступа и рефреш-токена",
            description = "В тело необходимо передать username (логин), password (паспорт). " +
                    "При нахождении в базе данных пользователя и совпадения пароля выдаются " +
                    "токен доступа и рефреш-токен.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Получение токенов"),
            @ApiResponse(responseCode = "401", description = "Таких логина либо пароля в базе данных нет",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    ResponseEntity<JwtResponse> createToken(@RequestBody @Valid JwtRequest request);
    @Operation(summary = "Обновление токена доступа",
            description = "В тело необходимо передать рефреш-токен. " +
                    "При нахождении в базе данных токена и при его валидности выдается новый токен доступа.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Получение токена доступа"),
            @ApiResponse(responseCode = "401", description = "Рефреш-токен не валидный",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    ResponseEntity<JwtRefreshResponse> getRefreshToken(@RequestBody JwtRefreshToken jwtRefreshToken);
}
