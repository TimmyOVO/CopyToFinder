package com.github.timmyovo;

import com.intellij.openapi.components.ApplicationComponent;
import com.intellij.openapi.ide.CopyPasteManager;
import org.apache.commons.io.IOUtils;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class NativeLibraryLoaderComponent implements ApplicationComponent {
    @NotNull
    public String getComponentName() {
        return "Load-Native-Library-Component";
    }

    public void initComponent() {
        try {
            byte[] bytes = IOUtils.toByteArray(getClass().getClassLoader().getResourceAsStream("libs/libNSPasteboardJNI.dylib"));
            File tempFile = File.createTempFile("libNSPasteboardJNI", ".dylib");
            Files.write(tempFile.toPath(), bytes);
            System.load(tempFile.getPath());
        } catch (IOException e) {
            e.printStackTrace();
        }

        CopyPasteManager.getInstance().addContentChangedListener(new CopyPasteListener());
    }

    public void disposeComponent() {
    }
}
