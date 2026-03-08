package com.example.myapplication.strategy;

import com.example.myapplication.util.Base62Encoder;
import org.springframework.stereotype.Component;

@Component
public class Base62UrlGenerator implements ShortUrlGenerator {

    @Override
    public String generateShortUrl(Long id) {
        String code = Base62Encoder.encode(id);
        // Keep fixed-length output (learning reference): 8-char code padded with '='.
        return String.format("%-8s", code).replace(' ', '=');
    }
}
