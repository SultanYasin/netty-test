package com.example.demotest.scal;

import io.netty.channel.EventLoop;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.WriteListener;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
public class NettyHttpServletResponse extends HttpServletResponseWrapper {

    private final FullHttpResponse response;
    private final ByteArrayOutputStream outputStream;
    private final ServletOutputStream servletOutputStream;

    public NettyHttpServletResponse(FullHttpResponse response, EventLoop eventLoop) {
        super(null);
        this.response = response;
        this.outputStream = new ByteArrayOutputStream();
        this.servletOutputStream = new ServletOutputStream() {

            @Override
            public boolean isReady() {
                return true;
            }

            @Override
            public void setWriteListener(WriteListener writeListener) {
                // Not needed
            }

            @Override
            public void write(int b) throws IOException {
                outputStream.write(b);
            }
        };
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        return servletOutputStream;
    }

    @Override
    public void setStatus(int sc) {
        response.setStatus(HttpResponseStatus.valueOf(sc));
    }


    @Override
    public void setHeader(String name, String value) {
        response.headers().set(name, value);
    }

    @Override
    public void addHeader(String name, String value) {
        response.headers().add(name, value);
    }

    @Override
    public void sendError(int sc) throws IOException {
        response.setStatus(HttpResponseStatus.valueOf(sc));
    }

    @Override
    public void sendError(int sc, String msg) throws IOException {
        response.setStatus(HttpResponseStatus.valueOf(sc));
    }
    public FullHttpResponse getNettyResponse() {
        response.content().writeBytes(outputStream.toByteArray());
        return response;
    }
}
