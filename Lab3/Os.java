package Lab3;

import java.util.ArrayList;
import java.util.Scanner;

public class Os {
    //文件目录
    public ArrayList<FileItem> fileCatalog = new ArrayList<>();
    //空闲区表
    public ArrayList<FreeItem> freeCatalog = new ArrayList<>();
    // 磁盘
    public Disc disc;

    public void initDisc(int cyl, int track, int sector, int blockSize){
        disc = new Disc(cyl, track, sector, blockSize);
        reSetFreeCatalog();
    }

    public void reSetFreeCatalog(){
        freeCatalog.clear();
        int no=0, start=-1,count;
        for(int i=0; i<disc.blockCount; i++){
            if(disc.blocks[i] == 0 && start == -1)
                start = i;
            if(disc.blocks[i]==1 && start!=-1){
                count = i - start;
                no++;
                freeCatalog.add(new FreeItem(no, start, count, "未分配"));
                start = -1;
            }

            else if(i == disc.blockCount-1 && disc.blocks[i]==0){
                count = i + 1 - start;
                no++;
                freeCatalog.add(new FreeItem(no, start, count, "未分配"));
            }
        }
    }

    public void allocateDisc(int start,int blockCount){
        int i;
        for(i=start; i< start + blockCount; i++)
            disc.blocks[i] = 1;
    }

    public void clearDisc(int start, int blockCount){
        int i;
        for(i=start; i< start + blockCount; i++)
            disc.blocks[i] = 0;
    }

    public boolean createFile(File file){
        int blockCount;
        boolean success = false;
        if(file.size % disc.blockSize == 0)
            blockCount = file.size / disc.blockSize;
        else
            blockCount = file.size / disc.blockSize + 1;
        for(FreeItem item : freeCatalog){
            if(item.count >= blockCount){
                int cyl, track, sector;
                allocateDisc(item.startBno, blockCount);
                sector = item.startBno % disc.sector;
                track = (item.startBno / disc.sector) % disc.track;
                cyl = (item.startBno / disc.sector) / disc.track;
                fileCatalog.add(new FileItem(file.name, cyl, track, sector, blockCount));
                success = true;
                break;
            }
        }
        if(success){
            reSetFreeCatalog();
            return true;
        }
        return false;
    }

    public boolean deleteFile(String name){
        FileItem delete = null;
        for(FileItem item : fileCatalog){
            if(item.fileName.equals(name)){
                delete = item;
                int start = (item.cyl* disc.track + item.track)* disc.sector + item.sector;
                int blockCount = item.recordCount;
                clearDisc(start, blockCount);
                break;
            }
        }
        if(delete!=null){
            fileCatalog.remove(delete);
            reSetFreeCatalog();
            return true;
        }
        else
            return false;
    }

    public void printFileCatalog(){
        if(fileCatalog.size()==0)
            System.out.println("<<<文件目录为空！");
        else{
            System.out.println("-------------------------------文件目录---------------------------------");
            System.out.println("\t\t\t文件名\t柱面号\t磁道号\t扇区号\t记录数");
            for(FileItem item : fileCatalog){
                System.out.println("\t\t\t"+item.fileName+"\t"+item.cyl+"\t\t"+item.track+"\t\t"+item.sector+"\t\t"+item.recordCount);
            }
            System.out.println("-------------------------------------------------------------------------");
        }
    }

    public void printFreeCatalog(){
        System.out.println("-------------------------------空闲区表---------------------------------");
        System.out.println("\t\t序号\t起始空闲块号\t空闲块数\t 状态");
        for(FreeItem item : freeCatalog){
            System.out.println("\t\t "+item.no+"\t\t\t"+item.startBno+"\t\t\t"+item.count+"\t\t"+item.state);
        }
        System.out.println("-------------------------------------------------------------------------");
    }

    public void menu(){
        Scanner sc = new Scanner(System.in);
        int op;
        System.out.println("<<<请选择");
        System.out.println("1.创建文件");
        System.out.println("2.删除文件");
        System.out.println("3.文件目录");
        System.out.println("4.空闲区表");
        System.out.println("5.退出");
        System.out.print(">>>");
        op = sc.nextInt();
        switch (op){
            case 1: op1();menu();
            case 2: op2();menu();
            case 3: printFileCatalog();menu();
            case 4: printFreeCatalog();menu();
            case 5: exit();
            default:
                System.out.println("<<<输入错误！");menu();
        }
    }

    public void op1(){
        Scanner sc = new Scanner(System.in);
        String name;
        int size;
        System.out.print("输入文件名：");
        name = sc.next();
        System.out.print("输入文件大小（K）：");
        size = sc.nextInt();
        File newFile = new File(name, size);
        if(createFile(newFile))
            System.out.println("<<<"+name+"创建成功！");
        else
            System.out.println("<<<磁盘空间不足，创建失败！");
    }

    public void op2(){
        Scanner sc = new Scanner(System.in);
        String name;
        System.out.print("输入要删除的文件名：");
        name = sc.next();
        if(deleteFile(name))
            System.out.println("<<<"+name+"删除成功！");
        else
            System.out.println("<<<文件不存在！删除失败");
    }

    public void exit(){
        System.exit(0);
    }
}
