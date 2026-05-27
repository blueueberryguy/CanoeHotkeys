package com.canoehotkeys;

import java.awt.event.KeyEvent;
import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.Keybind;

@ConfigGroup(CanoeHotkeysConfig.GROUP)
public interface CanoeHotkeysConfig extends Config
{
	String GROUP = "canoe-hotkeys";

	@ConfigItem(
		keyName = "logKeybind",
		name = "Log canoe",
		description = "Hotkey to make a log canoe at the canoe shaping dialog",
		position = 1
	)
	default Keybind logKeybind()
	{
		return new Keybind(KeyEvent.VK_1, 0);
	}

	@ConfigItem(
		keyName = "dugoutKeybind",
		name = "Dugout canoe",
		description = "Hotkey to make a dugout canoe at the canoe shaping dialog",
		position = 2
	)
	default Keybind dugoutKeybind()
	{
		return new Keybind(KeyEvent.VK_2, 0);
	}

	@ConfigItem(
		keyName = "stableDugoutKeybind",
		name = "Stable dugout canoe",
		description = "Hotkey to make a stable dugout canoe at the canoe shaping dialog",
		position = 3
	)
	default Keybind stableDugoutKeybind()
	{
		return new Keybind(KeyEvent.VK_3, 0);
	}

	@ConfigItem(
		keyName = "wakaKeybind",
		name = "Waka canoe",
		description = "Hotkey to make a waka canoe at the canoe shaping dialog",
		position = 4
	)
	default Keybind wakaKeybind()
	{
		return new Keybind(KeyEvent.VK_4, 0);
	}
}
