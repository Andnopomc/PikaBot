package com.andnopo.PikaBot.source;

import com.andnopo.PikaBot.info.Keys;
import com.andnopo.PikaBot.music.MPlayer;
import com.andnopo.PikaBot.utils.EasyEmbedBuilder;
import com.andnopo.PikaBot.utils.Tools;
import org.apache.commons.io.IOUtils;
import org.json.JSONObject;
import sx.blah.discord.handle.impl.events.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.EmbedBuilder;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;

import java.awt.*;
import java.io.IOException;
import java.net.URL;

public class Youtube {
    public static void play(String id, IUser user, MessageReceivedEvent event) throws IOException, RateLimitException, DiscordException, MissingPermissionsException {
        JSONObject jsonf = new JSONObject(IOUtils.toString(new URL("https://www.googleapis.com/youtube/v3/videos?part=id%2C+snippet&id=" + id + "&key" + Keys.ytKey), "UTF-8"));
        JSONObject j = jsonf.getJSONArray("items").getJSONObject(0).getJSONObject("snippet");
        build(j.getString("title"), j.getString("channelTitle"), user, event);
        MPlayer.play(id);
    }

    public static void find(String q, IUser user, MessageReceivedEvent event) throws IOException, RateLimitException, DiscordException, MissingPermissionsException {
        JSONObject jsonf = new JSONObject(IOUtils.toString(new URL("https://www.googleapis.com/youtube/v3/search?part=id%2C+snippet&q=" + q.replace(" ", "+") + "&key" + Keys.ytKey), "UTF-8"));
        JSONObject j = jsonf.getJSONArray("items").getJSONObject(0).getJSONObject("snippet");
        build(j.getString("title"), j.getString("channelTitle"), user, event);
    }


    public static void build(String song, String by, IUser user, MessageReceivedEvent event) throws RateLimitException, DiscordException, MissingPermissionsException {
        Tools.register("#E62117", song, "By: " + by, "Requested by " + user.getName(), user);
        event.getMessage().getChannel().sendMessage("", new EmbedBuilder().withColor(Color.decode("#E62117")).withAuthorName("Pika Bot").withAuthorIcon("http://i.imgur.com/hFYlfGW.gif").withTitle(song).withDescription("By: " + by).withFooterIcon(user.getAvatarURL()).withFooterText("Requested by " + user.getName()).withTimestamp(System.currentTimeMillis()).build(), false);
    }
}
