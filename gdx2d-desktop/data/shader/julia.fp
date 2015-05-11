// A nice Julia fractal
// Original version by ???
// Slightly modified

// What we get from Java, *once*
uniform vec2 center;
uniform vec2 resolution;
uniform sampler2D texture0;
uniform vec2 offset;
uniform float scale;

/**
 * Received from vertex shader
 */
in vec4 v_color; // The interpolated color of each fragment before transform
in vec2 vTexCoord;

/**
 * Produced by the fragment shader
 */
out vec4 o_fragColor; // Each fragment color

const int iter = 1000;

void main() {
    vec2 z;
    z.x = 2.0 * (vTexCoord.x - 0.5) * scale + offset.x;
    z.y = 2.0 * (vTexCoord.y - 0.5) * scale + offset.y;

    int i;
    for(i=0; i<iter; i++) {
        float x = (z.x * z.x - z.y * z.y) + center.x;
        float y = (z.y * z.x + z.x * z.y) + center.y;

        if((x * x + y * y) > 4.0) break;
        z.x = x;
        z.y = y;
    }

    o_fragColor = texture2D(texture0, vec2((i == iter ? 0.0 : float(i)) / 100.0), 0);
}
	