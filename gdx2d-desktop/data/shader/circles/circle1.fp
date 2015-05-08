// Draw a very simple disc in the center of the screen
// mui, 2014-2015
#pragma debug(on)
#pragma optimize(on)

precision mediump float;

// What we get from Java, *once*
uniform vec2 center;

/**
 * Received from vertex shader
 */

// The interpolated color of each fragment before transform
in vec4 v_color;

/**
 * Produced by the fragment shader
 */
out vec4 o_fragColor; // Each fragment color

void main() {
	// compute the distance between fragment and mouse position
	float dist = distance(gl_FragCoord.xy, center);

	// if the distance is bigger than a radius, discard the pixel
	// this corresponds to saying to the shader, "let's not draw this fragment (pixel)"
	if(dist > 50.0)
		discard;

	// if pixel hasn't been discarded, paint it green 
	o_fragColor = vec4(0, 1, 0, 1.0);
}
