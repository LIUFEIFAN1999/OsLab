package Lab3;

public class Test {
    public static void main(String[] args) {
        Os os = new Os();
        //初始化磁盘：柱面200，磁道20，扇区6，扇区大小4K
        os.initDisc(200, 20, 6, 4);
        System.out.println("初始化磁盘：柱面200，磁道20，扇区6，扇区大小4K");
        os.menu();
    }
}
