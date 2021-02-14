package run;

import primaryTasks.Service;
import primaryTasks.ServiceImpl;

public class Main2 {
    public static void main(String[] args) {

        System.out.println("main2");


        Service service = ServiceImpl.createNewProxy();
        for (int i=0; i < 10; i++) {
            service.doHardWork("key" + i, "value" + 0);
        }

        for (int i=0; i < 10; i++) {
            service.doHardWork("key" + i, "value" + 0);
        }

    }

}
