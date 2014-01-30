// Draw a very simple disc in the center of the screen
// Pierre-André Mudry, 2014

uniform vec2 resolution; // Stores the dimension of the fragment shader
uniform vec2 center; // This information is given from the Java side. 

void main() {	
	// compute the distance between fragment and mouse position	
	float dist = distance(gl_FragCoord.xy, center);

	// if the distance is bigger than a radius, discard the pixel
	// this corresponds to saying to the shader, "let's not draw this fragment (pixel)"
	if(dist > 50) 
		discard;	

	// if pixel hasn't been discarded, paint it green 
	gl_FragColor = vec4(0, 1, 0, 1.0);  	
}
