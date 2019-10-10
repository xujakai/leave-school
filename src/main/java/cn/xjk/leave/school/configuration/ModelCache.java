package cn.xjk.leave.school.configuration;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public enum ModelCache {
    MODEL;
    private ByteArrayOutputStream baos = new ByteArrayOutputStream();

    ModelCache() {
        InputStream model = Thread.currentThread().getContextClassLoader().getResourceAsStream("model.xlsx");
        try {
            byte[] buffer = new byte[1024];
            int len;
            while ((len = model.read(buffer)) > -1) {
                baos.write(buffer, 0, len);
            }
            baos.flush();
        } catch (IOException ex) {
            System.err.println("init model failed system exit");
            System.exit(500);
        }
    }

    public InputStream getModel() {
        return new ByteArrayInputStream(baos.toByteArray());
    }
}
