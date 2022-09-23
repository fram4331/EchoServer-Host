import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.util.CharsetUtil;

@ChannelHandler.Sharable
public class EchoServerHandler extends SimpleChannelInboundHandler<DataAgent> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelActive Event  ===========>>");
        super.channelActive(ctx);

        byte[] send = new byte[46];
        int copyOffset = 0;
        copyOffset = TypeHelper.writeBytes(send, copyOffset, (send.length - 5), 5);
        copyOffset = TypeHelper.writeBytes(send, copyOffset, "STK12".getBytes(), 5);
        copyOffset = TypeHelper.writeByte(send, copyOffset, EchoServer.getSerial());
        copyOffset = TypeHelper.writeByte(send, copyOffset, (byte)3);
        copyOffset = TypeHelper.writeBytes(send, copyOffset, "AUTH01   00       ST    1610095000".getBytes(), 34);

        ByteBuf bytebuf = Unpooled.copiedBuffer(send);
        ctx.writeAndFlush(bytebuf);

        TypeHelper.writeHexDump(send, send.length);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        System.out.println("exceptionCaught Event  ===========>>");
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DataAgent dataAgent) throws Exception {
        byte[] inBuff = dataAgent.getInBytes();
        TypeHelper.writeHexDump(inBuff, inBuff.length);
        dataAgent.released();
        dataAgent = null;
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelUnregistered Event  ===========>>");

        super.channelUnregistered(ctx);
    }
}
