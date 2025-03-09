package com.example.myapplication.strategy;

import com.example.myapplication.util.Base62Encoder;
import org.springframework.stereotype.Component;

@Component
public class Base62UrlGenerator implements ShortUrlGenerator {

    @Override
    public String generateShortUrl(Long id) {
        String code = Base62Encoder.encode(id);
        // Pad the code to 8 characters with leading zeros if necessary
        return String.format("%-8s", code).replace(' ', '=');
    }
}
