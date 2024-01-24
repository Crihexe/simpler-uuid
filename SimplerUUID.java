import java.nio.ByteBuffer;
import java.security.SecureRandom;

public class SimplerUUID {

    public static String randomUUID() {
        ByteBuffer uuid = ByteBuffer.allocate(8);

        uuid.put(generateRandom())
            .put(generateTimestamp())
            .put(generateCounter());

        return toString(uuid);
    }
    
    private static String toString(ByteBuffer uuid) {
    	byte buffer[] = uuid.array();
    	
    	StringBuilder builder = new StringBuilder();
    	
    	for(byte b : buffer)
            builder.append(Holder.alphanumericValues.charAt(Math.floorMod(b, Holder.alphanumericValues.length())));
    	
    	return builder.toString();
    }

    private static ByteBuffer generateTimestamp() {
        long time = System.currentTimeMillis();
        ByteBuffer full = ByteBuffer.allocate(Long.BYTES).putLong(time);
        ByteBuffer sliced = ByteBuffer.allocate(4).put(0, full, 4, 4);
        return sliced;
    }

    private static ByteBuffer generateRandom() {
        SecureRandom ng = Holder.numberGenerator;

        ByteBuffer buffer = ByteBuffer.allocate(3);
        byte[] randomBytes = buffer.array();

        ng.nextBytes(randomBytes);

        return buffer;
    }

    private static byte generateCounter() {
        return Holder.counter++;
    }

    private static class Holder {
        static final SecureRandom numberGenerator = new SecureRandom();
        static byte counter = 0x00;
        static final String alphanumericValues = "qwertyuiopasdfghjklzxcvbnm1234567890=-QWERTYUIOPASDFGHJKLZXCVBNM";
    }

}
