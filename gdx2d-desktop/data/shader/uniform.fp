// The simplest fragment shader one can imagine, a single color

/**
 * Produced by the fragment shader
 */
out vec4 o_fragColor; // Each fragment color

void main() {
	o_fragColor = vec4(1.0, 0.0, 0.0, 1.0);
}
