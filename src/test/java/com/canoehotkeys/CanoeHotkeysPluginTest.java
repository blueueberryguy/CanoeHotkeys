package com.canoehotkeys;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class CanoeHotkeysPluginTest
{
	public static void main(String[] args) throws Exception
	{
		ExternalPluginManager.loadBuiltin(CanoeHotkeysPlugin.class);
		RuneLite.main(args);
	}
}
