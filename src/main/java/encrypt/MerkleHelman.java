package encrypt;

import java.math.BigInteger;
import java.nio.charset.Charset;
import java.util.Random;
import java.util.Scanner;

public class MerkleHelman {
    private BigInteger[] w, b;
    private BigInteger q, r;
    private BigInteger rand = new BigInteger("9878888889878888888");
    private static final int MAX_CHARS = 150;
    private static final int BINARY_LENGTH = MAX_CHARS * 8;
    private static final Charset UTF8 = Charset.forName("UTF-8");
    public MerkleHelman() {
        genKeys();
    }
    private void genKeys() {
        int maxBits = 50;
        w = new BigInteger[BINARY_LENGTH];
        w[0] = rand.add(BigInteger.ONE);
        BigInteger sum = new BigInteger(w[0].toByteArray());
        for (int i = 1; i < w.length; i++) {
            w[i] = sum.add(rand).add(BigInteger.ONE);
            sum = sum.add(w[i]);
        }
        q = sum.add(rand).add(BigInteger.ONE);
        r = q.subtract(BigInteger.ONE);
        b = new BigInteger[BINARY_LENGTH];
        for (int i = 0; i < b.length; i++)
            b[i] = w[i].multiply(r).mod(q);
    }
    public String encryptMsg(String message) {
        if (message.length() > MAX_CHARS)
            throw new IndexOutOfBoundsException("Maximum message length allowed is " + MAX_CHARS + ".");
        if (message.length() <= 0)
            throw new Error("Cannot encrypt an empty string.");
        String msgBinary = new BigInteger(message.getBytes(UTF8)).toString(2);
        if (msgBinary.length() < BINARY_LENGTH) {
            msgBinary = String.format("%0" + (BINARY_LENGTH - msgBinary.length()) + "d", 0) + msgBinary;
        }
        BigInteger result = BigInteger.ZERO;
        for (int i = 0; i < msgBinary.length(); i++) {
            result = result.add(b[i].multiply(new BigInteger(msgBinary.substring(i, i+1))));
        }
        return result.toString();
    }
    public String decryptMsg(String ciphertext) {
        BigInteger tmp = new BigInteger(ciphertext).mod(q).multiply(r.modInverse(q)).mod(q);
        byte[] decrypted_binary = new byte[w.length];  // the decrypted message in binary

        for (int i = w.length - 1; i >= 0; i--) {
            if (w[i].compareTo(tmp) <= 0) {
                tmp = tmp.subtract(w[i]);
                decrypted_binary[i] = 1;
            } else {
                decrypted_binary[i] = 0;
            }
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < decrypted_binary.length; i++) {
            sb.append(decrypted_binary[i]);
        }
        return new String(new BigInteger(sb.toString(), 2).toByteArray());
    }

}
