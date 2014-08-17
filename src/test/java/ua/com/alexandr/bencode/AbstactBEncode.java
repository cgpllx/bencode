package ua.com.alexandr.bencode;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;

import ua.com.alexandr.bencode.BDecoder;
import ua.com.alexandr.bencode.BEncoder;
import static ua.com.alexandr.bencode.BEncoderConstants.UTF_8;

public abstract class AbstactBEncode {
	
	protected BEncoder getEncoder() {
		return new BEncoder();
	}
	
	protected BDecoder getDecoder(ByteArrayInputStream bais) {
		return new BDecoder(bais);
	}
	
	protected String createString(byte[] bytes) throws UnsupportedEncodingException {
		return new String(bytes, UTF_8);
	}
	
}
