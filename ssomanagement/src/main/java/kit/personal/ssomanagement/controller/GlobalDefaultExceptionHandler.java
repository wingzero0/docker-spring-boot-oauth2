package kit.personal.ssomanagement.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import kit.personal.ssomanagement.controller.exception.ResourceNotFoundException;
import kit.personal.ssomanagement.controller.exception.WrongParameterException;

@ControllerAdvice
class GlobalDefaultExceptionHandler {
    private static Logger LOG = LoggerFactory.getLogger(GlobalDefaultExceptionHandler.class);

    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public Map<String, String> defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
        LOG.error("getting error");
        LOG.error("in defualErrorHandler" + req.getServletPath());
        if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null)
            throw e;

        Map<String, String> ret = new HashMap<String, String>();
        ret.put("contact", "please contact site admin");
        return ret;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseBody
    public Object handleResourceNotFoundException(HttpServletRequest req, Exception ex) {
        Map<String, String> ret = new HashMap<String, String>();
        ret.put("ret", ex.getMessage());
        return ret;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(WrongParameterException.class)
    @ResponseBody
    public Object handleBadRequest(HttpServletRequest req, Exception ex) {
        Map<String, String> ret = new HashMap<String, String>();
        ret.put("ret", ex.getMessage());
        return ret;
    }
}
