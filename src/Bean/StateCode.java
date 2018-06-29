package Bean;

import java.util.ArrayDeque;

public class StateCode {

    volatile private int count;
    volatile private ArrayDeque<Integer> codes;

    public StateCode(){
        count=0;
        codes=new ArrayDeque<>();
    }

    public synchronized void putStateCode(int code){
        while (count==50){
            try {
                wait();
            }catch (InterruptedException ie){
                ie.printStackTrace();
            }
        }
        codes.add(code);
        count++;
        notifyAll();
    }

    public synchronized int getStateCode(){
        while (count==0){
            try {
                wait();
            }catch (InterruptedException ie){
                ie.printStackTrace();
            }
        }
        count--;
        notifyAll();
        return codes.remove();
    }
}
