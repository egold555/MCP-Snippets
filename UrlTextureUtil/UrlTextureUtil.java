package clientname.utils;

import java.awt.image.BufferedImage;

import org.apache.commons.io.FilenameUtils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IImageBuffer;
import net.minecraft.client.renderer.ImageBufferDownload;
import net.minecraft.client.renderer.ThreadDownloadImageData;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;

public class UrlTextureUtil
{
    public static void downloadAndSetTexture(String url, ResourceLocationCallback callback)
    {
       
        if (url != null && !url.isEmpty())
        {
            String baseName = FilenameUtils.getBaseName(url);
            final ResourceLocation resourcelocation = new ResourceLocation("clientname_temp/" + baseName);
            TextureManager texturemanager = Minecraft.getMinecraft().getTextureManager();
            ITextureObject itextureobject = texturemanager.getTexture(resourcelocation);

            if (itextureobject != null && itextureobject instanceof ThreadDownloadImageData)
            {
                ThreadDownloadImageData threaddownloadimagedata = (ThreadDownloadImageData)itextureobject;

                if (threaddownloadimagedata.imageFound != null)
                {
                    if (threaddownloadimagedata.imageFound.booleanValue())
                    {
                    	callback.onTextureLoaded(resourcelocation);
                    }

                    return;
                }
            }

            IImageBuffer iimagebuffer = new IImageBuffer()
            {
                ImageBufferDownload ibd = new ImageBufferDownload();
                public BufferedImage parseUserSkin(BufferedImage image)
                {
                    return image;
                }
                public void skinAvailable()
                {
                	callback.onTextureLoaded(resourcelocation);
                }
            };
            ThreadDownloadImageData threaddownloadimagedata1 = new ThreadDownloadImageData(null, url, null, iimagebuffer);
            threaddownloadimagedata1.pipeline = true;
            texturemanager.loadTexture(resourcelocation, threaddownloadimagedata1);
        }
    }
    
    public interface ResourceLocationCallback {
    	public void onTextureLoaded(ResourceLocation rl);
    }
    
}
