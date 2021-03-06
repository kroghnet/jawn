package net.javapla.jawn.server;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.fileupload.FileItem;

import net.javapla.jawn.core.uploads.FormItem;

public class ApacheFileItemFormItem implements FormItem {
    
    private FileItem fileItem;

    public ApacheFileItemFormItem(FileItem item) {
        this.fileItem = item;
    }

    @Override
    public String getName() {
        return fileItem.getName();
    }

    @Override
    public String getFieldName() {
        return fileItem.getFieldName();
    }

    @Override
    public boolean isFile() {
        return !fileItem.isFormField();
    }

    @Override
    public String getContentType() {
        return fileItem.getContentType();
    }

    @Override
    public InputStream openStream() throws IOException {
        return fileItem.getInputStream();
    }
    
    @Override
    public String toString() {
        return getStreamAsString();
    }

}
