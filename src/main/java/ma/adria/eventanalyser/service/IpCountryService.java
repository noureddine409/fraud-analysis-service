package ma.adria.eventanalyser.service;

import com.neovisionaries.i18n.CountryCode;

/**
 * A service for retrieving country names based on IP addresses.
 */
public interface IpCountryService {
    /**
     * Gets the country name corresponding to the provided IP address.
     *
     * @param ipAddress The IP address to look up.
     * @return The country code associated with the IP address.
     */
    CountryCode getCountryName(String ipAddress);
}
