package Lab1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

public class Manager {
    ArrayList<PCB> readyQueue = new ArrayList<>();
    ArrayList<PCB> endQueue = new ArrayList<>();

    public void addPCB(PCB p){
        readyQueue.add(p);
    }

    public void runPCB(){
        int count = 0;
        while(true) {
            count++;
            System.out.println("第"+count+"次进程调度>>>>");
            sort();
            PCB p = readyQueue.get(0);
            readyQueue.remove(p);
            p.run();
            showReady();
            if (p.time == 0) {
                p.state = "E";
                readyQueue.remove(p);
                endQueue.add(p);
            }
            else {
                addPCB(p);
            }
            if(readyQueue.size()==0) {
                System.out.println("<<<<End");
                return;
            }
        }
    }
    public void showReady(){
        if(readyQueue.size()==0){
            System.out.println("\t\t\t暂无就绪进程");
            return;
        }
        System.out.println("\t\t\t就绪进程列表");
        System.out.println("就绪进程\t运行时间\t优先数\t状态");
        for(PCB p: readyQueue){
            System.out.println("\t"+p.name+"\t\t\t"+p.time+"\t\t"+p.priority+"\t\t"+p.state);
        }
    }

    public void showEnd(){
        System.out.println("\t结束进程列表");
        System.out.println("\t结束进程\t状态");
        for(PCB p: endQueue){
            System.out.println("\t"+p.name+"\t\t\t"+p.state);
        }
    }

    public void sort(){
        Collections.sort(readyQueue, new Comparator<PCB>() {
            @Override
            public int compare(PCB o1, PCB o2) {
                return o2.priority-o1.priority;
            }
        });
    }

    public void initPCB(int n){
        Scanner sc = new Scanner(System.in);
        int i =1;
        int time;
        int priority;
        String name;
        while(i<=n){
            System.out.print("输入第"+i+"个进程的运行时间：");
            time = sc.nextInt();
            System.out.print("输入第"+i+"个进程的优先数：");
            priority = sc.nextInt();
            name = "P"+i;
            readyQueue.add(new PCB(name,time,priority));
            i++;
        }
    }
}
