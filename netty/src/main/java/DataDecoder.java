import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class DataDecoder extends ByteToMessageDecoder {
    private ParseData parseData;

    public DataDecoder(ParseData parseData) {
        this.parseData = parseData;
    }

    @Override
    protected void decode(ChannelHandlerContext arg0, ByteBuf in, List<Object> list) throws Exception {
        //System.err.println(parseData.getDrcProInfo().getParseDataName() + "--------解码器正在解码----------");
        try {
            in.retain();
            parseData.decoder(in, list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }
}
