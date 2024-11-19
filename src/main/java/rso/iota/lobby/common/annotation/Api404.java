package rso.iota.lobby.common.annotation;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.core.annotation.AliasFor;
import rso.iota.lobby.dto.OutError;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@ApiResponse(
        responseCode = "404",
        content = @Content(schema = @Schema(implementation = OutError.class))
)
public @interface Api404 {
    @AliasFor(
            annotation = ApiResponse.class,
            attribute = "description"
    ) String description() default "Not found.";
}
