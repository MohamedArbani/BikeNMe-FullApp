package ma.ac.emi.ginfo.restfull.entities;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Mail {
    private String from;
    private String to;
    private String content;
    private String subject;
}
