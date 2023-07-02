package com.github.timmyovo;

import com.intellij.openapi.components.ApplicationComponent;
import com.intellij.openapi.ide.CopyPasteManager;
import org.apache.commons.io.IOUtils;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public class NativeLibraryLoaderComponent implements ApplicationComponent {

    enum Arch {
        X86_64("x86_64"),
        AARCH64("aarch64");
        final String suffix;

        Arch(String suffix) {
            this.suffix = suffix;
        }
    }

    @NotNull
    public String getComponentName() {
        return "Load-Native-Library-Component";
    }

    public void initComponent() {
        try {
            Arch arch = getArch();
            InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream(getLibraryPathByArch());
            if (resourceAsStream == null) {
                throw new RuntimeException("Can not find native library: " + getLibraryPathByArch());
            }
            byte[] bytes = IOUtils.toByteArray(resourceAsStream);
            File tempFile = File.createTempFile(String.format("%s_%s", "libNSPasteboardJNI", arch.suffix), ".dylib");
            Files.write(tempFile.toPath(), bytes);
            System.load(tempFile.getPath());
        } catch (IOException e) {
            e.printStackTrace();
        }

        CopyPasteManager.getInstance().addContentChangedListener(new CopyPasteListener());
    }

    public String getLibraryPathByArch() {
        Arch arch = getArch();
        if (arch.equals(Arch.X86_64)) {
            return "libs/x86_64/libNSPasteboardJNI.dylib";
        } else if (arch.equals(Arch.AARCH64)) {
            return "libs/aarch64/libNSPasteboardJNI.dylib";
        } else {
            throw new RuntimeException("Unsupported arch: " + arch);
        }
    }

    Arch getArch() {
        String arch = System.getProperty("os.arch");
        if (arch.equals("x86_64")) {
            return Arch.X86_64;
        } else if (arch.equals("aarch64")) {
            return Arch.AARCH64;
        } else {
            throw new RuntimeException("Unsupported arch: " + arch);
        }
    }

    public void disposeComponent() {
    }
}
