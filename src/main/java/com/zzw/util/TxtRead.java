package com.zzw.util;

import com.zzw.entity.Ebook;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

public final class TxtRead{

    /**
     * nio读取txt文件
     * @param encode 为空时默认UTF-8
     * */
    public static Ebook nioTxtChapterMsgLists(File file, String encode, Ebook ebook) {
//        Assert.notNull(file);
//        Assert.notNull(txtSn);
        encode = encode == null ? "UTF-8" : encode;
        try {
            FileChannel fileChannel = new RandomAccessFile(file,"r").getChannel();
            ByteBuffer buffer = ByteBuffer.allocate((int) fileChannel.size());
            fileChannel.read(buffer);
            String line = null;
            int chapterCount = 0,beforeOffset = 0;
            List chapters = new ArrayList();//章节数组
            List nioOffsets = new ArrayList();//nio流操作时章节头的byte偏移量
            List titles = new ArrayList();
            for (int i = 0;i < buffer.capacity();i ++) {
                byte by = buffer.get(i);
                //读取到换行符（\r\n）
                if (by == '\n') {
                    i++;
                    //i - beforeOffset - 2 减2是为了去掉line末尾的\r\n 有\r\n时正则匹配错误
                    line = new String(buffer.array(),beforeOffset,i - beforeOffset - 2,encode);
                    if (line.matches("[\\s\\S]{0,15}第[\\S]{1,10}章[\\s\\S]{0,20}") || line.matches("全书完.*") || line.matches("剧终.*")) {
                        chapters.add(chapterCount++);
                        titles.add(line.replace(",","，").trim());
                        nioOffsets.add(beforeOffset);
                    }
                    beforeOffset = i;
                }
            }
//            TxtChapterMsgModel model = new TxtChapterMsgModel();
//            model.addChapter(chapters,null,titles,nioOffsets);
//            model.setTxtSn(txtSn);
            if(chapters.size()==0) {
                return null;
            }
            System.out.println(chapters);
            ebook.addChapter(chapters,null,titles,nioOffsets);
            return ebook;
        }catch (Exception e){
            e.printStackTrace();
            System.err.println("读取章节出错！！");
        }
        return null;
    }

    /**
     *  Stream读取txt文件
     * @return 读取失败返回null
     * */
    public static Ebook streamTxtChapterMsgLists(File file, String encode, Ebook ebook) {
//        Assert.notNull(file);
//        Assert.notNull(txtSn);
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file),encode));
            String line;
            int chapterCount = 0;
            long lineCount = 0;
            List chapters = new ArrayList();
            List offsets = new ArrayList();
            List titles = new ArrayList();
//            TxtChapterMsgModel model = new TxtChapterMsgModel();
            while ((line = reader.readLine()) != null) {
                if (line.matches("[\\s\\S]{0,15}第[\\S]{1,10}章[\\s\\S]{0,20}") || line.matches("全书完.*") || line.matches("剧终.*")) {
                    chapters.add(chapterCount++);
                    offsets.add(lineCount);
                    titles.add(line);
                }
                lineCount++;
            }
            ebook.addChapter(chapters,offsets,titles,null);
            return ebook;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
