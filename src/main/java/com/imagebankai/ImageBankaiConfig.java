package com.imagebankai;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("ImageBankai")
public interface ImageBankaiConfig extends Config
{
	@ConfigItem(
			keyName = "width",
			name = "Width",
			description = "The width of the image, in pixels",
			position = 1
	)
	default int width()
	{
		return 100;
	}

	@ConfigItem(
			keyName = "height",
			name = "Height",
			description = "The height of the image, in pixels",
			position = 2
	)
	default int height()
	{
		return 100;
	}

	@ConfigItem(
			keyName = "scalingMode",
			name = "Scaling Mode",
			description = "",
			position = 3
	)
	default ResizeMode scalingMode()
	{
		return ResizeMode.Bilinear;
	}

	@ConfigItem(
			keyName = "antialias",
			name = "Anti-Aliasing",
			description = "",
			position = 4
	)
	default boolean antiAliasEnabled()
	{
		return false;
	}
}
