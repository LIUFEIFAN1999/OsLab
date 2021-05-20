package Lab2;

import java.util.HashMap;
import java.util.Objects;

public class Job {
    //作业名称
    public String name;
    //作业大小（K）
    public int size;
    //页数
    int pageCount;
    //页表(pageNo -> blockNo)
    HashMap pageTable = new HashMap<Integer, Integer>();

    public Job(String name, int size){
        this.name = name;
        this.size = size;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Job job = (Job) o;
        return name.equals(job.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
