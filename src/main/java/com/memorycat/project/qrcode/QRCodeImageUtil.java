package com.memorycat.project.qrcode;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.FormatException;
import com.google.zxing.LuminanceSource;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;

/**
 * @author MemoryCat.com
 * @version 2016年9月25日
 */

public class QRCodeImageUtil {
	/**
	 * 
	 * @param contents
	 * @param imageType
	 *            png/jpg...
	 * @param size
	 * @return
	 */
	public static byte[] getQRcodeImageByte(String content, String imageType, int size, int length) {
		try {
			boolean[][] qrcodeBooleanArray = QRCodeUtil.getQrcodeMarginBooleanArray(content, size, size, 1);
			int byteSize = qrcodeBooleanArray.length;
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
			graphics.dispose();
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			ImageIO.write(bufferedImage, imageType, byteArrayOutputStream);
			byteArrayOutputStream.close();
			return byteArrayOutputStream.toByteArray();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 解释图片中的二维码
	 * 
	 * @param imageByte
	 * @return
	 */
	public static String parseQRcodeImageByte(byte[] imageByte) {
		String ret = new String();
		try {
			ret = parseQRcodeImageByteThrowingException(imageByte);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

	/**
	 * 解释图片中的二维码
	 * 
	 * @param imageByte
	 * @return
	 * @throws IOException
	 * @throws FormatException
	 * @throws ChecksumException
	 * @throws NotFoundException
	 */
	public static String parseQRcodeImageByteThrowingException(byte[] imageByte)
			throws IOException, NotFoundException, ChecksumException, FormatException {
		String ret = new String();
		BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageByte));
		LuminanceSource source = new BufferedImageLuminanceSource(image);
		BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
		QRCodeReader qrCodeReader = new QRCodeReader();
		Result result = qrCodeReader.decode(bitmap);
		ret = result.toString();
		return ret;
	}
}
