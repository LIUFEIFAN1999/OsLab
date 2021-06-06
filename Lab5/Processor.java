package Lab5;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Processor {
    //进程列表
    ArrayList<PCB> pList = new ArrayList<>();
    //就绪进程列表
    ArrayList<PCB> readyList = new ArrayList<>();
    //等待进程列表
    ArrayList<PCB> waitList = new ArrayList<>();
    //当前运行进程
    PCB p;
    //生产者、消费者生产、取出的产品
    String x1,x2,c1,c2;
    //生产者生产总数，当总数达到20，达到结束条件
    int count = 0;
    int s1, s2, PC;
    //指针
    int in=0, out = 0;
    public String[] products = new String[10];
    public String[] PA = {"produce", "p(s1)", "put", "v(s2)", "goto0"},
            SA = {"p(s2)", "get", "v(s1)", "consume", "goto0"};
    public Processor(){
        PCB p1 = new PCB("生产者", 1);
        PCB p2 = new PCB("消费者", 1);
        PCB p3 = new PCB("生产者", 2);
        PCB p4 = new PCB("消费者", 2);
        pList.add(p1);
        pList.add(p2);
        pList.add(p3);
        pList.add(p4);
        init();
    }
    public void init(){
        s1=10;
        s2 =0;
        p = pList.get(0);
        PC = 0;
    }
    public void start(){
        while(true) {
            p.breakPoint = PC;
            readyList.clear();
            for (PCB pcb : pList) {
                if (pcb.state.equals("ready"))
                    readyList.add(pcb);
            }
            //就绪进程数量
            int readySize = readyList.size();
            if (readySize == 0)
                return;
            int op;
            Random rd = new Random();
            op = rd.nextInt(readySize);
            p = readyList.get(op);
            p.state = "running";
            PC = p.breakPoint;
            printP();
            printBuffer();
            exec();
        }
    }
    public void exec(){
        String op;
        if(p.name.equals("生产者"))
            op = PA[PC];
        else
            op = SA[PC];
        PC = PC + 1;
        switch (op){
            case "produce": produceC();p.state="ready";break;
            case "p(s1)": p();break;
            case "put": put();p.state="ready";break;
            case "v(s2)": v();break;
            case "goto0": goto0();p.state="ready";break;
            case "p(s2)": p();break;
            case "get": get();p.state="ready";break;
            case "v(s1)": v();break;
            case "consume": consumeX();p.state="ready";break;
        }
        //生产者达到运行条件
        if(count>=20){
            for(PCB pcb:pList){
                if(pcb.name.equals("生产者"))
                    pcb.state = "completed";
            }
        }
    }
    public void p(){
        int s;
        if(p.name.equals("生产者")){
            s1 = s1-1;
            s = s1;
        }
        else{
            s2 = s2-1;
            s = s2;
        }
        if(s<0){
            String reason;
            if(p.name.equals("生产者"))
                reason = "等待s1";
            else
                reason = "等待s2";
            p.waitP(reason, PC);
            waitList.add(p);
        }
        else
            p.state = "ready";
    }
    public void v(){
        int s;
        if(p.name.equals("消费者")){
            s1 = s1+1;
            s = s1;
        }
        else{
            s2 = s2+1;
            s = s2;
        }
        if(s<=0){
            //随机选择一个等待进程
            int waitSize = waitList.size();
            Random rd = new Random();
            int op = rd.nextInt(waitSize);
            //修改状态为就绪
            waitList.get(op).state = "ready";
            //从等待列表中移除
            waitList.remove(op);
        }
        p.state = "ready";
        //生产者的V原语表示完成生产一个产品
        if(p.name.equals("生产者"))
            count = count + 1;
    }
    public void put(){
        if(p.no == 1)
            products[in] = c1;
        if(p.no == 2)
            products[in] = c2;
        in = (in + 1)%10;
    }
    public void get(){
        if(p.no == 1)
            x1 = products[out];
        if(p.no == 2)
            x2 = products[out];
        out = (out + 1)%10;
    }
    public void produceC(){
        Scanner sc = new Scanner(System.in);
        System.out.print("生产者"+p.no+"生产产品：");
        if(p.no == 1)
            c1 = sc.next();
        if(p.no == 2)
            c2 = sc.next();
    }
    public void consumeX(){
        if(p.no == 1)
            System.out.println("消费者者1正在消费产品："+x1);
        if(p.no == 2)
            System.out.println("消费者者1正在消费产品："+x2);
    }
    public void goto0(){
        PC = 0;
    }
    public void printP(){
        System.out.println("----------------进程状态-----------------");
        System.out.println("\t进程名\t状态\t断点\t对应的指令");
        for(PCB pcb:pList){
            if(pcb.name.equals("生产者"))
                System.out.println("\t"+pcb.name+pcb.no+"\t"+pcb.state+"\t"+pcb.breakPoint+"\t\t"+PA[pcb.breakPoint]);
            else
                System.out.println("\t"+pcb.name+pcb.no+"\t"+pcb.state+"\t"+pcb.breakPoint+"\t\t"+SA[pcb.breakPoint]);
        }
        System.out.println("-------------------------------------------");
    }
    public void printBuffer(){
        System.out.println("---------循环缓冲区---------");
        for(int i=0; i<10;i++){
            System.out.println("\t\t"+i+"\t\t"+products[i]);
        }
        System.out.println("插入位置："+in+"\t\t删除位置："+out);
        System.out.println("------------------------------");
    }
}
