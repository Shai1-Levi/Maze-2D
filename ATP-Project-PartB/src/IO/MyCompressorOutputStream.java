package IO;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Vector;

public class MyCompressorOutputStream extends OutputStream{

    private OutputStream out;
    private HashMap<Integer, Integer> byteHash;
    private byte[] compressedData;

    public MyCompressorOutputStream(OutputStream outputStreamData){
        this.out = outputStreamData;
         this.byteHash = new HashMap<Integer, Integer>();
    }

    public void write(int b) throws IOException {
        this.out.write(b);
    }

    private int getRealVal(int bytee) {
        if (bytee < 0) {
            return 256 + bytee;
        } else {
            return bytee;
        }
    }

    /**
     *  the method compress the grid of the maze that every 8 cell is represent in one byte.
     *  the zise, start, end we don't compress because it is all ready compressed
     *
     * @param b is from type byte[] that represent all the data of the maze
     */
    private void Compressor(byte[]b)
    {
        int index =0;
        int counter = 0;
        Vector compressToByte = new Vector();

        while(counter < 6)
        {
            if(getRealVal(b[index]) < 255){
                counter++;
            }
            compressToByte.add(b[index]);
            index++;
        }

        Integer sum = 0;
        counter = 0;
        int temp;

        for(int k = index; k < b.length; k++){
                if (counter == 8) {
                    sum = 0;
                    counter = 0;
                }
                if (getRealVal(b[k]) == 1) {
                    temp = (int) Math.pow(2, counter);
                    sum += temp;
                }
                if (counter == 7) {
                    compressToByte.add(sum.byteValue());
                }
                counter++;
        }
        if ( counter > 0 && counter < 7) {
            compressToByte.add(sum.byteValue());
        }
        int lSize = compressToByte.size();
        this.compressedData = new byte[lSize];
        for (int i = 0; i < lSize; i++) {
            this.compressedData[i] = (byte) compressToByte.get(i);
        }
    }

    @Override
    public void write(byte[] b) throws IOException{
        Compressor(b);

        for(int i = 0; i  < this.compressedData.length; i++)
        {
            //if(!this.byteHash.containsKey(this.compressedData[i])){
            //    this.byteHash.put(((int)this.compressedData[i]),1);
                this.out.write(this.compressedData[i]);
            //}
            //else{
            //    this.byteHash.replace((int)b[i],byteHash.get(this.compressedData[i]), byteHash.get(this.compressedData[i]) + 1);
            //}
        }
    }


}
