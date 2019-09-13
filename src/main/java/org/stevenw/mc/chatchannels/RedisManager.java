package org.stevenw.mc.chatchannels;

import com.google.gson.Gson;
import org.bukkit.Bukkit;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

public class RedisManager{
    private Gson gson = new Gson();

    // A static instance of this class so we can access it without making a new instance.
    private static RedisManager instance = new RedisManager();

    // A static getter for the instance so that it can be retrieved without making a new instance.
    public static RedisManager getInstance(){
        return instance;
    }

    // A private constructor so instances cannot be created from out of the class.
    private RedisManager() {
    }
    public void subscribe(){


        new Thread("Redis Subscriber"){
            @Override
            public void run(){
                final Jedis subscriber = getJedis();
                subscriber.connect();

                String[] channels = {"chatchannels"};

                subscriber.subscribe(new JedisPubSub(){
                    @Override
                    public void onMessage(String channel, String messageJson){
                        Message message = fromJson(messageJson);
                        System.out.println(messageJson);
                        if(message != null) {
                            Bukkit.getPluginManager().callEvent(new MessageEvent(message));
                        }
                    }
                }, channels);
            }
        }.start();
    }
    public Jedis getJedis() {
        Jedis subscriber = new Jedis(sChatChannels.getPlugin().getConfig().getString("redis-host", "127.0.0.1"),
                sChatChannels.getPlugin().getConfig().getInt("redis-port", 6379));
        if( sChatChannels.getPlugin().getConfig().getBoolean("redis-auth", false)) {
            subscriber.auth( sChatChannels.getPlugin().getConfig().getString("redis-pass", "password"));
        }
        return subscriber;
    }
    public void publish(String channel,  String message){
        try(Jedis publisher = getJedis()){
            publisher.publish(channel, message);
        }
    }
    public String toJson (Message payload){

        String json = gson.toJson(payload);
        JSONObject obj = new JSONObject();
        obj.put("name", "chatchannel");
        obj.put("payload", json);

        return obj.toString();
    }

    public Message fromJson (String json){
        JSONParser parser = new JSONParser();
        JSONObject obj = null;
        try {
            obj = (JSONObject) parser.parse(json);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        assert obj != null;
        String name = (String) obj.get("name");
        String payloadJson = (String) obj.get("payload");

        if(name.equals("chatchannel")){
            return gson.fromJson(payloadJson,  Message.class);
        }
        return null;
    }
}