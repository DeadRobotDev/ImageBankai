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
import java.time.Instant;

public class ImageOverlay extends OverlayPanel
{
	private static final File CUSTOM_IMAGE_FILE = new File(RuneLite.RUNELITE_DIR, "profile.png");

	private final ImageBankaiConfig _config;
	private final BufferedImage ErrorImage;

	private BufferedImage CustomImage;
	private BufferedImage Image;
	private Instant nextCheck;
	private boolean configChanged;

	@Inject
	private ImageOverlay(ImageBankaiPlugin plugin, ImageBankaiConfig config)
	{
		super(plugin);

		_config = config;

		setPriority(PRIORITY_LOW);
		setPosition(OverlayPosition.TOP_LEFT);
		setLayer(OverlayLayer.ABOVE_WIDGETS);

		ErrorImage = loadErrorImage();
	}

	@Override
	public Dimension render(Graphics2D graphics)
	{
		if (shouldLoadCustomImage())
		{
			CustomImage = loadCustomImage();
			if (CustomImage == null)
			{
				if (nextCheck == null || configChanged)
				{
					Image = resizeImage(ErrorImage);
				}
				nextCheck = Instant.now().plusSeconds(1);
			} else
			{
				Image = resizeImage(CustomImage);
				nextCheck = null;
			}

			configChanged = false;
		}

		ImageComponent imageComponent = new ImageComponent(Image);

		panelComponent.getChildren().add(imageComponent);

		return super.render(graphics);
	}

	public void onConfigChanged()
	{
		configChanged = true;
	}

	private BufferedImage loadErrorImage()
	{
		return ImageUtil.loadImageResource(ImageBankaiPlugin.class, "/no_image.png");
	}

	private boolean shouldLoadCustomImage()
	{
		if (configChanged)
		{
			return true;
		}

		if (CustomImage != null)
		{
			return false;
		}

		var currentTime = Instant.now();
		return nextCheck == null || currentTime.isAfter(nextCheck);
	}

	private BufferedImage loadCustomImage()
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

