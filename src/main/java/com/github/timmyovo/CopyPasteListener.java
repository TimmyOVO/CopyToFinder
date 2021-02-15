package com.github.timmyovo;

import com.github.timmyovo.nspasteboard.NSPasteboardAPI;
import com.intellij.ide.PsiCopyPasteManager;
import com.intellij.openapi.ide.CopyPasteManager;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.util.PsiUtilCore;
import org.jetbrains.annotations.Nullable;

import java.awt.datatransfer.Transferable;
import java.util.Arrays;
import java.util.Objects;

public class CopyPasteListener implements CopyPasteManager.ContentChangedListener {
    @Override
    public void contentChanged(@Nullable Transferable oldTransferable, Transferable newTransferable) {
        if (!(newTransferable instanceof PsiCopyPasteManager.MyTransferable)) {
            return;
        }
        PsiCopyPasteManager.MyTransferable myTransferable = (PsiCopyPasteManager.MyTransferable) newTransferable;
        writeFilesToClipboard(Arrays.stream(myTransferable.getElements())
                .filter(psiElement -> !CopyPasteManager.getInstance().isCutElement(psiElement))
                .map(PsiUtilCore::getVirtualFile)
                .filter(Objects::nonNull)
                .map(VirtualFile::getPath)
                .toArray(String[]::new));
    }

    void writeFilesToClipboard(String[] absPaths) {
        if (absPaths.length == 0) return;
        NSPasteboardAPI.writeClipboardFilesURL(absPaths);
    }
}
