
package org.third.netty.http2.tiles;

import static io.netty.buffer.Unpooled.unreleasableBuffer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.third.netty.http2.Http2ExampleUtil;

import io.netty.buffer.ByteBuf;

/**
 * Caches the images to avoid reading them every time from the disk.
 */
public final class ImageCache {

    public static ImageCache INSTANCE = new ImageCache();

    private final Map<String, ByteBuf> imageBank = new HashMap<String, ByteBuf>(200);

    private ImageCache() {
        init();
    }

    public static String name(int x, int y) {
        return "tile-" + y + "-" + x + ".jpeg";
    }

    public ByteBuf image(int x, int y) {
        return imageBank.get(name(x, y));
    }

    private void init() {
        for (int y = 0; y < 10; y++) {
            for (int x = 0; x < 20; x++) {
                try {
                    String name = name(x, y);
                    ByteBuf fileBytes = unreleasableBuffer(Http2ExampleUtil.toByteBuf(getClass()
                            .getResourceAsStream(name)).asReadOnly());
                    imageBank.put(name, fileBytes);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
