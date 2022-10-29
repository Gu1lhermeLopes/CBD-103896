package lab1.ex5;

import redis.clients.jedis.Jedis;
import java.util.*;
import java.io.*;

public class MsgSis {
   private Jedis jedis;

   public MsgSis() {
      this.jedis = new Jedis();
   }


   public void delDB() {
      jedis.flushDB();
   }

   public void delUser(String name) {
      jedis.srem("Users", name);
      jedis.del("User:"+name+"_followers");
      jedis.del("User:"+name+"_msg");
   }

   public void addUser(String name) {
      jedis.sadd("Users", name);
   }
   public void listUsers() {
      jedis.smembers("Users").forEach(System.out::println);
   }

   public void follow(String follower, String following) {
      if (jedis.sismember("Users", follower) && jedis.sismember("Users", following)) {
         jedis.sadd("User:"+following+"_followers", follower);
         System.out.println("now "+follower+" is following "+following);
      }else
         System.out.println("ERROR: at least a username doesnt exist");

   }

   public void storeMsg(String name, String message) {
      if(jedis.scard("User:"+name+"_followers")>0){
         Set<String> follows=jedis.smembers("User:"+name+"_followers");
         String msg=name+": "+message;
         for (String f : follows) {
            jedis.lpush("User:"+f+"_msg",msg);
         }
      }else
         System.out.println("ERROR: this user dont have followers");
   }

   public void readMsg(String name) {
      if (jedis.llen("User:"+name+"_msg")>0) {
         List<String> msg= jedis.lrange("User:"+name+"_msg",0,-1);
         for (String s : msg) {
            System.out.println(s);
         }
      }
   }

   public static void main(String[] args) {
      MsgSis sistem = new MsgSis();

      Scanner sc = new Scanner(System.in);

      int op;


      while (true) {
         System.out.println("\n\n\n");
         System.out.println("1- add user");
         System.out.println("2- list users");
         System.out.println("3- follow user");
         System.out.println("4- send a message");
         System.out.println("5- Read all messages");
         System.out.println("6- delete user");
         System.out.println("7- delete DB");
         System.out.println("0- Exit");
         System.out.print("Enter Your Menu Choice: ");
         op = sc.nextInt();
         sc.nextLine();
         switch (op) {
            case 1:
               System.out.print("new user:");
               String newuser = sc.nextLine();
               sistem.addUser(newuser);
               break;
            case 2:
               System.out.println("ALL USERS");
               sistem.listUsers();
               break;
            case 3:
               System.out.print("follower:");
               String follower = sc.nextLine();

               System.out.print("following:");
               String following = sc.nextLine();

               sistem.follow(follower,following);
               break;
            case 4:
               System.out.print("message:");
               String message = sc.nextLine();
               System.out.print("user:");
               String user = sc.nextLine();
               sistem.storeMsg(user,message);
               break;
            case 5:
               System.out.print("user:");
               String userread = sc.nextLine();
               sistem.readMsg(userread);
               break;
            case 6:
               System.out.print("user:");
               String duser = sc.nextLine();
               sistem.delUser(duser);
               break;
            case 7:
               sistem.delDB();break;

            case 0:
               sistem.jedis.close();
               System.exit(0);
               break;

            default:
               break;
         }
      }
   }
}