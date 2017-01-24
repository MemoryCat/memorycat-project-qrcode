package com.memorycat.project.QRCode;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.memorycat.project.qrcode.QRCodeUtil;

public class Test1 {

	private static final String STR = "http://www.helloworld1.com";

//	@Test
	public void testApp() throws IOException {
		boolean[][] qrcodeBooleanArray = QRCodeUtil.getQrcodeMarginBooleanArray(STR, 128, 128, 1);
		String string = QRCodeUtil.toString(qrcodeBooleanArray, "■", "□");
		System.out.println("getQrcodeMarginBooleanArray");
		System.out.println(string);

		int byteSize = qrcodeBooleanArray.length;
		int length = 5;
		BufferedImage bufferedImage = new BufferedImage(byteSize * length, byteSize * length,
				BufferedImage.TYPE_INT_ARGB);
		Graphics graphics = bufferedImage.getGraphics();
		boolean[][] b = qrcodeBooleanArray;
		graphics.setColor(Color.BLACK);
		for (int y = 0; y < qrcodeBooleanArray.length; y++) {
			for (int x = 0; x < qrcodeBooleanArray[0].length; x++) {
				if (b[y][x] == true) {
					graphics.fillRect(x * length, y * length, length, length);
				}
			}
		}
//		ImageIO.write(bufferedImage, "png", new File("c:/1.png"));
	}

	// @Test
	public void testApp2() throws IOException {
		boolean[][] qrcodeBooleanArray = QRCodeUtil.getQrcodeBooleanArray(STR, 1, 1);
		String string = QRCodeUtil.toString(qrcodeBooleanArray, "■", "□");
		System.out.println("getQrcodeBooleanArray");
		System.out.println(string);
	}

	// @Test
	public void test2() throws WriterException {
		QRCodeWriter qrCodeWriter = new QRCodeWriter();
		Map<EncodeHintType, Object> hints = new LinkedHashMap<EncodeHintType, Object>();
		hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
		BitMatrix bitMatrix = qrCodeWriter.encode(STR, BarcodeFormat.QR_CODE, 1, 1);
		System.out.println(bitMatrix.toString("■", "□"));
	}
}
