package nl.jochemkuijpers.raytracer.render;

import nl.jochemkuijpers.raytracer.math.Ray;
import nl.jochemkuijpers.raytracer.math.Vector;
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
        this.gaze = gaze.normalize();
        this.up = up.normalize();
        this.fov = fov / 180 * Math.PI;
    }

    /**
     * Renders the current scene to the sensor image.
     *
     * @param sensorImage sensor image to render to
     * @param rayCaster   raycaster to cast rays
     */
    public void render(SensorImage sensorImage, RayCaster rayCaster) {
        double sinHor, sinVer, cosFov;
        sinHor = Math.sin(fov / 2);
        sinVer = sinHor;
        cosFov = Math.cos(fov / 2);

        Vector3 viewPlaneOrigin = Vector.multiply(gaze, cosFov, new Vector3());
        Vector3 viewPlaneHorizontal = Vector.cross(gaze, up, new Vector3()).normalize();
        Vector3 viewPlaneVertical = Vector.cross(gaze, viewPlaneHorizontal, new Vector3()).normalize();

        if (sensorImage.getWidth() > sensorImage.getHeight()) {
            sinVer *= (double) sensorImage.getHeight() / (double) sensorImage.getWidth();
        } else {
            sinHor *= (double) sensorImage.getWidth() / (double) sensorImage.getHeight();
        }

        Ray primaryRay = new Ray(position, new Vector3());

        for (int y = 0; y < sensorImage.getHeight(); y++) {
            for (int x = 0; x < sensorImage.getWidth(); x++) {
                primaryRay.light.set(0, 0, 0);
                primaryRay.direction
                        .set(viewPlaneOrigin)
                        .addMultiple(viewPlaneHorizontal, (((x + 0.5) / sensorImage.getWidth() ) * 2 - 1) * sinHor)
                        .addMultiple(viewPlaneVertical,   (((y + 0.5) / sensorImage.getHeight()) * 2 - 1) * sinVer)
                        .normalize();

                sensorImage.setLight(x, y, rayCaster.cast(primaryRay));
            }
        }
    }
}
