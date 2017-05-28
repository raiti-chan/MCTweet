package com.raitichan.MCTweet.twitter;

/**
 * <br>Created by Raiti-chan on 2017/05/25.
 *
 * @author Raiti-chan
 * @version 1.0.0
 * @since 1.0.0
 */
public class TweetObject {
	
	private String tweetMsg;
	
	public TweetObject(String tweetMsg){
		this.tweetMsg = tweetMsg;
	}
	
	@SuppressWarnings("WeakerAccess")
	public String getTweetMsg() {
		return this.tweetMsg;
	}
	
}
