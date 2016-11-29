package com.dongnao.jack.topic;

import com.dongnao.jack.topic.provider.DNProvider;
import com.dongnao.jack.topic.provider.DNProviderImpl;

public class Test {
    public static void main(String[] args) {
        DNProvider dnp = new DNProviderImpl();
        dnp.init();
        Test t = new Test();
        new Thread(t.new MyThread(dnp)).start();
        new Thread(t.new MyThread(dnp)).start();
        new Thread(t.new MyThread(dnp)).start();
        new Thread(t.new MyThread(dnp)).start();
        new Thread(t.new MyThread(dnp)).start();
        new Thread(t.new MyThread(dnp)).start();
        new Thread(t.new MyThread(dnp)).start();
        new Thread(t.new MyThread(dnp)).start();
        new Thread(t.new MyThread(dnp)).start();
        new Thread(t.new MyThread(dnp)).start();
    }
    
    private class MyThread implements Runnable {
        
        private DNProvider dnp;
        
        public MyThread(DNProvider dnp) {
            this.dnp = dnp;
        }
        
        public void run() {
            while (true) {
                dnp.sendMessage("DN-JACK-TOPIC");
                try {
                    Thread.sleep(500);
                }
                catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }
}
