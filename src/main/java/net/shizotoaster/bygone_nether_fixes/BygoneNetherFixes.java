package net.shizotoaster.bygone_nether_fixes;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BygoneNetherFixes implements ModInitializer {
	public static final String MOD_ID = "bygone_nether_fixes";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("Patching Bygone Nether v{}...", FabricLoader.getInstance().getModContainer("bygonenether").get().getMetadata().getVersion());
	}
}