package com.example.demotest.scal;

import io.netty.channel.EventLoop;
import io.netty.handler.codec.http.FullHttpRequest;
import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class NettyHttpServletRequest extends HttpServletRequestWrapper {

    private final FullHttpRequest request;

    public NettyHttpServletRequest(FullHttpRequest request, EventLoop eventLoop) {
        super(null);
        this.request = request;
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        return new ServletInputStream() {

            private final ByteArrayInputStream inputStream = new ByteArrayInputStream(request.content().array());

            @Override
            public boolean isFinished() {
                return inputStream.available() == 0;
            }

            @Override
            public boolean isReady() {
                return true;
            }

            @Override
            public void setReadListener(ReadListener readListener) {
                // Not needed
            }

            @Override
            public int read() throws IOException {
                return inputStream.read();
            }
        };
    }

    @Override
    public int getContentLength() {
        return request.content().readableBytes();
    }

    @Override
    public long getContentLengthLong() {
        return request.content().readableBytes();
    }

    // Other methods...
}
