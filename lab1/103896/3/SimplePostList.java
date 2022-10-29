package lab1.ex3;
import redis.clients.jedis.Jedis;
import java.util.*;
import java.io.*;

public class SimplePostList {
   public static void main(String[] args) {
      String LL = "lista";
      Jedis jedis = new Jedis();
      jedis.del(LL);
      String[] users = { "Ana", "Pedro", "Maria", "Luis" };  
      for (String user : users)
         jedis.lpush(LL, user);
      jedis.lrange(LL,0,-1).forEach(System.out::println);
      jedis.close();
   }
}