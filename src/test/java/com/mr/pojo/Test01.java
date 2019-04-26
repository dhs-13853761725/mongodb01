package com.mr.pojo;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * Created by shun on 2019/4/10.
 */
public class Test01 {

    @Test//尝试连接mongo数据库,不报异常就说明连接成功
    public void test1(){
        //创建连接，如果用的本地IP和默认端口号则可以new MongoClient()
        MongoClient mongoClient = new MongoClient("127.0.0.1",27017);
       //获得一个库
        MongoDatabase mongoDatabase = mongoClient.getDatabase("mr");

        System.out.println("连接成功");
        //关闭连接释放资源
        mongoClient.close();
    }

    @Test
    public void test2(){
        //创建连接，如果用的本地IP和默认端口号则可以new MongoClient()
        MongoClient mongoClient = new MongoClient("127.0.0.1",27017);
        //获得一个库
        MongoDatabase mongoDatabase = mongoClient.getDatabase("mr");
        //获得一个指定的集合
        MongoCollection<Document> car = mongoDatabase.getCollection("car");
        //创建一个文档
        Document c1 = new Document();
        //文档中添加内容
        c1.append("name","奥玛");
        c1.append("money",19);
        //添加一个集合到文档中
        car.insertOne(c1);
        mongoClient.close();
    }
    @Test
    public void test3(){
        //创建连接，如果用的本地IP和默认端口号则可以new MongoClient()
        MongoClient mongoClient = new MongoClient("127.0.0.1",27017);
        //获得一个库
        MongoDatabase mongoDatabase = mongoClient.getDatabase("mr");
        //获得一个指定的集合
        MongoCollection<Document> car = mongoDatabase.getCollection("car");
        //创建一个文档
        Document c1 = new Document();
        //文档中添加内容
        c1.append("name","宝贝");
        c1.append("money",30);
        Document c2 = new Document();
        c2.append("name","宝贝11");
        c2.append("money",21);
        Document c3 = new Document();
        c3.append("name","宝贝22");
        c3.append("money",333);

        List<Document> documents = Arrays.asList(c1, c2, c3);
        car.insertMany(documents);
        mongoClient.close();
    }
    @Test
    public void test4(){
        //创建连接，如果用的本地IP和默认端口号则可以new MongoClient()
        MongoClient mongoClient = new MongoClient("127.0.0.1",27017);
        //获得一个库
        MongoDatabase mongoDatabase = mongoClient.getDatabase("mr");
        //获得一个指定的集合
        MongoCollection<Document> car = mongoDatabase.getCollection("car");
        //查询所有文档
        FindIterable<Document> documents = car.find();
        //遍历所有文档
        for(Document c : documents){
            System.out.println(c.get("_id")+"-"+c.get("name")+"-"+c.get("money"));
        }
        mongoClient.close();
    }
    @Test
    public void test5(){
        int page = 1;//查询页数
        int rows = 2;//每页条数
        //创建连接，如果用的本地IP和默认端口号则可以new MongoClient()
        MongoClient mongoClient = new MongoClient("127.0.0.1",27017);
        //获得一个库
        MongoDatabase mongoDatabase = mongoClient.getDatabase("mr");
        //获得一个指定的集合
        MongoCollection<Document> car = mongoDatabase.getCollection("car");
        //分页查询文档
        FindIterable<Document> limit = car.find().skip((page - 1) * rows).limit(rows);
        //遍历所有文档
        for(Document c : limit){
            System.out.println(c.get("_id")+"-"+c.get("name")+"-"+c.get("money"));
        }
        mongoClient.close();
    }
    @Test
    public void test6(){
        //创建连接，如果用的本地IP和默认端口号则可以new MongoClient()
        MongoClient mongoClient = new MongoClient("127.0.0.1",27017);
        //获得一个库
        MongoDatabase mongoDatabase = mongoClient.getDatabase("mr");
        //获得一个指定的集合
        MongoCollection<Document> car = mongoDatabase.getCollection("car");
        FindIterable<Document> money = car.find(Filters.eq("money", 333));
        //遍历所有文档
        for(Document c : money){
            System.out.println(c.get("_id")+"-"+c.get("name")+"-"+c.get("money"));
        }
        mongoClient.close();
    }

    /**
     * 没实现
     */
    @Test//多条件查询符合的文档
    public void test7(){
        //创建连接，如果用的本地IP和默认端口号则可以new MongoClient()
        MongoClient mongoClient = new MongoClient("127.0.0.1",27017);
        //获得一个库
        MongoDatabase mongoDatabase = mongoClient.getDatabase("mr");
        //获得一个指定的集合
        MongoCollection<Document> car = mongoDatabase.getCollection("car");
        Bson and = Filters.and(Filters.eq("name", "宝贝"), Filters.lt("money", 30));
        FindIterable<Document> documents = car.find(and);
        //遍历所有文档
        for(Document c : documents){
            System.out.println(c.get("_id")+"-"+c.get("name")+"-"+c.get("money"));
        }
        mongoClient.close();
    }
    @Test//修改符合条件的文档
    public void test8(){
        //创建连接，如果用的本地IP和默认端口号则可以new MongoClient()
        MongoClient mongoClient = new MongoClient("127.0.0.1",27017);
        //获得一个库
        MongoDatabase mongoDatabase = mongoClient.getDatabase("mr");
        //获得一个指定的集合
        MongoCollection<Document> car = mongoDatabase.getCollection("car");
        //修改符合条件的第一条数据
        //car.updateOne(Filters.eq("name","宝贝11"),new Document("$set",new Document("money",33)));
        car.updateMany(Filters.eq("name","宝贝11"),new Document("$set",new Document("money",55)));
        mongoClient.close();
    }

    @Test//删除符合条件的文档
    public void test9(){
        //创建连接，如果用的本地IP和默认端口号则可以new MongoClient()
        MongoClient mongoClient = new MongoClient("127.0.0.1",27017);
        //获得一个库
        MongoDatabase mongoDatabase = mongoClient.getDatabase("mr");
        //获得一个指定的集合
        MongoCollection<Document> car = mongoDatabase.getCollection("car");
        //car.deleteOne(Filters.eq("name","宝贝22"));
        car.deleteMany(Filters.eq("name","宝贝22"));
        mongoClient.close();
    }


}
