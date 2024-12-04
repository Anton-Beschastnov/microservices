package by.micro.contacts_service.config;

import feign.Response;
import feign.codec.ErrorDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

public class FeignErrorDecoder implements ErrorDecoder {
    private final static Logger LOGGER = LoggerFactory.getLogger(FeignErrorDecoder.class);

    @Override
    public Exception decode(String methodKey, Response response) {
        switch (response.status()) {
            case 500:
                LOGGER.debug("PhoneCodeService is not available");
            case 400:
                //TODO
                LOGGER.debug("PhoneCodeService is not available");
                break;
            case 404:
                if (methodKey.contains("getCodeInfo")) {
                    return new ResponseStatusException(HttpStatusCode.valueOf(404), "PhoneCodeService is not available");
                }
                LOGGER.debug("PhoneCodeService is not available");
                break;
            default:
                return new RuntimeException(response.reason());
        }
        return null;
    }
}
