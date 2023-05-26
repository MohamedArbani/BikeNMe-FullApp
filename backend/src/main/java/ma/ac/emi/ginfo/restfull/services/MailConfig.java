package ma.ac.emi.ginfo.restfull.services;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MailConfig {

    @Value("${spring.mail.host}")
    public String host;
    @Value("${spring.mail.port}")
    public int port;
    @Value("${spring.mail.username}")
    public String username;
    @Value("${spring.mail.password}")
    public String password;

    @Value("${backendUrl.origin}")
    public String url;

    @Override
    public String toString() {
        return "MailConfig{" +
                "host='" + host + '\'' +
                ", port=" + port +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
