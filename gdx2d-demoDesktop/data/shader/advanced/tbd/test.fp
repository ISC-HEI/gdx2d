uniform sampler2D backbuffer0;
uniform sampler2D backbuffer2;
uniform vec2 resolution;
uniform float time;

//"in" varyings from our vertex shader
varying vec4 vColor;
varying vec2 vTexCoord;

void main() {
	//sample the texture
	vec2 position = ( gl_FragCoord.xy / resolution.xy );
	vec4 texColor = texture2D(backbuffer2, position);
		
	// determines the center of the screen
	vec2 center = (gl_FragCoord.xy / resolution.xy) - vec2(0.5, 0.5);
	
	//determine the vector length from center
	float len = length(center);
	
	texColor.rgba += 0.01;
	
	//final color
	gl_FragColor = texColor;
}

