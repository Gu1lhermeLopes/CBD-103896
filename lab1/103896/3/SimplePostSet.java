package lab1.ex3;
import redis.clients.jedis.Jedis;
import java.util.*;
import java.io.*;

public class SimplePostSet {
    public static void main(String[] args) {
         String USERS_KEY = "users";
         Jedis jedis = new Jedis();
         String[] users = { "Ana", "Pedro", "Maria", "Luis" };
         
         for (String user : users)
            jedis.sadd(USERS_KEY, user);
         jedis.smembers(USERS_KEY).forEach(System.out::println);
         jedis.close();
      }
   }