package com.imagebankai;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("ImageBankai")
public interface ImageBankaiConfig extends Config
{
	@ConfigItem(
			keyName = "min",
			name = "Min Width",
			description = "The min width of the image, in pixels",
			position = 1
	)
	default int minWidth()
	{
		return 100;
	}

	@ConfigItem(
			keyName = "minHeight",
			name = "Min Height",
			description = "The min height of the image, in pixels",
			position = 2
	)
	default int minHeight()
	{
		return 100;
	}

	@ConfigItem(
			keyName = "transparentBackground",
			name = "Transparent Background",
			description = "If enabled, will have a transparent background. By default, uses Overlay color set in RuneLite plugin.",
			position = 3
	)
	default boolean transparentBackground() { return false; }

	@ConfigItem(
			keyName = "overlayMode",
			name = "Overlay Mode",
			description = "Behind Interfaces displays behind interfaces, i.e bank and map. Always On Top displays above everything.",
			position = 4
	)
	default OverlayMode overlayMode() { return OverlayMode.Default; }
}
