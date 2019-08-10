package pl.coderslab.charity.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.source.InvalidConfigurationPropertyValueException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@ControllerAdvice
public class NotFoundHandler {
    private static Logger log = LoggerFactory.getLogger(NotFoundHandler.class);

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(InvalidConfigurationPropertyValueException.class)
    public void handleResourceNotFoundException(HttpServletRequest httpServletRequest,
                                                HttpServletResponse httpServletResponse,
                                                InvalidConfigurationPropertyValueException e) throws IOException, ServletException {
        log.error("Resource not found: " + e.getMessage());
        httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/400");
    }
}
