package kit.personal.ssomanagementapplicationrunner;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import kit.personal.ssomanagementapplicationrunner.service.AppUserRoleService;
import kit.personal.ssomanagementapplicationrunner.service.AppUserService;
import kit.personal.ssomanagementapplicationrunner.service.RegisteredClientService;

@Component
@Profile("!test")
public class DBRunner implements ApplicationRunner {
    @Autowired
    private RegisteredClientService registeredClientService;
    @Autowired
    private AppUserRoleService appUserRoleService;
    @Autowired
    private AppUserService appUserService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Set<String> optionNames = args.getOptionNames();
        if (optionNames.contains("clientId")
                && optionNames.contains("clientSecret")
                && optionNames.contains("redirectUri")
                && optionNames.contains("scope")) {
            String clientId = args.getOptionValues("clientId").get(0);
            String clientSecret = args.getOptionValues("clientSecret").get(0);
            List<String> redirectUris = args.getOptionValues("redirectUri");
            List<String> scopes = args.getOptionValues("scope");
            registeredClientService.createRegisteredClient(clientId, clientSecret, redirectUris, scopes);
        } else if (optionNames.contains("clientId")
                && optionNames.contains("username")
                && optionNames.contains("userRole")) {
            String clientId = args.getOptionValues("clientId").get(0);
            String username = args.getOptionValues("username").get(0);
            List<String> userRoles = args.getOptionValues("userRole");
            appUserRoleService.createAppUserRole(clientId, username, userRoles);
        } else if (optionNames.contains("username")
                && optionNames.contains("displayName")
                && optionNames.contains("password")
                && optionNames.contains("email")) {
            String username = args.getOptionValues("username").get(0);
            String displayName = args.getOptionValues("displayName").get(0);
            String password = args.getOptionValues("password").get(0);
            String email = args.getOptionValues("email").get(0);
            appUserService.createAppUser(username, displayName, password, email);
        } else {
            System.out.println("for creating registered client (sso client):");
            System.out.println(
                    """
                            usage:
                                java -jar ssomanagement-application-runner.jar --clientId=xxx --clientSecret=xxx \\
                                --redirectUri=http://127.0.0.1:8080/ --scope=profile --scope=role
                            or:
                                mvn spring-boot:run -pl ssomanagement-application-runner -am \\
                                -Dspring-boot.run.arguments="--clientId=xxx --clientSecret=xxx --redirectUri=http://127.0.0.1:8080/ --redirectUri=https://somedomain/ --scope=profile --scope=role"
                            """);

            System.out.println("for creating user:");
            System.out.println(
                    """
                            usage:
                                java -jar ssomanagement-application-runner.jar --username=xxx --displayName=xxx --password=xxx --email=xx@xx
                            or:
                                mvn spring-boot:run -pl ssomanagement-application-runner -am \\
                                -Dspring-boot.run.arguments="--username=xxx --displayName=xxx --password=xxx --email=xx@xx"
                            """);
            System.out.println("for creating user role:");
            System.out.println(
                    """
                            usage:
                                java -jar ssomanagement-application-runner.jar --clientId=xxx --username=xxx --userRole=ADMIN --userRole=GUEST
                            or:
                                mvn spring-boot:run -pl ssomanagement-application-runner -am \\
                                -Dspring-boot.run.arguments="--clientId=xxx --username=xxx --userRole=ADMIN --userRole=GUEST"
                            """);
        }
    }
}
