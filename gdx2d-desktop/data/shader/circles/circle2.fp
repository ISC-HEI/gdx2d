// Draws a circle that can be moved with the mouse. The radius is also
// controllable.
// Pierre-André Mudry, 2014

#version 130
#pragma debug(on)
#pragma optimize(on)

precision mediump float;

// Information given from the Java side.
uniform float radius;
uniform vec2 resolution;
uniform vec2 mouse;

out vec4 o_fragColor; // Each fragment color

void main() {	
	// compute the distance between fragment and mouse position
	float dist = distance(gl_FragCoord.xy, mouse.xy);

	// if the distance is bigger than a radius, discard the pixel
	if(dist > radius)
		discard;

	// if pixel hasn't been discarded, paint it
	o_fragColor = vec4(1, 1, 1, 1.0);
}
