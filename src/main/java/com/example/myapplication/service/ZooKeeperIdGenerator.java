//package com.example.myapplication.service;
//
//import org.apache.zookeeper.*;
//import org.apache.zookeeper.data.Stat;
//import org.springframework.stereotype.Component;
//
//import java.io.IOException;
//import java.util.concurrent.CountDownLatch;
//
//@Component
//public class ZooKeeperIdGenerator {
//    private static final String ZK_SERVERS = "localhost:2181";
//    private static final int SESSION_TIMEOUT = 5000;
//    private static final String ID_PATH = "/shortener/id-";
//
//    private final ZooKeeper zooKeeper;
//
//    public ZooKeeperIdGenerator() throws IOException, InterruptedException, KeeperException {
//        CountDownLatch connectedSignal = new CountDownLatch(1);
//        zooKeeper = new ZooKeeper(ZK_SERVERS, SESSION_TIMEOUT, event -> {
//            if (event.getState() == Watcher.Event.KeeperState.SyncConnected) {
//                connectedSignal.countDown();
//            }
//        });
//        connectedSignal.await();
//
//        // Ensure the base path exists
//        Stat stat = zooKeeper.exists("/shortener", false);
//        if (stat == null) {
//            zooKeeper.create("/shortener", new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
//        }
//    }
//
//    public String generateId() throws KeeperException, InterruptedException {
//        String path = zooKeeper.create(ID_PATH, new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT_SEQUENTIAL);
//        return path.replace("/shortener/id-", "");
//    }
//}
