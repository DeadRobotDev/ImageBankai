package com.imagebankai;

import net.runelite.client.RuneLite;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPanel;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.components.ImageComponent;

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
		BufferedImage image = loadImage();
		if (image == null)
		{
			return null;
		}
		image = resizeImage(image, _config.width(), _config.height(), _config.scalingMode(), _config.antiAliasEnabled());

		ImageComponent imageComponent = new ImageComponent(image);

		panelComponent.getChildren().add(imageComponent);

		return super.render(graphics);
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

	private BufferedImage resizeImage(BufferedImage in, int targetWidth, int targetHeight, ResizeMode scalingMode, boolean antiAliasEnabled)
	{
		var currentHeight = in.getHeight();
		var currentWidth = in.getWidth();

		Dimension currentDimension = new Dimension(currentWidth, currentHeight);
		Dimension targetDimension = new Dimension(targetWidth, targetHeight);

		Dimension scaledDimension = getScaledDimension(currentDimension, targetDimension);

		int type = in.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : in.getType();

		BufferedImage scaledImage = new BufferedImage(
				scaledDimension.width,
				scaledDimension.height,
				type
		);

		Object scalingHint = null;
		switch (scalingMode)
		{
			case NearestNeighbour:
				scalingHint = RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR;
				break;
			case Bicubic:
				scalingHint = RenderingHints.VALUE_INTERPOLATION_BICUBIC;
				break;
			default:
				scalingHint = RenderingHints.VALUE_INTERPOLATION_BILINEAR;
				break;
		}

		Object antiAliasHint = antiAliasEnabled
				? RenderingHints.VALUE_ANTIALIAS_ON
				: RenderingHints.VALUE_ANTIALIAS_OFF;

		Graphics2D g2d = scaledImage.createGraphics();

		g2d.setComposite(AlphaComposite.Src);
		g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, scalingHint);
		g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, antiAliasHint);
		g2d.drawImage(in, 0, 0, scaledDimension.width, scaledDimension.height, null);
		g2d.dispose();

		return scaledImage;
	}

	private Dimension getScaledDimension(Dimension imageSize, Dimension boundary)
	{
		double widthRatio = boundary.getWidth() / imageSize.getWidth();
		double heightRatio = boundary.getHeight() / imageSize.getHeight();

		double ratio = Math.min(widthRatio, heightRatio);

		return new Dimension((int) (imageSize.width * ratio), (int) (imageSize.height * ratio));
	}
}

