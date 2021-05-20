package Lab2;

public class Test {
    public static void main(String[] args) {
        //创建操作系统
        Os os = new Os();
        //初始化内存，64块，块大小4K
        os.initMem(64, 4);
        os.menu();
    }
}
