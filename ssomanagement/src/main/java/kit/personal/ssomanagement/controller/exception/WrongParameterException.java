package kit.personal.ssomanagement.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class WrongParameterException extends RuntimeException {
    public WrongParameterException(String message) {
        super(message);
    }
    public WrongParameterException() {
        super();
    }
}