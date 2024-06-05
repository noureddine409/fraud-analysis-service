package ma.adria.eventanalyser.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class IpLocationResponse {
    private String ip;
    private String ip_number;
    private String ip_version;
    private String country_name;
    private String country_code2;
    private String isp;
    private String response_code;
    private String response_message;
}
