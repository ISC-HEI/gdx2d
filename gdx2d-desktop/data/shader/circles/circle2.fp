// Draws a circle that can be moved with the mouse. The radius is also
// controllable.
// mui, 2014-2015

// What we get from Java, *once*
uniform float radius;
uniform vec2 resolution;
uniform vec2 mouse;

/**
 * Received from vertex shader
 */
in vec4 v_color; // The interpolated color of each fragment before transform

void main() {	
	// compute the distance between fragment and mouse position
	float dist = distance(gl_FragCoord.xy, mouse.xy);

	// if the distance is bigger than a radius, discard the pixel
	if(dist > radius)
		discard;

	// if pixel hasn't been discarded, paint it
	gl_FragColor = vec4(1, 1, 1, 1.0);
}
