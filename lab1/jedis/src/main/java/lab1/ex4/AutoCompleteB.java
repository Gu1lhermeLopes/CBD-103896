package lab1.ex4;

import redis.clients.jedis.Jedis;
import java.util.*;

import java.io.*;

public class AutoCompleteB {




    public static void main(String[] args) {
        String KEY = "autocompleteB";
        Jedis jedis = new Jedis();
        jedis.del(KEY);

        try {
            FileInputStream file = new FileInputStream("nomes-pt-2021.csv");
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] spl = line.split(";");
                jedis.zadd(KEY, Double.parseDouble(spl[1]), spl[0]);
            }
            sc.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        Scanner s = new Scanner(System.in);
        String input;

        while (true) {
            System.out.print("Insert name to search:\t");
            input = s.nextLine();
            if (input.length() == 0) {
                break;
            }
            System.out.println();
            for (String name : jedis.zrevrange(KEY, 0, -1)     ) {
                if (name.startsWith(input))
                   System.out.println(name);
             }
        }

        jedis.close();
    }
}
