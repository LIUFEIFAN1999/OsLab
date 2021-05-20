package Lab3;

public class FileItem {
    public String fileName;
    //起始物理地址
    //  柱面号
    public int cyl;
    //  磁道号
    public int track;
    //  物理记录号
    public int sector;
    public int recordCount;

    public FileItem(String fileName, int cyl, int track, int sector, int recordCount) {
        this.fileName = fileName;
        this.cyl = cyl;
        this.track = track;
        this.sector = sector;
        this.recordCount = recordCount;
    }
}
