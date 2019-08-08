package pl.coderslab.charity.error;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@ControllerAdvice
public class ControllerExceptionHandler {

    private static Logger log = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public void notFoundHandler(HttpServletRequest httpServletRequest,
                                HttpServletResponse httpServletResponse) throws IOException, ServletException {
        {
            log.debug("Item not found. HTTP 500 returned.");

            httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/500");

        }

    }
}