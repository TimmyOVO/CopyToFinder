package com.github.timmyovo;

import com.intellij.openapi.components.ApplicationComponent;
import org.apache.commons.io.IOUtils;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class KeyboardComponent implements ApplicationComponent {
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

//        IdeEventQueue ideEventQueue = IdeEventQueue.getInstance();
//        ideEventQueue.addPostEventListener(event -> {
//            if (event instanceof KeyEvent) {
//                KeyEvent keyEvent = (KeyEvent) event;
//                if (keyEvent.isMetaDown() && keyEvent.getKeyChar() == 'c' && keyEvent.getID() == KeyEvent.KEY_PRESSED) {
//                    List<Project> collect = Arrays.stream(ProjectManager.getInstance().getOpenProjects())
//                            .filter(project -> Objects.requireNonNull(WindowManager.getInstance().suggestParentWindow(project)).isActive())
//                            .collect(Collectors.toList());
//                    for (Project project : collect) {
//                        project.getComponent(jlist)
//                    }
//                    if (collect.stream().map(FileEditorManager::getInstance)
//                            .anyMatch(fileEditorManager -> Arrays.stream(fileEditorManager.getAllEditors()).anyMatch(fileEditor -> fileEditor.getPreferredFocusedComponent().isFocusOwner()))) {
//                        return false;
//                    }
//
//
//                    collect
//                            .forEach(project -> {
//                                DataManager.getInstance().getDataContextFromFocusAsync().onSuccess(dataContext -> {
//                                    String path = dataContext.getData(PlatformDataKeys.VIRTUAL_FILE).getPath();
//                                    NSPasteboardAPI.writeClipboardFileURL(path);
//                                });
//                            });
//                    return false;
//                }
//            }
//            return false;
//        }, ApplicationManager.getApplication());
    }

    public void disposeComponent() {
    }
}
