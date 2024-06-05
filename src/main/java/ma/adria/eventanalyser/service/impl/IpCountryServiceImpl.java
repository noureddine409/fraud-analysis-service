package ma.adria.eventanalyser.service.impl;

import com.neovisionaries.i18n.CountryCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.adria.eventanalyser.service.IpCountryService;
import ma.adria.eventanalyser.utils.IpLocationResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Implementation of the {@link IpCountryService} interface for retrieving country names based on IP addresses.
 * Note: This implementation is not suitable for production environments and should be reimplemented.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class IpCountryServiceImpl implements IpCountryService {
    private final RestTemplate restTemplate;

    public static final String ipLocationEndpoint = "https://api.iplocation.net/?ip=";

    @Override
    public CountryCode getCountryName(String ipAddress) {
        IpLocationResponse response = restTemplate.getForObject(ipLocationEndpoint + ipAddress, IpLocationResponse.class);
        if (response != null) {
            log.info("response from ip lookup: {}", response);
            return CountryCode.getByAlpha2Code(response.getCountry_code2());
        }
        return null;
    }


}
