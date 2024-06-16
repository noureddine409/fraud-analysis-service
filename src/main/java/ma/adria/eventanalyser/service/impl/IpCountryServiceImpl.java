package ma.adria.eventanalyser.service.impl;

import com.neovisionaries.i18n.CountryCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.adria.eventanalyser.service.IpCountryService;
import ma.adria.eventanalyser.utils.IpLocationResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.*;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Implementation of the {@link IpCountryService} interface for retrieving country names based on IP addresses.
 * Note: This implementation is not suitable for production environments and should be reimplemented.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class IpCountryServiceImpl implements IpCountryService {

    private final RestTemplate restTemplate;
    private final Map<String, CountryCode> ipToCountryCodeCache = new ConcurrentHashMap<>();
    public static final String IP_LOCATION_ENDPOINT = "https://api.iplocation.net/?ip=";

    /**
     * Retrieves the country code for a given IP address.
     * Manually caches the result to avoid repeated calls for the same IP address.
     *
     * @param ipAddress the IP address to lookup
     * @return the CountryCode corresponding to the IP address, or null if not found
     */
    @Override
    public CountryCode getCountryName(String ipAddress) {
        log.info("Fetching country code for IP address: {}", ipAddress);

        // Check if the IP address is already cached
        CountryCode cachedCountryCode = ipToCountryCodeCache.get(ipAddress);
        if (cachedCountryCode != null) {
            log.info("Found cached country code for IP address {}: {}", ipAddress, cachedCountryCode);
            return cachedCountryCode;
        }

        // Perform the API call if not cached
        try {
            IpLocationResponse response = restTemplate.getForObject(IP_LOCATION_ENDPOINT + ipAddress, IpLocationResponse.class);
            if (response != null) {
                CountryCode countryCode = CountryCode.getByAlpha2Code(response.getCountry_code2());
                if (countryCode != null) {
                    // Cache the result
                    ipToCountryCodeCache.put(ipAddress, countryCode);
                    log.info("Caching country code for IP address {}: {}", ipAddress, countryCode);
                } else {
                    log.warn("Cannot find country code for IP address {} in the response: {}", ipAddress, response);
                }
                return countryCode;
            } else {
                log.warn("No response received for IP address lookup: {}", ipAddress);
                return null;
            }
        } catch (ResourceAccessException ex) {
            log.error("Failed to fetch country code for IP address {} due to network issue", ipAddress, ex);
            return null;
        } catch (HttpClientErrorException | HttpServerErrorException ex) {
            log.error("HTTP error occurred while fetching country code for IP address {}: {}", ipAddress, ex.getStatusCode(), ex);
            return null;
        } catch (RestClientException ex) {
            log.error("Error occurred while fetching country code for IP address {}", ipAddress, ex);
            return null;
        }
    }
}
