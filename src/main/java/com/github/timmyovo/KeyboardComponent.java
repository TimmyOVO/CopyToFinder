package com.github.timmyovo;

import com.github.timmyovo.nspasteboard.NSPasteboardAPI;
import com.intellij.ide.DataManager;
import com.intellij.ide.IdeEventQueue;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.ApplicationComponent;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.wm.WindowManager;
import org.apache.commons.io.IOUtils;
import org.jetbrains.annotations.NotNull;

import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Objects;

public class KeyboardComponent implements ApplicationComponent {
    @NotNull
    public String getComponentName() {
        return "My On-Save Component";
    }

    public void initComponent() {
        try {
            byte[] bytes = IOUtils.toByteArray(getClass().getClassLoader().getResourceAsStream("libs/libNSPasteboardJNI.dylib"));
            File tempFile = File.createTempFile("libNSPasteboardJNI", ".dylib");
            Files.write(tempFile.toPath(), bytes);
            System.load(tempFile.getPath());
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        IdeEventQueue ideEventQueue = IdeEventQueue.getInstance();
        ideEventQueue.addPostEventListener(event -> {
            //System.out.println(event);
            if (event instanceof KeyEvent) {
                KeyEvent keyEvent = (KeyEvent) event;
                if (keyEvent.isMetaDown() && keyEvent.getKeyChar() == 'c' && keyEvent.getID() == KeyEvent.KEY_PRESSED) {
                    Arrays.stream(ProjectManager.getInstance().getOpenProjects())
                            .filter(project -> Objects.requireNonNull(WindowManager.getInstance().suggestParentWindow(project)).isActive())
                            .forEach(project -> {
                                DataManager.getInstance().getDataContextFromFocusAsync().onSuccess(dataContext -> {
                                    String path = dataContext.getData(PlatformDataKeys.VIRTUAL_FILE).getPath();
                                    NSPasteboardAPI.writeClipboardFileURL(path);
                                });
                            });
                    return true;
                }
            }
            return false;
        }, ApplicationManager.getApplication());
    }

    public void disposeComponent() {
    }
}
