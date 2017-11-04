/**
 * (c) 2008 Fivewh.com
 */
package com.fivewh.deploy.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UTFDataFormatException;
import java.io.UnsupportedEncodingException;

/**
 * @Auther: <a mailto:yinpengyi@gmail.com>Yin Pengyi</a>
 */
public class UTF8InputStreamReader extends java.io.Reader {
    private InputStream inputStream;

    /**
     * Convenient factory method for creating Readers for various encodings.
     * If the desired encoding is UTF8, returns a UTF8InputStreamReader.
     * Otherwise, returns a vanilla InputStreamReader.
     *
     * @param in       InputStream to read from
     * @param encoding desired encoding
     * @return UTF8InputStreamReader or InputStreamReader
     * @throws java.io.UnsupportedEncodingException
     *          if the encoding is unknown
     */
    public static java.io.Reader makeInputStreamReader(InputStream in, String encoding) throws UnsupportedEncodingException {
        if (encoding.equalsIgnoreCase("UTF8") || encoding.equalsIgnoreCase("utf-8")) {
            return new UTF8InputStreamReader(in);
        }

        return new InputStreamReader(in, encoding);
    }

    /**
     * Constructs a new UTF8InputStreamReader for the specified InputStream.
     *
     * @param in InputStream whose bytes should be converted to chars
     */
    public UTF8InputStreamReader(InputStream in) {
        inputStream = in;
    }

    public int read(char cbuf[], int off, int len) throws IOException {
        int numRead;
        for (numRead = 0; numRead < len; numRead++) {
            int ch = read();
            if (ch == -1) {
                if (numRead == 0) {
                    return -1;
                }
                break;
            }

            cbuf[numRead + off] = (char) ch;
        }
        return numRead;
    }

    public int read() throws IOException {
        int c1 = inputStream.read();
        if (c1 == -1) {
            return -1;    // EOF
        }

        // This is code is based on DataInputStream.readUTF().
        int mask = c1 >> 4;
        switch (mask) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
                /* 0xxxxxxx*/
                return c1;

            case 12:
            case 13: {
                /* 110x xxxx   10xx xxxx*/
                int c2 = mustRead();
                if ((c2 & 0xC0) != 0x80) {
                    throw new UTFDataFormatException();
                }
                return ((c1 & 0x1F) << 6) | (c2 & 0x3F);
            }

            case 14: {
                /* 1110 xxxx  10xx xxxx  10xx xxxx */
                int c2 = mustRead();
                int c3 = mustRead();
                if (((c2 & 0xC0) != 0x80) || ((c3 & 0xC0) != 0x80)) {
                    throw new UTFDataFormatException();
                }

                return ((c1 & 0x0F) << 12)
                        | ((c2 & 0x3F) << 6)
                        | ((c3 & 0x3F) << 0);
            }

            default:
                /* 10xx xxxx,  1111 xxxx */
                throw new UTFDataFormatException();
        }
    }

    /**
     * Helper method to read a required byte from our underlying stream. If
     * the stream is at EOF, throws a UTFDataFormatException.
     *
     * @return next byte read from the stream
     * @throws IOException            if there was a problem reading
     * @throws UTFDataFormatException if no bytes were available
     */
    private int mustRead() throws IOException {
        int ch = inputStream.read();
        if (ch == -1) {
            throw new UTFDataFormatException("unterminated UTF sequence");
        }
        return ch;
    }

    public void close() throws IOException {
        if (inputStream != null) {
            inputStream.close();
            inputStream = null;
        }
    }
}
