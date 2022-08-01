package kit.personal.ssomanagement.controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ErrorHandlerController implements ErrorController {
    private static Logger LOG = LoggerFactory.getLogger(ErrorHandlerController.class);

    @RequestMapping("/error")
    public Object handleError(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        Object forward = request.getAttribute(RequestDispatcher.FORWARD_SERVLET_PATH);
        LOG.debug("in ErrorHandler request:" + request.getServletPath());
        LOG.debug("in ErrorHandler forward:" + forward.toString());

        if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString());
            if (statusCode == HttpStatus.NOT_FOUND.value()) { // all manully throwed excpetion should be catched in
                                                              // GlobalDefaultExceptionHandler, if the program run at
                                                              // this point,
                                                              // it should be the routing not found;
                // if (forward != null) {
                // Pattern p = Pattern.compile("api");
                // Matcher m = p.matcher(forward.toString());
                // if (m.find()){
                // LOG.debug("get api");
                // return "api resource not found"; // use white label error
                // } else {
                // LOG.debug("no api");
                // }
                // }
                RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/index.html");
                dispatcher.forward(request, response);
                return "forward:/index.html";
            }

        }
        return "unkonw error"; // use white label error
    }
}
