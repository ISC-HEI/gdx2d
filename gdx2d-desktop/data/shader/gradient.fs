// A simple gradient that spans the entire shader
// Pierre-André Mudry, 2014

/**
 * Stores the dimension of the size of the whole screen.
 * It normally set at the beginning, once
 */
uniform vec2 resolution;

void main() {
	// position of the fragment, normalized to [0..1]
	vec2 position = ( gl_FragCoord.xy / resolution.xy );
	
	// the color of the fragment depends on its position, linearly  
	gl_FragColor = vec4(0, 0, position.y, 1.0);
}
