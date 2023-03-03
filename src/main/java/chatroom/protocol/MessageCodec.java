package chatroom.protocol;

import chatroom.message.Message;
import com.google.gson.Gson;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Slf4j
@ChannelHandler.Sharable
public class MessageCodec extends MessageToMessageCodec<ByteBuf, Message> {

    @Override
    protected void encode(ChannelHandlerContext ctx, Message message, List<Object> list) throws Exception {
        ByteBuf out = ctx.alloc().buffer();
        // 4 魔数
        out.writeBytes(new byte[]{1,2,3,4});
        // 1 版本
        out.writeByte(1);
        // 1 序列化方式 json 0
        out.writeByte(0);
        // 1 数据对象类型
        out.writeByte(message.getMessageType());
        // 1 填充
        out.writeByte(0xff);

        // 填充真实数据
        String json = new Gson().toJson(message);
        byte[] bytes = json.getBytes(StandardCharsets.UTF_8);

        out.writeInt(bytes.length);
        out.writeBytes(bytes);
        list.add(out);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> list) throws Exception {
        int magicNum = in.readInt();
        byte version = in.readByte();
        byte serializerType = in.readByte();
        byte msgType = in.readByte();
        in.readByte();

        int length = in.readInt();

        // 读取真实数据
        // log.debug("length: {}", length);
        byte[] bytes = new byte[length];
        in.readBytes(bytes, 0, length);

        // System.out.println("json :" + json);
        String json = new String(bytes, StandardCharsets.UTF_8);
        Class<?> messageClass = Message.getMessageClass(msgType);
        Object msg = new Gson().fromJson(json, messageClass);

        list.add(msg);
    }
}
