package Lab5;

public class PCB {
    public int no;
    public String name;
    public String state;
    public String reason;
    public int breakPoint;

    public PCB(String name, int no) {
        this.no = no;
        this.name = name;
        this.breakPoint = 0;
        this.state = "ready";
    }

    public void waitP(String reason, int breakPoint){
        state = "wait";
        this.reason = reason;
        this.breakPoint = breakPoint;
    }
}
