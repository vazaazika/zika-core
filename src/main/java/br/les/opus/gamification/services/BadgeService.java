package br.les.opus.gamification.services;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Base64;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;
import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import br.les.opus.gamification.domain.Badge;

@Service
@Transactional
public class BadgeService {
	private Logger logger = Logger.getLogger(BadgeService.class.getName());
	
	public void encodeIcons(List<Badge> badges) {
		Iterator<Badge> iterator = badges.iterator();
		
		while(iterator.hasNext()){
			Badge b = iterator.next();
			
			encodeIconAsString(b);
		}
	}

	private void encodeIconAsString(Badge badge) {
		try {
			
			if (badge == null) {
				return;
			}

			File file = new File(badge.getImageUrl());
			
			
			ByteArrayOutputStream baos = new ByteArrayOutputStream(1000);
			BufferedImage img = ImageIO.read(file);
			
			ImageIO.write(img, "png", baos);
			baos.flush();
			
			String base64Image = Base64.getEncoder().encodeToString(baos.toByteArray());
			
			
			badge.setBase64Image(base64Image);
			baos.close();
		
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage(), e);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		
	}
	

}
