package com.raitichan.MCTweet.config;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;


/**
 * <br>Created by Raiti-chan on 2017/05/13.
 *
 * @author Raiti-chan
 * @version 1.0.0
 * @since 1.0.0
 */
public class MCTweetConfig {
	
	//==SF==============================================================================================================
	
	/**
	 * This class's only instance.
	 */
	private static final MCTweetConfig INSTANCE = new MCTweetConfig();
	
	//==F===============================================================================================================
	
	/**
	 * Config.
	 */
	private Configuration configuration = null;
	
	/**
	 * Data;
	 */
	private Map<ConfigKey, Property> data = new HashMap<>();
	
	//==Init============================================================================================================
	
	
	/**
	 * Private constructor.
	 */
	private MCTweetConfig () {
	}
	
	//==SM==============================================================================================================
	
	/**
	 * get this class instance.
	 *
	 * @return This class instance.
	 */
	public static MCTweetConfig getINSTANCE () {
		return INSTANCE;
	}
	
	//==M===============================================================================================================
	
	/**
	 * Initialized config.
	 *
	 * @param configFile configFile
	 */
	public void init (File configFile) {
		this.configuration = new Configuration(configFile);
		syncConfig();
	}
	
	/**
	 * Syncing config file and Properties.
	 */
	private void syncConfig () {
		this.data.clear();
		for (ConfigKey key : ConfigKey.values()) {
			if (key.defaultValue == null) {
				this.data.put(key, this.configuration.get(key.groupName, key.key, key.defaultValues, key.text, key.type));
			} else {
				this.data.put(key, this.configuration.get(key.groupName, key.key, key.defaultValue, key.text, key.type));
			}
		}
		if (this.configuration.hasChanged()) this.configuration.save();
	}
	
	/**
	 * Retrieve the data of the specified key.
	 * If specified key is not mapped, return null.
	 *
	 * @param key Property key.
	 * @return Property corresponding to the key.
	 */
	@SuppressWarnings("unused")
	public Property getProperty (ConfigKey key) {
		if (this.configuration == null) throw new IllegalStateException("Not initialized!!");
		return this.data.get(key);
	}
	
	/**
	 * Get to configuration.
	 * @return configuration.
	 */
	@SuppressWarnings("WeakerAccess")
	public Configuration getConfiguration() {
		return this.configuration;
	}
	
	//==================================================================================================================
	
	/**
	 * Config key enums.
	 */
	public enum ConfigKey {
		API_KEY(ConfigGroup.API, "APIKey", Property.Type.STRING, "Twitter api key.", "default"),
		API_SECRET(ConfigGroup.API, "APISecret", Property.Type.STRING, "Twitter api secret", "default"),
		USER_KEY(ConfigGroup.API, "UserKey", Property.Type.STRING, "User Key", "none"),
		USER_SECRET(ConfigGroup.API, "UserSecret", Property.Type.STRING, "User Secret", "none"),;
		
		/**
		 * group name.
		 */
		public final String groupName;
		
		/**
		 * key.
		 */
		public final String key;
		
		/**
		 * This property type.
		 */
		public final Property.Type type;
		
		/**
		 * default values.
		 */
		public final String defaultValues[];
		
		/**
		 * default value.
		 */
		public final String defaultValue;
		
		/**
		 * text.
		 */
		public final String text;
		
		/**
		 * @param group        group.
		 * @param key          key
		 * @param type         property type
		 * @param text         text
		 * @param defaultValue default value
		 */
		@SuppressWarnings("unused")
		ConfigKey (ConfigGroup group, String key, Property.Type type, String text, String... defaultValue) {
			this.groupName = group.groupName;
			this.key = key;
			this.type = type;
			this.defaultValues = defaultValue;
			this.defaultValue = null;
			this.text = text;
		}
		
		/**
		 * @param group        group.
		 * @param key          key
		 * @param type         property type
		 * @param text         text
		 * @param defaultValue default value
		 */
		ConfigKey (ConfigGroup group, String key, Property.Type type, String text, String defaultValue) {
			this.groupName = group.groupName;
			this.key = key;
			this.type = type;
			this.defaultValues = null;
			this.defaultValue = defaultValue;
			this.text = text;
		}
	}
	
	/**
	 * Config group enums.
	 */
	public enum ConfigGroup {
		API("api"),;
		
		/**
		 * Group name.
		 */
		public final String groupName;
		
		/**
		 * @param groupName Group name.
		 */
		ConfigGroup (String groupName) {
			this.groupName = groupName;
		}
	}
	
	
}
