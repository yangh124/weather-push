package com.yh.weatherpush.config.redis;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONReader;
import com.alibaba.fastjson2.JSONWriter;
import com.alibaba.fastjson2.filter.Filter;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import org.redisson.client.codec.BaseCodec;
import org.redisson.client.protocol.Decoder;
import org.redisson.client.protocol.Encoder;

import java.io.IOException;

class FastJson2Codec extends BaseCodec {

    private static final Filter autoTypeFilter = JSONReader.autoTypeFilter(
            // 按需加上需要支持自动类型的类名前缀，范围越小越安全
            "com.", "org.", "java."
    );

    public static final JSONWriter.Feature[] writeFeatures = new JSONWriter.Feature[]{
            JSONWriter.Feature.WriteClassName,
            JSONWriter.Feature.FieldBased,
            JSONWriter.Feature.ReferenceDetection,
            JSONWriter.Feature.NotWriteDefaultValue,
            JSONWriter.Feature.WriteNameAsSymbol,
            JSONWriter.Feature.WriteEnumsUsingName,
            JSONWriter.Feature.WriteMapNullValue
    };

    public static final JSONReader.Feature[] readFeatures = new JSONReader.Feature[]{
            JSONReader.Feature.FieldBased, JSONReader.Feature.SupportArrayToBean
    };

    private final Encoder encoder = in -> {
        ByteBuf out = ByteBufAllocator.DEFAULT.buffer();
        try {
            ByteBufOutputStream os = new ByteBufOutputStream(out);
            JSON.writeTo(os, in, writeFeatures);
            return os.buffer();
        } catch (Exception e) {
            out.release();
            throw new IOException(e);
        }
    };

    private final Decoder<Object> decoder =
            (buf, state) -> {
                try (ByteBufInputStream bufInputStream = new ByteBufInputStream(buf)) {
                    byte[] bytes = bufInputStream.readAllBytes();
                    return JSON.parseObject(bytes, Object.class, autoTypeFilter
                            , readFeatures);
                }
            };

    @Override
    public Decoder<Object> getValueDecoder() {
        return decoder;
    }

    @Override
    public Encoder getValueEncoder() {
        return encoder;
    }
}