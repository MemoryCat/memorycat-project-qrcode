package com.memorycat.project.qrcode;

import java.util.LinkedHashMap;
import java.util.Map;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

/**
 * @author MemoryCat.com
 * @version 2016年9月24日
 */

public class QRCodeUtil {

	/**
	 * 
	 * @param string
	 *            需要转为二维码的字符串
	 * @param width
	 * @param height
	 * @return
	 */
	public static boolean[][] getQrcodeBooleanArray(String string, int width, int height) {

		if (width <= 0 || height <= 0) {
			throw new IllegalArgumentException("参数值不能少于0");
		}

		try {
			QRCodeWriter qrCodeWriter = new QRCodeWriter();
			Map<EncodeHintType, Object> hints = new LinkedHashMap<EncodeHintType, Object>();
			hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
			BitMatrix bitMatrix = qrCodeWriter.encode(string, BarcodeFormat.QR_CODE, width, height, hints);

			int bheight = bitMatrix.getHeight();
			int bwidth = bitMatrix.getWidth();

			boolean[][] b = new boolean[bwidth][bheight];
			for (int y = 0; y < bheight; y++) {
				for (int x = 0; x < bwidth; x++) {
					if (bitMatrix.get(x, y)) {
						b[y][x] = true;
					}
				}
			}
			return b;
		} catch (WriterException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 
	 * @param string
	 *            需要转为二维码的字符串
	 * @param width
	 * @param height
	 * @return
	 */
	public static boolean[][] getQrcodeMarginBooleanArray(String string, int width, int height, int margin) {
		if (width <= 0 || height <= 0 || margin < 0) {
			throw new IllegalArgumentException("参数值不能少于0");
		}
		try {
			QRCodeWriter qrCodeWriter = new QRCodeWriter();
			Map<EncodeHintType, Object> hints = new LinkedHashMap<EncodeHintType, Object>();
			hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
			BitMatrix bitMatrix = qrCodeWriter.encode(string, BarcodeFormat.QR_CODE, width, height, hints);
			int[] enclosingRectangle = bitMatrix.getEnclosingRectangle();

			int left = enclosingRectangle[0];
			int top = enclosingRectangle[1];
			int right = enclosingRectangle[2];
			int bottom = enclosingRectangle[3];
			int bheight = bitMatrix.getHeight();
			int bwidth = bitMatrix.getWidth();

			boolean[][] b = new boolean[right + (margin * 2)][bottom + (margin * 2)];
			for (int y = top; y < bheight - top; y++) {
				for (int x = left; x < bwidth - left; x++) {
					if (bitMatrix.get(x, y)) {
						b[y - top + margin][x - left + margin] = true;
					}
				}
			}
			return b;
		} catch (WriterException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 把布尔类型数组转为字符串
	 * 
	 * @param b
	 * @param setString
	 *            有值时显示的字符
	 * @param unsetString
	 *            无值时显示的字符
	 * @return
	 */
	public static String toString(boolean[][] b, String setString, String unsetString) {
		String ret = new String();
		int width = b.length;
		int height = b[0].length;
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				if (b[i][j]) {
					ret += setString;
				} else {
					ret += unsetString;
				}
			}
			ret += "\n";
		}
		return ret;
	}

}
