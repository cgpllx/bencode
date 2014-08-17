package ua.com.alexandr.bencode;

import static org.junit.Assert.assertEquals;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

/**
 * <pre>
 * Bencode использует ASCII символы как разделители и цифры.
 *	<b>Целое число записывается так:</b> <i>i<число в десятичной системе счисления>e</i>. 
 *		Число не должно начинаться с нуля, но число ноль записывается как i0e. 
 *		Отрицательные числа записываются со знаком минуса перед числом. 
 *		Число −42 будет выглядеть так «i-42e».
 *	<b>Строка байт:</b> <i><размер>:<содержимое></i>. 
 *		Размер — это положительное число в десятичной системе счисления, может быть нулём; 
 *		Содержимое — это непосредственно данные, представленные цепочкой байт. 
 *		Строка «spam» в этом формате будет выглядеть так «4:spam».
 *	<b>Список (массив):</b> <i>l<содержимое>e</i>. 
 *		Содержимое включает в себя любые Bencode типы, следующие друг за другом. 
 *		Список, состоящий из строки «spam» и числа 42, будет выглядеть так: «l4:spami42ee».
 *	<b>Словарь:</b> <i>d<содержимое>e</i>.
 *		Содержимое состоит из пар ключ-значение, которые следуют друг за другом. 
 *		Ключи могут быть только строкой байт и должны быть упорядочены в лексикографическом порядке. 
 *		Значение может быть любым Bencode элементом. 
 *		Если сопоставить ключам «bar» и «foo» значения «spam» и 42, получится: «d3:bar4:spam3:fooi42ee». 
 *		(Если добавить пробелы между элементами, будет легче понять структуру: "d 3:bar 4:spam 3:foo i42e e".)
 *</pre>
 *
 * @see <a href="https://ru.wikipedia.org/wiki/Bencode">https://ru.wikipedia.org/wiki/Bencode</a>
 * @author lihonosov
 *
 */

public class TestBEncode extends AbstactBEncode {
	
	@Test
	public void testEncodePositiveNumber() throws UnsupportedEncodingException {
		byte[] encodedNumber = getEncoder().bencode(123);
		assertEquals("i123e", createString(encodedNumber));
	}
	
	@Test
	public void testEncodeZeroNumber() throws UnsupportedEncodingException {
		byte[] encodedNumber = getEncoder().bencode(0);
		assertEquals("i0e", createString(encodedNumber));
	}
	
	@Test
	public void testEncodeNegativeNumber() throws UnsupportedEncodingException {
		byte[] encodedNumber = getEncoder().bencode(-123);
		assertEquals("i-123e", createString(encodedNumber));
	}

	@Test
	public void testEncodeString() throws UnsupportedEncodingException {
		byte[] encoded = getEncoder().bencode("test");
		assertEquals("4:test", createString(encoded));
	}
	
	@Test
	public void testEncodeList() throws UnsupportedEncodingException {
		List<Object> list = new ArrayList<Object>();
		list.add(123);
		list.add("test");
		list.add("xxx");
		list.add(0);
		
		byte[] encoded = getEncoder().bencode(list);
		assertEquals("li123e4:test3:xxxi0ee", createString(encoded));
	}
	
	@Test
	public void testEncodeMap() throws UnsupportedEncodingException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("aaa", 123);
		map.put("bb", "test");
		map.put("c", "xxx");
		map.put("dddd", 0);
		
		byte[] encoded = getEncoder().bencode(map);
		assertEquals("d3:aaai123e2:bb4:test1:c3:xxx4:ddddi0ee", createString(encoded));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testBadEncode() {
		getEncoder().bencode(new Object());
	}
}
