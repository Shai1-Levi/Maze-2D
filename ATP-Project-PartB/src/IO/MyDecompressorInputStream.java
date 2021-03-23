package IO;
import java.io.IOException;
import  java.io.InputStream;

public class MyDecompressorInputStream extends InputStream{

    private InputStream in;
    public MyDecompressorInputStream(InputStream inputStream){
        this.in = inputStream;

    }

    private int getRealVal(int bytee) {
        if (bytee < 0) {
            return 256 + bytee;
        } else {
            return bytee;
        }
    }

    private int[] getBitsFromByte(int bytee){
        int[] arrayOfBits = new int[8];
        int temp = getRealVal(bytee);
        int i = 0;
        while(temp > 0){
            arrayOfBits[i] = temp%2;
            temp /=2;
            i++;
        }
        return arrayOfBits;
    }

    @Override
    public int read() throws IOException {
        return 0;
    }

    /**
     *
     * @param b the array of byte to write the decompressed data
     *  the method read the data from file, and decompressed it to array that the decompressed is execute is this steps
     *   1. we read the this categories size, start posiotion, end posiotion.
     *          if read 255 the next byte is connected to previous.
     *          else it is a new category
     *   2. every byte his bits represent 8 cell that every cell is 1 bit. inorder
     * @return we explain in the method
     * @throws IOException
     */
    public int read(byte[] b/* get an byteArray of zeros to write on here*/) throws IOException {
        if(b == null)
        {
            return 0; // return Failure
        }
        byte[] copyb = new byte[b.length];
        this.in.read(copyb);

        int count = 0;
        int index = 0;

        while(count < 6){
            if(getRealVal(copyb[index]) < 255){
                count ++;
            }
            b[index] = copyb[index];
            index++;
        }

        int[] arrayOfBits = getBitsFromByte(copyb[index]);
        int numberOfBit = 0;
        Integer one = 1;

        for(int i= index; i < b.length; i++)
        {
            if (numberOfBit == 8) {
                index++;
                arrayOfBits = getBitsFromByte(copyb[index]);
                numberOfBit = 0;
            }
            if(arrayOfBits[numberOfBit] == 1) {
                b[i] = one.byteValue();
            }
            numberOfBit++;
        }
        return 1; // return Success
    }
}
