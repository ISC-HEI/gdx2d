uniform sampler2D u_texture;
uniform vec2 resolution;
uniform float time;

//"in" varyings from our vertex shader
varying vec4 vColor;
varying vec2 vTexCoord;

const float RADIUS = 0.3; 			
const float SOFTNESS = 0.25; 

void main() {
	//sample the texture
	vec4 texColor = texture2D(u_texture, vTexCoord);
		
	// determines the center of the screen
	vec2 center = (gl_FragCoord.xy / resolution.xy) - vec2(0.5, 0.5);
	
	//determine the vector length from center
	float len = length(center);
	
	float r = abs(sin(time) * RADIUS) + 0.3; // Change the radius with time	
	float vignette = smoothstep(r, r - SOFTNESS, len);
	
	//apply our vignette
	texColor.rgb *= vignette;
	
	//final color
	gl_FragColor = texColor;
}

