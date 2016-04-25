// Draw a very simple disc in the center of the screen
// mui, 2014-2015

// What we get from Java, *once*
uniform vec2 center;
uniform vec2 resolution;

/**
 * Received from vertex shader
 */
in vec4 v_color; // The interpolated color of each fragment before transform

void main() {
	// compute the distance between fragment and mouse position
	float dist = distance(gl_FragCoord.xy, center);

	// if the distance is bigger than a radius, discard the pixel
	// this corresponds to saying to the shader, "let's not draw this fragment (pixel)"
	if(dist > 50.0)
		discard;

	// if pixel hasn't been discarded, paint it green 
	gl_FragColor = vec4(0, 1, 0, 1.0);
}