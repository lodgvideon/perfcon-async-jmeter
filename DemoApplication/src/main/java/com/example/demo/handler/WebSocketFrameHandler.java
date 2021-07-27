package com.example.demo.handler;

import com.example.demo.ws.ChannelService;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import org.slf4j.LoggerFactory;


/**
 * 6   * Echoes uppercase content of text frames.
 * 7
 */

public class WebSocketFrameHandler extends SimpleChannelInboundHandler<WebSocketFrame> {
    private final static org.slf4j.Logger LOG = LoggerFactory.getLogger(WebSocketFrameHandler.class);

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        LOG.info("CHANNEL REGISTERED: {}", ctx.channel().id().asShortText());
        ChannelService.addChannel(ctx.channel());
        ctx.fireChannelRegistered();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, WebSocketFrame frame) throws Exception {
        // ping and pong frames already handled

//        if (frame instanceof TextWebSocketFrame) {
//            // Send the uppercase string back.
//            String request = ((TextWebSocketFrame) frame).text();
//            ctx.channel().writeAndFlush(new TextWebSocketFrame(request.toUpperCase(Locale.US)));
//        } else {
//            String message = "unsupported frame type: " + frame.getClass().getName();
//            throw new UnsupportedOperationException(message);
//        }
    }
}
