package Lab2;

import java.util.ArrayList;
import java.util.Scanner;

public class Os {
    //主存
    public Memory memory;
    //作业列表
    public ArrayList<Job> jobs = new ArrayList<>();

    public void initMem(int n, int size){
        memory = new Memory(n, size);
    }


    private void allocateBlock(Job job){
        int pno;
        for(pno=0; pno<job.pageCount; pno++){
            int bno = memory.getAvailableBlock();
            job.pageTable.put(pno, bno);
        }
    }

    private void freeBlock(Job job){
        int pno, bno;
        for(pno=0; pno<job.pageCount; pno++){
            bno = (int) job.pageTable.get(pno);
            memory.freeBlock(bno);
        }
    }

    private boolean loadJob(Job job){
        if(job.size % memory.blockSize == 0)
            job.pageCount = job.size/memory.blockSize;
        else
            job.pageCount = job.size/memory.blockSize + 1;
        if(job.pageCount > memory.availableBlocks)
            return false;
        allocateBlock(job);
        jobs.add(job);
        return true;
    }

    private void freeJob(String jobName){
        Job outJob = null;
        for(Job job : jobs){
            if(job.name.equals(jobName)){
                freeBlock(job);
                job.pageTable.clear();
                outJob = job;
                break;
            }
        }
        if(outJob!=null){
            jobs.remove(outJob);
            System.out.println("<<<空间已释放");
        }
        else {
            System.out.println("<<<作业不存在！");
        }
    }

    private void freeJob(){
        for(Job job : jobs){
            freeBlock(job);
            job.pageTable.clear();
        }
        jobs.clear();
    }

    public void menu(){
        Scanner sc = new Scanner(System.in);
        int op;
        System.out.println("<<<请选择");
        System.out.println("1.空间申请");
        System.out.println("2.空间释放");
        System.out.println("3.位示图");
        System.out.println("4.退出");
        printJobs();
        System.out.print(">>>");
        op = sc.nextInt();
        switch (op){
            case 1: op1();menu();
            case 2: op2();menu();
            case 3: printBitPic();menu();
            case 4: exit();
            default:
                System.out.println("<<<输入错误！");menu();
        }
    }

    private void op1(){
        Scanner sc = new Scanner(System.in);
        String name;
        int size;
        System.out.print("装入作业名称：");
        name = sc.next();
        System.out.print("作业大小(K)：");
        size = sc.nextInt();
        Job newJob = new Job(name,size);
        if(! loadJob(newJob))
            System.out.println("<<<内存不足！");
        else
            printPageTable(newJob);
    }

    private void op2(){
        Scanner sc = new Scanner(System.in);
        String name;
        System.out.print("释放作业名称：");
        name = sc.next();
        freeJob(name);
    }

    private void printPageTable(Job job){
        System.out.println("<<<"+job.name+"页表");
        System.out.println("页号\t块号");
        int pno, bno;
        for(pno=0; pno<job.pageCount; pno++){
            bno = (int) job.pageTable.get(pno);
            System.out.println(pno+"\t\t"+bno);
        }
    }

    private void printBitPic(){
        System.out.println("<<<位示图");
        int i, j;
        System.out.print("\t");
        for(i=0; i<8;i++){
            System.out.print(i+"\t");
        }
        System.out.println();
        for(int index = 0; index<memory.n; index++) {
            i = index / 8;
            j = index % 8;
            if(j==0)
                System.out.print(i+"\t");
            System.out.print(memory.memory[i][j]+"\t");
            if(j==7)
                System.out.println();
        }
    }

    private void exit(){
        freeJob();
        System.out.println("<<<已释放全部空间");
        printBitPic();
        System.exit(0);
    }

    private void printJobs(){
        if(jobs.size()==0)
            return;
        System.out.println("已装入作业：");
        for(Job job : jobs){
            System.out.print(job.name+"  ");
        }
        System.out.println();
    }
}
