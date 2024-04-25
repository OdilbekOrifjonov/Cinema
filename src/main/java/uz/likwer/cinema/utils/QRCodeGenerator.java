package uz.likwer.cinema.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class QRCodeGenerator {

    public static final String QR_PATH = "C:\\Users\\User\\IdeaProjects/Cinema\\src\\main\\webapp\\tempFiles\\qrcode.png";

    public static void main(String[] args) throws IOException, WriterException {
        String text = "https://example.com";

        generateQRCode(text);
    }

    public static void generateQRCode(String text) {
        try {
            int size = 300;

            QRCodeWriter qrCodeWriter = new QRCodeWriter();

            Map<EncodeHintType, Object> hints = new HashMap<>();
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);

            com.google.zxing.common.BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, size, size, hints);

            BufferedImage bufferedImage = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
            bufferedImage.createGraphics();

            Graphics2D graphics = (Graphics2D) bufferedImage.getGraphics();
            graphics.setColor(Color.WHITE);
            graphics.fillRect(0, 0, size, size);
            graphics.setColor(Color.BLACK);

            for (int x = 0; x < size; x++) {
                for (int y = 0; y < size; y++) {
                    if (bitMatrix.get(x, y)) {
                        graphics.fillRect(x, y, 1, 1);
                    }
                }
            }

            File file = new File(QR_PATH);
            if (file.exists()) {
                file.delete();
            }
            ImageIO.write(bufferedImage, "png", new File(QR_PATH));
            while (!file.exists()) {
                Thread.sleep(100);
            }
        } catch (IOException | WriterException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
