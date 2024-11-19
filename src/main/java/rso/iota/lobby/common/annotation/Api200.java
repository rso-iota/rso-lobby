package rso.iota.lobby.common.annotation;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@ApiResponse(
        responseCode = "200"
)
public @interface Api200 {
    @AliasFor(
            annotation = ApiResponse.class,
            attribute = "description"
    ) String description() default "Ok";

    @AliasFor(
            annotation = ApiResponse.class,
            attribute = "content"
    ) Content[] content() default {};

}