package com.example.demo.credit;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * @author zph  on 2018/3/13
 */
public class App implements Watcher {

    private static CountDownLatch connectedSemaphore = new CountDownLatch(1);
    public static Stat stat = new Stat();
    ZooKeeper zooKeeper;

    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        String p = "/testaa";
        ZooKeeper zooKeeper = new ZooKeeper("192.168.1.10:2181", 5000, new App());
        connectedSemaphore.await();
        //exists register watch
        zooKeeper.exists(p, true);
        String path = zooKeeper.create(p, "456".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,
                CreateMode.PERSISTENT);
        //get register watch
        zooKeeper.getData(path, true, stat);
        zooKeeper.setData(path, "hhhh".getBytes(), -1);
        zooKeeper.exists(path, true);
        //exists register watch
        zooKeeper.delete(path, -1);

    }

    public void process(WatchedEvent event) {
        if (Watcher.Event.KeeperState.SyncConnected == event.getState()) {
            if (Watcher.Event.EventType.None == event.getType() && null == event.getPath()) {
                connectedSemaphore.countDown();
                System.out.println("Zookeeper session established");
            } else if (Watcher.Event.EventType.NodeCreated == event.getType()) {
                System.out.println("success create znode");

            } else if (Watcher.Event.EventType.NodeDataChanged == event.getType()) {
                System.out.println("success change znode: " + event.getPath());

            } else if (Watcher.Event.EventType.NodeDeleted == event.getType()) {
                System.out.println("success delete znode");

            } else if (Watcher.Event.EventType.NodeChildrenChanged == event.getType()) {
                System.out.println("NodeChildrenChanged");

            }

        }
    }
}
