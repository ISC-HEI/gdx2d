// Draw a very simple circle in the center of the screen
// Pierre-André Mudry, 2014

uniform vec2 resolution; // stores the dimension of the fragment shader

void main() {
	vec2 position = gl_FragCoord.xy / resolution.xy;
	float dist = distance(position, vec2(0.5, 0.5));

	if(dist > 0.2) 
		discard;	

	gl_FragColor = vec4(0, 1, 0, 1.0);  	
}
