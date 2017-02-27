package nl.jochemkuijpers.raytracer.render;

import nl.jochemkuijpers.raytracer.color.LightMap;
import nl.jochemkuijpers.raytracer.math.Ray;
import nl.jochemkuijpers.raytracer.math.Vector2;
import nl.jochemkuijpers.raytracer.math.Vector3;

public class Camera {
    private final Vector3 position;
    private final Vector3 gaze;
    private final Vector3 up;
    private final double fov;

    /**
     * Creates a camera with a position in the scene. The camera is pointed in the direction of the gaze vector and the
     * camera's roll is adjusted such that the up vector points up in the camera's view.
     * <p>
     * The fov parameter specifies the fov in degrees (0 - 180 degrees).
     *
     * @param position scene position
     * @param gaze     view direction
     * @param up       up direction
     * @param fov      field of view (in degrees)
     */
    public Camera(Vector3 position, Vector3 gaze, Vector3 up, double fov) {
        this.position = position;
        this.gaze = gaze.normalized();
        this.up = up.normalized();
        this.fov = fov / 180 * Math.PI;
    }

    public LightMap render(Vector2 resolution, RayCaster rayCaster) {

        Ray[][] rays = createRays(resolution);

        for (int y = 0; y < rays.length; y++) {
            for (int x = 0; x < rays[y].length; x++) {
                rayCaster.cast(rays[y][x]);
            }
        }

        return createLightMap(rays);
    }



    private Ray[][] createRays(Vector2 resolution) {
        Ray[][] rays = new Ray[(int) resolution.y][(int) resolution.x];
        Vector3 horizon = gaze.cross(up).normalized();
        Vector3 vertical = gaze.cross(horizon).normalized();

        double hFov, vFov;

        if (resolution.x > resolution.y) {
            hFov = fov;
            vFov = fov * resolution.y / resolution.x;
        } else {
            hFov = fov * resolution.x / resolution.y;
            vFov = fov;
        }

        double sinHor = Math.sin(hFov);
        double sinVer = Math.sin(vFov);

        for (int y = 0; y < resolution.y; y++) {
            for (int x = 0; x < resolution.x; x++) {
                Vector3 direction = gaze
                        .add(horizon.multiply(((x / (resolution.x - 1)) * 2 - 1) * sinHor))
                        .add(vertical.multiply(((y / (resolution.y - 1)) * 2 - 1) * sinVer))
                        .normalized();

                rays[y][x] = new Ray(position, direction);
            }
        }

        return rays;
    }

    private LightMap createLightMap(Ray[][] rays) {
        LightMap lightMap = new LightMap(rays[0].length, rays.length);

        for (int y = 0; y < rays.length; y++) {
            for (int x = 0; x < rays[y].length; x++) {
                lightMap.setLight(x, y, rays[y][x].light);
            }
        }

        return lightMap;
    }
}
