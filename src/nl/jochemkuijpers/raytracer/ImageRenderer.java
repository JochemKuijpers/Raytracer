package nl.jochemkuijpers.raytracer;

import nl.jochemkuijpers.raytracer.color.ColorStrategy;
import nl.jochemkuijpers.raytracer.color.LightMap;

import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;

public class ImageRenderer {
    private final ColorStrategy colorStrategy;

    public ImageRenderer(ColorStrategy colorStrategy) {
        this.colorStrategy = colorStrategy;
    }

    /**
     * Render the image from the light map
     *
     * @return image from the light map
     */
    public RenderedImage render(LightMap lightMap) {
        return render(lightMap, 1);
    }

    /**
     * Render the image with a given supersampling factor from the light map
     *
     * @param ssFactor super sampling factor
     * @return image from the light map
     */
    public RenderedImage render(LightMap lightMap, int ssFactor) {
        int width = lightMap.getWidth();
        int height = lightMap.getHeight();

        if (width % ssFactor != 0 || height % ssFactor != 0) {
            throw new RuntimeException("lightmap resolution is not a multiple of the super sampling factor given");
        }

        BufferedImage image = new BufferedImage(
                lightMap.getWidth() / ssFactor,
                lightMap.getHeight() / ssFactor,
                BufferedImage.TYPE_INT_ARGB
        );

        colorStrategy.reset();
        for (int y = 0; y < height / ssFactor; y++) {
            for (int x = 0; x < width / ssFactor; x++) {
                image.setRGB(x, y, colorStrategy.color(lightMap, x * ssFactor, y * ssFactor, ssFactor));
            }
        }

        return image;
    }
}
