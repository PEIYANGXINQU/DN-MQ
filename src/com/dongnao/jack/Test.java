package com.dongnao.jack;

import com.dongnao.jack.provider.DNProvider;
import com.dongnao.jack.provider.DNProviderImpl;

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
                dnp.sendMessage("DN-JACK-2");
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
