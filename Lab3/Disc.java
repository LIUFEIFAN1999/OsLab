package Lab3;

public class Disc {
    //  柱面数
    public int cyl;
    //  磁道数（每个柱面）
    public int track;
    //  扇区数（每个磁道）
    public int sector;
    //每一块的大小（K）
    public int blockSize;
    public int blockCount;
    public int[] blocks;

    public Disc(int cyl, int track, int sector, int blockSize) {
        this.cyl = cyl;
        this.track = track;
        this.sector = sector;
        this.blockSize = blockSize;
        blockCount = cyl * track * sector;
        blocks = new int[blockCount];
    }
}
