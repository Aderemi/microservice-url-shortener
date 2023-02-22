package com.xyz.redirect.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xyz.redirect.dto.FakeUrl;
import com.xyz.redirect.dto.KafkaMessage;
import com.xyz.redirect.dto.RealUrl;
import com.xyz.redirect.entity.URL;
import com.xyz.redirect.repository.UrlRepo;
import com.xyz.redirect.utility.KafkaMessageType;
import com.xyz.redirect.utility.UrlShortener;

import lombok.extern.slf4j.Slf4j;
import lombok.Data;
import lombok.AllArgsConstructor;


@Service
@Slf4j
@Data
@AllArgsConstructor
public class RoutingService {
    @Autowired
    private final UrlRepo urlRepo;

    @Autowired
    private final KafkaService kafkaService;

    private URL get(Long id) {
        log.info(String.format("Fetching Url from database for Id %d", id));
        URL url = urlRepo.findUrlByRefID(id).get(0);
        return url;
    }

    /**
     * Uses the Base62 encoded to convert to Base10 number and fetches the corresponding record from the database
     *
     * @param shortenString Base62 encoded string
     * @return RealUrl object
     */
    public RealUrl getRealUrl(String shortenString) {
        log.debug("Converting Base 62 string %s to Base 10 id");
        Long id = UrlShortener.strToId(shortenString);
        log.info(String.format("Converted Base 62 string %s to Base 10 id %s", shortenString, id));

        log.info(String.format("Retrieving full url for %d", id));
        return new RealUrl(this.get(id).getRealUrl());
    }
}
