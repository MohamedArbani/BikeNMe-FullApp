package ma.ac.emi.ginfo.restfull.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Map;

@Service
public class MailContentBuilder {
    @Autowired
    private TemplateEngine templateEngine;

    public MailContentBuilder(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    String build(String template, Map<String,Object> templateModel) {
        Context context = new Context();
        context.setVariables(templateModel);
        return templateEngine.process(template, context);
    }
}
