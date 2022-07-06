package kit.personal.ssoresourceserver;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CheckTokenResponse {
    public Set<String> aud;
    public String user_name;
    public Set<String> scope;
    public boolean active;
    public Long exp;
    public Set<String> authorities;
    public String client_id;

    public Map<String, Object> getMap() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("aud", aud);
        map.put("user_name", user_name);
        map.put("scope", scope);
        map.put("active", active);
        if (exp != null){
            map.put("exp", Instant.ofEpochSecond(exp));
        }
        map.put("authorities", authorities);
        map.put("client_id", client_id);
        return map;
    }
}
