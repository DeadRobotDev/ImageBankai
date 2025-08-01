package com.imagebankai;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class ImageBankaiPluginTest
{
	public static void main(String[] args) throws Exception
	{
		ExternalPluginManager.loadBuiltin(ImageBankaiPlugin.class);
		RuneLite.main(args);
	}
}