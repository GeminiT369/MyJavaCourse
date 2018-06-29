package Bean.Msg;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class FileMsg extends Message {

    private File file;

    static DecimalFormat df;
    static {
        df=new DecimalFormat("#0.0");
        df.setRoundingMode(RoundingMode.HALF_UP);
        df.setMinimumFractionDigits(1);
        df.setMaximumFractionDigits(1);
    }

    public FileMsg(String src,String dst,File file,boolean isGMsg){
        super(src, dst,isGMsg);
        this.file=file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public File getFile() {
        return file;
    }

    public void receiveFile(File dir) throws IOException {
        File save=new File(dir,file.getName());
        FileInputStream fi=new FileInputStream(file);
        FileOutputStream fo=new FileOutputStream(save);
        byte[] buff=new byte[1024];
        int count=fi.read(buff,0,buff.length);
        while (count!=-1){
            fo.write(buff,0,count);
            count=fi.read(buff,0,buff.length);
        }
        fi.close();
        fo.close();
        System.out.println("File Saved at "+dir.toString()+"!");
    }

    private String getFormatFileSize(long length){
        double size=((double)length)/(1<<30);
        if (size>=1)
            return df.format(size) +"GB";
        size=((double)length)/(1<<20);
        if (size>=1)
            return df.format(size) + "MB";
        size=((double)length)/(1<<10);
        if (size>=1)
            return df.format(size) + "KB";
        return length+"B";
    }

    @Override
    public String toString() {
        return "From "+src+" To "+dst+" File: "+file.getName();
    }

    @Override
    public String getMsg() {
        return src+"\t: "+file.getName();
    }
}
