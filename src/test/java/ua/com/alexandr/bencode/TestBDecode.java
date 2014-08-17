package ua.com.alexandr.bencode;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import ua.com.alexandr.bencode.BDecoder;
import ua.com.alexandr.bencode.InvalidBEncodingException;

public class TestBDecode extends AbstactBEncode {
	
	@Test
	public void testDecodePositiveNumber() throws IOException {
		ByteArrayInputStream bais = new ByteArrayInputStream("i123e".getBytes());
		BDecoder decoder = getDecoder(bais);
		Number bdecode = decoder.decodeNumber();
		assertEquals(123, bdecode.intValue());
	}
	
	@Test
	public void testDecodeNegativeNumber() throws IOException {
		ByteArrayInputStream bais = new ByteArrayInputStream("i-123e".getBytes());
		BDecoder decoder = getDecoder(bais);
		Number bdecode = decoder.decodeNumber();
		assertEquals(-123, bdecode.intValue());
	}
	
	@Test
	public void testDecodeZeroNumber() throws IOException {
		ByteArrayInputStream bais = new ByteArrayInputStream("i0e".getBytes());
		BDecoder decoder = getDecoder(bais);
		Number bdecode = decoder.decodeNumber();
		assertEquals(0, bdecode.intValue());
	}
	
	@Test(expected = InvalidBEncodingException.class)
	public void testDecodeBadZeroNumber() throws IOException {
		ByteArrayInputStream bais = new ByteArrayInputStream("i-0e".getBytes());
		BDecoder decoder = getDecoder(bais);
		Number bdecode = decoder.decodeNumber();
		assertEquals(0, bdecode.intValue());
	}
	
	@Test
	public void testDecodeString() throws IOException {
		ByteArrayInputStream bais = new ByteArrayInputStream("4:test".getBytes());
		BDecoder decoder = getDecoder(bais);
		String bdecode = decoder.decodeString();
		assertEquals("test", bdecode);
	}
	
	@Test
	public void testDecodeList() throws IOException {
		ByteArrayInputStream bais = new ByteArrayInputStream("li123e4:test3:xxxi0ee".getBytes());
		BDecoder decoder = getDecoder(bais);
		
		List<Object> bdecode = decoder.decodeList();
		List<Object> list = new ArrayList<Object>();
		list.add(123);
		list.add("test");
		list.add("xxx");
		list.add(0);
		
		assertThat(list, is(bdecode));
	}
	
	@Test
	public void testDecodeMap() throws IOException {
		ByteArrayInputStream bais = new ByteArrayInputStream("d3:aaai123e2:bb4:test1:c3:xxx4:ddddi0ee".getBytes());
		BDecoder decoder = getDecoder(bais);
		
		Map<String, Object> bdecode = decoder.decodeMap();
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("aaa", 123);
		map.put("bb", "test");
		map.put("c", "xxx");
		map.put("dddd", 0);
		
		assertThat(map, is(bdecode));
	}
		
}
