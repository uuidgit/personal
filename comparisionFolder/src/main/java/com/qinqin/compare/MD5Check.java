package com.qinqin.compare;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Check {
/**
* 默认的密码字符串组合，用来将字节转换成 16 进制表示的字符,apache校验下载的文件的正确性用的就是默认的这个组合
*/
    protected char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
    protected  MessageDigest messagedigest = null;

    {
        try {
            messagedigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public String getFileMD5String(File file) throws IOException {
        InputStream fis;
        fis = new FileInputStream(file);
        byte[] buffer = new byte[1024];
        int numRead = 0;
        while ((numRead = fis.read(buffer)) > 0) {
            messagedigest.update(buffer, 0, numRead);
        }
        fis.close();
        return bufferToHex(messagedigest.digest());
    }

    public String getFileMD5String(InputStream in) throws IOException {
        byte[] buffer = new byte[1024];
        int numRead = 0;
        while ((numRead = in.read(buffer)) > 0) {
            messagedigest.update(buffer, 0, numRead);
        }
        in.close();
        return bufferToHex(messagedigest.digest());
    }

    private String bufferToHex(byte bytes[]) {
        return bufferToHex(bytes, 0, bytes.length);
    }

    private String bufferToHex(byte bytes[], int m, int n) {
        StringBuffer stringbuffer = new StringBuffer(2 * n);
        int k = m + n;
        for (int l = m; l < k; l++) {
            appendHexPair(bytes[l], stringbuffer);
        }
        return stringbuffer.toString();
    }

    private void appendHexPair(byte bt, StringBuffer stringbuffer) {
        char c0 = hexDigits[(bt & 0xf0) >> 4];// 取字节中高 4 位的数字转换
        // 为逻辑右移，将符号位一起右移,此处未发现两种符号有何不同
        char c1 = hexDigits[bt & 0xf];// 取字节中低 4 位的数字转换
        stringbuffer.append(c0);
        stringbuffer.append(c1);
    }
    
    public static void main(String[] args) {
    	File srcFile = new File("F:\\testF\\OLD\\world.txt");
    	File srcFile1 = new File("F:\\testF\\OLD\\tes.txt");
    	File destFile1 = new File("F:\\testF\\NEW\\world.txt");
    	try {
			System.out.println(new MD5Check().getFileMD5String(srcFile));
			System.out.println(new MD5Check().getFileMD5String(srcFile1));
			System.out.println(new MD5Check().getFileMD5String(destFile1));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}


