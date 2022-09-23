import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.io.IOException;
import java.util.List;

public class ChannelByteDecoder extends ByteToMessageDecoder {
    public static final int LEN_LENGTH      = 5;        //통신 헤더 길이

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf in, List<Object> out) throws Exception {
        int iReadLen;

        try {
            System.out.println(String.format("decode Event [(Readable:%d | R:%d-W:%d)]", in.readableBytes(), in.readerIndex(), in.writerIndex()));

            if (in.readableBytes() >= (iReadLen = parsePacketLength(in))) {
                byte[] readBytes = new byte[iReadLen];
                in.readBytes(readBytes);

                DataAgent dataAgent = new DataAgent();
                dataAgent.setInBytes(readBytes);

                out.add(dataAgent);
            }
        } catch (Exception e) {
            System.out.println("======================================================");
            System.out.println("==========AppClientChannelDecoder Error===============");
            System.out.println(e.getMessage());
            System.out.println("======================================================");
        }
    }

    public int parsePacketLength(ByteBuf inBuffer) throws IOException
    {
        if (inBuffer.readableBytes() >= LEN_LENGTH) {
            byte[] lengthBuf = new byte[LEN_LENGTH];
            inBuffer.getBytes(inBuffer.readerIndex(), lengthBuf);
            return Integer.parseInt(new String(lengthBuf)) + LEN_LENGTH;
        }
        return 0;
    }
}
