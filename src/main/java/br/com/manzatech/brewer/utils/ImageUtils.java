package br.com.manzatech.brewer.utils;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import javax.imageio.ImageIO;

public class ImageUtils {

	
	public static void resizeImage(Path foto, Path pathToSave, Dimension newSize) {
		try {
			String name = foto.getFileName().toString();
			BufferedImage image = ImageIO.read(new File(foto.toString()));
			BufferedImage thumbnail = new BufferedImage(newSize.width, newSize.height, image.getType());
			Graphics2D g2d = thumbnail.createGraphics();
			g2d.drawImage(image, 0, 0, newSize.width, newSize.height, null);
			g2d.dispose();
			Optional<String> ext = Optional.ofNullable(name).filter(f -> f.contains(".")).map(f -> f.substring(name.lastIndexOf(".")).toLowerCase());
			String extension = ext.isPresent() ? ext.get().replace(".", "") : "jpg";
			if (extension.equals("jpeg")) {
				extension = "jpg";
			}
			File toSave = new File(pathToSave.resolve("thumb_" + name).toString());
			if (!toSave.exists()) {
				toSave.createNewFile();
				toSave.setWritable(true);
			}
			ImageIO.write(thumbnail, extension.toUpperCase(), toSave);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException("Erro ao redimensionar a imagem", e);
		}		
	}
}
