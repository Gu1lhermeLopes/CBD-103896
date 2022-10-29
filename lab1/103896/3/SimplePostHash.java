package lab1.ex3;

import java.io.*;
import java.util.*;
import redis.clients.jedis.Jedis;

public class SimplePostHash {
  public static void main(String[] args) {
    String HASH_KEY = "nomesss";
    Jedis jedis = new Jedis();
    int i = 0;
    String[] nome = { "Ana", "Pedro", "Maria", "Luis" };

    for (String user : nome) {
      jedis.hset(HASH_KEY, String.valueOf(i), user);
      i++;
    }
    jedis.hgetAll(HASH_KEY).forEach((k, v) -> {
        System.out.println(k+") "+ v);
      }
    );

    jedis.close();
  }
}
