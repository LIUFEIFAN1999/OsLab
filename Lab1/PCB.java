package Lab1;

public class PCB {
    public String name;
    public int time;
    public int priority;
    public String state;

    public PCB(String name, int time, int priority) {
        this.name = name;
        this.time = time;
        this.priority = priority;
        this.state = "R";
    }

    public void run(){
        System.out.println("进程"+name+"正在运行...");
        time = time-1;
        priority = priority -1;
    }
}
