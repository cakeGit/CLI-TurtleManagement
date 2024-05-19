package networking.stream;

import java.nio.ByteBuffer;
import java.util.Arrays;

public class NetworkReadStream {
    
    final byte[] data;
    final int length;
    int position = 0;
    
    public NetworkReadStream(byte[] bytes) {
        data = bytes;
        length = bytes.length;
    }
    
    public byte[] readBytes(int readLength) {
        if (position + readLength > length)
            throw new IndexOutOfBoundsException("Tried to read past the end of a network byte stream at " + position + " for " + readLength + " bytes for length " + length);
        byte[] bytes = Arrays.copyOfRange(data, position, position + readLength);
        position += readLength;
        return bytes;
    }
    
    public byte readByte() {
        if (position + 1 > length)
            throw new IndexOutOfBoundsException("Tried to read past the end of a network byte stream at " + position + " for 1  byte for length " + length);
        byte b = data[position];
        position += 1;
        return b;
    }
    
    public float readFloat() {
        return Float.intBitsToFloat(readInt());
    }
    
    public int readInt() {
        return ByteBuffer.wrap(readBytes(4)).getInt();
    }
    
    public String readString() {
        int length = readInt();
        return new String(readBytes(length));
    }
    
    public boolean readByteBool() {
        return readByte() != 0;
    }

    public int readByteInt() {
        return readByte();
    }

    public void seek(int i) {
        position += i;
    }

}
