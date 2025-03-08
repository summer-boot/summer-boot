package io.github.summer.boot.spi.subscriber;

import feign.FeignException;
import feign.Response;
import feign.codec.StringDecoder;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * Service Provider Interface Result Decoder
 *
 * @author changebooks@qq.com
 */
public class SpiDecoder extends StringDecoder {

    @Override
    public Object decode(Response response, Type type) throws FeignException, IOException {
        return super.decode(response, String.class);
    }

}
