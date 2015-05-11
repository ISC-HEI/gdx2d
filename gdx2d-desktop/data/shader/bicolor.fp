// A simple gradient that spans the entire resolution

// What we get from Java, *once*
uniform vec2 resolution;

/**
 * Received from vertex shader
 */
in vec4 v_color; // The interpolated color of each fragment before transform
in vec2 vSurfacePosition;

out vec4 o_fragColor; // Each fragment color

void main() {
	vec2 position = (vSurfacePosition.xy + (gl_FragCoord.xy / resolution.xy));  
	o_fragColor = vec4(0, position.x, position.y, 1.0);
}