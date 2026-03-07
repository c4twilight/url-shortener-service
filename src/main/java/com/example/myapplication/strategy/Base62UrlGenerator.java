package com.example.myapplication.strategy;

import com.example.myapplication.util.Base62Encoder;
import org.springframework.stereotype.Component;

@Component
public class Base62UrlGenerator implements ShortUrlGenerator {

    @Override
    public String generateShortUrl(Long id) {
        return Base62Encoder.encode(id);
    }
}
