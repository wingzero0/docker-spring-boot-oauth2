package kit.personal.ssoresourceserver;

import org.springframework.security.core.AuthenticationException;

public class IntrospectException extends AuthenticationException {

    public IntrospectException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public IntrospectException(String msg) {
        super(msg);
    }

}
