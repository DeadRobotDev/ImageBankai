package com.imagebankai;

import net.runelite.client.RuneLite;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPanel;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.components.ImageComponent;
import net.runelite.client.util.ImageUtil;

import javax.imageio.ImageIO;
import javax.inject.Inject;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageOverlay extends OverlayPanel
{
	private static final File CUSTOM_IMAGE_FILE = new File(RuneLite.RUNELITE_DIR, "profile.png");

	private final ImageBankaiConfig _config;

	private BufferedImage OriginalImage;
	private BufferedImage Image;
	private boolean updateImage;

	@Inject
	private ImageOverlay(ImageBankaiPlugin plugin, ImageBankaiConfig config)
	{
		super(plugin);

		_config = config;

		setPriority(PRIORITY_LOW);
		setPosition(OverlayPosition.TOP_LEFT);
		setLayer(OverlayLayer.ABOVE_WIDGETS);
	}

	@Override
	public Dimension render(Graphics2D graphics)
	{
		if (OriginalImage == null || updateImage)
		{
			OriginalImage = loadImage();
			if (OriginalImage == null)
			{
				return null;
			}

			Image = resizeImage(OriginalImage);
			updateImage = false;
		}

		ImageComponent imageComponent = new ImageComponent(Image);

		panelComponent.getChildren().add(imageComponent);

		return super.render(graphics);
	}

	public void onConfigChanged()
	{
		updateImage = true;
	}

	private BufferedImage loadImage()
	{
		try
		{
			return ImageIO.read(ImageOverlay.CUSTOM_IMAGE_FILE);
		} catch (IOException e)
		{
			return null;
		}
	}

	private BufferedImage resizeImage(BufferedImage in)
	{
		var maxWidth = _config.minWidth();
		var maxHeight = _config.minHeight();

		return ImageUtil.resizeImage(in, maxWidth, maxHeight, true);
	}
}

