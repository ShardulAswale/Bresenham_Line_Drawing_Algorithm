# Bresenham Line Drawing

Java OpenGL exercise drawing polygon outlines with a Bresenham-style line routine.

## How it works

The algorithm advances along the dominant axis and updates an error term to select the next raster point. The scene combines thin, thick and dotted polygon edges.

## Usage

Requires a Java Development Kit, a desktop display and a legacy JOGL installation compatible with the `javax.media.opengl` API. Configure the JOGL JARs and native libraries in the Java classpath, then compile `BresenhamLine.java` and run `Bresenham.BresenhamLine`.

## Notes

Compile with an output directory that preserves package `Bresenham`. Polygon positions, sizes and styles are configured in the source; dotted edges use a separate incremental-coordinate routine.
