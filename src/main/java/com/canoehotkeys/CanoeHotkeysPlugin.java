package com.canoehotkeys;

import com.google.inject.Provides;
import java.awt.event.KeyEvent;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.MenuAction;
import net.runelite.api.gameval.InterfaceID;
import net.runelite.api.widgets.Widget;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.config.Keybind;
import net.runelite.client.input.KeyListener;
import net.runelite.client.input.KeyManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;

@Slf4j
@PluginDescriptor(
	name = "Canoe Hotkeys"
)
public class CanoeHotkeysPlugin extends Plugin implements KeyListener
{
	@Inject
	private Client client;

	@Inject
	private CanoeHotkeysConfig config;

	@Inject
	private KeyManager keyManager;

	@Inject
	private ClientThread clientThread;

	@Override
	protected void startUp()
	{
		keyManager.registerKeyListener(this);
	}

	@Override
	protected void shutDown()
	{
		keyManager.unregisterKeyListener(this);
	}

	@Override
	public void keyTyped(KeyEvent e)
	{
	}

	@Override
	public void keyPressed(KeyEvent e)
	{
		CanoeType match = matchCanoe(e);
		if (match == null)
		{
			return;
		}

		int widgetId = match.widgetId;
		Widget widget = client.getWidget(widgetId);
		if (widget == null || widget.isHidden())
		{
			return;
		}

		e.consume();

		String option = "Make " + match.optionLabel;
		clientThread.invoke(() -> client.menuAction(
			-1, widgetId, MenuAction.CC_OP, 1, -1, option, ""));
	}

	@Override
	public void keyReleased(KeyEvent e)
	{
	}

	private CanoeType matchCanoe(KeyEvent e)
	{
		for (CanoeType type : CanoeType.values())
		{
			if (type.keybind.apply(config).matches(e))
			{
				return type;
			}
		}
		return null;
	}

	@Provides
	CanoeHotkeysConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(CanoeHotkeysConfig.class);
	}

	private enum CanoeType
	{
		LOG(InterfaceID.Canoeing.LOG, "Log canoe", CanoeHotkeysConfig::logKeybind),
		DUGOUT(InterfaceID.Canoeing.DUGOUT, "Dugout canoe", CanoeHotkeysConfig::dugoutKeybind),
		STABLE_DUGOUT(InterfaceID.Canoeing.STABLE_DUGOUT, "Stable dugout canoe", CanoeHotkeysConfig::stableDugoutKeybind),
		WAKA(InterfaceID.Canoeing.WAKA, "Waka canoe", CanoeHotkeysConfig::wakaKeybind);

		final int widgetId;
		final String optionLabel;
		final java.util.function.Function<CanoeHotkeysConfig, Keybind> keybind;

		CanoeType(int widgetId, String optionLabel, java.util.function.Function<CanoeHotkeysConfig, Keybind> keybind)
		{
			this.widgetId = widgetId;
			this.optionLabel = optionLabel;
			this.keybind = keybind;
		}
	}
}
