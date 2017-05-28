package com.raitichan.MCTweet.twitter;

import com.raitichan.MCTweet.config.MCTweetConfig;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

/**
 * <br>Created by Raiti-chan on 2017/05/14.
 *
 * @author Raiti-chan
 * @version 1.0.0
 * @since 1.0.0
 */
public class TwitterCore {
	
	@SuppressWarnings("SpellCheckingInspection")
	private static final String API_KEY = "Qq8NdudcjEFnJCWoRy2x1uust";
	
	@SuppressWarnings("SpellCheckingInspection")
	private static final String API_SECRET = "lYJqtPPl7xmjzWilZG1JPNqzN3duijMo0sTc3tDyxFwU5SzUmJ";
	
	private static final TwitterCore INSTANCE = new TwitterCore();
	
	private Twitter twitter = null;
	
	private RequestToken requestToken = null;
	
	public static TwitterCore getInstance () {
		return INSTANCE;
	}
	
	private void newTwitter() {
		this.twitter = new TwitterFactory().getInstance();
		this.setConsumer(twitter);
	}
	
	public String getRequestToken () throws TwitterException {
		this.newTwitter();
		this.requestToken = this.twitter.getOAuthRequestToken();
		return requestToken.getAuthorizationURL();
	}
	
	public void getAccessTokenForPin (String pin) throws TwitterException {
		AccessToken accessToken;
		if (pin.length() > 0) {
			accessToken = this.twitter.getOAuthAccessToken(requestToken, pin);
		} else {
			accessToken = this.twitter.getOAuthAccessToken();
		}
		MCTweetConfig.getINSTANCE().getProperty(MCTweetConfig.ConfigKey.USER_KEY).set(accessToken.getToken());
		MCTweetConfig.getINSTANCE().getProperty(MCTweetConfig.ConfigKey.USER_SECRET).set(accessToken.getTokenSecret());
	}
	
	public void getAccessToken () {
		String userKey = MCTweetConfig.getINSTANCE().getProperty(MCTweetConfig.ConfigKey.USER_KEY).getString();
		String userSecret = MCTweetConfig.getINSTANCE().getProperty(MCTweetConfig.ConfigKey.USER_SECRET).getString();
		if (userKey.equals("none") || userSecret.equals("none")) return;
		AccessToken token = new AccessToken(userKey, userSecret);
		this.newTwitter();
		this.twitter.setOAuthAccessToken(token);
	}
	
	public void tweet (String text) throws TwitterException {
		if (this.twitter == null) return;
		this.twitter.updateStatus(text);
	}
	
	public void tweet (TweetObject object) throws TwitterException {
		if (this.twitter == null) return;
		this.twitter.updateStatus(object.getTweetMsg());
	}
	
	private void setConsumer (Twitter twitter) {
		String configKey = MCTweetConfig.getINSTANCE().getProperty(MCTweetConfig.ConfigKey.API_KEY).getString();
		String configSecret = MCTweetConfig.getINSTANCE().getProperty(MCTweetConfig.ConfigKey.API_SECRET).getString();
		twitter.setOAuthConsumer(configKey.equals("default") ? API_KEY : configKey, configSecret.equals("default") ? API_SECRET : configSecret);
	}
}
