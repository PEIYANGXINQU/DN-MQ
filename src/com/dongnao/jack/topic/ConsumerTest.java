package com.dongnao.jack.topic;

import com.dongnao.jack.topic.consumer.DNConsumer;
import com.dongnao.jack.topic.consumer.DNConsumerImpl;

public class ConsumerTest {
    
    public static void main(String[] args) {
        DNConsumer dnc = new DNConsumerImpl();
        dnc.init();
        new ConsumerTest().handlerMsg(dnc);
    }
    
    public void handlerMsg(DNConsumer dnc) {
        MyThread myt = new MyThread(dnc);
        
        new Thread(myt).start();
        new Thread(myt).start();
        new Thread(myt).start();
        new Thread(myt).start();
        new Thread(myt).start();
        new Thread(myt).start();
        new Thread(myt).start();
        new Thread(myt).start();
        new Thread(myt).start();
        new Thread(myt).start();
    }
    
    private class MyThread implements Runnable {
        
        private DNConsumer dnc;
        
        public MyThread(DNConsumer dnc) {
            this.dnc = dnc;
        }
        
        public void run() {
            while (true) {
                dnc.getMessage("DN-JACK-TOPIC");
                try {
                    Thread.sleep(500);
                }
                catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        
        public DNConsumer getDnc() {
            return dnc;
        }
        
        public void setDnc(DNConsumer dnc) {
            this.dnc = dnc;
        }
    }
}
