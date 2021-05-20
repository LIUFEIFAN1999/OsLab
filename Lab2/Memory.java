package Lab2;

public class Memory {
    public byte[][] memory;
    //块的数量
    int n;
    //位示图的字节数
    int row;
    //块的大小（K）
    int blockSize;
    //空闲块数
    int availableBlocks;

    public Memory(int n, int size){
        this.n = availableBlocks = n;
        row = n/9 + 1;
        blockSize = size;
        memory = new byte[row][n];
    }

    public int getAvailableBlock(){
        int i, j;
        for(int index = 0; index<n; index++){
            i = index/8;
            j = index%8;
            if(memory[i][j] == 0){
                memory[i][j] = 1;
                availableBlocks -= 1;
                return index;
            }
        }
        return -1;
    }

    public void freeBlock(int bno){
        int i, j;
        i = bno/8;
        j = bno%8;
        memory[i][j] = 0;
    }
}
