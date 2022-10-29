package lab1.ex4;

import redis.clients.jedis.Jedis;
import java.util.*;
import java.io.*;

public class AutoComplete {
   public static void main(String[] args) {
      String KEY = "autocomplete";
      Jedis jedis = new Jedis();
      jedis.del(KEY);
      try {
         File file = new File("names.txt");
         Scanner myReader = new Scanner(file);
         while (myReader.hasNextLine()) {
            String name = myReader.nextLine();
            jedis.zadd(KEY, 0, name);
         }
         myReader.close();
      } catch (FileNotFoundException e) {
         System.out.println("An error occurred.");
         e.printStackTrace();
      }

      Scanner sc = new Scanner(System.in);
      String input;

      while (true) {
         System.out.print("Insert name to search:\t");
         input = sc.nextLine();
         if (input.length() == 0) {
            break;
         }
         System.out.println();

         for (String name : jedis.zrange(KEY, 0, -1)) {
            if (name.startsWith(input))
               System.out.println(name);
         }
      }
      jedis.close();
   }
}