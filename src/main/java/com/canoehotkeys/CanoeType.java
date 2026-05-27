package com.canoehotkeys;

import java.util.function.Function;
import net.runelite.api.gameval.InterfaceID;
import net.runelite.client.config.Keybind;

enum CanoeType
{
	LOG(InterfaceID.Canoeing.LOG, "Log canoe", CanoeHotkeysConfig::logKeybind),
	DUGOUT(InterfaceID.Canoeing.DUGOUT, "Dugout canoe", CanoeHotkeysConfig::dugoutKeybind),
	STABLE_DUGOUT(InterfaceID.Canoeing.STABLE_DUGOUT, "Stable dugout canoe", CanoeHotkeysConfig::stableDugoutKeybind),
	WAKA(InterfaceID.Canoeing.WAKA, "Waka canoe", CanoeHotkeysConfig::wakaKeybind);

	final int widgetId;
	final String optionLabel;
	final Function<CanoeHotkeysConfig, Keybind> keybind;

	CanoeType(int widgetId, String optionLabel, Function<CanoeHotkeysConfig, Keybind> keybind)
	{
		this.widgetId = widgetId;
		this.optionLabel = optionLabel;
		this.keybind = keybind;
	}
}
