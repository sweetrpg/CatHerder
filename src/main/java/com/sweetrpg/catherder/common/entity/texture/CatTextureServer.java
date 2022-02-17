package com.sweetrpg.catherder.common.entity.texture;

import com.google.common.hash.Hashing;
import com.sweetrpg.catherder.CatHerder;
import com.sweetrpg.catherder.common.util.Util;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.server.ServerLifecycleHooks;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import javax.annotation.Nullable;
import java.io.*;

public class CatTextureServer {

    public static final CatTextureServer INSTANCE = new CatTextureServer();

    /**
     * Get the dedicated servers cache location
     */
    public File getServerFolder() {
        return new File(ServerLifecycleHooks.getCurrentServer().getServerDirectory(), "cat_skins");
    }

    @Nullable
    public byte[] getCachedBytes(File baseFolder, String hash) {
        InputStream stream = getCachedStream(baseFolder, hash);
        try {
            return stream != null ? IOUtils.toByteArray(stream) : null;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(stream);
        }

        return null;
    }

    @Nullable
    public InputStream getCachedStream(File baseFolder, String hash) {
        File cacheFile = getCacheFile(baseFolder, hash);

        if (cacheFile.isFile() && cacheFile.exists()) {
            try {
                FileInputStream stream = new FileInputStream(cacheFile);
                CatHerder.LOGGER.debug("Loaded cat texture from local cache ({})", cacheFile);
                return stream;
            } catch (FileNotFoundException e) {
                CatHerder.LOGGER.debug("Failed to load cat texture from local cache ({})", cacheFile);
                e.printStackTrace();
            }
        }

        return null;
    }

    public String getHash(byte[] targetArray) {
        return Hashing.sha1().hashBytes(targetArray).toString();
    }

    public File getCacheFile(File baseFolder, String name) {
        File subFolder = new File(baseFolder, name.length() > 2 ? name.substring(0, 2) : "xx");
        File cacheFile = new File(subFolder, name);
        return cacheFile;
    }


    public ResourceLocation getResourceLocation(String name) {
        return Util.getResource("catskins/" + name);
    }

    /**
     * Normally used to save server side
     */
    public boolean saveTexture(File baseFolder, byte[] stream) throws IOException {
        String hash = getHash(stream);
        File cacheFile = getCacheFile(baseFolder, hash);

        if (!cacheFile.isFile()) {
            CatHerder.LOGGER.debug("Saved cat texture to local cache ({})", cacheFile);
            FileUtils.writeByteArrayToFile(cacheFile, stream);
            return true;
        }

        CatHerder.LOGGER.debug("Server already has cache for cat texture ({})", cacheFile);
        return false;
    }
}
