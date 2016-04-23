// Color spectrum that moves with time
// Pierre-André Mudry, 2014

// What we get from Java, *once*
// The current time
uniform float time;
uniform vec2 resolution;

/**
 * Received from vertex shader
 */
in vec4 v_color; // The interpolated color of each fragment before transform
in vec2 vSurfacePosition;

void main( void ) {
	// position of the fragment, normalized to [0..1]	
	vec2 position = ( gl_FragCoord.xy / resolution.xy );
	
	// color that moves with time
	vec3 col = vec3( (1.0 + sin(position.x)) / 2.0, 
					 (1.0 + sin(position.y)) / 2.0,
					 (1.0 + sin(time)) / 2.0);	
	gl_FragColor = vec4(col, 1.0 );
}
