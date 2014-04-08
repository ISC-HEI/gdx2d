// Draws a circle that can be moved with the mouse. The radius is also
// controllable.
// Pierre-André Mudry, 2014

uniform vec2 resolution; // Stores the dimension of the fragment shader
uniform vec2 mouse; // This information is given from the Java side. 
uniform float radius; // This information is given from the Java side

void main() {	
	// compute the distance between fragment and mouse position
	float dist = distance(gl_FragCoord.xy, mouse.xy);

	// if the distance is bigger than a radius, discard the pixel
	if(dist > radius) 
		discard;	

	// if pixel hasn't been discarded, paint it
	gl_FragColor = vec4(0, 1, 1, 1.0);  	
}
