package cn.xjk.leave.school.model;

import lombok.NonNull;
import org.springframework.web.multipart.MultipartFile;

import java.beans.ConstructorProperties;
import java.io.*;
import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 *
 * @author 张骥
 * @date 2019/6/13
 * Description:
 */
public class ByteArrayMultipartFile implements MultipartFile {
    private final String name;
    private final String originalFilename;
    private final String contentType;
    @NonNull
    private final byte[] bytes;

    public boolean isEmpty() {
        return this.bytes.length == 0;
    }

    public long getSize() {
        return (long)this.bytes.length;
    }

    public InputStream getInputStream() {
        return new ByteArrayInputStream(this.bytes);
    }

    public void transferTo(File destination) throws IOException {
        FileOutputStream outputStream = new FileOutputStream(destination);

        try {
            outputStream.write(this.bytes);
        } finally {
            outputStream.close();
        }

    }

    @ConstructorProperties({"name", "originalFilename", "contentType", "bytes"})
    public ByteArrayMultipartFile(String name, String originalFilename, String contentType, @NonNull byte[] bytes) {
        if (bytes == null) {
            throw new NullPointerException("bytes");
        } else {
            this.name = name;
            this.originalFilename = originalFilename;
            this.contentType = contentType;
            this.bytes = bytes;
        }
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getOriginalFilename() {
        return this.originalFilename;
    }

    @Override
    public String getContentType() {
        return this.contentType;
    }

    @Override
    public byte[] getBytes() {
        return this.bytes;
    }


    public String toString() {
        return "ByteArrayMultipartFile(name=" + this.getName() + ", originalFilename=" + this.getOriginalFilename() + ", contentType=" + this.getContentType() + ", bytes=" + Arrays.toString(this.getBytes()) + ")";
    }
}

