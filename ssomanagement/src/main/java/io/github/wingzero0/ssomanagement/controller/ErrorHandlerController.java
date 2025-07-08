package io.github.wingzero0.ssomanagement.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@Hidden
public class ErrorHandlerController implements ErrorController {
    private static Logger LOG = LoggerFactory.getLogger(ErrorHandlerController.class);

    @RequestMapping("/error")
    public void handleError(HttpServletRequest request, HttpServletResponse response, Exception e) throws Exception {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        Object message = request.getAttribute(RequestDispatcher.ERROR_MESSAGE);
        Object forward = request.getAttribute(RequestDispatcher.FORWARD_SERVLET_PATH);
        LOG.debug("in ErrorHandler request:" + request.getServletPath());
        if (message != null) {
            LOG.debug("in ErrorHandler message:" + message.toString());
        }
        if (forward != null) {
            LOG.debug("in ErrorHandler forward:" + forward.toString());
        }
        if (e != null) {
            LOG.debug("in ErrorHandler e:" + e.getMessage());
        }

        if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString());

            if (statusCode == HttpStatus.NOT_FOUND.value()) { // all manully throwed excpetion should be catched in
                                                              // GlobalDefaultExceptionHandler, if the program run at
                                                              // this point, it should be the routing not found;
                RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/index.html");
                dispatcher.forward(request, response);
            }
        }
    }
}
