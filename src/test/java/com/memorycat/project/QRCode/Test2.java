package com.memorycat.project.QRCode;

import java.io.File;
import java.io.FileOutputStream;

import org.junit.Test;

import com.memorycat.project.qrcode.QRCodeImageUtil;

/**
 * @author MemoryCat.com
 * @version 2016年9月25日
 */

public class Test2 {
	@Test
	public void testImage() throws Exception {
		byte[] qRcodeImageByte = QRCodeImageUtil.getQRcodeImageByte("http://www.baidu.com", "png", 1, 2);
		 FileOutputStream fileOutputStream = new FileOutputStream(new
		 File("c:/2.png"));
		 fileOutputStream.write(qRcodeImageByte);
		 fileOutputStream.close();

		String parseQRcodeImageByte = QRCodeImageUtil.parseQRcodeImageByte(qRcodeImageByte);
		System.out.println(parseQRcodeImageByte);
	}
}
