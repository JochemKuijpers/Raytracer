# Raytracer

This is a private project where I attempt to create a raytracing program that takes a scene file and outputs a PNG image.
I try to make performant code, but focus is not on absolute performance.

# Status

## Current features:

- Tracing intersections with spheres
- Colored objects
- Directional lighting with shadow casting
- Reflection
- Refraction
- Gamma correction
- Automatic 'exposure' based on the received light values
- Dithering to prevent color banding (on 8-bit color channels)

## Interested in implementing (in no particular order):

- Point lights
- Area lights
- Supersampling
- Scene file import
- Other shapes; cuboids, planes, donuts, cones, pyramids, prisms, etc.
- Frensel calculations for more accurate reflection/refraction
- Multithreaded raycasting workers
- Indirect lighting
- Depth of field
- Chromatic aberration on refractions (slight variations in refractive index based on light wavelength)
- Likely even more...
