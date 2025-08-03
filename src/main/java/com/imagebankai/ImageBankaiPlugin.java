package com.imagebankai;

import com.google.inject.Provides;
import lombok.extern.slf4j.Slf4j;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.ConfigChanged;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;

import javax.inject.Inject;

@Slf4j
@PluginDescriptor(
		name = "Image Bankai"
)
public class ImageBankaiPlugin extends Plugin
{
	@Inject
	private OverlayManager overlayManager;

	@Inject
	private ImageOverlay overlay;

	@Override
	protected void startUp() throws Exception
	{
		overlayManager.add(overlay);
	}

	@Override
	protected void shutDown() throws Exception
	{
		overlayManager.remove(overlay);
	}

	@Subscribe
	public void onConfigChanged(ConfigChanged event)
	{
		if (event.getGroup().equals("ImageBankai"))
		{
			overlay.onConfigChanged();
		}
	}

	@Provides
	ImageBankaiConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(ImageBankaiConfig.class);
	}
}
