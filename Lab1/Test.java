package Lab1;

public class Test {
    public static void main(String[] args) {
        Manager manager = new Manager();
        /*PCB p1 = new PCB("P1", 5, 5);
        PCB p2 = new PCB("P2", 4, 1);
        PCB p3 = new PCB("P3", 2, 3);
        PCB p4 = new PCB("P4", 3, 4);
        PCB p5 = new PCB("P5", 2, 2);
        manager.addPCB(p1);
        manager.addPCB(p2);
        manager.addPCB(p3);
        manager.addPCB(p4);
        manager.addPCB(p5);*/
        manager.initPCB(5);
        manager.showReady();
        manager.runPCB();
        manager.showEnd();
    }
}
