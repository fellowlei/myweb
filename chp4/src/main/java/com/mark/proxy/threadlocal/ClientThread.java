package com.mark.proxy.threadlocal;

/**
 * Created by lulei on 2017/6/14.
 */
public class ClientThread extends Thread {
    private Sequence sequence;

    public ClientThread(Sequence sequence){
        this.sequence = sequence;
    }

    @Override
    public void run() {
        for(int i=0; i<3; i++){
            System.out.println(Thread.currentThread().getName() + " => " +  sequence.getNumber());
        }
    }
}
